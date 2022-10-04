package project.together.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.together.dao.OrganizationDao;
import project.together.dao.ServantDao;
import project.together.dto.Organization;
import project.together.dto.Servant;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Getter
@ToString
@AllArgsConstructor
public class NaverLoginService {
    private final ServantDao servantDao;
    private final OrganizationDao organizationDao;

    public ResponseEntity<?> getUserInfo(String access_Token) {
        System.out.println(access_Token);
        String requestURL = "https://openapi.naver.com/v1/nid/me";

        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            //전송할 Header 작성.  Authorization = Bearer{access_Token}
            connection.setRequestProperty("Authorization", "Bearer " + access_Token);

            //응답코드 확인용
//            int responseCode = connection.getResponseCode();

            //Response 메세지 읽어오기.
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            br.close();

            JSONObject userInfo = new JSONObject(result).getJSONObject("response");

            boolean check = flag(userInfo);

            //Response Body 에 전달할 데이터 가공.
            Map<String,Object> resServant= new HashMap<>();
            Map<String,Object> resOrganization= new HashMap<>();

            if(check){
                Servant servant = servantDao.findById(userInfo.getString("id"));
                resServant.put("userInfo",servant);
                resServant.put("Role","Servant");
                return ResponseEntity.status(HttpStatus.OK).body(resServant);
            } else {
                //내부에서 체크.
                Organization organization = processOrganization(userInfo);
                resOrganization.put("userInfo",organization);
                resOrganization.put("Role","Organization");
                return ResponseEntity.status(HttpStatus.OK).body(resOrganization);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //에러 핸들링할 클래스 생성 필요
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error Message : 로그인 실패!");
    }

    public Organization processOrganization(JSONObject userInfo){
        Organization organization = new Organization();
        //ID 설정
        organization.setOrgId(userInfo.getString("id"));
        //Gender 설정
        if (userInfo.getString("gender").equals("M")) organization.setOrgManGender(0); //남자 0, 여자면 1
        else organization.setOrgManGender(1);

        //이름 설정
        organization.setOrgManName(userInfo.getString("name"));
        //Email 설정
        organization.setOrgEmail(userInfo.getString("email"));
        //Mobile 설정
        organization.setOrgManMobile(userInfo.getString("mobile"));
        //Birth 설정
        Date birthday = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            birthday = format.parse(userInfo.getString("birthyear") + userInfo.getString("birthday").replace("-", ""));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        organization.setOrgManBirth(birthday);
        //sns 설정
        organization.setOrgSns("NAVER");

        //db에 저장된 data 가 있는지 확인하기 위한 checkOrganization
        Organization checkOrganization = new Organization();

        //id 로 db 조회
        checkOrganization = organizationDao.findById(organization.getOrgId());

        if(checkOrganization == null){
            organizationDao.createOrganization(organization);
            return organization;
        }else{
            return checkOrganization;
        }
    }

    //access_token 을 이용한 사용자 정보 조회
    public boolean flag(JSONObject userInfo) {
        //최초가입시 데이터 저장을 위한 Servant 객체.
        Servant user = new Servant();

        //ID 설정
        user.setSerId(userInfo.getString("id"));
        //Gender 설정
        if (userInfo.getString("gender").equals("M")) user.setSerGender(0); //남자 0, 여자면 1
        else user.setSerGender(1);

        //이름 설정
        user.setSerName(userInfo.getString("name"));
        //Email 설정
        user.setSerEmail(userInfo.getString("email"));
        //Mobile 설정
        user.setSerMobile(userInfo.getString("mobile"));

        //Birth 설정
        Date birthday = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            birthday = format.parse(userInfo.getString("birthyear") + userInfo.getString("birthday").replace("-", ""));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        user.setSerBirth(birthday);
        //sns 설정
        user.setSerSns("NAVER");

        //db에 저장된 data 가 있는지 확인하기 위한 checkServant
        Servant checkServant = new Servant();
        Organization checkOrganization = new Organization();

        //id 로 db 조회
        checkServant = servantDao.findById(user.getSerId());
        checkOrganization = organizationDao.findById(user.getSerId());

        if(checkServant == null && checkOrganization != null) { //기관 사용자 일때
            //insert 진행.
            return false;
        } else if(checkServant != null && checkOrganization == null) { // 봉사자 사용자 일때
            //id 로 해당 유저를 찾아 리턴.
            return true;
        } else{ //db 에 저장된 데이터가 없을 경우 -> 최초가입. both null.
            servantDao.createServant(user);
            return true;
        }
    }

}

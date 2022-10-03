package project.together.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
import java.util.Optional;

@Slf4j
@Service
@Getter
@ToString
@AllArgsConstructor
public class NaverLoginService {
    private final ServantDao servantDao;
    private final OrganizationDao organizationDao;


    //프론트 요청에서..
    //access_token 을 이용한 사용자 정보 조회
    public boolean process(JSONObject userInfo) {
        Servant user = new Servant();

        //ID 설정
        user.setSerId(userInfo.getString("id"));
        //Gender 설정
        if (userInfo.getString("gender").equals("M")) user.setSerGender(0); //남자 1, 여자면 0
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

        //db에 저장된 data 가 있는지 확인하기 위한 checkServant,checkOrganization
        Servant checkServant = new Servant();
        Organization checkOrganization = new Organization();

        //id 로 db 조회
        checkServant = servantDao.findById(user.getSerId());
//        checkOrganization = organizationDao.findById(user.getSerId());

        System.out.println(checkServant);

        if(checkServant == null || checkOrganization == null ) { //db 에 저장된 데이터가 없을 경우 -> 최초가입.
            //insert 진행.
            servantDao.createServant(user);
        } else {
            //db 에 data가 있을 경우? -> 최초가입이 아닐 경우..
            // 관리자 계정  or 봉사자 계정 리턴.
        }

        return true;
        }
    }

package project.together.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import project.together.dao.OrganizationDao;
import project.together.dao.ServantDao;
import project.together.dto.Organization;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Service
public class NaverLoginService {

    private final ServantDao servantDao;
    private final OrganizationDao organizationDao;

    private final String clientId;
    private final String clientSecret;

    public NaverLoginService( @Value("${spring.security.oauth2.client.registration.naver.client-id}")String clientId,
                              @Value("${spring.security.oauth2.client.registration.naver.client-secret}")String clientSecret,
                              ServantDao servantDao,OrganizationDao organizationDao){
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.servantDao = servantDao;
        this.organizationDao =organizationDao;
    }

    //프론트 요청에서..
    public String getToken(String code) {
        String access_Token = "";
        String refresh_Token = "";
        String id_Token = "";
        String requestURL = "https://nid.naver.com/oauth2.0/token";

        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST"); //카카오의 경우 POST 권장, 보호해야할 정보이기 때문.
            connection.setDoOutput(true); // 데이터 기록 알려주기

            //post 요청에 필요한 파라미터를 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append(String.format("&client_id=%s", this.clientId));
            sb.append(String.format("&client_secret=%s", this.clientSecret));
            sb.append(String.format("&code=%s", code)); // 로그인 요청에서 얻은 인증 코드로 Accuss_Token 요청.
            sb.append("&state=STATE_STRING");

            bw.write(sb.toString());
            bw.flush();

            //결과 코드 확인
            int responseCode = connection.getResponseCode();
            log.info("getToken responseCode : " + String.valueOf(responseCode));

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            log.info("getToken responseBody : " + result);


            //String type 만 생성시 초기화 가능.
            //String Builder 기 떄문에 다시 String 으로 바꿔줌. or result 또한 String 으로 선언 후 append 대신 += 이용.
            JSONObject responseBody = new JSONObject(String.valueOf(result));
            access_Token = responseBody.getString("access_token");
            refresh_Token = responseBody.getString("refresh_token");


            br.close();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }

    //access_token 을 이용한 사용자 정보 조회
    public String getUserInfo(String access_Token) {
        String requestURL = "https://openapi.naver.com/v1/nid/me";

        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            //전송할 Header 작성.  Authorization = Bearer{access_Token}
            connection.setRequestProperty("Authorization", "Bearer " + access_Token);

            //응답코드 확인
            int responseCode = connection.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //Resopnse 메세지 읽어오기.
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            System.out.println("resopnse body : " + result);

            String responseMessage = new JSONObject(String.valueOf(result)).getString("message");

            JSONObject userInfo = new JSONObject(String.valueOf(result)).getJSONObject("response");

            if(responseMessage.equals("success")){
            }

            br.close();

            //이메일 유효 여부 확인 - 유효하다면 해당 정보 받아와서 DB 저장 + 로그인 완료


            //DB 에서 ID 값으로 조회하여 해당 USER 가 있으면 로그인 처리.


            //IF 문 사용으로 insert() 호출 결과가 f 일 경우 어떻게 처리? 실 사용시 오류가 날만한 부분 생각해야할듯. naver 응답 실패말고는 없는거 같긴함.

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


}

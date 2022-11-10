package project.together.util;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import project.together.repository.OrganizationMapper;
import project.together.repository.ServantMapper;
import project.together.vo.Organization;
import project.together.vo.Servant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Component
public class UserUtil {
    private final ServantMapper servantMapper;
    private final OrganizationMapper organizationMapper;

    public JSONObject getUserInfo(JSONObject data) {
        String requestURL = "https://openapi.naver.com/v1/nid/me";
//                data.getString("requestUrl");
        JSONObject userInfo = null;
        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();


            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            //전송할 Header 작성.  Authorization = Bearer{access_Token}
            connection.setRequestProperty("Authorization", "Bearer " + data.get("access_token"));

            //응답코드 확인용
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                String line = "";
                String result = "";

                while ((line = br.readLine()) != null) {
                    result += line;
                }

                br.close();

                userInfo = new JSONObject(result).getJSONObject("response");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userInfo;
    }

    // 봉사자 및 기관 관리자가 db에 존재하는지 체크
    // 회원가입시에만 사용하여, 두 테이블에서 모두 NULL ( SELECT 결과가 NULL ) 일떄만 가입 처리.
    public boolean userFlag(String id) {
        Servant servant = servantMapper.findServantById(id);
        Organization organization = organizationMapper.findOrganizationById(id);

        return servant == null && organization == null;
    }
}

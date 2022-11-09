package project.together.util;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
public class SnsUtil {

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
}

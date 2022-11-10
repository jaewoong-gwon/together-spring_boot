package project.together.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.together.repository.OrganizationMapper;
import project.together.repository.ServantMapper;
import project.together.util.UserUtil;
import project.together.vo.Organization;
import project.together.vo.Servant;

import java.sql.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final ServantMapper servantMapper;
    private final OrganizationMapper organizationMapper;
    private final UserUtil userUtil;

    public Object login(JSONObject loginInfo) {
        JSONObject userInfo = userUtil.getUserInfo(loginInfo);
        log.info("userLogin - userInfo : {}", userInfo);
        String loginType = loginInfo.getString("loginType");

        if (loginType.equals("ser")) {
            return servantMapper.findServantById(userInfo.getString("id"));
        } else if (loginType.equals("org")) {
            return organizationMapper.findOrganizationById(userInfo.getString("id"));
        }
        return null;
    }

    @Transactional
    public Object signUp(JSONObject signUpInfo) {
        JSONObject userInfo = userUtil.getUserInfo(signUpInfo);

        String loginType = signUpInfo.getString("loginType");
        boolean flag = userUtil.userFlag(userInfo.getString("id"));
        log.info("flag : {}", flag);
        if (loginType.equals("ser") && flag) {
            Servant servant = servantMapper.findServantById(userInfo.getString("id"));
            log.info("ser : {}", servant);
            if (servant != null) return servant;
            else {
                servant = new Servant();

                // 식별값인 id 값 설정
                servant.setSerId(userInfo.getString("id"));

                // 이름 설정
                servant.setSerName(userInfo.getString("name"));

                    /*
                        네이버에서 birthYear 과 birthDay 를 구분해서 전송
                        이 둘을 합쳐 birthDay ( yyyy-mm-dd ) 로 구현.
                     */
                String year = userInfo.getString("birthyear");
                String day = userInfo.getString("birthday");
                Date birthDay = Date.valueOf(year + "-" + day);
                servant.setSerBirth(birthDay);

                    /*
                        성별 설정(TINYINT) : 남자면 0, 여자면 1
                        필수 동의항목이기 때문에 null 일 경우 구현 X.
                     */
                if (userInfo.getString("gender").equals("M")) servant.setSerGender(0); //남자 0, 여자면 1
                else servant.setSerGender(1);

                // 휴대폰 번호 설정
                servant.setSerMobile(userInfo.getString("mobile"));

                // 이메일 설정
                servant.setSerEmail(userInfo.getString("email"));

                    /*
                        회원가입 시 누적 봉사 횟수, 누적 봉사 시간의 경우
                        db 에서 default 값이 있으나, 미 설정시 java 에서는 null 로 저장된다
                        db 저장 후 따로 조회 없이 전달시 0 으로 넘기기 위해 0으로 세팅
                     */

                    /*
                         연동 SNS 설정, 현재의 경우 NAVER 만 구현한 상태이기 떄문에 네이버로 수동처리.
                         추후 연동 가능한 SNS 가 늘어나면, 리다이렉트 되는 URL 또는 사용하는 API 에 따라
                         SNS 를 구분하여 설정.
                     */

                servant.setSerSns("NAVER");
                servantMapper.createServant(servant);
                return servant;
            }
        } else if (loginType.equals("org") && flag) {
            Organization organization = organizationMapper.findOrganizationById(userInfo.getString("id"));
            if (organization != null) return organization;
            else {

                organization = new Organization();
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
                String year = userInfo.getString("birthyear");
                String day = userInfo.getString("birthday");
                Date birthDay = Date.valueOf(year + "-" + day);
                organization.setOrgManBirth(birthDay);

                //sns 설정
                organization.setOrgSns("NAVER");

                return organization;

            }
        }
        return null;
    }
        /*
            Error 를 핸들링할 클래스가 있으면 좋을것 같음
            Enum 으로 error type 선언.
            해당 type 에 따라 설정된 메세지 지정.
         */

    @Transactional
    public Organization signUpOrganization(Organization organization) {
        organization.setOrgCertified(0);
        if (organizationMapper.createOrganization(organization) > 0) return organization;

        return null;
    }

}
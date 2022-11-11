package project.together.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.together.repository.ManagerMapper;
import project.together.repository.OrganizationMapper;
import project.together.repository.ServantMapper;
import project.together.util.UserUtil;
import project.together.vo.Manager;
import project.together.vo.Organization;
import project.together.vo.Servant;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class testService {
    private final ServantMapper ServantMapper;
    private final OrganizationMapper organizationMapper;
    private final ManagerMapper managerMapper;
    private final UserUtil userUtil;

    @Transactional
    public Manager signUpAndLogin(JSONObject info) {
        JSONObject userInfo = userUtil.getUserInfo(info);

        log.info("managerSingUpAndLogin - userInfo : {}", userInfo);
        Manager manager = managerMapper.findManagerById(userInfo.getString("id"));
        boolean flag = userUtil.userFlag(info.getString("id"));
            /*
                 null : 회원가입 진행
                not null : 로그인 진행
           */
        if (manager == null && flag) {
            // db 조회 후 null 일 경우, 해당 참조변수에 접근 시 NPE 발생.
            manager = new Manager();

            manager.setManId(userInfo.getString("id"));

            manager.setManName(userInfo.getString("name"));

            manager.setManEmail(userInfo.getString("email"));

            manager.setManMobile(userInfo.getString("mobile"));

            manager.setManSns("NAVER");

            if (managerMapper.createManager(manager) > 0) return manager;
            else return null;
        } else return manager;
    }

    public List<Servant> testFindAllServant() {
        return ServantMapper.findAllServant();
    }

    public Servant findServantById(String serId) {
        Servant servant = ServantMapper.findServantById(serId);
        log.info("testService user : {}", servant);
        if (servant != null) return servant;
        else return null;
    }

    public int createServant(Servant user) {
        log.info("testService createServant : {}", user);
        return ServantMapper.createServant(user);
    }

    public int deleteServantById(String serId) {
        log.info("testService deleteServantById : {}", serId);
        return ServantMapper.deleteServantById(serId);
    }

    public List<Organization> FindAllOrganization() {
        return organizationMapper.findAllOrganization();
    }

    public Organization findOrganizationById(String orgId) {
        log.info("testService Organization : {}", orgId);
        return organizationMapper.findOrganizationById(orgId);
    }

    public int createOrganization(Organization org) {
        log.info("testService createOrganization : {}", org);
        return organizationMapper.createOrganization(org);
    }

    public int deleteOrganizationById(String ordId) {
        log.info("testService deleteOrganizationById : {}", ordId);
        return organizationMapper.deleteOrganizationById(ordId);
    }
}

package project.together.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.together.repository.ManagerMapper;
import project.together.repository.NoticeMapper;
import project.together.repository.OrganizationMapper;
import project.together.repository.ServantMapper;
import project.together.util.UserUtil;
import project.together.vo.Manager;
import project.together.vo.Notice;
import project.together.vo.Organization;
import project.together.vo.Servant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class ManagerService {
    private final ServantMapper servantMapper;
    private final OrganizationMapper organizationMapper;
    private final ManagerMapper managerMapper;
    private final NoticeMapper noticeMapper;
    private final UserUtil userUtil;

    @Transactional
    public Manager signUpAndLogin(JSONObject info) {
        JSONObject userInfo = userUtil.getUserInfo(info);

        log.info("managerSingUpAndLogin - userInfo : {}", userInfo);
        Manager manager = managerMapper.findManagerById(userInfo.getString("id"));
        boolean flag = userUtil.userFlag(userInfo.getString("id"));
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

    @Transactional
    public Notice createNotice(Notice notice) {
        notice.setNotId(0);
        log.info("createNotice : {}", notice);
        if (noticeMapper.createNotice(notice) > 0) return noticeMapper.findNoticeById(notice.getNotId());
        else return null;
    }

    @Transactional
    public Notice updateNotice(Notice notice) {
        log.info("updateNotice : {}", notice);
        if (noticeMapper.updateNotice(notice) > 0) return noticeMapper.findNoticeById(notice.getNotId());
        else return null;
    }

    @Transactional
    public Integer deleteNoticeById(Notice notice) {
        log.info("deleteNoticeById : {}", notice);
        return noticeMapper.deleteNoticeById(notice.getNotId());
    }

    public Notice findNoticeById(Notice notice) {
        return noticeMapper.findNoticeById(notice.getNotId());
    }

    public Map<String, Object> findAllUsers() {
        List<Servant> servantList = servantMapper.findAllServant();
        log.info("servantList : {}", servantList);
        List<Organization> organizationList = organizationMapper.findAllOrganization();
        log.info("organizationList : {}", organizationList);
        Map<String, Object> response = new HashMap<>();
        response.put("servant", servantList);
        response.put("organization", organizationList);

        return response;
    }

    public int certificateOrganization(Organization organization) {
        log.info("certificateOrganization : {}", organization);
        return organizationMapper.certificateOrganization(organization);
    }


}

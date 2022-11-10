package project.together.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.together.repository.ManagerMapper;
import project.together.repository.NoticeMapper;
import project.together.util.UserUtil;
import project.together.vo.Manager;
import project.together.vo.Notice;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ManagerService {
    private final ManagerMapper managerMapper;
    private final NoticeMapper noticeMapper;
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

    public List<Notice> findAllNotice() {
        return noticeMapper.findAllNotice();
    }

    public Notice findNoticeById(Notice notice) {
        log.info("findNoticeById : {}", notice);
        return noticeMapper.findNoticeById(notice.getNotId());
    }

    @Transactional
    public Notice createNotice(Notice notice) {
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


}

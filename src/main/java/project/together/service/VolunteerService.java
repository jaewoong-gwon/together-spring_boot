package project.together.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.together.repository.VolunteerMapper;
import project.together.vo.Volunteer;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class VolunteerService {
    private final VolunteerMapper volunteerMapper;

    @Transactional
    public Volunteer createVolunteer(Volunteer volunteer) {

        /*
            not null 을 대비하여, 초기화 해도 되는 값의 경우 0 으로 초기화
            ex ) 현재 모집 인원 등..
         */
        volunteer.setVolCurNumber(0);
        log.info("createVolunteer : {}", volunteer);
        int result = volunteerMapper.createVolunteer(volunteer);

        Volunteer resVolunteer = volunteerMapper.findVolunteerById(volunteer.getVolId());
        if (result > 0) {
            return resVolunteer;
        } else return null;
    }

    public Volunteer findVolunteerById(Volunteer volunteer) {
        log.info("findVolunteerById : {}", volunteer);
        return volunteerMapper.findVolunteerById(volunteer.getVolId());
    }

    public List<Volunteer> findAllVolunteer() {
        return volunteerMapper.findAllVolunteer();
    }

    public int findVolunteerCurNumber(int volId) {
        log.info("findVolunteerCurNumber : {}", volId);
        return volunteerMapper.findVolunteerCurNumber(volId);
    }


    @Transactional
    public int updateVolCurNumberById(Volunteer volunteer) {
        log.info("updateVolCurNumberById : {}", volunteer);
        /*
            update 실행 시 -> vol_cur_number
             1. 명시적으로 지정한 경우 : 지정한 숫자로 변경
             2. 지정 하지 않은 경우 : 봉사자가 봉사 신청 요청의 경우로, 현재 신청인원을 가져와서 + 1
         */
        if (volunteer.getVolCurNumber() == null) {
            int nowVolCurNumber = volunteerMapper.findVolunteerCurNumber(volunteer.getVolId());
            volunteer.setVolCurNumber(nowVolCurNumber + 1);
        }
        return volunteerMapper.updateVolCurNumberById(volunteer);
    }

    @Transactional
    public int deleteVolunteerById(Volunteer volunteer) {
        log.info("deleteVolunteerById : {}", volunteer);

        return volunteerMapper.deleteVolunteerById(volunteer.getVolId());
    }

}

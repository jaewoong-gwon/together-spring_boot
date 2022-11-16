package project.together.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.together.repository.VolunteerMapper;
import project.together.vo.Application;
import project.together.vo.Organization;
import project.together.vo.Servant;
import project.together.vo.Volunteer;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Service
public class VolunteerService {
    private final VolunteerMapper volunteerMapper;

    @Transactional
    public Volunteer createVolunteer(Volunteer volunteer) {
        /*
            NOT NULL 인 항목이 NULL 로 넘어오는지 CHECK
         */
        boolean checkNotNull = Stream.of(volunteer.getVolOrganization(), volunteer.getVolRecPeriod(),
                volunteer.getVolType(), volunteer.getVolLocation(), volunteer.getVolStTime(), volunteer.getVolEndTime(),
                volunteer.getVolTitle(), volunteer.getVolContent()).allMatch(Objects::nonNull);

        if (checkNotNull) {
            /*
           현재 모집 인원은 생성 시 0 (null)
           not null 을 대비하여, 0 으로 초기화
            */
            volunteer.setVolCurNumber(0);
            log.info("createVolunteer : {}", volunteer);
            int result = volunteerMapper.createVolunteer(volunteer);

            Volunteer resVolunteer = volunteerMapper.findVolunteerById(volunteer.getVolId());
            if (result > 0) {
                return resVolunteer;
            }
        }
        return null;
    }

    public Volunteer findVolunteerById(Volunteer volunteer) {
        log.info("findVolunteerById : {}", volunteer);
        return volunteerMapper.findVolunteerById(volunteer.getVolId());
    }

    public List<Volunteer> findAllVolunteer(Volunteer volunteer) {
        if (nullCheck(volunteer.getOrgName())) volunteer.setOrgName(null);
        if (nullCheck(volunteer.getVolType())) volunteer.setVolType(null);
        if (nullCheck(volunteer.getVolTitle())) volunteer.setVolTitle(null);

        log.info("convert String : {}", volunteer);
        return volunteerMapper.findAllVolunteer(volunteer);
    }

    public List<Volunteer> findAllApplicatedVolunteerByServantId(Servant servant) {
        return volunteerMapper.findAllApplicatedVolunteerByServantId(servant.getSerId());
    }

    public List<Volunteer> findVolunteerAddServant(Organization organization) {
        return volunteerMapper.findVolunteerAddServant(organization.getOrgId());
    }

    @Transactional
    public int deleteVolunteerById(Volunteer volunteer) {
        log.info("deleteVolunteerById : {}", volunteer);

        return volunteerMapper.deleteVolunteerById(volunteer.getVolId());
    }

    /*
        봉사 신청
     */
    @Transactional
    public int createApplication(Application application) {
        log.info("createApplication : {}", application);
        /*
            update 실행 시 -> vol_cur_number
             1. 명시적으로 지정한 경우 : 지정한 숫자로 변경
             2. 지정 하지 않은 경우 : 봉사자가 봉사 신청 요청의 경우로, 현재 신청인원을 가져와서 + 1
         */
        int result = volunteerMapper.createApplication(application);
        if (result > 0) {
            Volunteer volunteer = volunteerMapper.findVolunteerById(application.getVolId());
            volunteer.setVolCurNumber(volunteer.getVolCurNumber() + 1);
            return volunteerMapper.updateVolCurNumberById(volunteer);
        } else return 0;
    }

    @Transactional
    public int deleteApplication(Application application) {
        log.info("deleteApplication : {}", application);
        return volunteerMapper.deleteApplication(application);
    }

    public boolean nullCheck(String str) {

        try {
            if (str.isEmpty()) throw new NullPointerException();
            return false;
        } catch (NullPointerException e) {
            return true;
        }
    }

}

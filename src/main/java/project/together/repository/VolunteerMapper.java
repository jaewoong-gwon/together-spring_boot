package project.together.repository;

import org.apache.ibatis.annotations.Mapper;
import project.together.vo.Application;
import project.together.vo.Volunteer;

import java.util.List;

@Mapper
public interface VolunteerMapper {

    List<Volunteer> findAllVolunteer(Volunteer volunteer);

    List<Volunteer> findAllApplicatedVolunteerByServantId(String serId);

    List<Volunteer> findVolunteerAddServant(String orgId);

    Volunteer findVolunteerById(int volId);

    int findVolunteerCurNumber(int volId);

    int createVolunteer(Volunteer volunteer);

    int updateVolCurNumberById(Volunteer volunteer);

    int deleteVolunteerById(int volId);

    /*
        Application Table 관련
     */
    int createApplication(Application application);

    int deleteApplication(Application application);
}

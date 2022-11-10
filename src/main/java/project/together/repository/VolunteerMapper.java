package project.together.repository;

import org.apache.ibatis.annotations.Mapper;
import project.together.vo.Volunteer;

import java.util.List;

@Mapper
public interface VolunteerMapper {

    List<Volunteer> findAllVolunteer();

    Volunteer findVolunteerById(int volId);

    int findVolunteerCurNumber(int volId);

    int createVolunteer(Volunteer volunteer);

    int updateVolCurNumberById(Volunteer volunteer);

    int deleteVolunteerById(int volId);
}

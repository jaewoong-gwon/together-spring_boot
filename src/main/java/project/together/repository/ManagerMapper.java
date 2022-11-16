package project.together.repository;

import org.apache.ibatis.annotations.Mapper;
import project.together.vo.Manager;

@Mapper
public interface ManagerMapper {
    Manager findManagerById(String manId);

    int createManager(Manager manager);
}

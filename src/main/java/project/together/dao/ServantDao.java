package project.together.dao;

import org.apache.ibatis.annotations.Mapper;
import project.together.dto.Servant;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ServantDao {
    List<Servant>findAll();
    Servant findById(String serId);
    void createServant(Servant servant);

    //    @Delete("DELETE FROM Servant ser_id WHERE ser_id #{serId}")
    //    boolean deleteServantById(String serId);
}

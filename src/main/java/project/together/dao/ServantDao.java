package project.together.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import project.together.dto.Servant;

import java.util.List;

@Mapper
public interface ServantDao {

    @Select("SELECT * FROM Servant")
    List<Servant>findAll();

    @Select("SELECT ser_id FROM Servant WHERE ser_id = #{serId}")
    void findById(String serId);

    @Insert("INSERT INTO Servant ser_id,ser_name,ser_birth,ser_gender,ser_mobile,ser_email " +
            "VALUES #{serId},#{serName},#{serBirth},#{serGender},#{serMobile},#{serEmail}")
    boolean createServant(Servant servant);
}

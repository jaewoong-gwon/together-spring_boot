package project.together.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import project.together.dto.Servant;

import java.util.List;
// import java.util.Optional; -> NPE 방지를 위한 클래스. 추후 공부 필요. 특히 DB 에서 값이 넘어오지 않거나, DTO 에 저장 실패한 경우 NULL 이 저장.

@Mapper
public interface ServantDao {
    @Select("SELECT * FROM Servant")
    List<Servant> findAll();
    @Select("SELECT * FROM Servant WHERE ser_id = #{serId}")
    Servant findById(String serId);

    @Insert("INSERT INTO Servant (ser_id,ser_name,ser_birth,ser_gender,ser_mobile,ser_email,ser_sns) VALUES( #{serId}, #{serName}, #{serBirth},#{serGender},#{serMobile},#{serEmail},#{serSns}")
    void createServant(Servant servant);

    @Delete("DELETE FROM Servant ser_id WHERE ser_id #{serId}")
    boolean deleteServantById(String serId);
}

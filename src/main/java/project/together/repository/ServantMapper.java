package project.together.repository;

import org.apache.ibatis.annotations.Mapper;
import project.together.vo.Servant;

import java.util.List;
// import java.util.Optional; -> NPE 방지를 위한 클래스. 추후 공부 필요. 특히 DB 에서 값이 넘어오지 않거나, VO 에 저장 실패한 경우 NULL 이 저장.

@Mapper
public interface ServantMapper {
    Servant findServantById(String serId);

    List<Servant> findAllServant();

    int createServant(Servant servant);

    int deleteServantById(String serId);


}

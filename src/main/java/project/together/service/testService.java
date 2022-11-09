package project.together.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.together.repository.OrganizationMapper;
import project.together.repository.ServantMapper;
import project.together.vo.Organization;
import project.together.vo.Servant;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class testService {
    private final ServantMapper ServantMapper;
    private final OrganizationMapper organizationMapper;

    public List<Servant> testFindAllServant() {
        return ServantMapper.findAllServant();
    }

    public Servant findServantById(String serId) {
        Servant servant = ServantMapper.findServantById(serId);
        log.info("testService user : {}", servant);
        if (servant != null) return servant;
        else return null;
    }

    public int createServant(Servant user) {
        log.info("testService createServant : {}", user);
        return ServantMapper.createServant(user);
    }

    public int deleteServantById(String serId) {
        log.info("testService deleteServantById : {}", serId);
        return ServantMapper.deleteServantById(serId);
    }

    public List<Organization> FindAllOrganization() {
        return organizationMapper.findAllOrganization();
    }

    public Organization findOrganizationById(String orgId) {
        log.info("testService Organization : {}", orgId);
        return organizationMapper.findOrganizationById(orgId);
    }

    public int createOrganization(Organization org) {
        log.info("testService createOrganization : {}", org);
        return organizationMapper.createOrganization(org);
    }

    public int deleteOrganizationById(String ordId) {
        log.info("testService deleteOrganizationById : {}", ordId);
        return organizationMapper.deleteOrganizationById(ordId);
    }
}

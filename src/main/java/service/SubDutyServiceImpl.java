package service;

import base.exception.ReturnMethodException;
import base.service.BaseServiceImpl;
import entity.SubDuty;
import org.hibernate.SessionFactory;
import repository.SubDutyRepository;

import java.util.List;

public class SubDutyServiceImpl extends BaseServiceImpl<SubDuty,Integer, SubDutyRepository>
        implements SubDutyService {
    public SubDutyServiceImpl(SubDutyRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public List<SubDuty> subDuties(int id) {
        List<SubDuty> subDuties;
        try {
            subDuties = repository.subDuties(id);
        }catch (NullPointerException e){
            throw new ReturnMethodException("");
        }
        return subDuties;
    }
}

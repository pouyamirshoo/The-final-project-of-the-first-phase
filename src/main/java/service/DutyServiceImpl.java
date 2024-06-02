package service;

import base.exception.ReturnMethodException;
import base.service.BaseServiceImpl;
import entity.Duty;
import org.hibernate.SessionFactory;
import repository.DutyRepository;

import java.util.List;

public class DutyServiceImpl extends BaseServiceImpl<Duty,Integer, DutyRepository>
        implements DutyService {
    public DutyServiceImpl(DutyRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public List<Duty> duties() {
        List<Duty> duties;
        try {
            duties = repository.duties();
        }catch (NullPointerException e){
            throw new ReturnMethodException("");
        }
        return duties;
    }
}

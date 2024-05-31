package service;

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
        return repository.duties();
    }
}

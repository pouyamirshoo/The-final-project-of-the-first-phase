package service;

import base.service.BaseServiceImpl;
import entity.Duty;
import org.hibernate.SessionFactory;
import repository.DutyRepository;

public class DutyServiceImpl extends BaseServiceImpl<Duty,Integer, DutyRepository>
        implements DutyService {
    public DutyServiceImpl(DutyRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }
}

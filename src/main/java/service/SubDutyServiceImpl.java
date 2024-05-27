package service;

import base.service.BaseServiceImpl;
import entity.SubDuty;
import org.hibernate.SessionFactory;
import repository.SubDutyRepository;

public class SubDutyServiceImpl extends BaseServiceImpl<SubDuty,Integer, SubDutyRepository>
        implements SubDutyService {
    public SubDutyServiceImpl(SubDutyRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }
}

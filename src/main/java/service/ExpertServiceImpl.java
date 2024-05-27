package service;

import base.service.BaseServiceImpl;
import entity.Expert;
import org.hibernate.SessionFactory;
import repository.ExpertRepository;

public class ExpertServiceImpl extends BaseServiceImpl<Expert,Integer, ExpertRepository>
        implements ExpertService {
    public ExpertServiceImpl(ExpertRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }
}

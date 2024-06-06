package service;

import base.exception.NotFoundException;
import base.exception.ReturnMethodException;
import base.service.BaseServiceImpl;
import entity.Expert;
import entity.enums.ExpertCondition;
import org.hibernate.SessionFactory;
import repository.ExpertRepository;

import java.util.List;

public class ExpertServiceImpl extends BaseServiceImpl<Expert,Integer, ExpertRepository>
        implements ExpertService {
    public ExpertServiceImpl(ExpertRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public Expert findByUsernameAndPassword(String username, String password) {
        return repository.findByUsernameAndPassword(username, password).orElseThrow(() -> new NotFoundException(""));
    }

    @Override
    public List<Expert> findByCondition(ExpertCondition expertCondition) throws ReturnMethodException {
        List<Expert> experts;
        try {
            experts = repository.findByCondition(expertCondition);
        } catch (NullPointerException e) {
            throw new ReturnMethodException("");
        }
        return experts;
    }

    @Override
    public List<Expert> allExperts() throws ReturnMethodException {
        List<Expert> experts;
        try {
            experts = repository.allExperts();
        } catch (NullPointerException e) {
            throw new ReturnMethodException("");
        }
        return experts;
    }
}

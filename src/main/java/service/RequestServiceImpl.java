package service;

import base.exception.NotFoundException;
import base.service.BaseServiceImpl;
import entity.Requested;
import org.hibernate.SessionFactory;
import repository.RequestRepository;


public class RequestServiceImpl extends BaseServiceImpl<Requested,Integer, RequestRepository>
        implements RequestService {
    public RequestServiceImpl(RequestRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public Requested findExpertRequests(int id) {
        return repository.findExpertRequests(id).orElseThrow(() -> new NotFoundException(""));
    }
}

package service;

import base.exception.NotFoundException;
import base.service.BaseServiceImpl;
import entity.Admin;
import org.hibernate.SessionFactory;
import repository.AdminRepository;

public class AdminServiceImpl extends BaseServiceImpl<Admin,Integer, AdminRepository>
        implements AdminService {
    public AdminServiceImpl(AdminRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public Admin findByUsernameAndPassword(String username, String password) {
        return repository.findByUsernameAndPassword(username,password).orElseThrow(() -> new NotFoundException(""));
    }
}

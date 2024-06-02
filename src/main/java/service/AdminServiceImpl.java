package service;

import base.service.BaseServiceImpl;
import entity.Admin;
import org.hibernate.SessionFactory;
import repository.AdminRepository;

public class AdminServiceImpl extends BaseServiceImpl<Admin,Integer, AdminRepository>
        implements AdminService {
    public AdminServiceImpl(AdminRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }
}

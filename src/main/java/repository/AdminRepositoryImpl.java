package repository;

import base.repository.BaseRepositoryImpl;
import entity.Admin;
import org.hibernate.SessionFactory;

public class AdminRepositoryImpl extends BaseRepositoryImpl<Admin,Integer> implements AdminRepository {
    public AdminRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Admin> getEntityClass() {
        return Admin.class;
    }
}

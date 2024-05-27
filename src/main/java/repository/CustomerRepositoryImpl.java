package repository;

import base.repository.BaseRepositoryImpl;
import entity.Customer;
import org.hibernate.SessionFactory;

public class CustomerRepositoryImpl extends BaseRepositoryImpl<Customer,Integer>
        implements CustomerRepository {
    public CustomerRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Customer> getEntityClass() {
        return null;
    }
}

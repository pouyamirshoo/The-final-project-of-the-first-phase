package service;

import base.service.BaseServiceImpl;
import entity.Customer;
import org.hibernate.SessionFactory;
import repository.CustomerRepository;

public class CustomerServiceImpl extends BaseServiceImpl<Customer,Integer,CustomerRepository>
        implements CustomerService {
    public CustomerServiceImpl(CustomerRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }
}

package repository;

import base.exception.NotFoundException;
import base.repository.BaseRepositoryImpl;
import connection.SessionFactorySingleton;
import entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Optional;

public class CustomerRepositoryImpl extends BaseRepositoryImpl<Customer,Integer>
        implements CustomerRepository {
    public CustomerRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Customer> getEntityClass() {
        return Customer.class;
    }


    @Override
    public Optional<Customer> findByUsernameAndPassword(String username, String password) throws NotFoundException {
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Customer> query = session.createQuery("FROM Customer c WHERE c.username = :username " +
                "AND c.password = :password", Customer.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        Optional<Customer> optionalCustomer = query .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
        session.close();
        return optionalCustomer;
    }
}

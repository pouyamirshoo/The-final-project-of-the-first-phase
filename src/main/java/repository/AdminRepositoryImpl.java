package repository;

import base.exception.NotFoundException;
import base.repository.BaseRepositoryImpl;
import connection.SessionFactorySingleton;
import entity.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Optional;

public class AdminRepositoryImpl extends BaseRepositoryImpl<Admin,Integer> implements AdminRepository {
    public AdminRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Admin> getEntityClass() {
        return Admin.class;
    }

    @Override
    public Optional<Admin> findByUsernameAndPassword(String username, String password) throws NotFoundException {
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Admin> query = session.createQuery("FROM Admin a WHERE a.username = :username " +
                "AND a.password = :password", Admin.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        Optional<Admin> optionalCustomer = query .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
        session.close();
        return optionalCustomer;
    }
}

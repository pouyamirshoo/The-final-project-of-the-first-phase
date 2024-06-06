package repository;

import base.exception.NotFoundException;
import base.repository.BaseRepositoryImpl;
import connection.SessionFactorySingleton;
import entity.Expert;
import entity.enums.ExpertCondition;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class ExpertRepositoryImpl extends BaseRepositoryImpl<Expert,Integer>
        implements ExpertRepository {
    public ExpertRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Expert> getEntityClass() {
        return Expert.class;
    }

    @Override
    public Optional<Expert> findByUsernameAndPassword(String username, String password) throws NotFoundException {
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Expert> query = session.createQuery("FROM Expert e WHERE e.username = :username " +
                "AND e.password = :password", Expert.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        Optional<Expert> optionalCustomer = query .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
        session.close();
        return optionalCustomer;
    }

    @Override
    public List<Expert> findByCondition(ExpertCondition expertCondition) throws NullPointerException {
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Expert> query = session.createQuery("FROM Expert e WHERE e.expertCondition = :expertCondition ", Expert.class);
        query.setParameter("expertCondition", expertCondition);
        List<Expert> experts = query.list();
        session.close();
        return experts;
    }

    @Override
    public List<Expert> allExperts() throws NullPointerException {
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Expert> query = session.createQuery("FROM Expert e ", Expert.class);
        List<Expert> experts = query.list();
        session.close();
        return experts;
    }
}

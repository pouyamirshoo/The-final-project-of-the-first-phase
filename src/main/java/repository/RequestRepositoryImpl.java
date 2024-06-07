package repository;

import base.repository.BaseRepositoryImpl;
import connection.SessionFactorySingleton;
import entity.Requested;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Optional;

public class RequestRepositoryImpl extends BaseRepositoryImpl<Requested, Integer>
        implements RequestRepository {
    public RequestRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Requested> getEntityClass() {
        return Requested.class;
    }

    @Override
    public Optional<Requested> findExpertRequests(int id) throws NullPointerException {
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Requested> query = session.createQuery("FROM Requested r WHERE r.expert.id = :id", Requested.class);
        query.setParameter("id", id);
        Optional<Requested> optionalRequested = query.setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
        session.close();
        return optionalRequested;
    }
}

package repository;

import base.repository.BaseRepositoryImpl;
import connection.SessionFactorySingleton;
import entity.Duty;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class DutyRepositoryImpl extends BaseRepositoryImpl<Duty,Integer>
        implements DutyRepository {
    public DutyRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Duty> getEntityClass() {
        return null;
    }

    @Override
    public List<Duty> duties(){
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Duty> query = session.createQuery(" FROM Duty d ",
                Duty.class);
        List<Duty> duties = query.list();
        session.close();
        return duties;
    }
}

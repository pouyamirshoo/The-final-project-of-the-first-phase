package repository;

import base.repository.BaseRepositoryImpl;
import connection.SessionFactorySingleton;
import entity.SubDuty;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;


public class SubDutyRepositoryImpl extends BaseRepositoryImpl<SubDuty,Integer>
        implements SubDutyRepository {
    public SubDutyRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<SubDuty> getEntityClass() {
        return null;
    }

    @Override
    public List<SubDuty> subDuties(int id) {
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<SubDuty> query = session.createQuery(" FROM SubDuty s WHERE s.duty.id =:id",
                SubDuty.class);
        query.setParameter("id",id);
        List<SubDuty> subDuties = query.list();
        session.close();
        return subDuties;
    }
}

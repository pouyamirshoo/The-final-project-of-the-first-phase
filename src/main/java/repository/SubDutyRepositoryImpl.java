package repository;

import base.repository.BaseRepositoryImpl;
import entity.SubDuty;
import org.hibernate.SessionFactory;


public class SubDutyRepositoryImpl extends BaseRepositoryImpl<SubDuty,Integer> implements SubDutyRepository {
    public SubDutyRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<SubDuty> getEntityClass() {
        return null;
    }
}

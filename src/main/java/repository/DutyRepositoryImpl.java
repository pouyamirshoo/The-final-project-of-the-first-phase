package repository;

import base.repository.BaseRepositoryImpl;
import entity.Duty;
import org.hibernate.SessionFactory;

public class DutyRepositoryImpl extends BaseRepositoryImpl<Duty,Integer>
        implements DutyRepository {
    public DutyRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Duty> getEntityClass() {
        return null;
    }
}

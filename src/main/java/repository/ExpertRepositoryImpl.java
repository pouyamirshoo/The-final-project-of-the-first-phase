package repository;

import base.repository.BaseRepositoryImpl;
import entity.Expert;
import org.hibernate.SessionFactory;

public class ExpertRepositoryImpl extends BaseRepositoryImpl<Expert,Integer>
        implements ExpertRepository {
    public ExpertRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Expert> getEntityClass() {
        return Expert.class;
    }
}

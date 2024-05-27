package repository;

import base.repository.BaseRepositoryImpl;
import entity.Offer;
import org.hibernate.SessionFactory;

public class OfferRepositoryImpl extends BaseRepositoryImpl<Offer,Integer>
        implements OfferRepository {
    public OfferRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Offer> getEntityClass() {
        return null;
    }
}

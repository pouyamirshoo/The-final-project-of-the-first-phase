package repository;

import base.exception.NotFoundException;
import base.repository.BaseRepositoryImpl;
import connection.SessionFactorySingleton;
import entity.Offer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class OfferRepositoryImpl extends BaseRepositoryImpl<Offer,Integer>
        implements OfferRepository {
    public OfferRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Offer> getEntityClass() {
        return Offer.class;
    }

    @Override
    public Optional<Offer> findByOrderId(int id) throws NotFoundException {
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Offer> query = session.createQuery("FROM Offer o WHERE o.order.id = :id ", Offer.class);
        query.setParameter("id", id);
        Optional<Offer> optionalOffer = query.setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
        session.close();
        return optionalOffer;
    }

    @Override
    public List<Offer> findAllOfOneOrderOffers(int id) throws NullPointerException{
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Offer> query = session.createQuery("FROM Offer o WHERE o.order.id = :id ", Offer.class);
        query.setParameter("id", id);
        List<Offer> offers = query.list();
        session.close();
        return offers;
    }

    @Override
    public List<Offer> findAllExpertOffers(int id) throws NullPointerException {
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Offer> query = session.createQuery("FROM Offer o WHERE o.expert.id = :id ", Offer.class);
        query.setParameter("id", id);
        List<Offer> offers = query.list();
        session.close();
        return offers;
    }
}

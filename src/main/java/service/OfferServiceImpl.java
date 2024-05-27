package service;

import base.service.BaseServiceImpl;
import entity.Offer;
import org.hibernate.SessionFactory;
import repository.OfferRepository;

public class OfferServiceImpl extends BaseServiceImpl<Offer,Integer, OfferRepository>
        implements OfferService {
    public OfferServiceImpl(OfferRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }
}

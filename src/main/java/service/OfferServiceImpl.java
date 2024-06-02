package service;

import base.exception.NotFoundException;
import base.exception.ReturnMethodException;
import base.service.BaseServiceImpl;
import entity.Offer;
import org.hibernate.SessionFactory;
import repository.OfferRepository;

import java.util.List;

public class OfferServiceImpl extends BaseServiceImpl<Offer,Integer, OfferRepository>
        implements OfferService {
    public OfferServiceImpl(OfferRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public List<Offer> findByOrderId(int id) {
        List<Offer> offers;
        try {
            offers = repository.findByOrderId(id);
        } catch (NotFoundException e){
            throw new ReturnMethodException("");
        }
        return offers;
    }
}

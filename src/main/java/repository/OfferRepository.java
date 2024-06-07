package repository;

import base.exception.NotFoundException;
import base.repository.BaseRepository;
import entity.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends BaseRepository<Offer,Integer> {
    Optional<Offer> findByOrderId(int id) throws NotFoundException;
    List<Offer> findAllOfOneOrderOffers(int id) throws NullPointerException;
    List<Offer> findAllExpertOffers(int id) throws NullPointerException;

}

package repository;

import base.repository.BaseRepository;
import entity.Offer;

import java.util.List;


public interface OfferRepository extends BaseRepository<Offer, Integer> {
    List<Offer> findAllOfOneOrderOffers(int id) throws NullPointerException;

    List<Offer> findAllExpertOffers(int id) throws NullPointerException;

}

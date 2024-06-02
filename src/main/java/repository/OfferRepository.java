package repository;

import base.exception.NotFoundException;
import base.repository.BaseRepository;
import entity.Offer;

import java.util.List;

public interface OfferRepository extends BaseRepository<Offer,Integer> {
    List<Offer> findByOrderId(int id) throws NotFoundException;
}

package service;


import base.exception.ReturnMethodException;
import base.service.BaseService;
import entity.Offer;

import java.util.List;

public interface OfferService extends BaseService<Offer,Integer> {
    List<Offer> findAllOfOneOrderOffers(int id) throws ReturnMethodException;
    List<Offer> findAllExpertOffers(int id) throws ReturnMethodException;
}

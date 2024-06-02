package service;

import base.exception.ReturnMethodException;
import base.service.BaseService;
import entity.Offer;

import java.util.List;

public interface OfferService extends BaseService<Offer,Integer> {
    List<Offer> findByOrderId(int id) throws ReturnMethodException;
}

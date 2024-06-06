package service;

import base.exception.ReturnMethodException;
import base.service.BaseService;
import entity.Duty;

import java.util.List;

public interface DutyService extends BaseService<Duty,Integer> {
    List<Duty> duties() throws ReturnMethodException;
}

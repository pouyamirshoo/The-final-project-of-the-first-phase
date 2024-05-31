package service;

import base.service.BaseService;
import entity.SubDuty;

import java.util.List;

public interface SubDutyService extends BaseService<SubDuty,Integer> {
    List<SubDuty> subDuties(int id);
}

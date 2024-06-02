package repository;

import base.repository.BaseRepository;
import entity.SubDuty;

import java.util.List;

public interface SubDutyRepository extends BaseRepository<SubDuty,Integer> {
    List<SubDuty> subDuties(int id) throws NullPointerException;
}

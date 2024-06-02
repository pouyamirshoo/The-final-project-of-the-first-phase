package repository;

import base.repository.BaseRepository;
import entity.Duty;

import java.util.List;

public interface DutyRepository extends BaseRepository<Duty,Integer> {
     List<Duty> duties() throws NullPointerException;
}

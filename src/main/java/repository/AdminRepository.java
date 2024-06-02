package repository;

import base.exception.NotFoundException;
import base.repository.BaseRepository;
import entity.Admin;

import java.util.Optional;

public interface AdminRepository extends BaseRepository<Admin,Integer> {
    Optional<Admin> findByUsernameAndPassword(String username, String password) throws NotFoundException;
}

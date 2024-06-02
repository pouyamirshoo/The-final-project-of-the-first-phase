package repository;

import base.exception.NotFoundException;
import base.repository.BaseRepository;
import entity.Customer;

import java.util.Optional;

public interface CustomerRepository extends BaseRepository<Customer,Integer> {
    Optional<Customer> findByUsernameAndPassword(String username, String password) throws NotFoundException;
}

package com.myprojects.customerrewardsservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myprojects.customerrewardsservice.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

}

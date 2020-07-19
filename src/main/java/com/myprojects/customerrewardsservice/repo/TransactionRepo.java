package com.myprojects.customerrewardsservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myprojects.customerrewardsservice.model.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer> {

}

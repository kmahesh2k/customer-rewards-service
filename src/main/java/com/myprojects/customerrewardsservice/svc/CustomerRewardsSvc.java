package com.myprojects.customerrewardsservice.svc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprojects.customerrewardsservice.exception.BadRequestException;
import com.myprojects.customerrewardsservice.model.Customer;
import com.myprojects.customerrewardsservice.model.Transaction;
import com.myprojects.customerrewardsservice.repo.CustomerRepo;

@Service
public class CustomerRewardsSvc {

	@Autowired
	CustomerRepo customerRepo;

	public Customer addCustTransactions(Customer customer) {

		Optional<Customer> custOpt = customerRepo.findById(customer.getCustId());

		if (custOpt.isPresent()) {
			Customer cust = custOpt.get();
			cust.setCustName(customer.getCustName());
			cust.getTransactions().addAll(customer.getTransactions());
			return customerRepo.save(cust);
		} else {
			return customerRepo.save(customer);
		}
	}

	public Map<String, Integer> getTotalPoints(String custId) {
		Optional<Customer> custOpt = customerRepo.findById(Integer.valueOf(custId));
		Map<String, Integer> totalPoints = new HashMap<>();
		if (custOpt.isPresent()) {

			List<Transaction> listOfTrans = custOpt.get().getTransactions();

			Map<String, List<Transaction>> mapOfMnthlyTrans = listOfTrans.stream()
					.collect(Collectors.groupingBy(tran -> tran.getTransDate().getMonth().toString()));

			for (Map.Entry<String, List<Transaction>> set : mapOfMnthlyTrans.entrySet()) {
				Integer total = set.getValue().stream()
						.mapToInt(tran -> calculateTotalPts(tran.getTransAmt().intValue())).sum();
				totalPoints.put(set.getKey(), total);
			}
			return totalPoints;
		} else {
			throw new BadRequestException("No customer found for the Customer Id - " + custId);
		}
	}

	private int calculateTotalPts(int amt) {

		/*
		 * Problem statement - A customer receives 2 points for every dollar spent over
		 * $100 in each transaction, plus 1 point for every dollar spent over $50 in
		 * each transaction
		 * 
		 * (e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).
		 * 
		 * Need to discuss more with the concerned person about the correct meaning of
		 * the above problem statement because the solution (90 points) for the example
		 * of $120 given above is not exactly matching with the problem statement
		 */

		int sum = 0;
		if (amt > 100) {
			sum = (amt - 100) * 2 + 50;
		} else if (amt > 50) {
			sum = (amt - 50) * 1;
		}
		return sum;
	}

}

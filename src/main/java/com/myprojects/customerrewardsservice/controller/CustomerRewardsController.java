package com.myprojects.customerrewardsservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myprojects.customerrewardsservice.model.Customer;
import com.myprojects.customerrewardsservice.model.CustomerRequest;
import com.myprojects.customerrewardsservice.svc.CustomerRewardsSvc;

@RestController
public class CustomerRewardsController {

	@Autowired
	CustomerRewardsSvc customerSvc;

	@PostMapping(value = "/addTransactions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Customer addTransactions(@RequestBody CustomerRequest customer) {
		return customerSvc.addCustTransactions(customer.getCustomer());
	}

	@GetMapping(value = "/getTotalByCustomer/{custId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Integer> getTotalByCust(@PathVariable(value = "custId") String custId) {
		return customerSvc.getTotalPoints(custId);
	}

}

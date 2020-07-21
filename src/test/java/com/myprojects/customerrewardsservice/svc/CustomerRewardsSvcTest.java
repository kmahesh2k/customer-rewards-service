package com.myprojects.customerrewardsservice.svc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.myprojects.customerrewardsservice.model.Customer;
import com.myprojects.customerrewardsservice.model.Transaction;
import com.myprojects.customerrewardsservice.repo.CustomerRepo;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerRewardsSvcTest {

	@InjectMocks
	CustomerRewardsSvc service;

	@Mock
	CustomerRepo customerRepo;

	@Test
	public void testaddCustTransactions() throws Exception {

		Customer customer = new Customer();
		customer.setCustId(100);
		customer.setCustName("CustName");
		Transaction transaction = new Transaction();
		transaction.setTransId(1);
		transaction.setTransAmt(new BigDecimal("10"));
		transaction.setTransDate(LocalDateTime.now());
		List<Transaction> list = new ArrayList<>();
		list.add(transaction);
		customer.setTransactions(list);
		Optional<Customer> opt = Optional.of(customer);
		when(customerRepo.findById(Mockito.any(Integer.class))).thenReturn(opt);

		when(customerRepo.save(Mockito.any(Customer.class))).thenReturn(customer);

		Customer cust2 = new Customer();
		cust2.setCustId(100);
		cust2.setCustName("CustName");
		Transaction transaction2 = new Transaction();
		transaction2.setTransId(2);
		transaction2.setTransAmt(new BigDecimal("20"));
		transaction2.setTransDate(LocalDateTime.now());
		List<Transaction> list2 = new ArrayList<>();
		list2.add(transaction2);
		cust2.setTransactions(list2);

		// Under Test
		Customer res = service.addCustTransactions(cust2);

		assertNotNull(res);
		assertEquals("CustName", res.getCustName());
		assertEquals(2, res.getTransactions().size());
		assertEquals(BigDecimal.TEN, res.getTransactions().get(0).getTransAmt());
	}

}

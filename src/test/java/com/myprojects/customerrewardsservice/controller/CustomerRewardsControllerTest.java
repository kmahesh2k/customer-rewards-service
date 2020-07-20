package com.myprojects.customerrewardsservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.myprojects.customerrewardsservice.model.Customer;
import com.myprojects.customerrewardsservice.model.CustomerRequest;
import com.myprojects.customerrewardsservice.model.Transaction;
import com.myprojects.customerrewardsservice.svc.CustomerRewardsSvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerRewardsControllerTest {

	@InjectMocks
	CustomerRewardsController controller;

	private MockMvc mockMvc;

	@Mock
	CustomerRewardsSvc customerSvc;

//	@Before
//	public void setUp() throws Exception {
//		MockitoAnnotations.initMocks(this);
//		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
//	}

	@Test
	public void testAddTransactions() throws Exception {

		Customer customer = new Customer();
		customer.setCustId(100);
		customer.setCustName("Cust1");
		Transaction transaction = new Transaction();
		transaction.setTransId(1);
		transaction.setTransAmt(new BigDecimal("10"));
		transaction.setTransDate(LocalDateTime.now());
		List<Transaction> list = new ArrayList<>();
		list.add(transaction);
		customer.setTransactions(list);
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		when(customerSvc.addCustTransactions(Mockito.any(Customer.class))).thenReturn(customer);

		CustomerRequest customerRequest = new CustomerRequest();
		Customer customer2 = new Customer();
		customer2.setCustName("test");
		customerRequest.setCustomer(customer2);

		// Under test
		Customer res = controller.addTransactions(customerRequest);
		
		assertNotNull(res);
		assertEquals("Cust1", res.getCustName());
		assertEquals(1, res.getTransactions().size());
	}

}

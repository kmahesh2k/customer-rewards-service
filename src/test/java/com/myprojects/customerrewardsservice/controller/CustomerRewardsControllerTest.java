package com.myprojects.customerrewardsservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testAddTransactions() throws Exception {

		Customer customer = new Customer();
		customer.setCustId(100);
		customer.setCustName("Cust1");
		Transaction transaction = new Transaction();
		transaction.setTransId(1);
		transaction.setTransAmt(new BigDecimal("10"));
		transaction.setTransDate(LocalDateTime.of(2020, 7, 21, 5, 5, 0));
		List<Transaction> list = new ArrayList<>();
		list.add(transaction);
		customer.setTransactions(list);

		when(customerSvc.addCustTransactions(Mockito.any(Customer.class))).thenReturn(customer);

		CustomerRequest customerRequest = new CustomerRequest();
		Customer customer2 = new Customer();
		customer2.setCustName("test");
		customerRequest.setCustomer(customer2);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/rewards/addTransactions")
				.contentType(MediaType.APPLICATION_JSON).content(getCustWithTrans());

		// Under test
		MvcResult mvc = mockMvc.perform(builder).andExpect(status().isOk()).andReturn();

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		Customer customerRes = mapper.readValue(mvc.getResponse().getContentAsString(), Customer.class);

		assertNotNull(customerRes);
		assertEquals("Cust1", customerRes.getCustName());
		assertEquals(1, customerRes.getTransactions().size());
	}

	private String getCustWithTrans() {
		return "{\r\n" + 
				"  \"customer\": {\r\n" + 
				"    \"custId\": 0,\r\n" + 
				"    \"custName\": \"string\",\r\n" + 
				"    \"transactions\": [\r\n" + 
				"      {\r\n" + 
				"        \"transAmt\": 0,\r\n" + 
				"        \"transDate\": \"2020-07-21T05:05:00.000Z\"\r\n" + 
				"      }\r\n" + 
				"    ]\r\n" + 
				"  }\r\n" + 
				"}";
	}

}

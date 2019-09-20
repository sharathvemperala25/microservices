package com.ing.stock.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.stock.dto.ResponseStockDto;
import com.ing.stock.service.StockService;
import com.ing.stock.service.StockServiceImpl;


/**
 * 
 * @author Sushil
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class StockControllerTest {

	@Mock
	StockService stockService;
	
	@InjectMocks
	StockCotroller stockCotroller;
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		
		mockMvc = MockMvcBuilders.standaloneSetup(stockCotroller).build();
	}
	
	@Test
	public void customerLoginTest() throws JsonProcessingException, Exception {
		ResponseStockDto responsedto = new ResponseStockDto();
		responsedto.setStockId(1L);
		responsedto.setStockName("WFC");
		responsedto.setStockPrice(48.91);
		
		
		Mockito.when(stockService.getStocks(Mockito.any())).thenReturn(responsedto);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/stock/1").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.ALL).content(asJsonString(""))).andReturn();		
		ResponseEntity<CustomerLoginResponse> response1  = customerLoginController.customerLogin(requestdto);
		
		assertEquals(200, response1.getStatusCodeValue());
		assertEquals("Ram", response1.getBody().getCustomerName());
		
	}
	

}
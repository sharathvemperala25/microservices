package com.ing.stock.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ing.stock.dto.ResponseStockDto;
import com.ing.stock.service.StockService;

@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RequestMapping("/stock")
public class StockCotroller {

	@Autowired
	StockService stockService;

	@GetMapping("/{stockId}")
	public ResponseStockDto getStocks(@PathVariable("stockId") Long stockId) throws IOException {
		ResponseStockDto stock = stockService.getStocks(stockId);
		return stock;
	}
}

package com.ing.stock.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.stock.dto.ResponseStockDto;
import com.ing.stock.entity.Stock;
import com.ing.stock.repository.StockRepository;

@Service
public class StockServiceImpl implements StockService {

	@Autowired
	StockRepository stockRepository;

	@Autowired
	RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Override
	public ResponseStockDto getStocks(Long stockId) throws IOException {
		Optional<Stock> stock = stockRepository.findById(stockId);
		Double stockPrice = null;
		String response = restTemplate
				.exchange(
						"https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + stock.get().getStockName()
								+ "&apikey=JAWMQTBSYN26FR3M",
						HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
						}, stockId)
				.getBody();

		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(response);
		JsonNode errorResponse = node.findValue("Error Message");
		if (errorResponse.isNull()) {
			JsonNode subNode = node.get("Global Quote");
			stockPrice = subNode.get("05. price").asDouble();
		}
		ResponseStockDto responseStockDto = new ResponseStockDto();
		responseStockDto.setStockId(stock.get().getStockId());
		responseStockDto.setStockName(stock.get().getStockName());
		responseStockDto.setStockPrice(stockPrice);
		System.out.println(stockPrice);
		return responseStockDto;
	}

}

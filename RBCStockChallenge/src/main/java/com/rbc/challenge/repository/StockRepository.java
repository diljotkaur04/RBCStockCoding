package com.rbc.challenge.repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;


import com.rbc.challenge.domain.Stock;

public interface StockRepository extends MongoRepository<Stock, String> {
	List<Stock> findAllByStock(String stock);

	List<Stock> findAll(Sort by);



}

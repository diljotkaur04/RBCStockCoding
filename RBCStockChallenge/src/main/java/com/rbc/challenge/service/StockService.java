package com.rbc.challenge.service;


import java.util.List;



import com.rbc.challenge.domain.Stock;

public interface StockService {
  List<Stock> listAll(); 

    Stock saveOrUpdate(Stock stock);  

	List<Stock> getByStock(String stock);
	
	void importData(String stockForm);
	   

	

}

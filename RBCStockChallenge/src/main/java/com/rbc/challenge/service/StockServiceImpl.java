package com.rbc.challenge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;


import com.rbc.challenge.domain.Stock;
import com.rbc.challenge.repository.StockRepository;
@Service
public class StockServiceImpl implements StockService {

	 @Autowired
	    private StockRepository stockRepo;
	    
	    @Autowired
	    MongoTemplate mongoTemplate;
	    
	    @Value("${mongodb.installation.path}")
	    private  String MONGODBPATH;

	    public void importData(String  filePath){
	    	
	    	System.out.println(filePath+"Path of mongodb = "+MONGODBPATH);
	    	
	    	  Runtime r = Runtime.getRuntime();
	    	  Process p = null;
	    	  String command =  MONGODBPATH+" --db djindex --collection stock " +
	    	             "-f quarter,stock,date,open,high,low,close,volume,percent_change_price,percent_change_volume_over_last_wk,previous_weeks_volume,next_weeks_open,next_weeks_close,percent_change_next_weeks_price,days_to_next_dividend,percent_return_next_dividend"+
	    			  " --type csv --file   "+filePath;
	    	  try {
	    	   p = r.exec(command);
	    	   System.out.println("Reading csv into Database");
	    	   
	    	  } catch (Exception e){
	    		  e.printStackTrace();
	    	   System.out.println("Error executing " + command + e.toString());
	    	  }
	    	 }
	    
	    
	    public void queryDocument() {
	    }


	   @Override
	    public List<Stock> listAll() { 
	        
		   List<Stock> returnedList =  (List<Stock>) stockRepo.findAll(Sort.by(Sort.Direction.ASC, "stock"));
	        System.out.println("******** returned list = "+returnedList.size());
	        return returnedList;
	               
	    }

	    @Override
	    public List<Stock> getByStock(String stock) {    	
	    	List<Stock>  returnedList =  (List<Stock>) stockRepo.findAllByStock(stock);
	        return returnedList;
	    }

	    @Override
	    public Stock saveOrUpdate(Stock stock) {
	    	  stockRepo.save(stock);
	        return stock;
	    }

		
	 
	    
	 

}

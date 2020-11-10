package com.rbc.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rbc.challenge.commands.NewStock;
import com.rbc.challenge.commands.StockForm;
import com.rbc.challenge.domain.Stock;
import com.rbc.challenge.service.StockService;

import java.io.File;
import java.io.IOException;

import javax.validation.Valid;

@Controller
public class StockController {
	
	@Autowired
    private StockService stockService; 
	
	@RequestMapping("/stock/login")
	public String login() {
	    return "/stock/login";
	  }
	
	@RequestMapping("/stock/options")
	  public String options() {
	    return "/stock/options";
	  }

	@RequestMapping({"/stock/list", "/stocks"})
    public String listStocks(Model model){
        model.addAttribute("stocks", stockService.listAll());
        return "stock/list";
    }
	
   @RequestMapping("/stock/upload")
    public String newUpload(Model model){
        model.addAttribute("stockForm", new StockForm());
        return "stock/stockform";
    }
   
   
   @RequestMapping("/stock/new")
   public String newStock(Model model){
       model.addAttribute("stockForm", new NewStock());
       return "stock/addstock";
   }
  
   @RequestMapping(value = "/stock/upload", method = RequestMethod.POST)
   public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes,Model model) throws IllegalStateException, IOException {

	   long size=file.getSize();
	   String filename = file.getOriginalFilename();
	   System.out.println(" size ="+size);
	   if(size<1)
	   {
		   model.addAttribute("stockForm", new StockForm());
	       model.addAttribute("message", "No file uploaded");
	       return "stock/stockform"; 
	   }
	   File tempFile = File.createTempFile("DJI", "TEMP");
	   tempFile.deleteOnExit();
	   file.transferTo(tempFile);
	   System.out.println(" tempFile="+tempFile.getAbsolutePath());
	   System.out.println(" tempFile="+tempFile.length()); 
       stockService.importData(tempFile.getAbsolutePath()); 
       model.addAttribute("stockForm", new StockForm());
       model.addAttribute("message", "Successfully uploaded!!");
       return "stock/stockform";
   }
   
   
   @RequestMapping("stock/list/{code}")
   public String listStock(@PathVariable String code, Model model){
	   model.addAttribute("stocks", stockService.getByStock(code));
       return "stock/list";
   }
   
   @RequestMapping("stock/search")
   public String searchStock(@RequestParam(name = "ticker") String code, Model model){
	   model.addAttribute("stocks", stockService.getByStock(code));
       return "stock/list";
   }
   @RequestMapping("/stock/addstock")
   public String addStock(@Valid Stock stock, BindingResult bindingResult,Model model){
	   stockService.saveOrUpdate(stock);
       model.addAttribute("message", "Successfully Saved");
       model.addAttribute("stockForm", new Stock());
       return "stock/addstock";
   }
   
}

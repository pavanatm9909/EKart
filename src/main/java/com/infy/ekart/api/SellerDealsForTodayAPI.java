package com.infy.ekart.api;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.infy.ekart.dto.DealsForTodayDTO;
import com.infy.ekart.dto.ProductDTO;
import com.infy.ekart.entity.DealsForToday;
import com.infy.ekart.exception.EKartException;
import com.infy.ekart.service.DftSellerProductService;

@RestController
@CrossOrigin
@RequestMapping("SellerDealsForTodayAPI")
public class SellerDealsForTodayAPI {
	
	@Autowired
	private DftSellerProductService dftSellerProductService;
	
	@Autowired
	private Environment environment;
	
	static Log Logger = LogFactory.getLog(SellerDealsForTodayAPI.class);

	//for user story 03
	@PostMapping(value ="removeProductInDeals")
	public ResponseEntity<?> removeProductInDeals(@RequestBody DealsForToday dealsForToday)throws Exception{
		try
		{
			Logger.info("REMOVING PRODUCT DETAILS, PRODUCT ID: "+dealsForToday.getProductId());
			Integer deleteProduct = dftSellerProductService.removeProductDeals(dealsForToday);
			Logger.info("PRODUCT DETAILS REMOVED SUCCESSFULLY, PRODUCT ID: "+dealsForToday.getProductId());
			String successMessage ="Product deleted successfully with product id: "+deleteProduct;
			return new ResponseEntity<String>(successMessage,HttpStatus.OK);
				}
		catch (Exception e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,environment.getProperty(e.getMessage()));
		}
	}
	
	@PostMapping(value="addProductInDeal")
	public ResponseEntity<?> addProductInDeal(@RequestBody ProductDetails productDetails) throws EKartException {
		try {
			Logger.info("ADDING PRODUCT TO DEALS FOR TODAY, PRODUCT ID: "+productDetails.getProductId()+"\t SELLER NAME ="+productDetails.getSellerEmailId());
			Integer productId = dftSellerProductService.addProductInDeal(productDetails.getProductId(),productDetails.getSellerEmailId(),productDetails.getDiscount(),productDetails.getStartDateTime(), productDetails.getEndDateTime());
			Logger.info("PRODUCT ADDED SUCCESSFULLY TO THE DEALS FOR TODAY, PRODUCT ID: "+productDetails.getProductId()+"\t SELLER NAME ="+productId);
			String successMessage= environment.getProperty("SellerProductAPI.PRODUCT_ADDED_SUCCESSFULLY")+productDetails.productId;
			return new ResponseEntity<String>(successMessage, HttpStatus.OK);
		}
		catch(EKartException e) 
		{
			if(e.getMessage().contains("Validator")) 
				throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, environment.getProperty(e.getMessage()));
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
		}
		
	}

	@PostMapping(value= "productsCurrentlyBeingSold")
	public ResponseEntity<List<ProductDTO>> productsCurrentlyBeingSold(@RequestBody String sellerEmailId) throws EKartException {
		try {
			List<ProductDTO> productsNotInDeal = dftSellerProductService.getProductListNotInDeal(sellerEmailId);
			return new ResponseEntity<List<ProductDTO>>(productsNotInDeal, HttpStatus.OK);
		}
		catch (EKartException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
		}
	}
	
	@PostMapping(value= "productsInDealList")
	public ResponseEntity<?> productsInDealList(@RequestBody String sellerEmailId) throws EKartException {
		try {
			List<DealsForTodayDTO> productsInDeal = dftSellerProductService.getProductInDealList(sellerEmailId);
			return new ResponseEntity<List<DealsForTodayDTO>>(productsInDeal, HttpStatus.OK);
		}
		catch (EKartException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
		}
	}
	
}

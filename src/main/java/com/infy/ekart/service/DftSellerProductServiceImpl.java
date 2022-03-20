package com.infy.ekart.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.ekart.dto.DealsForTodayDTO;
import com.infy.ekart.dto.ProductDTO;
import com.infy.ekart.entity.DealsForToday;
import com.infy.ekart.entity.Product;
import com.infy.ekart.exception.EKartException;
import com.infy.ekart.repository.DealsForTodayRepository;
import com.infy.ekart.validator.DealsForTodayValidator;

@Service(value = "dftSellerProductService")
@Transactional
public class DftSellerProductServiceImpl implements DftSellerProductService{
	
	@Autowired
	private DealsForTodayRepository dealsForTodayRepository;
		
	@Override
	public Integer removeProductDeals(DealsForToday dealsForToday) throws EKartException{
		Optional<DealsForToday> optional = dealsForTodayRepository.findById(dealsForToday.getDealId());
		DealsForToday dft = optional.orElseThrow(()-> new EKartException("DftSellerProductService.NO_DEAL_FOUND"));
		Integer productId = dft.getProductId();
		dealsForTodayRepository.delete(dft);
		return productId;
	}
	
	@Override
	public List<ProductDTO> getProductListNotInDeal(String sellerEmailId) throws EKartException{
		List<Product> products = dealsForTodayRepository.findBySellersEmailId(sellerEmailId);
		List<ProductDTO> productNotInDealList = new ArrayList<>();
		
		for(Product p : products) {
			ProductDTO pDTO = new ProductDTO();
			pDTO.setProductId(p.getProductId());
			pDTO.setName(p.getName());
			pDTO.setDescription(p.getDescription());
			pDTO.setBrand(p.getBrand());
			pDTO.setCategory(p.getCategory());
			pDTO.setPrice(p.getPrice());
			pDTO.setDiscount(p.getDiscount());
			pDTO.setQuantity(p.getQuantity());
			pDTO.setErrorMessage(null);
			pDTO.setSuccessMessage(null);
			pDTO.setSellerEmailId(p.getSellerEmailId());
			
			productNotInDealList.add(pDTO);
		}
		return productNotInDealList;
	}

	@Override
	public Integer addProductInDeal(Integer productId, String sellerEmailId, Double discount, LocalDateTime startDateTime, LocalDateTime endDateTime) throws EKartException{
		DealsForTodayValidator.validateProductForDeal(startDateTime, endDateTime);
		DealsForToday dft = new DealsForToday();
		dft.setProductId(productId);
		dft.setSellerEmailId(sellerEmailId);
		dft.setDealDiscount(discount);
		dft.setDealStartsAt(startDateTime);
		dft.setDealEndsAt(endDateTime);
		
		DealsForToday deals = dealsForTodayRepository.save(dft);
		
		return deals.getDealId();
	}
	
	@Override
	public List<DealsForTodayDTO> getProductInDealList(String sellerEmailId) throws EKartException{
		List<DealsForToday> dftList = dealsForTodayRepository.findBySellerEmailId(sellerEmailId);
		List<DealsForTodayDTO> productInDealList = new ArrayList<>();

		for(DealsForToday dft: dftList) {
			DealsForTodayDTO dDTO=new DealsForTodayDTO();
			dDTO.setDealId(dft.getDealId());
			dDTO.setProductId(dft.getProductId());
			dDTO.setDealDiscount(dft.getDealDiscount());
			dDTO.setDealStartsAt(dft.getDealStartsAt());
			dDTO.setDealEndsAt(dft.getDealEndsAt());
			dDTO.setSellerEmailId(dft.getSellerEmailId());
			
			productInDealList.add(dDTO);
		}
		return productInDealList;
	}
	
	

}

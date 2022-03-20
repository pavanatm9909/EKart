package com.infy.ekart.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.ekart.dto.DealsForTodayDTO;
import com.infy.ekart.entity.DealsForToday;
import com.infy.ekart.exception.EKartException;
import com.infy.ekart.repository.DealsForTodayRepository;

@Service(value="customerDealForTodayService")
@Transactional
public class CustomerDealForTodayServiceImpl implements CustomerDealForTodayService{
	@Autowired
	private DealsForTodayRepository dealsForTodayRepository;
	
	
	//confused about how to pass the deal of only current day in below method
	
	@Override
	public List<DealsForTodayDTO> getCustomerDeals() throws Exception {
		List<DealsForToday> dftList=dealsForTodayRepository.findAll();
		if(dftList.isEmpty())
			throw new EKartException("CustomerDealForTodayService.NO_DEAL_FOUND");
		
		List<DealsForTodayDTO> productInDeal=new ArrayList<>();
		for(DealsForToday dft:dftList) {
			DealsForTodayDTO dftDTO=new DealsForTodayDTO();
			dftDTO.setDealId(dft.getDealId());
			dftDTO.setProductId(dft.getProductId());
			dftDTO.setDealDiscount(dft.getDealDiscount());
			dftDTO.setDealStartsAt(dft.getDealStartsAt());
			dftDTO.setDealEndsAt(dft.getDealEndsAt());
			dftDTO.setSellerEmailId(dft.getSellerEmailId());
			productInDeal.add(dftDTO);
		}
		
		return productInDeal;
	}

}

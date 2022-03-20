package com.infy.ekart.service;

import java.util.List;

import com.infy.ekart.dto.DealsForTodayDTO;

public interface CustomerDealForTodayService {
	public List <DealsForTodayDTO> getCustomerDeals() throws Exception;

}

package com.infy.ekart.service.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.infy.ekart.dto.DealsForTodayDTO;
import com.infy.ekart.entity.DealsForToday;
import com.infy.ekart.repository.DealsForTodayRepository;
import com.infy.ekart.service.CustomerDealForTodayService;
import com.infy.ekart.service.CustomerDealForTodayServiceImpl;

@SpringBootTest
public class CustomerDealForTodayTest {
	
	@Mock
	private DealsForTodayRepository dftRepository;
	
	@InjectMocks
	private CustomerDealForTodayService customerDealForTodayService =new CustomerDealForTodayServiceImpl();

	@Test
	public void getDealsValid() throws Exception{
		List<DealsForToday> dftList = new ArrayList<>();

		Mockito.when(dftRepository.findAll()).thenReturn(dftList);
		List<DealsForTodayDTO> list = customerDealForTodayService.getCustomerDeals();
		Assert.assertNotNull(list);
	}
	
}

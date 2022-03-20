package com.infy.ekart.service.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.infy.ekart.dto.DealsForTodayDTO;
import com.infy.ekart.entity.DealsForToday;
import com.infy.ekart.exception.EKartException;
import com.infy.ekart.repository.DealsForTodayRepository;
import com.infy.ekart.service.DftSellerProductService;
import com.infy.ekart.service.DftSellerProductServiceImpl;



@SpringBootTest
public class DftSellerProductServiceTest {
	
	@Mock
	private DealsForTodayRepository dealsForTodayRepository;
	
	@InjectMocks
	private DftSellerProductService dftSellerProductService = new DftSellerProductServiceImpl();
	
	@Test
	public void addProductInDealValid() throws EKartException{
		LocalDateTime dealStarts = LocalDateTime.now().plusDays(1).withNano(0);
		LocalDateTime dealEnds = LocalDateTime.now().plusDays(1).plusMinutes(50).withNano(0);
		
		DealsForToday dft = new DealsForToday();
		dft.setDealId(101);
		dft.setProductId(1001);
		dft.setSellerEmailId("john@gmail.com");
		dft.setDealDiscount(15.0);
		dft.setDealStartsAt(dealStarts);
		dft.setDealEndsAt(dealEnds);
		
		Mockito.when(dealsForTodayRepository.save(Mockito.any())).thenReturn(dft);
		Assertions.assertEquals(101,dftSellerProductService.addProductInDeal(1001, "john@gmail.com", 15.0, dealStarts, dealEnds));
	}
	
	@Test
	public void viewDealsForTodayListValid() throws EKartException{
		String emailId = "jack@gmail.com";
		List<DealsForToday> dftList = new ArrayList<>();

		Mockito.when(dealsForTodayRepository.findBySellerEmailId(emailId)).thenReturn(dftList);
		List<DealsForTodayDTO> list = dftSellerProductService.getProductInDealList(emailId);
		Assert.assertNotNull(list);
	}

}

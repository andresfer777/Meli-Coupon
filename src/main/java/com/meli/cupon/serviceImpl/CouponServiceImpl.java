package com.meli.cupon.serviceImpl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meli.cupon.model.dto.CouponBodyDto;
import com.meli.cupon.model.dto.CouponResponseDto;
import com.meli.cupon.model.entity.ItemEntity;
import com.meli.cupon.repository.ItemRepository;
import com.meli.cupon.service.CouponService;

@Service
public class CouponServiceImpl implements CouponService {

	@Autowired
	ItemRepository itemRepository;
	
	@Override
	public CouponResponseDto maximizarInversionDeCupon(CouponBodyDto couponBodyDto) {
		List<String> itemIds = Arrays.asList(couponBodyDto.getItemIds());
		
		List<ItemEntity> items = itemRepository.obtenerItemsOrdenados(itemIds);
		
		return new CouponResponseDto(couponBodyDto.getItemIds(), couponBodyDto.getAmount());
	}

}

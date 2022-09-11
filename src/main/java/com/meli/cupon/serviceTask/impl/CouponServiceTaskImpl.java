package com.meli.cupon.serviceTask.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meli.cupon.model.dto.CouponBodyDto;
import com.meli.cupon.model.dto.CouponResponseDto;
import com.meli.cupon.service.CouponService;
import com.meli.cupon.serviceTask.CouponServiceTask;

@Service
public class CouponServiceTaskImpl implements CouponServiceTask {

	@Autowired
	CouponService couponService;
	
	@Override
	public ResponseEntity<CouponResponseDto> maximizarInversionDeCupon(CouponBodyDto couponBodyDto) {
		return new ResponseEntity<CouponResponseDto>(couponService.maximizarInversionDeCupon(couponBodyDto), HttpStatus.OK);
	}

}

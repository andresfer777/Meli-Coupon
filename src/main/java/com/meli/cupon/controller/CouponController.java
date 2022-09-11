package com.meli.cupon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.meli.cupon.model.dto.CouponBodyDto;
import com.meli.cupon.model.dto.CouponResponseDto;
import com.meli.cupon.serviceTask.CouponServiceTask;

@RestController
public class CouponController {
	
	@Autowired
	CouponServiceTask couponServiceTask;
	
	@PostMapping("/coupon")
	public ResponseEntity<CouponResponseDto> obtenerListaArticulos(@RequestBody CouponBodyDto couponBodyDto){
		return couponServiceTask.maximizarInversionDeCupon(couponBodyDto);
	}
}

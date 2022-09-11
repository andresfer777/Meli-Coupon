package com.meli.cupon.serviceTask;

import org.springframework.http.ResponseEntity;

import com.meli.cupon.model.dto.CouponBodyDto;
import com.meli.cupon.model.dto.CouponResponseDto;

public interface CouponServiceTask {
	public ResponseEntity<CouponResponseDto> maximizarInversionDeCupon(CouponBodyDto couponBodyDto);
}

package com.meli.cupon.service;

import com.meli.cupon.model.dto.CouponBodyDto;
import com.meli.cupon.model.dto.CouponResponseDto;

public interface CouponService {
	public CouponResponseDto maximizarInversionDeCupon(CouponBodyDto couponBodyDto);
}

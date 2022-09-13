package com.meli.cupon.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.meli.cupon.model.dto.CouponBodyDto;
import com.meli.cupon.model.dto.CouponResponseDto;
import com.meli.cupon.model.dto.ItemDto;
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
		
		ModelMapper modelMapper = new ModelMapper();
		List<ItemDto> itemDtos = Arrays.asList(modelMapper.map(items, ItemDto[].class));
		
		Collections.sort(itemDtos);
		
		List<ItemDto> itemsSeleccionados = this.optimizarItems(itemDtos, couponBodyDto);
		
		String[] codigosSeleccionados = obtenerCodigos(itemsSeleccionados);
		
		return new CouponResponseDto(codigosSeleccionados, couponBodyDto.getAmount());
	}

	private String[] obtenerCodigos(List<ItemDto> itemsSeleccionados) {
		String[] codigos = new String[itemsSeleccionados.size()];
		int indice = 0;
		for (ItemDto itemDto : itemsSeleccionados) {
			if(!ObjectUtils.isEmpty(itemDto)) {
				codigos[indice] = itemDto.getItemId();
				indice++;
			}
		}
		return codigos;
	}

	private List<ItemDto> optimizarItems(List<ItemDto> itemDtos, CouponBodyDto couponBodyDto) {
		Double amountTotal = sumarItems(itemDtos);
		Double diferencia = amountTotal - couponBodyDto.getAmount();
		
		if (diferencia <= 0) {
			return itemDtos;
		}else {
			return depurarLista(new ArrayList<>(itemDtos), diferencia, couponBodyDto.getAmount());
		}
	}

	private List<ItemDto> depurarLista(List<ItemDto> itemDtos, Double diferencia, Double amount) {
		for (int rango = 1; rango <= itemDtos.size()/2; rango++) {
			for(int pivote = 0; pivote <= itemDtos.size()-rango; pivote++) {
				if(sumarPorRango(itemDtos, rango, pivote) > diferencia) {
					return quitarRango(itemDtos, rango, pivote);
				}
			}
		}
		return itemDtos;
	}
	
	private Double sumarPorRango(List<ItemDto> itemDtos, int rango, int pivote) {
		Double subtotal = 0D;
		for(int i = pivote; i < pivote + rango; i++) {
			subtotal += itemDtos.get(i).getPrice();
		}
		return subtotal;
	}
	
	private List<ItemDto> quitarRango(List<ItemDto> itemDtos, int rango, int pivote) {
		for(int i = pivote; i < pivote + rango; i++) {
			itemDtos.set(i, null);
		}
		return itemDtos;
	}

	private Double sumarItems(List<ItemDto> itemDtos) {
		Double amountTotal = 0D;
		for (ItemDto itemDto : itemDtos) {
			if(!ObjectUtils.isEmpty(itemDto)) {
				amountTotal += itemDto.getPrice();
			}
		}
		return amountTotal;
	}

}

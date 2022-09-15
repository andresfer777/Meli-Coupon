package com.meli.cupon.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
		CouponResponseDto couponResponseDto = new CouponResponseDto();
		/*PASO 1: Obtener la lista ordenada de los ítems solicitados por el usuario mediante los item_ids*/
		List<ItemDto> itemDtoList = obtenerItemDtos(couponBodyDto.getItemIds());
		
		/*PASO 2: Obtener un map con los objetos obtenidos en la lista*/
		Map<String, ItemDto> itemDtoMap = obtenerItemDtoMap(itemDtoList);
		
		/*PASO 3: Obtener el total de los precios de los ítems seleccionados*/
		Double totalSeleccionado = sumarItems(itemDtoList);
		
		/*PASO 4: Depurar los ítems que serán aceptados*/
		if(totalSeleccionado - couponBodyDto.getAmount() <= 0) {
			couponResponseDto.setItemIds(couponBodyDto.getItemIds());
			couponResponseDto.setTotal(totalSeleccionado);
		}else {
			List<String> itemIdSeleccionadosList = depurarItems(itemDtoList, itemDtoMap, totalSeleccionado, couponBodyDto.getAmount());
			couponResponseDto.setItemIds(obtenerVector(itemIdSeleccionadosList));
			couponResponseDto.setTotal(obtenerSumatoria(couponResponseDto.getItemIds(), itemDtoMap));
		}
		
		return couponResponseDto;
	}

	private Double obtenerSumatoria(String[] itemIds, Map<String, ItemDto> itemDtoMap) {
		Double total = 0D;
		for(int indice = 0; indice < itemIds.length; indice++) {
			total = total + itemDtoMap.get(itemIds[indice]).getPrice();
		}
		return total;
	}

	private String[] obtenerVector(List<String> itemIdSeleccionadosList) {
		/*Paso 5.1: Convertir la lista de códigos de ítems en Strings*/
		return itemIdSeleccionadosList.toArray(new String[itemIdSeleccionadosList.size()]);
	}

	private List<String> depurarItems(List<ItemDto> itemDtoList, Map<String, ItemDto> itemDtoMap, Double totalSeleccionado, Double montoCupon) {
		Double diferencia = totalSeleccionado - montoCupon;
		Double sumaDeRango = 0D;
		for (int ratio = 1; ratio <= itemDtoList.size(); ratio++) {
			for(int pivote = 0; pivote <= itemDtoList.size()-ratio; pivote++) {
				sumaDeRango = sumarItems(itemDtoList.subList(pivote, pivote + ratio));
				if(sumaDeRango > totalSeleccionado || sumaDeRango > diferencia) {
					return eliminarRango(itemDtoList.subList(pivote, pivote + ratio), itemDtoList, itemDtoMap);
				}
			}
		}
		
		return null;
	}

	private List<String> eliminarRango(List<ItemDto> itemsEliminados, List<ItemDto> itemDtoList, Map<String, ItemDto> itemDtoMap) {
		for(ItemDto itemDtoPrincipal : itemDtoList) {
			for (ItemDto itemDtoEliminado : itemsEliminados) {
				if(itemDtoPrincipal.equals(itemDtoEliminado)) {
					itemDtoMap.remove(itemDtoPrincipal.getItemId());
				}
			}
		}
		
		return new ArrayList<String>(itemDtoMap.keySet());
	}

	private Double sumarItems(List<ItemDto> itemDtoList) {
		Double total = 0D;
		/*Paso 3.1: Calcular la sumatoria de los precios de los items*/
		total = itemDtoList.stream().mapToDouble(ItemDto::getPrice).sum();
		return total;
	}

	private Map<String, ItemDto> obtenerItemDtoMap(List<ItemDto> itemDtoList) {
		/*Paso 2.1: Obtener un mapa derivado de la lista*/
		return itemDtoList.stream().collect(Collectors.toMap(ItemDto::getItemId, Function.identity()));
	}

	private List<ItemDto> obtenerItemDtos(String[] itemIds) {
		/*Paso 1.1: Obtener la lista de los itemIds basada en el vector recibido*/
		List<String> itemIdsList = Arrays.asList(itemIds);
		
		/*Paso 1.2: Consultar en la base de datos los ítems seleccionados con sus precios*/
		List<ItemEntity> items = itemRepository.obtenerItems(itemIdsList);
		
		/*Paso 1.3: Obtener los DTO para separar las entidades de la capa de negocio*/
		ModelMapper modelMapper = new ModelMapper();
		List<ItemDto> itemDtos = Arrays.asList(modelMapper.map(items, ItemDto[].class));
		
		/*Paso 1.4; Ordenar los resultados para la ejecución del algoritmo*/
		Collections.sort(itemDtos);
		
		return itemDtos;
	}
	
	

}

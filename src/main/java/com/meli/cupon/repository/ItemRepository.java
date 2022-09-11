package com.meli.cupon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.meli.cupon.model.dto.ItemDto;
import com.meli.cupon.model.entity.ItemEntity;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, String>{

	@Query("SELECT i FROM ItemEntity i WHERE i.itemId IN :itemIds ORDER BY i.price ASC")
	List<ItemEntity> obtenerItemsOrdenados(@Param("itemIds") List<String> itemIds);
	
}

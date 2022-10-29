package com.pro2111.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.entities.Option;
import com.pro2111.entities.Product;

public interface OptionRepository extends JpaRepository<Option, Integer> {

	Option findByOptionNameLike(String nameOption);

	List<Option> findByStatusLike(int status);
	
	@Query("SELECT o FROM Option o WHERE o.status = 1 AND NOT EXISTS (SELECT po FROM ProductOption po WHERE po.options = o AND po.products = :product)")
	List<Option> findOptionNotExistsProductOption(@Param("product") Product product);
	
	@Query("SELECT o FROM Option o WHERE o.optionName LIKE %:name%")
	List<Option> findByApproximateName(@Param("name")String name);
	
}

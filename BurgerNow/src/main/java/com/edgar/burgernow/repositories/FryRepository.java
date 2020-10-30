package com.edgar.burgernow.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.edgar.burgernow.models.Fry;

@Repository
public interface FryRepository extends CrudRepository <Fry, Long> {
	List<Fry> findAll();
	

}

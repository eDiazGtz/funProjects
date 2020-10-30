package com.edgar.burgernow.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edgar.burgernow.models.Fry;
import com.edgar.burgernow.repositories.FryRepository;


@Service
public class FryService {
	@Autowired
	private FryRepository fRepo;
	
	//Find All Fries
	public List<Fry> findAllFries() {
		List<Fry> allBurgers = this.fRepo.findAll();
		return allBurgers;
	}
	
	//Find One Fry
	public Fry findOneFry(Long id) {
		Fry fry = this.fRepo.findById(id).orElse(null);
		return fry;
	}
	
	
	//Save Fry
	public Fry saveFry(Fry newFry) {
		return this.fRepo.save(newFry);
	}
	
	//Delete Fry
	public void deleteFry(Long id) {
		this.fRepo.deleteById(id);
	}
	
}

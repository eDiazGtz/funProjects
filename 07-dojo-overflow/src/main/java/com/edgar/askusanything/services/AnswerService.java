package com.edgar.askusanything.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edgar.askusanything.models.Answer;
import com.edgar.askusanything.repositories.AnswerRepository;

@Service
public class AnswerService {
	@Autowired
	private AnswerRepository aRepo;

	public Answer createAnswer(Answer newAnswer) {
		Answer answer = this.aRepo.save(newAnswer);
		return answer;
	}
}

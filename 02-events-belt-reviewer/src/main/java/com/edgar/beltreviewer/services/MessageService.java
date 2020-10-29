package com.edgar.beltreviewer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edgar.beltreviewer.models.Message;
import com.edgar.beltreviewer.repositories.MessageRepository;

@Service
public class MessageService {
	@Autowired
	private MessageRepository mRepo;

	public Message createMessage(Message newMessage) {
		Message message = this.mRepo.save(newMessage);
		return message;
	}
}

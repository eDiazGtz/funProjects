package com.edgar.burgernow.validatiors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.edgar.burgernow.models.User;
import com.edgar.burgernow.repositories.UserRepository;

@Component
public class UserValidator {
	@Autowired
	private UserRepository uRepo;
	
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }
    
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        
        if(!user.getPassword().equals(user.getConfirmPassword())) {
        	//first field is a PATH to the ATTRIBUTE on the MODEL -- password is attr, passwordError isn't == that's why it broke
        	errors.rejectValue("password", "match", "Passwords must match");
        }
        
        //Make sure Email is unique in the DB
        if(this.uRepo.existsByEmail(user.getEmail())) {
        	errors.rejectValue("email", "unique", "Email already exists");
        }
        
        
        
        
        
    }
	
	
	
}

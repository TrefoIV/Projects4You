package it.uniroma3.projects4you.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.projects4you.controller.session.SessionData;
import it.uniroma3.projects4you.model.User;
import it.uniroma3.projects4you.services.UserService;

@Component
public class UserValidator implements Validator {

	final Integer MAX_NAME_LENGTH = 100;
	final Integer MIN_NAME_LENGTH = 2;
	
	@Autowired
	private UserService userService;
	@Autowired
	private SessionData sessionData;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		User user = (User) o;
		String firstname = user.getFirstname().trim();
		String lastname = user.getLastname().trim();
		String nickname = user.getNickname().trim();
		
		if(nickname.isEmpty())
			errors.rejectValue("nickname", "required");
		else if(nickname.length()<MIN_NAME_LENGTH || nickname.length() > MAX_NAME_LENGTH)
			errors.rejectValue("nickname", "size");
		else if(this.userService.getUser(nickname) != null)
			errors.rejectValue("nickname", "duplicate");
			
		if(firstname.isEmpty())
			errors.rejectValue("firstname", "required");
		else if(firstname.length() < MIN_NAME_LENGTH || firstname.length() > MAX_NAME_LENGTH)
			errors.rejectValue("firstname", "size");
		
		if(lastname.isEmpty())
			errors.rejectValue("lastname", "required");
		else if(lastname.length() < MIN_NAME_LENGTH || lastname.length() > MAX_NAME_LENGTH)
			errors.rejectValue("lastname", "size");
		
	}
	
	public void validateUpdate(User user, Errors errors) {
		String firstname = user.getFirstname().trim();
		String lastname = user.getLastname().trim();
		String nickname = user.getNickname().trim();
		User userLoggato = this.sessionData.getLoggedUser();
		
		if(nickname.isEmpty())
			errors.rejectValue("nickname", "required");
		else if(nickname.length()<MIN_NAME_LENGTH || nickname.length() > MAX_NAME_LENGTH)
			errors.rejectValue("nickname", "size");
		else if(userLoggato != null) {
				if (!userLoggato.getNickname().equals(nickname) && this.userService.getUser(nickname) != null)
					errors.rejectValue("nickname", "duplicate");
		}else if(this.userService.getUser(nickname) != null)
			errors.rejectValue("nickname", "duplicate");
			
		if(firstname.isEmpty())
			errors.rejectValue("firstname", "required");
		else if(firstname.length() < MIN_NAME_LENGTH || firstname.length() > MAX_NAME_LENGTH)
			errors.rejectValue("firstname", "size");
		
		if(lastname.isEmpty())
			errors.rejectValue("lastname", "required");
		else if(lastname.length() < MIN_NAME_LENGTH || lastname.length() > MAX_NAME_LENGTH)
			errors.rejectValue("lastname", "size");
	}

}

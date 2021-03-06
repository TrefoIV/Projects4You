package it.uniroma3.projects4you.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import it.uniroma3.projects4you.model.Project;
import it.uniroma3.projects4you.model.Task;

@Component
public class TaskValidator implements org.springframework.validation.Validator{

	public static final int MAX_NAME_SIZE = 100;
	public static final int MAX_DESCRIPTION_SIZE = 2500;
	public static final int MIN_NAME_SIZE = 2;
	
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Task.class.equals(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		Task task = (Task) target;
		
		if(task.getName().isEmpty())
			errors.rejectValue("name", "required");
		if(task.getName().length() < MIN_NAME_SIZE || task.getName().length()> MAX_NAME_SIZE)
			errors.rejectValue("name", "size");
		
		if(task.getDescription().length() > MAX_DESCRIPTION_SIZE)
			errors.rejectValue("description", "size");
		
	}

	public void validateEdit(Project project, Task taskDaModificare, Task taskModifiche, Errors errors) {
		
		if(!taskDaModificare.getProject().equals(project)) {
			errors.rejectValue(null, "nonAppartenente");
		}
		this.validate(taskModifiche, errors);
		
		
	}
	
	
	
}

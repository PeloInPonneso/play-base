package forms;

import java.util.ArrayList;
import java.util.List;

import models.Role;
import models.User;
import play.data.validation.ValidationError;
import play.i18n.Messages;

public class UserFormData {
	
	public String username = "";
	public List<String> authorities = new ArrayList<>(); 
	
	public UserFormData() {
		super();
	}

	public UserFormData(User user) {
		super();
		this.username = user.getUsername();
		for (Role role : user.getAuthorities()) {
			authorities.add(role.getAuthority());
		}
	}

	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		if(username==null || "".equals(username)) {
			errors.add(new ValidationError("username", Messages.get("login.page.username.not.null")));
		}
		if(errors.size() > 0) return errors;
		return null;
	}
	
}

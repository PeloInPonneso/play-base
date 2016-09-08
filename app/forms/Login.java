package forms;

import java.util.ArrayList;
import java.util.List;

import play.data.validation.ValidationError;
import play.i18n.Messages;


public class Login {
	
	public String username;
	public String password;
	
	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		if(username==null || "".equals(username)) {
			errors.add(new ValidationError("username", Messages.get("login.page.username.not.null")));
		}
		if(password==null || "".equals(password)) {
			errors.add(new ValidationError("password", Messages.get("login.page.password.not.null")));
		}
		if(errors.size() > 0) return errors;
		return null;
	}
}

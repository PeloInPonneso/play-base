package controllers;


import java.util.ArrayList;
import java.util.List;

import models.User;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import persistence.service.IUserService;
import play.data.Form;
import play.data.validation.ValidationError;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.login;
import forms.Login;

@org.springframework.stereotype.Controller
public class Application extends Controller {
	
	@Autowired
	private IUserService userService;
	
	public Result login() {
	    return play.mvc.Controller.ok(login.render(null, Form.form(Login.class)));
	}
	
    public Result index() {
        return play.mvc.Controller.ok(index.render(null));
    }
    
    public Result authenticate() {
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return play.mvc.Controller.badRequest(login.render(null, loginForm));
        } else {
        	
        	User user = userService.loadUserByUsername(loginForm.get().username);
            if(user==null) {
            	List<ValidationError> errors = new ArrayList<ValidationError>();
            	errors.add(new ValidationError("user", "not found"));
            	loginForm.errors().put("username", errors);
            	return play.mvc.Controller.badRequest(login.render(null, loginForm));
            } else if(user!=null && !user.getPassword().equals(Hashing.sha256().hashString(loginForm.get().password, Charsets.UTF_8).toString())) {
            	List<ValidationError> errors = new ArrayList<ValidationError>();
            	errors.add(new ValidationError("password", "wrong password"));
            	loginForm.errors().put("password", errors);
            	return play.mvc.Controller.badRequest(login.render(null, loginForm));
            } else {
            	play.mvc.Controller.session().clear();
            	play.mvc.Controller.session("username", loginForm.get().username);
                return play.mvc.Controller.redirect(routes.Application.index());
            }
        }
    }
    
    public Result logout() {
    	play.mvc.Controller.session().clear();
        return play.mvc.Controller.redirect(routes.Application.login());
    }
}

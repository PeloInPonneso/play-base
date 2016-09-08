package controllers;

import java.util.List;

import models.Role;
import models.User;

import org.springframework.beans.factory.annotation.Autowired;

import persistence.service.IUserService;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import views.html.editRole;
import views.html.editUser;
import views.html.roles;
import views.html.users;
import web.Page;
import forms.UserFormData;
import forms.util.UserFormUtil;

@org.springframework.stereotype.Controller
public class Administration extends Controller {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserFormUtil userFormUtil;
	
	public static Result GO_USERS = play.mvc.Controller.redirect(routes.Administration.users(0));
	public static Result GO_ROLES = play.mvc.Controller.redirect(routes.Administration.roles(0));
	
	@Authenticated(Secured.class)
	public Result users(int page) {
		int pageSize = 10;
		if(page < 1) page = 1;
		int total = userService.countUsers();
		List<User> data = userService.loadUsers((page - 1) * pageSize, pageSize);
		Page<User> userPage = new Page<User>(data, total, page, pageSize);
        return play.mvc.Controller.ok(users.render(userPage));
    }
	
	@Authenticated(Secured.class)
    public Result editUser(Long id) {
		User user = userService.findUserById(id);
		Form<UserFormData> userFormData = Form.form(UserFormData.class).fill(new UserFormData(user));
		return play.mvc.Controller.ok(editUser.render(id, userFormData, userFormUtil.makeRoleMap(user)));
	}
	
	@Authenticated(Secured.class)
    public Result updateUser(Long id) {
		User user = userService.findUserById(id);
		Form<UserFormData> userFormData = Form.form(UserFormData.class).bindFromRequest();
        if(userFormData.hasErrors()) {
            return play.mvc.Controller.badRequest(editUser.render(id, userFormData, userFormUtil.makeRoleMap(user)));
        }
        user.setUsername(userFormData.get().username);
        user.setAuthorities(userFormUtil.makeRoleList(userFormData.get().authorities));
        userService.updateUser(user);
        return GO_USERS;
	}
	
	@Authenticated(Secured.class)
    public Result deleteUser(Long id) {
    	userService.deleteUser(id);
    	return GO_USERS;
	}
    
	@Authenticated(Secured.class)
	public Result roles(int page) {
		int pageSize = 10;
		if(page < 1) page = 1;
		int total = userService.countRoles();
		List<Role> data = userService.loadRoles((page - 1) * pageSize, pageSize);
		Page<Role> rolePage = new Page<Role>(data, total, page, pageSize);
        return play.mvc.Controller.ok(roles.render(rolePage));
    }
	
	@Authenticated(Secured.class)
    public Result editRole(Long id) {
		Form<Role> roleForm = Form.form(Role.class).fill(userService.findRoleById(id));
		return play.mvc.Controller.ok(editRole.render(id, roleForm));
	}
	
	@Authenticated(Secured.class)
    public Result updateRole(Long id) {
		Form<Role> roleForm = Form.form(Role.class).bindFromRequest();
        if(roleForm.hasErrors()) {
            return play.mvc.Controller.badRequest(editRole.render(id, roleForm));
        }
        userService.updateRole(roleForm.get());
        return GO_ROLES;
	}
	
	@Authenticated(Secured.class)
    public Result deleteRole(Long id) {
    	userService.deleteRole(id);
    	return GO_ROLES;
	}
	
}

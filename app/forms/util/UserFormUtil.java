package forms.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import models.Role;
import models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import persistence.service.IUserService;

@Component
public class UserFormUtil {

	@Autowired
	private IUserService userservice;
	
	public Map<String, Boolean> makeRoleMap(User user) {
		List<Role> allRoles = userservice.getAllRoles();
	    Map<String, Boolean> roleMap = new HashMap<String, Boolean>();
	    for (Role role : allRoles) {
	    	roleMap.put(role.getAuthority(), (user != null && user.getAuthorities().contains(role)));
	    }
	    return roleMap;
	}
	
	public Set<Role> makeRoleList(List<String> authorities) {
		Set<Role> roles = new HashSet<Role>();
		for (String authority : authorities) {
			roles.add(userservice.loadRoleByAuthority(authority));
		}
		return roles;
	}
}

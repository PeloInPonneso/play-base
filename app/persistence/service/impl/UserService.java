package persistence.service.impl;

import java.util.Date;
import java.util.List;

import models.Role;
import models.User;

import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.dao.IRoleDao;
import persistence.dao.IUserDao;
import persistence.service.IUserService;

@Service
@Transactional(rollbackFor=Exception.class)
public class UserService implements IUserService {
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IRoleDao roleDao;
	
	public User loadUserByUsername(String username) {
		User user = userDao.findByUsername(username); 
		if(user!=null) Hibernate.initialize(user.getAuthorities());
		return user;
	}
	
	public Role loadRoleByAuthority(String authority) {
		return roleDao.findByAuthority(authority);
	}
	
	public User findUserById(Long id) {
		User user = userDao.findOne(id);
		if(user!=null) Hibernate.initialize(user.getAuthorities());
		return user;
	}
	
	public Role findRoleById(Long id) {
		return roleDao.findOne(id);
	}
	
	public List<User> getAllUsers() {
		return userDao.findAll();
	}
	
	public List<User> getAllUsersWithRoles() {
		List<User> users = userDao.findAll();
		for (User user : users) {
			Hibernate.initialize(user.getAuthorities());
		}
		return users;
	}
	
	public List<Role> getAllRoles() {
		return roleDao.findAll();
	}
	
	public List<Role> getAllRolesWithUsers() {
		List<Role> roles = roleDao.findAll();
		for (Role role : roles) {
			Hibernate.initialize(role.getUsers());
		}
		return roles;
	}
	
	public void createUser(User user) {
		user.setCreationDate(new Date());
		user.setLastModifyDate(new Date());
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		userDao.create(user);
	}
	
	public void createRole(Role role) {
		roleDao.create(role);
	}
	
	public void updateUser(User user) {
		user.setLastModifyDate(new Date());
		userDao.update(user);
	}
	
	public void updateRole(Role role) {
		roleDao.update(role);
	}
	
	public void deleteUser(Long id) {
		userDao.deleteById(id);
	}
	
	public void deleteRole(Long id) {
		Role role = roleDao.findOne(id);
		for (User user : role.getUsers()) {
			user.getAuthorities().remove(role);
		}
		roleDao.delete(role);
	}
	
	public void purgeUnconfirmedUsers() {
		List<User> unconfirmedUsers = userDao.findUnconfirmedUsers();
		for (User unconfirmedUser : unconfirmedUsers) {
			userDao.delete(unconfirmedUser);
		}
	}
	
	public int countUsers() {
		return userDao.countAll();
	}
	
	public int countRoles() {
		return roleDao.countAll();
	}
	
	public List<User> loadUsers(int first, int pageSize) {
		return userDao.findByCriteria(first, pageSize);
	}
	
	public List<Role> loadRoles(int first, int pageSize) {
		return roleDao.findByCriteria(first, pageSize);
	}
	
	public List<Role> findRolesByIds(Long... id) {
		return roleDao.findByCriteria(Restrictions.in("id", id));
	}
}

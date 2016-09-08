package persistence.dao;

import java.util.List;

import models.User;
import persistence.IOperations;

public interface IUserDao extends IOperations<User> {
	
	public User findByUsername(String username);
	
	public List<User> findUnconfirmedUsers();
}

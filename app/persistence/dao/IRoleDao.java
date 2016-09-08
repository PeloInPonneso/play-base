package persistence.dao;

import models.Role;
import persistence.IOperations;

public interface IRoleDao extends IOperations<Role> {

	public Role findByAuthority(String authority);
	
}

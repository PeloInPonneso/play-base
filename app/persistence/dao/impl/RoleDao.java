package persistence.dao.impl;

import models.Role;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import persistence.AbstractHibernateDao;
import persistence.dao.IRoleDao;

@Repository
public class RoleDao extends AbstractHibernateDao<Role> implements IRoleDao {
	
	public Role findByAuthority(String authority) {
		return findByCriteria(Restrictions.eq("authority", authority)).get(0);
	}
	
}

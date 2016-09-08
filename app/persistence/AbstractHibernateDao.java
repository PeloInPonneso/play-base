package persistence;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;

import com.google.common.base.Preconditions;

public abstract class AbstractHibernateDao<T extends Serializable> implements IOperations<T> {
	
    private Class<T> type;

    @PersistenceContext(unitName="jpa-persistence")
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public AbstractHibernateDao() {
    	Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class<T>) pt.getActualTypeArguments()[0];
	}
    
	@SuppressWarnings("unchecked")
	public final T findOne(final long id) {
        return ((T) getCurrentSession().get(type, id));
    }

	@SuppressWarnings("unchecked")
    public final List<T> findAll() {
        return getCurrentSession().createQuery("from " + type.getName()).list();
    }
	
	public int countAll() {
		return countByCriteria();
	}
	
	public final List<T> findByCriteria(final Criterion... criterions) {
		return findByCriteria(-1, -1, criterions);
	}
	
	public final T findOneByCriteria(final Criterion... criterions) {
		List<T> list = findByCriteria(0, 1, criterions);
		if(list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public final List<T> findByCriteria(final int firstResult, final int maxResults, final Criterion... criterions) {
		Criteria crit = getCurrentSession().createCriteria(type);
		for (Criterion criterion : criterions) {
			crit.add(criterion);
		}
		if (firstResult > 0) {
			crit.setFirstResult(firstResult);
		}

		if (maxResults > 0) {
			crit.setMaxResults(maxResults);
		}
		return crit.list();
    }
	
	public int countByCriteria(final Criterion... criterions) {
		Criteria crit = getCurrentSession().createCriteria(type);
		crit.setProjection(Projections.rowCount());
		for (final Criterion criterion : criterions) {
			crit.add(criterion);
		}
		return ((Long) crit.uniqueResult()).intValue();
	}
	
    public final void create(final T entity) {
        Preconditions.checkNotNull(entity);
        getCurrentSession().persist(entity);
    }

    @SuppressWarnings("unchecked")
    public final T update(final T entity) {
        Preconditions.checkNotNull(entity);
        return (T) getCurrentSession().merge(entity);
    }

    public final void delete(final T entity) {
        Preconditions.checkNotNull(entity);
        getCurrentSession().delete(entity);
    }

    public final void deleteById(final long entityId) {
        final T entity = findOne(entityId);
        Preconditions.checkState(entity != null);
        delete(entity);
    }

    protected final Session getCurrentSession() {
    	return (Session) entityManager.getDelegate();
    }

}

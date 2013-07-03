package com.medialab.services.generic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public abstract class GenericService<T extends Serializable, TPk extends Serializable>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericService.class);
	
	private Class<T> clazz;

    @PersistenceContext
    private EntityManager em;

    protected void setClazz(final Class<T> clazzToSet)
    {
        clazz = clazzToSet;
    }

    @Transactional(readOnly = true)
    public T findOne(final TPk entityId)
    {
        assert (entityId != null);
        return ((T) getEntityManager().find(clazz, entityId));
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<T> findAll()
    {
        return getEntityManager().createQuery("from " + clazz.getName()).getResultList();
    }

    @SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
    public List<T> findAll(String orderBy)
    {
        return getEntityManager().createQuery("from " + clazz.getName() + " ORDER BY " + orderBy).getResultList();
    }

    @Transactional
    public void save(final T entity)
    {
        assert (entity != null);
        getEntityManager().persist(entity);
    }

    @Transactional
    public void merge(final T entity)
    {
        assert (entity != null);
        getEntityManager().merge(entity);
    }

    @Transactional
    public void delete(final T entity)
    {
        assert (entity != null);
        getEntityManager().remove(entity);
    }
    
    @Transactional
    public void flush() {
    	getEntityManager().flush();
    }

    @Transactional
    public void deleteById(final TPk entityId)
    {
        final T entity = findOne(entityId);
        assert (entity != null);
        delete(entity);
    }
    
    public int executeUpdate(final Query query)
    {
        return query.executeUpdate();
    }

    /**
     * @param namedQuery
     * @param parameters
     * @return
     */
    public int executeUpdate(final String namedQuery, final List<? extends Object> parameters)
    {
        return executeUpdate(createNamedQuery(namedQuery, parameters));
    }

    protected int executeSqlQueryUpdate(final String query)
    {
    	return getEntityManager().createNativeQuery(query).executeUpdate();
    }
    
    protected int executeQueryUpdate(final String query)
    {
    	return getEntityManager().createNamedQuery(query).executeUpdate();
    }
    
    /**
     * 
     * @param args
     * @return
     */
    protected List<Object> createParameterList(Object... args)
    {
        List<Object> objects = new ArrayList<Object>();
        for (Object object : args)
        {
            objects.add(object);
        }
        return objects;
    }
    
    /**
     * 
     * @param namedQuery
     * @param parameters
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    protected T getByNamedQuery(final String namedQuery, final List<? extends Object> parameters)
    {
        try
        {
        	return (T) this.createNamedQuery(namedQuery, parameters).getSingleResult();
        }
        catch (NoResultException ex)
        {
        	LOGGER.error(String.format("Error retrieving entity[%s]: %s", clazz.getName(), ex.getMessage()));
        	return null;
        }
    }
    
    /**
     * 
     * @param namedQuery
     * @param parameters
     * @return
     */
    @Transactional(readOnly = true)
    protected Boolean getBooleanByNamedQuery(final String namedQuery, final List<? extends Object> parameters)
    {
        try
        {
        	return (Boolean) this.createNamedQuery(namedQuery, parameters).getSingleResult();
        }
        catch (NoResultException ex)
        {
        	LOGGER.error(String.format("Error retrieving entity[%s]: %s", clazz.getName(), ex.getMessage()));
        	return null;
        }
    }
    
    /**
     * 
     * @param namedQuery
     * @param parameters
     * @param maxResult
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    protected T getByNamedQuery(final String namedQuery, final List<? extends Object> parameters, int maxResult)
    {
        try
        {
        	return (T) this.createNamedQuery(namedQuery, parameters).setMaxResults(maxResult).getSingleResult();
        }
        catch (NoResultException ex)
        {
        	LOGGER.error(String.format("Error retrieving entity[%s]: %s", clazz.getName(), ex.getMessage()));
        	return null;
        }
    }

    /**
     * 
     * @param sqlQuery
     * @param parameters
     * @return
     */
    @Transactional(readOnly = true)
    protected Object getBySqlQuery(final String sqlQuery, final List<? extends Object> parameters)
    {
    	try
    	{
    		return this.createNativeQuery(sqlQuery, parameters).getSingleResult();
    	}
        catch (NoResultException ex)
        {
        	LOGGER.error(String.format("Error retrieving entity[%s]: %s", clazz.getName(), ex.getMessage()));
        	return null;
        }
    }

    /**
     * 
     * @param query
     * @param parameters
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    protected T getByQuery(final String query, final List<? extends Object> parameters)
    {
    	try
    	{
    		return (T) this.createQuery(query, parameters).getSingleResult();
    	}
        catch (NoResultException ex)
        {
        	LOGGER.error(String.format("Error retrieving entity[%s]: %s", clazz.getName(), ex.getMessage()));
        	return null;
        }
    }

    /**
     * 
     * @param namedQuery
     * @param parameters
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    protected List<T> getListByNamedQuery(final String namedQuery, final List<? extends Object> parameters)
    {
        return (List<T>) this.createNamedQuery(namedQuery, parameters).getResultList();
    }
    
    /**
     * 
     * @param namedQuery
     * @param parameters
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    protected List<T> getListByNamedQuery(final String namedQuery, final List<? extends Object> parameters, int maxResult)
    {
        return (List<T>) this.createNamedQuery(namedQuery, parameters).setMaxResults(maxResult).getResultList();
    }

    /**
     * 
     * @param sqlQuery
     * @param parameters
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    protected List<T> getListBySqlQuery(final String sqlQuery, final List<? extends Object> parameters)
    {
        return (List<T>) this.createNativeQuery(sqlQuery, parameters).getResultList();
    }

    /**
     * 
     * @param query
     * @param parameters
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    protected List<T> getListByQuery(final String query, final List<? extends Object> parameters)
    {
        return (List<T>) this.createQuery(query, parameters).getResultList();
    }

    /**
     * 
     * @param query
     * @param parameters
     * @return
     */
    private Query setQueryParameters(final Query query, final List<? extends Object> parameters)
    {
        for (int i = 0; i < parameters.size(); i++)
        {
        	if (parameters.get(i) instanceof Map<?,?>) {
        		@SuppressWarnings("unchecked")
				Map<String,Object> map = (Map<String, Object>) parameters.get(i);
        		for (String key : map.keySet()) {
            		query.setParameter(key, map.get(key));
				}
        	} else {
        		query.setParameter(i+1, parameters.get(i));
        	}
        }
        return query;
    }
    
    /**
     * 
     * @param namedQuery
     * @param parameters
     * @return
     */
    private Query createNamedQuery(final String namedQuery, final List<? extends Object> parameters)
    {
    	EntityManager em = getEntityManager();
        Query sessionQuery = em.createNamedQuery(namedQuery);
        return setQueryParameters(sessionQuery, parameters);
    }
    
    /**
     * 
     * @param query
     * @param parameters
     * @return
     */
    private Query createNativeQuery(final String query, final List<? extends Object> parameters)
    {
        EntityManager em = getEntityManager();
        Query sessionQuery = em.createNativeQuery(query);
        return setQueryParameters(sessionQuery, parameters);
    }

    /**
     * 
     * @param query
     * @param parameters
     * @return
     */
    private Query createQuery(final String query, final List<? extends Object> parameters)
    {
    	EntityManager em = getEntityManager();
        Query sessionQuery = em.createQuery(query);
        return setQueryParameters(sessionQuery, parameters);
    }
    
    @Transactional(readOnly = true)
    protected int count(final String query, final List<? extends Object> parameters)
    {
    	return ((Long)createQuery(query, parameters).getSingleResult()).intValue();
    }

    private EntityManager getEntityManager()
    {
        return em;
    }
}
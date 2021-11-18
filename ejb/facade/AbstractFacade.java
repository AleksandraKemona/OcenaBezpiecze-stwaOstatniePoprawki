/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.spjava.e11.sa.ejb.facade;

import java.util.List;
import javax.persistence.EntityManager;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;

/**
 *
 * @author Ola
 */
public abstract class AbstractFacade<T> {

    static final public String DB_UNIQUE_CONSTRAINT_FOR_SUBSTRATE_NAME
            = "DBCONSTRAINT_UNIQUE_SUBSTRATE_NAME";
    static final public String DB_UNIQUE_CONSTRAINT_FOR_COSMETIC_NAME
            = "DBCONSTRAINT_UNIQUE_COSMETIC_NAME";
    static final public String DB_UNIQUE_CONSTRAINT_FOR_LOGIN
            = "DBCONSTRAINT_UNIQUE_LOGIN";
    static final public String DB_UNIQUE_CONSTRAINT_FOR_EMAIL
            = "DBCONSTRAINT_UNIQUE_EMAIL";
    static final public String DB_UNIQUE_CONSTRAINT_FOR_ANALYSIS_NAME
            = "DBCONSTRAINT_UNIQUE_ANALYSIS_NAME";
    static final public String DB_UNIQUE_CONSTRAINT_FOR_CATEGORY_NAME
            = "DBCONSTRAINT_UNIQUE_CATEGORY_NAME";
    
    
   
    
    


    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) throws AppBaseException {
        getEntityManager().persist(entity);
        
    }

    public void edit(T entity) throws AppBaseException{
        getEntityManager().merge(entity);
    }

    public void remove(T entity) throws AppBaseException{
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public void refresh(T entity) {
        getEntityManager().refresh(entity);
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() throws AppBaseException{
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}

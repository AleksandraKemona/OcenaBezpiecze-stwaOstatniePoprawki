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
            = "SQL211104091813570";
    static final public String DB_UNIQUE_CONSTRAINT_FOR_COSMETIC_NAME
            = "SQL211030223404500";
    static final public String DB_UNIQUE_CONSTRAINT_FOR_LOGIN
            = "SQL211024000530411";
    static final public String DB_UNIQUE_CONSTRAINT_FOR_EMAIL
            = "SQL211024000530410";
    static final public String DB_UNIQUE_CONSTRAINT_FOR_ANALYSIS_NAME
            = "SQL211024000530080";
   
    
    


    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) throws AppBaseException {
        System.out.println("-----------------------------------");
        System.out.println("AbstractFacade entity "+entity);
        getEntityManager().persist(entity);
        
    }

    public void edit(T entity) throws AppBaseException{
        System.out.println("-----------------------------------");
        System.out.println("AbstractFacade entity Edit "+entity);
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

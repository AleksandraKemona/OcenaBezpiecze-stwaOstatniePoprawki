/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.spjava.e11.sa.ejb.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pl.lodz.p.it.spjava.e11.sa.entity.Category;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;

/**
 *
 * @author Ola
 */
@Stateless
public class CategoryFacade extends AbstractFacade<Category> {

    @PersistenceContext(unitName = "SafetyAssessementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoryFacade() {
        super(Category.class);
    }
    
    
    
     public Category findByCategoryName(String categoryName) {
        TypedQuery<Category> tq = em.createNamedQuery("Category.findByCategoryName", Category.class);
        tq.setParameter("categoryName", categoryName);
        Category category = tq.getSingleResult();
        em.refresh(category);
        return category;
    }
     
     public Category findByCategoryId(long categoryId) throws AppBaseException{
        TypedQuery<Category> tq = em.createNamedQuery("Category.findByCategoryId", Category.class);
        tq.setParameter("categoryId", categoryId);
        Category category = tq.getSingleResult();
        em.refresh(category);
        return category;
    }
     
}

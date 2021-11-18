/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.spjava.e11.sa.ejb.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import org.eclipse.persistence.exceptions.DatabaseException;
import static pl.lodz.p.it.spjava.e11.sa.ejb.facade.AbstractFacade.DB_UNIQUE_CONSTRAINT_FOR_COSMETIC_NAME;
import pl.lodz.p.it.spjava.e11.sa.entity.Category;
import pl.lodz.p.it.spjava.e11.sa.entity.Cosmetic;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.exception.CategoryException;
import pl.lodz.p.it.spjava.e11.sa.exception.CosmeticException;

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
     
     @Override
    public void edit(Category entity) throws AppBaseException {
        try {
            super.edit(entity);
            em.flush();
        } catch (OptimisticLockException oe) {
            throw CategoryException.createCategoryExceptionWithOptimisticLockKey(entity, oe);
        } catch (PersistenceException | DatabaseException ex) {

            final Throwable cause = ex.getCause();
            final Throwable causeCause = ex.getCause().getCause();
            if (cause instanceof DatabaseException
                    && causeCause.getMessage().contains(DB_UNIQUE_CONSTRAINT_FOR_CATEGORY_NAME)) {
                throw CategoryException.createWithDbCheckConstraintKey(entity, cause);
            }
        }
    }

    @Override
    public void create(Category entity) throws AppBaseException {
        try {
            super.create(entity);
            em.flush();
        } catch (PersistenceException | DatabaseException ex) {
            final Throwable cause = ex.getCause();
            final Throwable causeCause = ex.getCause().getCause();
            if (cause instanceof DatabaseException
                    && causeCause.getMessage().contains(DB_UNIQUE_CONSTRAINT_FOR_CATEGORY_NAME)) {
                throw CategoryException.createWithDbCheckConstraintKey(entity, cause);
            }

        }
    }
     
}

package pl.lodz.p.it.spjava.e11.sa.ejb.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.spjava.e11.sa.entity.Cosmetic;
import pl.lodz.p.it.spjava.e11.sa.entity.Toxicology;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.exception.CosmeticException;

@Stateless
public class CosmeticFacade extends AbstractFacade<Cosmetic> {

    @PersistenceContext(unitName = "SafetyAssessementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CosmeticFacade() {
        super(Cosmetic.class);
    }

    public Cosmetic findById(long cosmeticId) throws AppBaseException{
        Cosmetic cosmetic= new Cosmetic();
        TypedQuery<Cosmetic> tq = em.createNamedQuery("Cosmetic.findById", Cosmetic.class);
        tq.setParameter("cosmeticId", cosmeticId);
        try {
        cosmetic = tq.getSingleResult();
        } catch (NoResultException nre) {
            throw CosmeticException.createCosmeticDoesNotExistException(cosmeticId, nre);
        }
        em.refresh(cosmetic);
        return cosmetic;
    }
    
    public Cosmetic findByName(String name)throws AppBaseException{
        Cosmetic cosmetic= new Cosmetic();
        TypedQuery<Cosmetic> tq = em.createNamedQuery("Cosmetic.findByName", Cosmetic.class);
        tq.setParameter("name", name);
        try {
        cosmetic = tq.getSingleResult();
        } catch (NoResultException nre) {
            throw CosmeticException.createCosmeticDoesNotExistException(name, nre);
        }
        em.refresh(cosmetic);
        return cosmetic;
    }
    

    @Override
    public void edit(Cosmetic entity) throws AppBaseException {
        try {
            super.edit(entity);
            em.flush();
        } catch (OptimisticLockException oe) {
            throw CosmeticException.createCosmeticExceptionWithOptimisticLockKey(entity, oe);
        } catch (PersistenceException | DatabaseException ex) {

            final Throwable cause = ex.getCause();
            final Throwable causeCause = ex.getCause().getCause();
            if (cause instanceof DatabaseException
                    && causeCause.getMessage().contains(DB_UNIQUE_CONSTRAINT_FOR_COSMETIC_NAME)) {
                throw CosmeticException.createWithDbCheckConstraintKey(entity, cause);
            }
        }
    }

    @Override
    public void create(Cosmetic entity) throws AppBaseException {
        try {
            super.create(entity);
            em.flush();
        } catch (PersistenceException | DatabaseException ex) {
            final Throwable cause = ex.getCause();
            final Throwable causeCause = ex.getCause().getCause();
            if (cause instanceof DatabaseException
                    && causeCause.getMessage().contains(DB_UNIQUE_CONSTRAINT_FOR_COSMETIC_NAME)) {
                throw CosmeticException.createWithDbCheckConstraintKey(entity, cause);
            }

        }
    }
    
    
    
    

    @Override
    public void remove(Cosmetic entity) throws AppBaseException {
        try {
            super.remove(entity);
            em.flush();
        } catch (OptimisticLockException oe) {
            throw CosmeticException.createCosmeticExceptionWithOptimisticLockKey(entity, oe);
        }
    }

    public void updateToxicology(Cosmetic updatedCosmetic, Toxicology addedToxicology) {
        TypedQuery<Cosmetic> tq = em.createNamedQuery("Cosmetic.findById", Cosmetic.class);
        tq.setParameter("cosmeticId", updatedCosmetic.getId());
        Cosmetic cosmetic = tq.getSingleResult();
        cosmetic.setToxicology(addedToxicology);
        em.merge(cosmetic);
    }

}

package pl.lodz.p.it.spjava.e11.sa.ejb.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.spjava.e11.sa.entity.Substrate;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.exception.SubstrateException;

@Stateless
public class SubstrateFacade extends AbstractFacade<Substrate> {

    @PersistenceContext(unitName = "SafetyAssessementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SubstrateFacade() {
        super(Substrate.class);
    }

    public Substrate findBySubstrateId(long substrateId) throws AppBaseException{
        TypedQuery<Substrate> tq = em.createNamedQuery("Substrate.findBySubstrateId", Substrate.class);
        tq.setParameter("substrateId", substrateId);
        Substrate substrate = tq.getSingleResult();
        em.refresh(substrate);
        return substrate;
    }
    
    public Substrate findBySubstrateName(String substrateName) throws AppBaseException{
        TypedQuery<Substrate> tq = em.createNamedQuery("Substrate.findBySubstrateName", Substrate.class);
        tq.setParameter("substrateName", substrateName);
        Substrate substrate = tq.getSingleResult();
        em.refresh(substrate);
        return substrate;
    }
    
    public Substrate findBySubstrateNameForToxicology(String substrateName){
        TypedQuery<Substrate> tq = em.createNamedQuery("Substrate.findBySubstrateName", Substrate.class);
        tq.setParameter("substrateName", substrateName);
        Substrate substrate = tq.getSingleResult();
        em.refresh(substrate);
        return substrate;
    }

    @Override
    public void edit(Substrate entity) throws AppBaseException {
        try {
            super.edit(entity);
            em.flush();
        } catch (OptimisticLockException oe) {
            throw SubstrateException.createSubstrateExceptionWithOptimisticLockKey(entity, oe);
        } catch (PersistenceException|DatabaseException ex) {
            final Throwable cause = ex.getCause();
            final Throwable causeCause = ex.getCause().getCause();
            if (cause instanceof DatabaseException
                    && causeCause.getMessage().contains(DB_UNIQUE_CONSTRAINT_FOR_SUBSTRATE_NAME)) {
                throw SubstrateException.createWithDbCheckConstraintKey(entity, cause);
            }
        }
    }

    @Override
    public void create(Substrate entity) throws AppBaseException {
        try {
            super.create(entity);
            em.flush();
        } catch (PersistenceException|DatabaseException ex) {
            
            final Throwable cause = ex.getCause();
            final Throwable causeCause = ex.getCause().getCause();
            if (cause instanceof DatabaseException
                    && causeCause.getMessage().contains(DB_UNIQUE_CONSTRAINT_FOR_SUBSTRATE_NAME)) {
                throw SubstrateException.createWithDbCheckConstraintKey(entity, cause);
            }
        }
    }
}

package pl.lodz.p.it.spjava.e11.sa.ejb.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import org.eclipse.persistence.exceptions.DatabaseException;
import static pl.lodz.p.it.spjava.e11.sa.ejb.facade.AbstractFacade.DB_UNIQUE_CONSTRAINT_FOR_SUBSTRATE_NAME;
import pl.lodz.p.it.spjava.e11.sa.entity.Substrate;
import pl.lodz.p.it.spjava.e11.sa.entity.Toxicology;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.exception.SubstrateException;
import pl.lodz.p.it.spjava.e11.sa.exception.ToxicologyException;


@Stateless
public class ToxicologyFacade extends AbstractFacade<Toxicology> {

    @PersistenceContext(unitName = "SafetyAssessementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ToxicologyFacade() {
        super(Toxicology.class);
    }
    
    public Toxicology findById(long toxicologyId) throws AppBaseException{
        TypedQuery<Toxicology> tq = em.createNamedQuery("Cosmetic.findById", Toxicology.class);
        tq.setParameter("toxicologyId", toxicologyId);
        Toxicology toxicology = tq.getSingleResult();
        em.refresh(toxicology);
        return toxicology;
    }
    
    public Toxicology findByToxicologyName(String toxicologyName) throws AppBaseException{
        TypedQuery<Toxicology> tq = em.createNamedQuery("Toxicology.findByToxicologyName", Toxicology.class);
        tq.setParameter("toxicologyName", toxicologyName);
        Toxicology toxicology = tq.getSingleResult();
        em.refresh(toxicology);
        return toxicology;
    }
}

package pl.lodz.p.it.spjava.e11.sa.ejb.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pl.lodz.p.it.spjava.e11.sa.entity.Account;
import pl.lodz.p.it.spjava.e11.sa.entity.LabTechnician;

/**
 *
 * @author Ola
 */
@Stateless
public class LabTechnicianFacade extends AbstractFacade<LabTechnician> {

    @PersistenceContext(unitName = "SafetyAssessementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LabTechnicianFacade() {
        super(LabTechnician.class);
    }
    
    public LabTechnician findByAccount(Account account) {
        TypedQuery<LabTechnician> tq = em.createNamedQuery("LabTechnician.findByAccount", LabTechnician.class);
        tq.setParameter("account", account);
        LabTechnician labTechnician = tq.getSingleResult();
        em.refresh(labTechnician);
        return labTechnician;
    }
    
    public LabTechnician findById(long labId) {
        TypedQuery<LabTechnician> tq = em.createNamedQuery("LabTechnicina.findById", LabTechnician.class);
        tq.setParameter("labId", labId);
        LabTechnician labTechnician = tq.getSingleResult();
        em.refresh(labTechnician);
        return labTechnician;
    }
    
}

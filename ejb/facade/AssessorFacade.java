package pl.lodz.p.it.spjava.e11.sa.ejb.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pl.lodz.p.it.spjava.e11.sa.entity.Account;
import pl.lodz.p.it.spjava.e11.sa.entity.Assessor;
import pl.lodz.p.it.spjava.e11.sa.entity.Sales;

/**
 *
 * @author Ola
 */
@Stateless
public class AssessorFacade extends AbstractFacade<Assessor> {

    @PersistenceContext(unitName = "SafetyAssessementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AssessorFacade() {
        super(Assessor.class);
    }
    
    public Assessor findByAccount(Account account) {
        TypedQuery<Assessor> tq = em.createNamedQuery("Assessor.findByAccount", Assessor.class);
        tq.setParameter("account", account);
        Assessor assessor = tq.getSingleResult();
        em.refresh(assessor);
        return assessor;
    }
    
    public Assessor findById(long assessorId) {
        TypedQuery<Assessor> tq = em.createNamedQuery("Assessor.findById", Assessor.class);
        tq.setParameter("assessorId", assessorId);
        Assessor assessor = tq.getSingleResult();
        em.refresh(assessor);
        return assessor;
    }
    
}

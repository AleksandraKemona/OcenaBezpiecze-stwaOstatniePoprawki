package pl.lodz.p.it.spjava.e11.sa.ejb.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pl.lodz.p.it.spjava.e11.sa.entity.Account;
import pl.lodz.p.it.spjava.e11.sa.entity.Sales;

/**
 *
 * @author Ola
 */
@Stateless
public class SalesFacade extends AbstractFacade<Sales> {

    @PersistenceContext(unitName = "SafetyAssessementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SalesFacade() {
        super(Sales.class);
    }
    
    public Sales findByAccount(Account account) {
        TypedQuery<Sales> tq = em.createNamedQuery("Sales.findByAccount", Sales.class);
        tq.setParameter("account", account);
        Sales sales = tq.getSingleResult();
        em.refresh(sales);
        return sales;
    }
    
    public Sales findById(long salesId) {
        TypedQuery<Sales> tq = em.createNamedQuery("Sales.findById", Sales.class);
        tq.setParameter("salesId", salesId);
        Sales sales = tq.getSingleResult();
        em.refresh(sales);
        return sales;
    }
    
}

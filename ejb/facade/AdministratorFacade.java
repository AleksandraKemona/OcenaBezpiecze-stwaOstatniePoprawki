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
import pl.lodz.p.it.spjava.e11.sa.entity.Account;
import pl.lodz.p.it.spjava.e11.sa.entity.Administrator;

/**
 *
 * @author Ola
 */
@Stateless
public class AdministratorFacade extends AbstractFacade<Administrator> {

    @PersistenceContext(unitName = "SafetyAssessementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdministratorFacade() {
        super(Administrator.class);
    }
    
    public Administrator findById(long adminId) {
        TypedQuery<Administrator> tq = em.createNamedQuery("Administrator.findById", Administrator.class);
        tq.setParameter("adminId", adminId);
        Administrator administrator = tq.getSingleResult();
        em.refresh(administrator);
        return administrator;
    }
    
    public Administrator findByAccount(Account account) {
        TypedQuery<Administrator> tq = em.createNamedQuery("Administrator.findByAccount", Administrator.class);
        tq.setParameter("account", account);
        Administrator administrator = tq.getSingleResult();
        em.refresh(administrator);
        return administrator;
    }
    
    
    
}

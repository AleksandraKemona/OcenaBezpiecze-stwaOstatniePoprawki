/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.spjava.e11.sa.ejb.facade;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.ExcludeClassInterceptors;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.eclipse.persistence.exceptions.DatabaseException;
import static pl.lodz.p.it.spjava.e11.sa.ejb.facade.AbstractFacade.DB_UNIQUE_CONSTRAINT_FOR_SUBSTRATE_NAME;
import pl.lodz.p.it.spjava.e11.sa.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.e11.sa.entity.Account;
import pl.lodz.p.it.spjava.e11.sa.entity.Administrator;
import pl.lodz.p.it.spjava.e11.sa.entity.Assessor;
import pl.lodz.p.it.spjava.e11.sa.entity.LabTechnician;
import pl.lodz.p.it.spjava.e11.sa.entity.Sales;
import pl.lodz.p.it.spjava.e11.sa.exception.AccountException;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.exception.SubstrateException;

/**
 *
 * @author Ola
 */
@Stateless
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AccountFacade extends AbstractFacade<Account> {

    private static final Logger LOG = Logger.getLogger(AccountFacade.class.getName());

    @PersistenceContext(unitName = "SafetyAssessementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountFacade() {
        super(Account.class);
    }
    
    @Override
    public void create(Account entity) throws AppBaseException {
        try {
            super.create(entity);
            em.flush();
        } catch (PersistenceException|DatabaseException ex) {
            
            final Throwable cause = ex.getCause();
            final Throwable causeCause = ex.getCause().getCause();
            System.out.println("-----------------------fasada Create------------------------");
            System.out.println("cause "+cause);
            System.out.println("cause "+cause.getMessage());
            System.out.println("causeCause "+causeCause);
            System.out.println("causeCause "+causeCause.getMessage());
            if (cause instanceof DatabaseException
                    && causeCause.getMessage().contains(DB_UNIQUE_CONSTRAINT_FOR_LOGIN)) {
                
                throw AccountException.createWithDbCheckConstraintKeyLogin(entity, cause);
            }else if(cause instanceof DatabaseException
                    && causeCause.getMessage().contains(DB_UNIQUE_CONSTRAINT_FOR_EMAIL)) {
                System.out.println("------------------fasada create email----------------------");
                System.out.println("DB_UNIQUE_CONSTRAINT_FOR_EMAIL");
                throw AccountException.createWithDbCheckConstraintKeyEmail(entity, cause);    
            }

        }
    }

    public Account findLogin(String login) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> from = query.from(Account.class);
        query = query.select(from);
        query = query.where(cb.equal(from.get("login"), login));
        TypedQuery<Account> tq = em.createQuery(query);

        return tq.getSingleResult();
    }

    public Account findById(long accountId) {
        TypedQuery<Account> tq = em.createNamedQuery("Account.findById", Account.class);
        tq.setParameter("accountId", accountId);
        Account account = tq.getSingleResult();
        em.refresh(account);
        return account;
    }

    public Account findByLogin(String login) throws AppBaseException{
        Account account= new Account();
        TypedQuery<Account> tq = em.createNamedQuery("Account.findByLogin", Account.class);
        System.out.println("tq "+tq);
        tq.setParameter("login", login);
        try {

            account = tq.getSingleResult();

        } catch (NoResultException nre) {
            throw AccountException.createAccountDoesNotExistException(login, nre);
        }
        em.refresh(account);
        return account;
    }
    


    @ExcludeClassInterceptors //Nie chcemy ujawniać w dziennikach skrótu hasła
//    @RolesAllowed("AUTHENTICATOR") //"Zwykłe" role nie mają tu dostępu. Musi pośredniczyć odpowiedni endpoint opisany jako @RunAs("AUTHENTICATOR").
    public Account findLoginAndPasswordHashInActiveAndConfirmedAccounts(String login, String passwordHash
    ) {
        if (null == login || null == passwordHash || login.isEmpty() || passwordHash.isEmpty()) {
            return null;
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> from = query.from(Account.class);
        Predicate criteria = cb.conjunction();
        criteria = cb.and(criteria, cb.equal(from.get("login"), login));
        criteria = cb.and(criteria, cb.equal(from.get("password"), "passwordHash"));
//        criteria = cb.and(criteria, cb.isTrue(from.get("isActive")));
//        criteria = cb.and(criteria, cb.isTrue(from.get("confirmed")));
        query = query.select(from);
        query = query.where(criteria);
        TypedQuery<Account> tq = em.createQuery(query);

        try {
            return tq.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "Authentication for login: {0} failed with: {1}", new Object[]{login, ex});
        }
        return null;

    }

//    public Administrator2 findLoginAsAdministrator(String login) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Administrator> query = cb.createQuery(Administrator2.class);
//        Root<Administrator> from = query.from(Administrator2.class);
//        query = query.select(from);
//        query = query.where(cb.equal(from.get("login"), login));
//        TypedQuery<Administrator> tq = em.createQuery(query);
//
//        return tq.getSingleResult();
//    }
//    
//    public Assessor findLoginAsAssessor(String login) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Assessor> query = cb.createQuery(Assessor.class);
//        Root<Assessor> from = query.from(Assessor.class);
//        query = query.select(from);
//        query = query.where(cb.equal(from.get("login"), login));
//        TypedQuery<Assessor> tq = em.createQuery(query);
//
//        return tq.getSingleResult();
//    }
//    
//    public Sales findLoginAsSales(String login) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Sales> query = cb.createQuery(Sales.class);
//        Root<Sales> from = query.from(Sales.class);
//        query = query.select(from);
//        query = query.where(cb.equal(from.get("login"), login));
//        TypedQuery<Sales> tq = em.createQuery(query);
//
//        return tq.getSingleResult();
//    }
//    
//    public LabTechnician findLoginAsLabTechnician(String login) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<LabTechnician> query = cb.createQuery(LabTechnician.class);
//        Root<LabTechnician> from = query.from(LabTechnician.class);
//        query = query.select(from);
//        query = query.where(cb.equal(from.get("login"), login));
//        TypedQuery<LabTechnician> tq = em.createQuery(query);
//
//        return tq.getSingleResult();
//    }
//    
}

package pl.lodz.p.it.spjava.e11.sa.ejb.manager;

import javax.ejb.*;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.AccountFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.AdministratorFacade;
import pl.lodz.p.it.spjava.e11.sa.entity.Account;

import pl.lodz.p.it.spjava.e11.sa.entity.Administrator;
import pl.lodz.p.it.spjava.e11.sa.entity.Assessor;
import pl.lodz.p.it.spjava.e11.sa.entity.LabTechnician;
import pl.lodz.p.it.spjava.e11.sa.entity.Sales;
import pl.lodz.p.it.spjava.e11.sa.entity.Substrate;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.web.account.AccountController;



@Stateful
public class AdministratorManager extends AbstractManager
         {

    @Inject
    private AdministratorFacade administratorFacade;
    
    @Inject
    private AccountFacade accountFacade;
   

    public void setTypeAsAdministrator (Administrator administrator, String accountLogin) throws AppBaseException{
        System.out.println("----------------------------------");
        System.out.println("administrator Manager administrator "+administrator + administrator.getAdminId() + administrator.getAdminStamp()
        +administrator.getAccount());
        Account accountInEndpoint = accountFacade.findByLogin(accountLogin);
        System.out.println("account " + accountInEndpoint);
        administrator.setAccount(accountInEndpoint);
        System.out.println("administrator Manager administrator "+administrator + administrator.getAdminId() + administrator.getAdminStamp()
        +administrator.getAccount());
        administratorFacade.create(administrator);
    }
    
}

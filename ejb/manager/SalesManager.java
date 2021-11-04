package pl.lodz.p.it.spjava.e11.sa.ejb.manager;

import javax.ejb.*;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.AccountFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.AdministratorFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.AssessorFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.SalesFacade;
import pl.lodz.p.it.spjava.e11.sa.entity.Account;

import pl.lodz.p.it.spjava.e11.sa.entity.Administrator;
import pl.lodz.p.it.spjava.e11.sa.entity.Assessor;
import pl.lodz.p.it.spjava.e11.sa.entity.LabTechnician;
import pl.lodz.p.it.spjava.e11.sa.entity.Sales;
import pl.lodz.p.it.spjava.e11.sa.entity.Substrate;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.web.account.AccountController;



@Stateful
public class SalesManager extends AbstractManager
         {

    @Inject
    private SalesFacade salesFacade;
    
    @Inject
    private AccountFacade accountFacade;
   

    public void setTypeAsSales (Sales sales, String accountLogin) throws AppBaseException{
        Account accountInEndpoint = accountFacade.findByLogin(accountLogin);
        sales.setAccount(accountInEndpoint);
        salesFacade.create(sales);
    }
    
}

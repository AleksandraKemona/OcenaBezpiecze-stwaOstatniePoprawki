package pl.lodz.p.it.spjava.e11.sa.ejb.manager;

import javax.ejb.*;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.AccountFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.AdministratorFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.AssessorFacade;
import pl.lodz.p.it.spjava.e11.sa.entity.Account;

import pl.lodz.p.it.spjava.e11.sa.entity.Administrator;
import pl.lodz.p.it.spjava.e11.sa.entity.Assessor;
import pl.lodz.p.it.spjava.e11.sa.entity.LabTechnician;
import pl.lodz.p.it.spjava.e11.sa.entity.Sales;
import pl.lodz.p.it.spjava.e11.sa.entity.Substrate;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.web.account.AccountController;



@Stateful
public class AssessorManager extends AbstractManager
         {

    @Inject
    private AssessorFacade assessorFacade;
    
    @Inject
    private AccountFacade accountFacade;
   

    public void setTypeAsAssessor (Assessor assessor, String accountLogin) throws AppBaseException{
        Account accountInEndpoint = accountFacade.findByLogin(accountLogin);
        assessor.setAccount(accountInEndpoint);
        assessorFacade.create(assessor);
    }
    
}

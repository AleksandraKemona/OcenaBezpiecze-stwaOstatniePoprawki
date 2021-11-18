package pl.lodz.p.it.spjava.e11.sa.ejb.manager;

import java.util.List;
import javax.ejb.*;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.AccountFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.AdministratorFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.AssessorFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.LabTechnicianFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.SalesFacade;
import pl.lodz.p.it.spjava.e11.sa.entity.Account;

import pl.lodz.p.it.spjava.e11.sa.entity.Administrator;
import pl.lodz.p.it.spjava.e11.sa.entity.Assessor;
import pl.lodz.p.it.spjava.e11.sa.entity.LabTechnician;
import pl.lodz.p.it.spjava.e11.sa.entity.Sales;
import pl.lodz.p.it.spjava.e11.sa.entity.Substrate;
import pl.lodz.p.it.spjava.e11.sa.exception.AccountException;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.web.account.AccountController;
import pl.lodz.p.it.spjava.e11.sa.web.account.AdministratorController;
import pl.lodz.p.it.spjava.e11.sa.web.account.AssessorController;
import pl.lodz.p.it.spjava.e11.sa.web.account.LabController;
import pl.lodz.p.it.spjava.e11.sa.web.account.SalesController;

@Stateful
@LocalBean

public class AccountManager extends AbstractManager
        implements SessionSynchronization {

    @Inject
    private AccountFacade accountFacade;

    @Inject
    private AccountController accountController;

    @Inject
    private AdministratorController administratorController;

    @Inject
    private AssessorController assessorController;

    @Inject
    private SalesController salesController;

    @Inject
    private LabController labController;

    @Inject
    private AdministratorFacade administratorFacade;

    @Inject
    private LabTechnicianFacade labTechnicianFacade;

    @Inject
    private SalesFacade salesFacade;

    @Inject
    private AssessorFacade assessorFacade;

    private Account validatedAccount;

    public List<Account> downloadAllAccounts() throws AppBaseException {
        return accountFacade.findAll();
    }

    public void createAccount(Account account) throws AppBaseException {
        accountFacade.create(account);
    }

    public Account downloadAccountForValidation(Long id) {
        validatedAccount = accountFacade.find(id);
        accountFacade.refresh(validatedAccount);
        return validatedAccount;
    }

    public void resetPassword(Account account) throws AppBaseException {
        accountFacade.edit(account);
    }

    public void saveAccountAfterValidation(Account account) throws AppBaseException {
        accountFacade.edit(account);
        String accountLogin = account.getLogin();
        String typeName = account.getType();
        String name = account.getName();
        String surname = account.getSurname();
        String newStamp = name.substring(0, 1) + ". " + surname;
        if ("Administrator".equals(typeName)) {
            String adminStamp = newStamp;
            administratorController.setTypeAsAdministrator(accountLogin, adminStamp);
        } else if ("Assessor".equals(typeName)) {
            String assessorStamp = newStamp;
            assessorController.setTypeAsAssessor(accountLogin, assessorStamp);
        } else if ("Sales".equals(typeName)) {
            String salesStamp = newStamp;
            salesController.setTypeAsSales(accountLogin, salesStamp);
        } else if ("LabTechnician".equals(typeName)) {
            String labStamp = newStamp;
            labController.setTypeAsLab(accountLogin, labStamp);
        }

    }

    public Account findByLogin(String login) throws AppBaseException {
        return accountFacade.findByLogin(login);
    }

    public Account findByEmail(String email) throws AppBaseException {;
        Account isEmailDuplicate = accountFacade.findByEmail(email);
        if (isEmailDuplicate != null) {
            throw AccountException.createEmailDoesExistException(email);
        }
        return accountFacade.findByEmail(email);
    }
    
    public void confirmAccount (String login, String email)throws AppBaseException{
        List<Account> accountsList = accountFacade.findAll();
        for (Account account : accountsList) {
            String existingLogin = account.getLogin();
            String existingEmail = account.getEmail();
            if (existingLogin.equals(login)) {
                throw AccountException.createAccountDoesExistException(login);
            }
            if (existingEmail.equals(email)) {
                throw AccountException.createEmailDoesExistException(email);
            }
        }
        
    }

    public Account downloadAccountForEdition(Long id) {
        accountFacade.refresh(validatedAccount);
        return validatedAccount;
    }

    public void saveUserAccountAfterEdition(Account account) throws AppBaseException {
        accountFacade.edit(account);
    }

    public void saveMyAccountAfterEdition(Account account) throws AppBaseException {
        
        accountFacade.edit(account);
    }

    public Long getUserId(String userName) throws AppBaseException {
        Account account = accountFacade.findByLogin(userName);
        String userType = account.getType();
        Long id = null;
        if (userType.equals("Administrator")) {
            Administrator administrator = administratorFacade.findByAccount(account);
            Long adminId = administrator.getAdminId();
            id = adminId;
        } else if (userType.equals("Sales")) {
            Sales sales = salesFacade.findByAccount(account);
            Long salesId = sales.getSalesId();
            id = salesId;
        } else if (userType.equals("LabTechnician")) {
            LabTechnician labTechnician = labTechnicianFacade.findByAccount(account);
            Long labId = labTechnician.getLabId();
            id = labId;
        } else if (userType.equals("Assessor")) {
            Assessor assessor = assessorFacade.findByAccount(account);
            Long assessorId = assessor.getAssessorId();
            id = assessorId;
        }
        return id;
    }

}

package pl.lodz.p.it.spjava.e11.sa.ejb.endpoint;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import pl.lodz.p.it.spjava.e11.sa.dto.AccountDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.AccountFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.AdministratorFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.AssessorFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.LabTechnicianFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.facade.SalesFacade;
import pl.lodz.p.it.spjava.e11.sa.ejb.manager.AccountManager;
import pl.lodz.p.it.spjava.e11.sa.entity.Account;

import pl.lodz.p.it.spjava.e11.sa.entity.Administrator;

import pl.lodz.p.it.spjava.e11.sa.exception.AccountException;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
import pl.lodz.p.it.spjava.e11.sa.security.HashGenerator;
import pl.lodz.p.it.spjava.e11.sa.utils.converter.AccountsConverter;

@Stateful

public class AccountEndpoint implements Serializable {

    @Inject
    private AccountFacade accountFacade;

    @Inject
    private AccountManager accountManager;

    @Inject
    private AdministratorFacade administratorFacade;

    @Inject
    private SalesFacade salesFacade;

    @Inject
    private LabTechnicianFacade labTechnicianFacade;

    @Inject
    private AssessorFacade assessorFacade;

    @Inject
    private HashGenerator hashGenerator;

    protected SessionContext sctx;

    private int txRetryLimit;

    private Account accountState;

    @RolesAllowed({"Assessor", "LabTechnician", "Administrator", "Sales"})
    public List<AccountDTO> listAllAccounts() throws AppBaseException {
        return AccountsConverter.createListAccountsDTOFromEntity(accountManager.downloadAllAccounts());
    }

    @RolesAllowed({"Administrator"})
    public AccountDTO getUserAccountForEdition(AccountDTO accountDTO) throws AppBaseException {
        accountState = accountFacade.findByLogin(accountDTO.getLogin());
        return AccountsConverter.createAccountDTOFromEntity(accountState);
    }

    @RolesAllowed({"Assessor", "LabTechnician", "Administrator", "Sales"})
    public AccountDTO getMyAccountForEdition(String userName) throws AppBaseException {
        accountState = accountFacade.findByLogin(userName);
        return AccountsConverter.createAccountDTOFromEntity(accountState);
    }

    @RolesAllowed({"Assessor", "LabTechnician", "Administrator", "Sales"})
    public AccountDTO downloadAccountForDetails(String userName) throws AppBaseException {
        System.out.println("----------Endpoint-------");
        System.out.println("username " + userName);
        accountState = accountFacade.findByLogin(userName);
        return AccountsConverter.createAccountDTOFromEntity(accountState);
    }

    public AccountDTO getAccountForChangePassword(AccountDTO accountDTO) throws AppBaseException {
        accountState = accountFacade.findByLogin(accountDTO.getLogin());
        return AccountsConverter.createAccountDTOFromEntity(accountState);

    }

    public void resetPassword(AccountDTO accountDTO) throws AppBaseException {
        if (null == accountState) {
            throw AccountException.createExceptionWrongState(accountState);
        } else if (!accountDTO.getAnswer().equals(accountState.getAnswer())) {
            throw AccountException.createExceptionWrongAnswer(accountState);
        }
        accountState.setPassword(hashGenerator.generateHash(accountDTO.getPassword()));

        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;
        do {
            try {
                accountManager.resetPassword(accountState);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AccountException se) {
                throw se;
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX && retryTXCounter
                == 0) {
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }
    }

    @RolesAllowed({"Assessor", "LabTechnician", "Administrator", "Sales"})
    public String downloadMyLogin() throws IllegalStateException {
        return sctx.getCallerPrincipal().getName();
    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void createAccount(AccountDTO accountDTO) throws AppBaseException {
        Account account = new Account();
        account.setLogin(accountDTO.getLogin());
        account.setPassword(hashGenerator.generateHash(accountDTO.getPassword()));
        account.setName(accountDTO.getName());
        account.setSurname(accountDTO.getSurname());
        account.setPhone(accountDTO.getPhone());
        account.setEmail(accountDTO.getEmail());
        account.setQuestion(accountDTO.getQuestion());
        account.setAnswer(accountDTO.getAnswer());
        account.setType("Unconfirmed");
        account.setActive(false);

        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                accountManager.createAccount(account);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AccountException ae) {
                throw ae;
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }

        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX && retryTXCounter == 0) {
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }
    }

    @RolesAllowed({"Administrator"})
    public AccountDTO getAccountForValidation(AccountDTO accountDTO) {
        accountState = accountFacade.findById(accountDTO.getId());
        System.out.println("-------------------------------");
        System.out.println("get Account for validation in Endpoint " + accountDTO);
        return AccountsConverter.createAccountDTOFromEntityForVerification(accountState);
    }

    @RolesAllowed({"Administrator"})
    public void saveAccountAfterValidation(AccountDTO accountDTO, String adminLogin) throws AppBaseException {
        Long adminId = accountManager.getUserId(adminLogin);
        Administrator admin = administratorFacade.findById(adminId);
        accountState = accountFacade.findByLogin(accountDTO.getLogin());
        accountState.setType(accountDTO.getType());
        accountState.setVerifiedBy(admin);

        if (null == accountState) {
            throw AccountException.createExceptionWrongState(accountState);
        }
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;
        do {
            try {
                accountManager.saveAccountAfterValidation(accountState);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AccountException ae) {
                throw ae;
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX && retryTXCounter
                == 0) {
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }
    }

    @RolesAllowed({"Administrator"})
    public void saveUserAccountAfterEdition(AccountDTO accountDTO) throws AppBaseException {
        if (null == accountState) {
            throw AccountException.createExceptionWrongState(accountState);
        }
        accountState.setLogin(accountDTO.getLogin());
        if (accountDTO.getNewPassword() != null) {
            accountState.setPassword(hashGenerator.generateHash(accountDTO.getNewPassword()));
        }
        accountState.setName(accountDTO.getName());
        accountState.setSurname(accountDTO.getSurname());
        accountState.setPhone(accountDTO.getPhone());
        accountState.setEmail(accountDTO.getEmail());
        accountState.setType(accountDTO.getType());
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;
        do {
            try {
                accountManager.saveUserAccountAfterEdition(accountState);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AccountException ae) {
                throw ae;
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX && retryTXCounter
                == 0) {
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }
    }

    @RolesAllowed({"Assessor", "LabTechnician", "Administrator", "Sales"})
    public void saveMyAccountAfterEdition(AccountDTO accountDTO) throws AppBaseException {
        if (null == accountState) {
            throw AccountException.createExceptionWrongState(accountState);
        }
        accountState.setLogin(accountDTO.getLogin());
        accountState.setPassword(hashGenerator.generateHash(accountDTO.getPassword()));
        accountState.setName(accountDTO.getName());
        accountState.setSurname(accountDTO.getSurname());
        accountState.setPhone(accountDTO.getPhone());
        accountState.setEmail(accountDTO.getEmail());
        accountState.setQuestion(accountDTO.getQuestion());
        accountState.setAnswer(accountDTO.getAnswer());
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;
        do {
            try {
                accountManager.saveUserAccountAfterEdition(accountState);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AccountException ae) {
                throw ae;
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX && retryTXCounter
                == 0) {
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }
    }
 
    @RolesAllowed({"Administrator"})
    public void activateAccount(AccountDTO accountDTO) {
        Account account = accountFacade.find(accountDTO.getId());
        account.setActive(true);
    }

    @RolesAllowed({"Administrator"})
    public void deactivateAccount(AccountDTO accountDTO) {
        Account account = accountFacade.find(accountDTO.getId());
        account.setActive(false);
    }

}

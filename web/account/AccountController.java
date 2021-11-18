package pl.lodz.p.it.spjava.e11.sa.web.account;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.e11.sa.dto.AccountDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.endpoint.AccountEndpoint;
import pl.lodz.p.it.spjava.e11.sa.web.utils.ContextUtils;
import java.io.*;
import lombok.Getter;
import pl.lodz.p.it.spjava.e11.sa.dto.AdministratorDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.CosmeticDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.SubstrateDTO;
import pl.lodz.p.it.spjava.e11.sa.ejb.endpoint.AdministratorEndpoint;
import pl.lodz.p.it.spjava.e11.sa.entity.Administrator;
import pl.lodz.p.it.spjava.e11.sa.exception.AccountException;
import static pl.lodz.p.it.spjava.e11.sa.exception.AccountException.KEY_LOGIN_DOES_EXIST;
import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;

/**
 *
 * @author java
 */
@Named("accountSession")
@SessionScoped
public class AccountController implements Serializable {

    private static final Logger LOG = Logger.getLogger(AccountController.class.getName());

    @Inject
    private AccountEndpoint accountEndpoint;

    @Inject
    private ResetPasswordBean resetPasswordBean;

    @Inject
    private AccountDetailsBean accountDetailsBean;

    @Inject
    private AdministratorEndpoint administratorEndpoint;

    @Getter
    private AccountDTO validatedAccount;

    @Getter
    private AccountDTO editedAccount;

    @Getter
    private AccountDTO downloadedAccount;

    private AccountDTO editAccount;

    private AccountDTO createdAccount;

    public String confirmAccount(AccountDTO newAccount) {
        try {
            accountEndpoint.confirmAccount(newAccount);
            return "confirmAccount";
        } catch (AccountException ae) {
            if (AccountException.KEY_LOGIN_DOES_NOT_EXIST.equals(ae.getMessage())) {
                return "confirmAccount";
            } else if (AccountException.KEY_LOGIN_DOES_EXIST.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage("createAccountForm:login",
                        AccountException.KEY_LOGIN_DOES_EXIST);
            } else if (AccountException.KEY_EMAIL_EXISTS.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage("createAccountForm:email",
                        AccountException.KEY_EMAIL_EXISTS);
            } else {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji createSubstrate wyjątku: ", ae);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji createSubstrate wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public String createAccount(AccountDTO newAccountDTO) {
        try {
            createdAccount = newAccountDTO;
            accountEndpoint.createAccount(createdAccount);
            createdAccount = null;
            return "createAccountSucces";
        } catch (AccountException se) {
            if (AccountException.KEY_LOGIN_EXISTS.equals(se.getMessage())) {
                ContextUtils.emitInternationalizedMessage("createNewAccountForm:login",
                        AccountException.KEY_LOGIN_EXISTS);
            } else if (AccountException.KEY_EMAIL_EXISTS.equals(se.getMessage())) {
                ContextUtils.emitInternationalizedMessage("createNewAccountForm:email",
                        AccountException.KEY_EMAIL_EXISTS);
            } else {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji createAccount wyjątku: ", se);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji createAccount wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public AccountDTO getUserAccountForEdition(AccountDTO accountDTO) {
        try {
            editAccount = accountEndpoint.getUserAccountForEdition(accountDTO);
            return editAccount;
        } catch (AccountException ae) {
            if (AccountException.KEY_ACCOUNT_NOT_READ_FOR_EDITION.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage("editUserAccountForm:name",
                        AccountException.KEY_ACCOUNT_NOT_READ_FOR_EDITION);
            } else {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji getUserAccountForEdition wyjątku: ", ae);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji getUserAccountForEdition wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public AccountDTO getMyAccountForEdition(String userName) {
        try {
            editAccount = accountEndpoint.getMyAccountForEdition(userName);
            return editAccount;
        } catch (AccountException ae) {
            if (AccountException.KEY_ACCOUNT_NOT_READ_FOR_EDITION.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage("editUserAccountForm:name",
                        AccountException.KEY_ACCOUNT_NOT_READ_FOR_EDITION);
            } else {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji getUserAccountForEdition wyjątku: ", ae);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji getUserAccountForEdition wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public AccountDTO downloadAccountForDetails(String userName) {
        try {
            downloadedAccount = accountEndpoint.downloadAccountForDetails(userName);
            return downloadedAccount;
        } catch (AccountException ae) {
            if (AccountException.KEY_ACCOUNT_NOT_READ_FOR_EDITION.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage("editUserAccountForm:name",
                        AccountException.KEY_ACCOUNT_NOT_READ_FOR_EDITION);
            } else {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji getUserAccountForEdition wyjątku: ", ae);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji getUserAccountForEdition wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public List<AccountDTO> listAllAccounts() {
        try {
            return accountEndpoint.listAllAccounts();
        } catch (AppBaseException abe) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji listAllAccounts wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    @Getter
    private AccountDTO accountChangePassword;

    public String downloadAccountForChangePassword(AccountDTO accountDTO) {
        try {
            accountChangePassword = accountEndpoint.getAccountForChangePassword(accountDTO);
            accountChangePassword.setAnswer(null);
            resetPasswordBean.setAccountForChangePassword(accountChangePassword);
            return "resetPassword";
        } catch (AccountException ae) {
            if (AccountException.KEY_ACCOUNT_NOT_READ_FOR_CHANGE_PASSWORD.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage("resetPasswordRequestForm:login",
                        AccountException.KEY_ACCOUNT_NOT_READ_FOR_CHANGE_PASSWORD);
            } else if (AccountException.KEY_LOGIN_DOES_NOT_EXIST.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null,
                        AccountException.KEY_LOGIN_DOES_NOT_EXIST);
            } else {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji editSubstrate wyjątku: ", ae);
            }

            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji getAccountForChangePassword wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public String resetPassword(AccountDTO accountDTO) {
        try {
            accountEndpoint.resetPassword(accountDTO);
            return "passwordResetSucces";
        } catch (AccountException se) {
            if (AccountException.KEY_ACCOUNT_ANSWER_INCORRECT.equals(se.getMessage())) {
                ContextUtils.emitInternationalizedMessage("resetPaswordForm:answer",
                        AccountException.KEY_ACCOUNT_ANSWER_INCORRECT);

            } else if (AccountException.KEY_ADMIN_OPTIMISTIC_LOCK.equals(se.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, AccountException.KEY_ACCOUNT_OPTIMISTIC_LOCK);
            } else {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji resetPassword wyjątku: ", se);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji resetPassword wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public AccountController() {
    }

    public String getAccountForValidation(AccountDTO accountDTO) {
        validatedAccount = accountEndpoint.getAccountForValidation(accountDTO);
        return "validateAccount";

    }

    public String saveAccountAfterValidation(AccountDTO accountDTO, String adminLogin) {
        try {
            accountEndpoint.saveAccountAfterValidation(accountDTO, adminLogin);
            return "listAccounts";
        } catch (AccountException ae) {
            if (AccountException.KEY_ADMIN_STAMP_EXISTS.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage("validateAccountForm:adminStamp",
                        AccountException.KEY_ADMIN_STAMP_EXISTS);
            } else if (AccountException.KEY_ADMIN_OPTIMISTIC_LOCK.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, AccountException.KEY_ADMIN_OPTIMISTIC_LOCK);
            } else if (AccountException.KEY_ACCOUNT_VALIDATED.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, AccountException.KEY_ACCOUNT_VALIDATED);
            } else {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji validateAccount wyjątku: ", ae);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji validateAccount wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }

    }

    public String saveUserAccountAfterEdition(AccountDTO accountDTO, String oldLogin) {
        try {
            accountEndpoint.saveUserAccountAfterEdition(accountDTO, oldLogin);
            return "listAccounts";
        } catch (AccountException ae) {
            if (AccountException.KEY_ADMIN_STAMP_EXISTS.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage("validateAccountForm:adminStamp",
                        AccountException.KEY_ADMIN_STAMP_EXISTS);
            } else if (AccountException.KEY_EMAIL_EXISTS.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, AccountException.KEY_EMAIL_EXISTS);
            } else if (AccountException.KEY_LOGIN_DOES_EXIST.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, AccountException.KEY_LOGIN_DOES_EXIST);
            } else if (AccountException.KEY_ADMIN_OPTIMISTIC_LOCK.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, AccountException.KEY_ADMIN_OPTIMISTIC_LOCK);
            } else {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji validateAccount wyjątku: ", ae);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji validateAccount wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }

    }

    public String saveMyAccountAfterEdition(AccountDTO accountDTO) {
        try {
            accountEndpoint.saveMyAccountAfterEdition(accountDTO);
            return accountDetailsBean.setMyAccountChoice(accountDTO.getLogin());
        } catch (AccountException ae) {
            if (AccountException.KEY_ADMIN_STAMP_EXISTS.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage("validateAccountForm:adminStamp",
                        AccountException.KEY_ADMIN_STAMP_EXISTS);
            } else if (AccountException.KEY_EMAIL_EXISTS.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, AccountException.KEY_EMAIL_EXISTS);
            } else if (AccountException.KEY_LOGIN_DOES_EXIST.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, AccountException.KEY_LOGIN_DOES_EXIST);
            } else if (AccountException.KEY_ACCOUNT_OPTIMISTIC_LOCK.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, AccountException.KEY_ACCOUNT_OPTIMISTIC_LOCK);
            } else {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji validateAccount wyjątku: ", ae);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji validateAccount wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }

    }

    public String activateAccount(AccountDTO accountDTO) {
        try {
            accountEndpoint.activateAccount(accountDTO);
            return "listAccounts";
        } catch (AccountException ae) {
            if (AccountException.KEY_ACCOUNT_ACTIVATED.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, AccountException.KEY_ACCOUNT_ACTIVATED);
            } else if (AccountException.KEY_ACCOUNT_DEACTIVATED.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, AccountException.KEY_ACCOUNT_DEACTIVATED);
            } else {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji validateAccount wyjątku: ", ae);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji validateAccount wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public String deactivateAccount(AccountDTO accountDTO) {       
        try {
            accountEndpoint.deactivateAccount(accountDTO);
            return "listAccounts";
        } catch (AccountException ae) {
            if (AccountException.KEY_ACCOUNT_ACTIVATED.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, AccountException.KEY_ACCOUNT_ACTIVATED);
            } else if (AccountException.KEY_ACCOUNT_DEACTIVATED.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, AccountException.KEY_ACCOUNT_DEACTIVATED);
            } else {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                        "Zgłoszenie w metodzie akcji validateAccount wyjątku: ", ae);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
                    "Zgłoszenie w metodzie akcji validateAccount wyjątku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
        
    }

    @PostConstruct
    private void init() {
        LOG.severe("Session started: " + ContextUtils.getSessionID());
    }
}

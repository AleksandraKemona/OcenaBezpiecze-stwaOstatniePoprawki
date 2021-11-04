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
    private AdministratorEndpoint administratorEndpoint;

    @Getter
    private AccountDTO validatedAccount;

    @Getter
    private AccountDTO editedAccount;
    
    @Getter
    private AccountDTO downloadedAccount;

    private AccountDTO editAccount;

    private AccountDTO createdAccount;

    public String createAccount(AccountDTO newAccountDTO) throws AppBaseException {
        try {
            createdAccount = newAccountDTO;
            accountEndpoint.createAccount(createdAccount);
            createdAccount = null;
            return "createAccountSucces";
        } catch (AccountException se) {
            System.out.println("SE Message" + se.getMessage());
            if (AccountException.KEY_LOGIN_EXISTS.equals(se.getMessage())) {
                System.out.println("-----------Exception Login------------");
                ContextUtils.emitInternationalizedMessage("createNewAccountForm:login",
                        AccountException.KEY_LOGIN_EXISTS);
            } else if (AccountException.KEY_EMAIL_EXISTS.equals(se.getMessage())) {
                System.out.println("-----------Exception Email------------");
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
            System.out.println("-------------Controller-------------");
            System.out.println("user Name "+userName);
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

    public String resetSession() {
        ContextUtils.invalidateSession();
        /* Poprawne zakończenie sesji wymaga wymuszenia nowego żądania na przeglądarce, stąd metoda ta
         * prowadzi do przypadku nawigacji z elementem <redirect />.
         * UWAGA: integracja logowania typu BASIC z przeglądarką powoduje, że czasem mimo to "wylogowanie" jest nieskuteczne - 
         * powstaje nowa sesja już zalogowanego użytkownika. Dlatego bezpieczniej jest stosować uwierzytelnianie przez formularz (FORM).
         */
        return "cancelAction";
    }

    public String getMyLogin() {
        return ContextUtils.getUserName();
    }

//    private KlientDTO klientUtworz;
//
//    private PracownikDTO pracownikUtworz;
//
//    private AdministratorDTO administratorUtworz;
    @Getter
    private AccountDTO accountChangePassword;

    public String downloadAccountForChangePassword(AccountDTO accountDTO) throws AppBaseException {
        try {

            accountChangePassword = accountEndpoint.getAccountForChangePassword(accountDTO);
            System.out.println("-----------------Controller--------------");
            System.out.println("accountChangePassword " + accountChangePassword);
            System.out.println("login " + accountChangePassword.getLogin());
            accountChangePassword.setAnswer(null);
            System.out.println("-----------czy po ustawnieniu konta wraca do Controllera -------");
            System.out.println("account Change Password " + accountChangePassword);
            System.out.println("account for opassword " + resetPasswordBean.getAccountForPassword());
            resetPasswordBean.setAccountForPassword(accountChangePassword);
            return "resetPassword";
//            return accountChangePassword;

        } catch (AccountException ae) {
            if (AccountException.KEY_ACCOUNT_NOT_READ_FOR_CHANGE_PASSWORD.equals(ae.getMessage())) {
                System.out.println("--------Controller------- download Account for Change Password-------------");
                System.out.println("account not read");
                ContextUtils.emitInternationalizedMessage("changePasswordForm:notReadForChangePassword",
                        AccountException.KEY_ACCOUNT_NOT_READ_FOR_CHANGE_PASSWORD);
            } else if (AccountException.KEY_LOGIN_DOES_NOT_EXIST.equals(ae.getMessage())) {
                System.out.println("--------Controller------- download Account for Change Password-------------");
                System.out.println("login nie istnieje");
                ContextUtils.emitInternationalizedMessage("changePasswordForm:accountDoesNotExist",
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

    public String resetPassword(AccountDTO accountDTO) throws AppBaseException {
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

    public AccountDTO getKontoEdytuj() {
        return editAccount;
    }

//    public KlientDTO getKlientRejestracja() {
//        return klientRejestracja;
//    }
    public AccountController() {
    }

    public String getAccountForValidation(AccountDTO accountDTO) {
        validatedAccount = accountEndpoint.getAccountForValidation(accountDTO);
        return "validateAccount";

    }

    public String saveAccountAfterValidation(AccountDTO accountDTO, String adminLogin) throws AppBaseException {
        try {
            System.out.println("Account controller accountDTO " + accountDTO);
            accountEndpoint.saveAccountAfterValidation(accountDTO, adminLogin);
            System.out.println("Account controller accountDTO " + accountDTO);
            return "listAccounts";
        } catch (AccountException ae) {
            if (AccountException.KEY_ADMIN_STAMP_EXISTS.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage("validateAccountForm:adminStamp",
                        AccountException.KEY_ADMIN_STAMP_EXISTS);
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


    public String saveUserAccountAfterEdition(AccountDTO accountDTO) {
        try {
            accountEndpoint.saveUserAccountAfterEdition(accountDTO);
            return "listAccounts";
        } catch (AccountException ae) {
            if (AccountException.KEY_ADMIN_STAMP_EXISTS.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage("validateAccountForm:adminStamp",
                        AccountException.KEY_ADMIN_STAMP_EXISTS);
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
            return "listAccounts";
        } catch (AccountException ae) {
            if (AccountException.KEY_ADMIN_STAMP_EXISTS.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage("validateAccountForm:adminStamp",
                        AccountException.KEY_ADMIN_STAMP_EXISTS);
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

//    public String setTypeAsAdministrator (String accountLogin, String newStamp) throws AppBaseException{
//        try {
//        AdministratorDTO administratorDTO = new AdministratorDTO();
//            System.out.println("--------------------------");
//            System.out.println("Account controller set type administratorDTO"+ administratorDTO);
//            System.out.println("Account controller set type newStamp "+newStamp);
//            System.out.println("Account controller set type accountLogin "+accountLogin);
//        administratorEndpoint.setTypeAsAdministrator(administratorDTO, accountLogin, newStamp);
//        return "listAccounts";
//        }catch (AccountException se) {
//            System.out.println("SE Message" + se.getMessage());
//            if (AccountException.KEY_ADMIN_STAMP_EXISTS.equals(se.getMessage())) {
//                ContextUtils.emitInternationalizedMessage("validateAccountForm:adminStamp",
//                        AccountException.KEY_ADMIN_STAMP_EXISTS);
//
//            } else {
//                Logger.getLogger(AccountException.class.getName()).log(Level.SEVERE,
//                        "Zgłoszenie w metodzie akcji validateAccount-setType  wyjątku: ", se);
//            }
//            return null;
//        } catch (AppBaseException abe) {
//            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE,
//                    "Zgłoszenie w metodzie akcji validateAccount-setType wyjątku typu: ", abe.getClass());
//            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
//                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
//            }
//            return null;
//        }
//    }
//    public String utworzKlienta(KlientDTO klient) {
//        try {
//            klientUtworz = klient;
//            kontoEndpoint.utworzKonto(klientUtworz);
//            klientUtworz = null;
//            return "success";
//        } catch (KontoException ke) {
//            if (KontoException.KEY_DB_CONSTRAINT.equals(ke.getMessage())) {
//                ContextUtils.emitInternationalizedMessage("login", KontoException.KEY_DB_CONSTRAINT); //wyjątki aplikacyjne powinny przenosić jedynie klucz do internacjonalizacji
//            } else {
//                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji utworzKlienta wyjatku: ", ke);
//            }
//            return null;
//        } catch (AppBaseException abe) {
//            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji utworzKlienta wyjatku typu: ", abe.getClass());
//            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
//                ContextUtils.emitInternationalizedMessage(null, abe.getMessage()); //wyjątki aplikacyjne powinny przenosić jedynie klucz do internacjonalizacji
//            }
//            return null;
//        }
//    }
//
//    public String utworzPracownika(PracownikDTO pracownik) {
//        try {
//            pracownikUtworz = pracownik;
//            kontoEndpoint.utworzKonto(pracownikUtworz);
//            pracownikUtworz = null;
//            return "success";
//        } catch (KontoException ke) {
//            if (KontoException.KEY_DB_CONSTRAINT.equals(ke.getMessage())) {
//                ContextUtils.emitInternationalizedMessage(null, KontoException.KEY_DB_CONSTRAINT); //wyjątki aplikacyjne powinny przenosić jedynie klucz do internacjonalizacji
//            } else {
//                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji utworzPracownika wyjatku: ", ke);
//            }
//            return null;
//        } catch (AppBaseException abe) {
//            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji utworzPracownika wyjatku typu: ", abe.getClass());
//            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
//                ContextUtils.emitInternationalizedMessage(null, abe.getMessage()); //wyjątki aplikacyjne powinny przenosić jedynie klucz do internacjonalizacji
//            }
//            return null;
//        }
//    }
//
//    public String utworzAdministratora(AdministratorDTO admin) {
//        try {
//            administratorUtworz = admin;
//            kontoEndpoint.utworzKonto(administratorUtworz);
//            administratorUtworz = null;
//            return "success";
//        } catch (KontoException ke) {
//            if (KontoException.KEY_DB_CONSTRAINT.equals(ke.getMessage())) {
//                ContextUtils.emitInternationalizedMessage("login", KontoException.KEY_DB_CONSTRAINT); //wyjątki aplikacyjne powinny przenosić jedynie klucz do internacjonalizacji
//            } else {
//                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji utworzAdministratora wyjatku: ", ke);
//            }
//            return null;
//        } catch (AppBaseException abe) {
//            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji utworzAdministratora wyjatku typu: ", abe.getClass());
//            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
//                ContextUtils.emitInternationalizedMessage(null, abe.getMessage()); //wyjątki aplikacyjne powinny przenosić jedynie klucz do internacjonalizacji
//            }
//            return null;
//        }
//    }
//
//    public String potwierdzRejestracjeKlienta(KlientDTO klient) {
//        this.klientRejestracja = klient;
//        return "confirmRegister";
//    }
    public String startChangingPassword(AccountDTO account) {
        this.accountChangePassword = account;
        return "changePassword";
    }

//    public String rejestrujKlienta() {
//        try {
//            kontoEndpoint.rejestrujKlienta(klientRejestracja);
//            klientRejestracja = null;
//            return "success";
//        } catch (KontoException ke) {
//            if (KontoException.KEY_DB_CONSTRAINT.equals(ke.getMessage())) {
//                ContextUtils.emitInternationalizedMessage("login", KontoException.KEY_DB_CONSTRAINT); //wyjątki aplikacyjne powinny przenosić jedynie klucz do internacjonalizacji
//            } else {
//                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji rejestrujKlienta wyjatku: ", ke);
//            }
//            return null;
//        } catch (AppBaseException abe) {
//            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji rejestrujKlienta wyjatku typu: ", abe.getClass());
//            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
//                ContextUtils.emitInternationalizedMessage(null, abe.getMessage()); //wyjątki aplikacyjne powinny przenosić jedynie klucz do internacjonalizacji
//            }
//            return null;
//        }
//    }
//
    public void activateAccount(AccountDTO accountDTO) {
        accountEndpoint.activateAccount(accountDTO);
    }
//
    public void deactivateAccount(AccountDTO accountDTO) {
        accountEndpoint.deactivateAccount(accountDTO);
    }
//
//    public void potwierdzKonto(KontoDTO konto) {
//        kontoEndpoint.potwierdzKonto(konto);
//        ContextUtils.emitSuccessMessage(ListaKontPageBean.GENERAL_MSG_ID);
//    }
//
//    public String pobierzKontoDoEdycji(KontoDTO konto) {
//        kontoEdytuj = kontoEndpoint.pobierzKontoDoEdycji(konto);
//        return "editAccount";
//    }
//
//    public String zapiszKontoKlientaPoEdycji(KontoDTO konto) throws AppBaseException {
//        kontoEndpoint.zapiszKontoKlientaPoEdycji(konto);
//        return "success";
//    }
//
//    public String zapiszKontoPracownikaPoEdycji(KontoDTO konto) throws AppBaseException {
//        kontoEndpoint.zapiszKontoPracownikaPoEdycji(konto);
//        return "success";
//    }
//
//    public String zapiszKontoAdministratoraPoEdycji(KontoDTO konto) throws AppBaseException {
//        kontoEndpoint.zapiszKontoAdministratoraPoEdycji(konto);
//        return "success";
//    }
//
//    public String zmienHasloKonta(String haslo) {
//        kontoEndpoint.zmienHaslo(kontoZmienHaslo, haslo);
//        return "success";
//    }
//
//    public String zmienMojeHaslo(String stare, String nowe) {
//        kontoEndpoint.zmienMojeHaslo(stare, nowe);
//        return "success";
//    }
//
//    public List<KontoDTO> pobierzWszystkieKonta() {
//        return kontoEndpoint.pobierzWszystkieKonta();
//    }
//
//    public List<KontoDTO> dopasujKonta(String loginWzor, String imieWzor, String nazwiskoWzor, String emailWzor) {
//        return kontoEndpoint.dopasujKonta(loginWzor, imieWzor, nazwiskoWzor, emailWzor);
//    }
//
//    public KontoDTO pobierzMojeKonto() {
//        return kontoEndpoint.pobierzMojeKontoDTO();
//    }
    @PostConstruct
    private void init() {
        LOG.severe("Session started: " + ContextUtils.getSessionID());
    }
}

package pl.lodz.p.it.spjava.e11.sa.exception;

import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import lombok.Getter;
import pl.lodz.p.it.spjava.e11.sa.entity.Account;
import pl.lodz.p.it.spjava.e11.sa.entity.Administrator;

/**
 *
 */
public class AccountException extends AppBaseException {

    static final public String KEY_DB_CONSTRAINT = "error.accounts.db.constraint.uniqe.login";
    static final public String KEY_ADMIN_STAMP_EXISTS = "error.accounts.db.constraint.uniq.adminStamp";
    static final public String KEY_SALES_STAMP_EXISTS = "error.accounts.db.constraint.uniq.salesStamp";
    static final public String KEY_ASSESSOR_STAMP_EXISTS = "error.accounts.db.constraint.uniq.assessorStamp";
    static final public String KEY_LAB_STAMP_EXISTS = "error.accounts.db.constraint.uniq.labStamp";
    static final public String KEY_ADMIN_OPTIMISTIC_LOCK = "error.accounts.AdminOptimisticlock";
    static final public String KEY_SALES_OPTIMISTIC_LOCK = "error.accounts.SalesOptimisticlock";
    static final public String KEY_ASSESSOR_OPTIMISTIC_LOCK = "error.accounts.AssessorOptimisticlock";
    static final public String KEY_LAB_OPTIMISTIC_LOCK = "error.accounts.LabOptimisticlock";
    static final public String KEY_ACCOUNT_OPTIMISTIC_LOCK = "error.accounts.AccountOptimisticlock";
    static final public String KEY_ACCOUNT_NOT_READ_FOR_VALIDATION = "error.accounts.accountNotDownoladedForValidation";
    static final public String KEY_ACCOUNT_NOT_READ_FOR_EDITION = "error.accounts.accountNotDownoladedForEdition";
    static final public String KEY_ACCOUNT_NOT_READ_FOR_CHANGE_PASSWORD = "error.accounts.accountNotDownoladedForChangePassword";

    static final public String KEY_ACCOUNT_ANSWER_INCORRECT = "error.accounts.accountIncorrectAnswer";
    static final public String KEY_LOGIN_DOES_NOT_EXIST = "error.accounts.loginDoesNotExist";
    static final public String KEY_EMAIL_DOES_NOT_EXISTS = "error.accounts.emailDoesNotExist";
    static final public String KEY_LOGIN_DOES_EXIST = "error.accounts.loginDoesExist";
    static final public String KEY_ACCOUNT_VALIDATED = "error.accounts.alreadyValidated";
    static final public String KEY_ACCOUNT_ACTIVATED = "error.accounts.alreadyActivated";
    static final public String KEY_ACCOUNT_DEACTIVATED = "error.accounts.alreadyDeactivated";
    
    
    
    

    static final public String KEY_LOGIN_EXISTS = "error.accounts.db.constraint.uniqe.login";
    static final public String KEY_EMAIL_EXISTS = "error.accounts.db.constraint.uniqe.email";

    private AccountException(String message) {
        super(message);
    }

    private AccountException(String message, Throwable cause) {
        super(message, cause);
    }

    private Account account;

    @Getter
    private Administrator administrator;

    @Getter
    private String login;
    
    @Getter
    private String email;

    public Account getAccount() {
        return account;
    }

    public static AccountException createExceptionWrongState(Account account) {
        AccountException ae = new AccountException(KEY_ACCOUNT_NOT_READ_FOR_VALIDATION);
        ae.account = account;
        return ae;
    }

    public static AccountException createExceptionWrongAnswer(Account account) {
        AccountException ae = new AccountException(KEY_ACCOUNT_ANSWER_INCORRECT);
        ae.account = account;
        return ae;
    }

    static public AccountException createAdministratorExceptionWithOptimisticLockKey(Administrator administrator, OptimisticLockException oe) {
        AccountException ae = new AccountException(KEY_ADMIN_OPTIMISTIC_LOCK, oe);
        ae.administrator = administrator;
        return ae;
    }

    static public AccountException createAccountExceptionWithOptimisticLockKey(Account account, OptimisticLockException oe) {
        AccountException ae = new AccountException(KEY_ACCOUNT_OPTIMISTIC_LOCK, oe);
        ae.account = account;
        return ae;
    }

    static public AccountException createAccountExceptionWithTxRetryRollback() {
        AccountException ae = new AccountException(KEY_TX_RETRY_ROLLBACK);
        return ae;
    }

    static public AccountException createWithDbCheckConstraintKeyLogin(Account account, Throwable cause) {
        AccountException ae = new AccountException(KEY_LOGIN_EXISTS, cause);
        ae.account = account;
        return ae;
    }

    static public AccountException createWithDbCheckConstraintKeyEmail(Account account, Throwable cause) {
        AccountException ae = new AccountException(KEY_EMAIL_EXISTS, cause);
        ae.account = account;
        return ae;
    }

    static public AccountException createWithDbCheckConstraintKey(Administrator administrator, Throwable cause) {
        AccountException ae = new AccountException(KEY_ADMIN_STAMP_EXISTS, cause);
        ae.administrator = administrator;
        return ae;
    }

    static public AccountException createAccountDoesNotExistException(String login, NoResultException nre) {
        AccountException ae = new AccountException(KEY_LOGIN_DOES_NOT_EXIST, nre);
        ae.login = login;
        return ae;
    }

    static public AccountException createAccountDoesExistException(String login) {
        AccountException ae = new AccountException(KEY_LOGIN_DOES_EXIST);
        ae.login = login;
        return ae;
    }

    static public AccountException createEmailDoesExistException(String email) {
        AccountException ae = new AccountException(KEY_EMAIL_EXISTS);
        ae.email = email;
        return ae;
    }
    
    public static AccountException createAccountAlreadyValidatedException(Account account) {
        AccountException ae = new AccountException(KEY_ACCOUNT_VALIDATED);
        ae.account=account;
        return ae;
    }
    
    public static AccountException createAccountAlreadyActivatedException(Account account) {
        AccountException ae = new AccountException(KEY_ACCOUNT_ACTIVATED);
        ae.account=account;
        return ae;
    }
    
    public static AccountException createAccountAlreadyDeactivatedException(Account account) {
        AccountException ae = new AccountException(KEY_ACCOUNT_DEACTIVATED);
        ae.account=account;
        return ae;
    }
}

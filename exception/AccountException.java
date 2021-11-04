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

    static final public String KEY_DB_CONSTRAINT = "error.konto.db.constraint.uniq.login";
    static final public String KEY_ADMIN_STAMP_EXISTS = "error.accounts.db.constraint.uniq.adminStamp";
    static final public String KEY_ADMIN_OPTIMISTIC_LOCK = "error.accounts.AdminOptimisticlock";
    static final public String KEY_ACCOUNT_OPTIMISTIC_LOCK = "error.accounts.AccountOptimisticlock";
    static final public String KEY_ACCOUNT_NOT_READ_FOR_VALIDATION = "error.accounts.accountNotDownoladedForValidation";
    static final public String KEY_ACCOUNT_NOT_READ_FOR_EDITION = "error.accounts.accountNotDownoladedForEdition";
    static final public String KEY_ACCOUNT_NOT_READ_FOR_CHANGE_PASSWORD = "error.accounts.accountNotDownoladedForChangePassword";
    
    
    static final public String KEY_ACCOUNT_ANSWER_INCORRECT = "error.accounts.accountIncorrectAnswer";
    static final public String KEY_LOGIN_DOES_NOT_EXIST = "error.accounts.loginDoesNotExist";
    
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

     
    public Account getAccount() {
        return account;
    }
    
    public static AccountException createExceptionWrongState(Account account) {
        AccountException ae = new AccountException(KEY_ACCOUNT_NOT_READ_FOR_VALIDATION);
        ae.account=account;
        return ae;
    }
    
    public static AccountException createExceptionWrongAnswer(Account account) {
        AccountException ae = new AccountException(KEY_ACCOUNT_ANSWER_INCORRECT);
        ae.account=account;
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
        System.out.println("------------------Account Exception----------------");
        AccountException ae = new AccountException(KEY_LOGIN_DOES_NOT_EXIST, nre);
        System.out.println("ae "+ ae);
        ae.login = login;
        return ae;
    }
}

//package pl.lodz.p.it.spjava.e11.sa.web.account;
//
//import javax.annotation.PostConstruct;
//import javax.enterprise.context.RequestScoped;
//import javax.inject.Inject;
//import javax.inject.Named;
//import lombok.Getter;
//import lombok.Setter;
//import pl.lodz.p.it.spjava.e11.sa.dto.AccountDTO;
//import pl.lodz.p.it.spjava.e11.sa.exception.AppBaseException;
//
///**
// *
// * @author java
// */
//@Named("requestResetPasswordBean")
//@RequestScoped
//public class RequestResetPasswordBean {
//
//    public RequestResetPasswordBean() {
//    }
//    
//    @Inject
//    private ResetPasswordBean resetPasswordBean;
//
//    @Inject
//    private AccountController accountController;
//
//    @Setter
//    private AccountDTO accountForChangePassword;
//
//    
//    public String begin() {
//        accountForChangePassword = new AccountDTO();
//        System.out.println("----------------------------------------Bean-----------------------");
//        System.out.println("account For Change Password w Begin "+accountForChangePassword);
//        return "resetPasswordRequest";
//    }
//    
//    public AccountDTO getAccountForChangePassword() {
//        if (null != accountForChangePassword) {
//            System.out.println("----------------------------------------Bean-----------------------");
//            System.out.println("konto nie jest null "+accountForChangePassword);
//            return accountForChangePassword;
//        } else {
//            System.out.println("----------------------------------------Bean-----------------------");
//            System.out.println("konto jest null "+accountForChangePassword);
//            AccountDTO accountDTO =new AccountDTO();
//            accountForChangePassword=accountDTO;
//            return accountForChangePassword;
//        }
//    }
//    
//    public String resetPassword() throws AppBaseException{
//        System.out.println("----------------------------------------Bean-----------------------");
//        System.out.println("account For Change Password w resetPassword "+accountForChangePassword);
//        return accountController.downloadAccountForChangePassword(accountForChangePassword);
//    }
//   
//    public String abort() {
//        return "main";
//    }
//
//}

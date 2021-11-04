/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.spjava.e11.sa.utils.converter;

import java.util.ArrayList;
import java.util.List;
import pl.lodz.p.it.spjava.e11.sa.dto.AccountDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.AdministratorDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.AssessorDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.LabDTO;
import pl.lodz.p.it.spjava.e11.sa.dto.SalesDTO;
import pl.lodz.p.it.spjava.e11.sa.entity.Account;
import pl.lodz.p.it.spjava.e11.sa.entity.Administrator;
import pl.lodz.p.it.spjava.e11.sa.entity.Assessor;
import pl.lodz.p.it.spjava.e11.sa.entity.LabTechnician;
import pl.lodz.p.it.spjava.e11.sa.entity.Sales;

/**
 *
 * @author Ola
 */
public class AccountsConverter {


    public static AccountDTO createAccountDTOFromEntity(Account account) {
        return new AccountDTO(account.getAccountId(), account.getType(),
                account.getLogin(), account.getPassword(),account.getName(), account.getSurname(),
                account.getEmail(), account.getPhone(), createAdministratorDTOFromEntity(account.getVerifiedBy()), account.isActive(),
                account.getQuestion(), account.getAnswer());
    }
    
    public static AccountDTO createAccountDTOFromEntityForVerification(Account account) {
        return new AccountDTO(account.getLogin(), account.getName(), 
                account.getSurname(), account.getEmail(), account.getPhone());
    }
    
    public static AdministratorDTO createAdministratorDTOFromEntity(Administrator administrator) {
        AdministratorDTO administratorDTO = new AdministratorDTO();
        if (administrator==null) {
            administratorDTO.setAdminId(-100l);
            administratorDTO.setAdminStamp(" ");    
        }else{
        administratorDTO.setAdminId(administrator.getAdminId());
        administratorDTO.setAdminStamp(administrator.getAdminStamp());
        }
        return administratorDTO;
    }
    
    public static Administrator createAdministratorEntityFromDTO(AdministratorDTO administratorDTO){
    return new Administrator(administratorDTO.getAdminStamp());
}
    public static SalesDTO createSalesDTOFromEntity(Sales sales) {
        return new SalesDTO(sales.getSalesId(), sales.getSalesStamp());
    }
    
    public static Sales createSalesEntityFromDTO(SalesDTO salesDTO){
    return new Sales(salesDTO.getSalesId(),salesDTO.getSalesStamp());
}
    public static AssessorDTO createAssessorDTOFromEntity(Assessor assessor) {
        AssessorDTO assessorDTO = new AssessorDTO();
        if (assessor==null) {
            assessorDTO.setAssessorId(-100l);
            assessorDTO.setAssessorStamp(" ");    
        }else{

        assessorDTO.setAssessorId(assessor.getAssessorId());
        assessorDTO.setAssessorStamp(assessor.getAssessorStamp());

        }
        return assessorDTO;
    }
    
    public static Assessor createAssessorEntityFromDTO(AssessorDTO assessorDTO){
    return new Assessor(assessorDTO.getAssessorId(), assessorDTO.getAssessorStamp());
}
    public static LabDTO createLabDTOFromEntity(LabTechnician labTechnician) {
        LabDTO labDTO = new LabDTO();
        if (labTechnician==null) {
            labDTO.setLabId(-100l);
            labDTO.setLabStamp(" ");   
        }else{
            labDTO.setLabId(labTechnician.getLabId());
            labDTO.setLabStamp(labTechnician.getLabStamp());
        }
        return labDTO;
    }
    
    public static LabTechnician createLabEntityFromDTO(LabDTO labDTO){
    return new LabTechnician(labDTO.getLabId(), labDTO.getLabStamp());
}



    public static List<AccountDTO> createListAccountsDTOFromEntity(List<Account> listAccounts) {
        List<AccountDTO> listAccountDTO = new ArrayList<>();

        for (Account account : listAccounts) {
            if(account.getVerifiedBy()!=null){
                AccountDTO accountDTO = new AccountDTO(account.getAccountId(), account.getType(),
                    account.getLogin(), account.getPassword(), account.getName(), account.getSurname(),
                    account.getEmail(), account.getPhone(), createAdministratorDTOFromEntity(account.getVerifiedBy()),
                    account.isActive(),account.getQuestion(),account.getAnswer());
            listAccountDTO.add(accountDTO);
                
            }else{
            AccountDTO accountDTO = new AccountDTO(account.getAccountId(), account.getType(),
                    account.getLogin(), account.getPassword(), account.getName(), account.getSurname(),
                    account.getEmail(), account.getPhone(), null,
                    account.isActive(),account.getQuestion(),account.getAnswer());
            listAccountDTO.add(accountDTO);
            }
        }

        return listAccountDTO;
    }
    
    public static List<Account> createListAccountsEntityFromDTO(List<AccountDTO> listAccountsDTO) {
        List<Account> listAccount = new ArrayList<>();

        for (AccountDTO accountDTO : listAccountsDTO) {
            Account account = new Account(accountDTO.getId(), accountDTO.getLogin(), 
                    accountDTO.getName(), accountDTO.getSurname(), accountDTO.getEmail(), accountDTO.getPhone(), 
                    accountDTO.getQuestion(), accountDTO.getAnswer());
            listAccount.add(account);
        }

        return listAccount;
    }
    
    //    public static AccountDTO createAccountDTOFromEntity(Account account) {
//        // Konto jest abstrakcyjna, obiekt musi być klasy potomnej
//        
//        if(account instanceof Administrator2)
//            return createAdministratorDTOFromEntity((Administrator2) account);
//        
//        if(account instanceof Assessor)
//            return createAssessorDTOFromEntity((Assessor) account);
//        
//        if(account instanceof Sales)
//            return createSalesDTOFromEntity((Sales) account);
//        
//        if(account instanceof LabTechnician)
//            return createLabTechnicianDTOFromEntity((LabTechnician) account);
//        
//        return null;
//    }
//    private static AdministratorDTO createAdministratorDTOFromEntity(Administrator2 administrator) {
//        return null == administrator ? null : new AdministratorDTO(administrator.getAdminStamp(), administrator.getId(), 
//                administrator.getType(), administrator.getLogin(), administrator.getName(), administrator.getSurname(),
//                administrator.getEmail(), administrator.getPhone(), administrator.getVerifiedBy(), administrator.isActive(), 
//                administrator.getQuestion(), administrator.getAnswer());
//    }
//     
//    private static AssessorDTO createAssessorDTOFromEntity(Assessor assessor) {
//        return null == assessor ? null : new AssessorDTO(assessor.getAssessorStamp(), assessor.getId(), 
//                assessor.getType(), assessor.getLogin(), assessor.getName(), assessor.getSurname(),
//                assessor.getEmail(), assessor.getPhone(), assessor.getVerifiedBy(), assessor.isActive(), 
//                assessor.getQuestion(), assessor.getAnswer());
//    }
//     
//    private static SalesDTO createSalesDTOFromEntity(Sales sales) {
//        return null == sales ? null : new SalesDTO(sales.getSalesStamp(), sales.getId(), 
//                sales.getType(), sales.getLogin(), sales.getName(), sales.getSurname(),
//                sales.getEmail(), sales.getPhone(), sales.getVerifiedBy(), sales.isActive(), 
//                sales.getQuestion(), sales.getAnswer());
//    }
//     
//    private static LabDTO createLabTechnicianDTOFromEntity(LabTechnician labTechnician) {
//        return null == labTechnician ? null : new LabDTO(labTechnician.getLabStamp(), labTechnician.getId(), 
//                labTechnician.getType(), labTechnician.getLogin(), labTechnician.getName(), labTechnician.getSurname(),
//                labTechnician.getEmail(), labTechnician.getPhone(), labTechnician.getVerifiedBy(), labTechnician.isActive(), 
//                labTechnician.getQuestion(), labTechnician.getAnswer());
//    }
//     
//     
//    

//    public static List<AccountDTO> createListUnconfirmedAccountsDTOFromEntity(List<AccountNotConfirmed> listAccounts) {
//        List<AccountDTO> listAccountDTO = new ArrayList<>();
//
//        for (Account account : listAccounts) {
//            AccountDTO accountDTO = new AccountDTO(account.getAccountId(), account.getType(),
//                    account.getLogin(), account.getName(), account.getSurname(),
//                    account.getEmail(), account.getPhone(), account.getVerifiedBy(),
//                    account.isActive(),account.getQuestion(),account.getAnswer());
//            listAccountDTO.add(accountDTO);
//        }
//
//        return listAccountDTO;
//    }
}

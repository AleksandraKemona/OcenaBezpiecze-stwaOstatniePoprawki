
package pl.lodz.p.it.spjava.e11.sa.web.account;

import javax.ejb.Stateless;
import javax.inject.Named;
import pl.lodz.p.it.spjava.e11.sa.web.utils.ContextUtils;

@Stateless
@Named("logoutBean")
public class LogoutBean {

    
public String logout(){
    ContextUtils.invalidateSession();
    return "main";
}
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.spjava.e11.sa.web.utils;

import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named
@SessionScoped
@ManagedBean
public class LanguageChangeController implements Serializable {

 private Locale locale;
 
 @Getter
 @Setter
 private String language = "pl";

 @PostConstruct
public void init() {
locale = FacesContext.getCurrentInstance()
.getExternalContext()
.getRequestLocale();
}

 public void setLanguage(String language) {
locale = new Locale(language);
FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
}
 
 public void onLanguageChange(ValueChangeEvent vce) {
		if (vce != null) {
			String l = vce.getNewValue().toString();
			setLanguage(l);
			locale = new Locale(l);
			FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
		} else {
			FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.ENGLISH);
		}
	}
}
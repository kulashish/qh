package in.ac.iitb.cse.qh.util;

import javax.faces.context.FacesContext;

public class BeanFinder {
	@SuppressWarnings("unchecked")
	public static <T> T findBean(String beanName) {
		FacesContext context = FacesContext.getCurrentInstance();
		return (T) context.getExternalContext().getSessionMap().get(beanName);
	}
}

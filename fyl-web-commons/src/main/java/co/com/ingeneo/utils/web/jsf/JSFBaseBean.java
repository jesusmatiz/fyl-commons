package co.com.ingeneo.utils.web.jsf;

import java.io.InputStream;
import java.io.Serializable;
import java.security.Principal;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.FactoryFinder;
import javax.faces.annotation.ApplicationMap;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.com.ingeneo.utils.jwt.JWTUtil;
import co.com.ingeneo.utils.messages.MessageLocale;
import co.com.ingeneo.utils.web.messages.ResourceBundleManager;
import lombok.extern.slf4j.Slf4j;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@ApplicationScoped
@Slf4j
public class JSFBaseBean implements Serializable {

    @Inject
    private ExternalContext externalContext;

    @Inject
    private FacesContext facesContext;

    @Inject
    @ApplicationMap
    private Map<String, Object> applicationMap;

    @Inject
    private UIViewRoot viewRoot;

    public String getMessage(String bundleName, String key, Object... params) {
        Locale locale = getLocale();
        return ResourceBundleManager.getMessage(locale, bundleName, key, params);
    }

    protected void forceView() {
        Application jsfApplication = facesContext.getApplication();
        ViewHandler jsfViewHandler = jsfApplication.getViewHandler();

        UIViewRoot jsfViewRoot = jsfViewHandler.createView(facesContext, viewRoot.getViewId());
        facesContext.setViewRoot(jsfViewRoot);
        facesContext.renderResponse();
    }

    protected Locale getLocale() {
        if (viewRoot != null) {
            Locale viewRootLocale = viewRoot.getLocale();
            return viewRootLocale;
        }
        Locale systemLocale = MessageLocale.getLocale();
        return systemLocale;
    }

    protected void clearInputChildComponents(String parentId) {
    clearInputChildComponents(findElementById(parentId));
    }

    protected void clearInputChildComponents(UIComponent parent) {
        List<UIComponent> childrens = parent.getChildren();
        for (UIComponent children : childrens) {
            if ((children instanceof UIInput)) {
                ((UIInput)children).resetValue();
            } else {
                clearInputChildComponents(children);
            }
        }
    }

    protected void downloadFile(InputStream pInputStream, String pFileName) throws Exception {
        int contentLength = pInputStream.available();

        HttpServletResponse response = getResponse();
        response.setContentType("application/x-download; charset=ISO-8859-1");
        response.setContentLength(contentLength);
        response.setHeader("Content-disposition", "attachment; filename=\"" + pFileName + "\"");

        int valor = 0;
        pInputStream.reset();
        while ((valor = pInputStream.read()) != -1) {
            response.getOutputStream().write(valor);
        }
        pInputStream.close();
        facesContext.responseComplete();
    }

    protected String getPrincipalName() {
        Principal principal = getRequest().getUserPrincipal();
        if (principal != null) {
            return principal.getName();
        }
        return "admin";
    }

    /*
	    @Deprecated
	    protected Principal getPrincipal() {
	        return getRequest().getUserPrincipal();
	    }
    */
    
    protected Principal getPrincipal() {
    	return (Principal)getSessionMap().get("portal.session");
    }

    protected boolean isUserInRole(String roleName) {
        return getRequest().isUserInRole(roleName);
    }

    protected HttpServletRequest getRequest() {
        return (HttpServletRequest)externalContext.getRequest();
    }

    protected HttpServletResponse getResponse() {
        return (HttpServletResponse)externalContext.getResponse();
    }

    protected Application getApplication() {
        return FacesContext.getCurrentInstance().getApplication();
    }

    protected Map<String, Object> getApplicationMap() {
        return applicationMap;
    }

    protected ExternalContext getExternalContext() {
        return externalContext;
    }

    protected FacesContext getFacesContext() {
        return facesContext;
    }

    protected Lifecycle getLifecycle() {
        String lifecycleId = externalContext.getInitParameter("javax.faces.LIFECYCLE_ID");
        if ((lifecycleId == null) || (lifecycleId.length() == 0)) {
            lifecycleId = "DEFAULT";
        }
        LifecycleFactory lifecycleFactory = (LifecycleFactory)FactoryFinder.getFactory("javax.faces.lifecycle.LifecycleFactory");
        return lifecycleFactory.getLifecycle(lifecycleId);
    }

    protected Map<String, String> getRequestParameterMap() {
        return getExternalContext().getRequestParameterMap();
    }

    protected Map<String, Object> getRequestAttributeMap() {
        return getExternalContext().getRequestMap();
    }

    protected void info(String summary) {
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }

    protected void info(UIComponent component, String summary) {
        facesContext.addMessage(component.getClientId(getFacesContext()), new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }

    protected void error(String summary) {
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
    }

    protected void error(UIComponent component, String summary) {
        facesContext.addMessage(component.getClientId(getFacesContext()), new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
    }

    protected <T> T getBean(Class<T> clazz, String bean) {
        Application app = facesContext.getApplication();
        ExpressionFactory ef = app.getExpressionFactory();
        ValueExpression ve = ef.createValueExpression(facesContext.getELContext(), String.format("#{%s}", new Object[] { bean }), Object.class);
        return (T)ve.getValue(facesContext.getELContext());
    }

    protected UIComponent findElementById(UIComponent uiComponent, String id) {
        UIComponent ret = null;
        String actualId = uiComponent.getId();
        if (id.equals(actualId)) {
            return uiComponent;
        }
        List<UIComponent> list = uiComponent.getChildren();
        Iterator<UIComponent> iter = list.iterator();
        while ((iter.hasNext()) && (ret == null)) {
            UIComponent element = iter.next();
            ret = findElementById(element, id);
        }
        return ret;
    }

    protected UIComponent findElementById(String id) {
        return findElementById(getFacesContext().getViewRoot(), id);
    }

    protected HttpSession getSession() {
        return getRequest().getSession();
    }
    
    protected Map<String, Object> getSessionMap() {
    	return getExternalContext().getSessionMap();
    }

    protected boolean isGet() {
        return getRequest().getMethod().equals("GET");
    }

    protected boolean isPost() {
        return getRequest().getMethod().equals("POST");
    }

    protected void setSessionMapValue(String key, Object value) {
        getSessionMap().put(key, value);
    }

    protected void setRequestMapValue(String key, Object value) {
        getRequestAttributeMap().put(key, value);
    }

    public String getContextPath() {
        HttpServletRequest request = getRequest();
        if (request != null) {
            return request.getContextPath();
        }
        return "";
    }

    protected String getUserIp() {
        return getRequest().getRemoteAddr();
    }

    protected String getServerIp() {
        return getRequest().getLocalAddr();
    }

    protected String getPrincipalJWT(){
        log.trace("[Start][JSFBaseBean][getPrincipalJWT]");
        HttpServletRequest request = getRequest();
        String payload = JWTUtil.decodeToken(request.getParameter("portal_jwt"));
        try{
            String value = (String) JWTUtil.toClame(payload);
            log.trace("[End][JSFBaseBean][getPrincipalJWT]");
            return value;
        }catch (Exception e){
            log.error("[Error][JSFBaseBean][getPrincipalJWT]Error", e);
        }
        return null;
    }
}

package co.com.ingeneo.utils.web.jsf;

import java.io.IOException;
import java.util.Locale;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import co.com.ingeneo.utils.messages.MessageLocale;

public class CustomViewHandler extends ViewHandler {

  protected ViewHandler baseHandler;
  
  public CustomViewHandler(ViewHandler baseHandler)
  {
    this.baseHandler = baseHandler;
  }
  
  public Locale calculateLocale(FacesContext context)
  {
    return MessageLocale.getLocale();
  }
  
  public String calculateRenderKitId(FacesContext context)
  {
    return this.baseHandler.calculateRenderKitId(context);
  }
  
  public UIViewRoot createView(FacesContext context, String viewId) {
    return this.baseHandler.createView(context, viewId);
  }
  
  public String getActionURL(FacesContext context, String viewId) {
    return this.baseHandler.getActionURL(context, viewId);
  }
  
  public String getResourceURL(FacesContext context, String path) {
    return this.baseHandler.getResourceURL(context, path);
  }

  @Override
  public String getWebsocketURL(FacesContext facesContext, String s) {
    return null;
  }

  public void renderView(FacesContext context, UIViewRoot viewToRender)
    throws IOException, FacesException {
    this.baseHandler.renderView(context, viewToRender);
  }
  
  public UIViewRoot restoreView(FacesContext context, String viewId)
  {
    return this.baseHandler.restoreView(context, viewId);
  }
  
  public void writeState(FacesContext context)
    throws IOException
  {
    this.baseHandler.writeState(context);
  }
}

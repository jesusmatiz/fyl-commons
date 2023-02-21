package co.com.ingeneo.utils.web.jsf;

import java.util.Locale;

import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;

import co.com.ingeneo.utils.messages.MessageLocale;

import org.jboss.weld.jsf.ConversationAwareViewHandler;

public class CustomFaceletViewHandler extends ConversationAwareViewHandler {
    public CustomFaceletViewHandler(ViewHandler parent) {
        super(parent);
    }

    public Locale calculateLocale(FacesContext context) {
        return MessageLocale.getLocale();
    }
}

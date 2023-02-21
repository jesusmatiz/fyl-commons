package co.com.ingeneo.utils.web.jsf;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class TextValidator implements Validator {

	public void validate(FacesContext facexcontext, UIComponent uicomponent, Object objValue)
			throws ValidatorException {
		if ((uicomponent.getAttributes().get("regexp") == null)
				|| (((String) uicomponent.getAttributes().get("regexp")).trim().length() < 1)) {
			String msgRegExpNull = "attribute regexp is required. Example: <f:attribute name=\"regexp\" value=\"[^,:;]*\"/>";
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msgRegExpNull, msgRegExpNull));
		}
		String regexp = (String) uicomponent.getAttributes().get("regexp");

		String val = objValue.toString();
		if (!val.matches(regexp)) {
			if ((uicomponent.getAttributes().get("messageerror") == null)
					|| (((String) uicomponent.getAttributes().get("messageerror")).trim().length() < 1)) {
				String msgMessageErrorNull = "attribute messageerror is required. Example: <f:attribute name=\"messageerror\" value=\"Invalid value\"/>";
				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, msgMessageErrorNull, msgMessageErrorNull));
			}
			String messageError = (String) uicomponent.getAttributes().get("messageerror");

			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, messageError, messageError));
		}
	}
}

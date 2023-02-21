package co.com.ingeneo.utils.web.jsf;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class ListShuttleItemConverter implements Converter {
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
        int index = value.indexOf(":");
        Long id = Long.valueOf(value.substring(0, index));
        String valueStr = value.substring(index + 1);
        return new ListShuttleItem(id, valueStr);
    }

    public String getAsString(FacesContext ctx, UIComponent component, Object value) {
        ListShuttleItem item = (ListShuttleItem)value;
        return item.getId() + ":" + item.getLabel();
    }
}

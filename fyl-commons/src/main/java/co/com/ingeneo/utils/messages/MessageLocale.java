package co.com.ingeneo.utils.messages;

import java.util.Locale;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class MessageLocale {
	private static final String SYSTEM_PROP = "co.com.ingeneo.utils.messages.locale";
	private static final String UNDERBAR = "_";
	private static Locale locale = null;

	static {
		String localeStr = System.getProperty("co.com.ingeneo.utils.messages.locale");
		if (localeStr == null) {
			locale = Locale.getDefault();
			localeStr = locale.toString();
		}
		setLocale(localeStr);
	}

	public static Locale getLocale() {

		Object localeSession = getObjectfromSession("dms_locale");

		if (localeSession != null) {
			return buildLocale(localeSession.toString());
		}

		Cookie cookieLocale = getCookie("dms_locale");

		if (cookieLocale != null) {
			return buildLocale(cookieLocale.getValue());
		}

		final FacesContext facesContext = FacesContext.getCurrentInstance();

		if (facesContext != null) {

			final Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap();
			final String lang = (String) sessionMap.get("lang");

			if (lang != null && !lang.isEmpty()) {
				return buildLocale(lang);
			}
		}

		return locale;
	}

	private static Cookie getCookie(String name) {
		final FacesContext facesContext = FacesContext.getCurrentInstance();

		if (facesContext != null) {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();

			Cookie cookie = null;
			Cookie[] userCookies = new Cookie[] {};
			if (request != null) {
				userCookies = request.getCookies();
			}
			if (userCookies != null && userCookies.length > 0) {
				for (int i = 0; i < userCookies.length; i++) {
					if (userCookies[i].getName().equals(name)) {
						cookie = userCookies[i];
						return cookie;
					}
				}
			} else {
				// System.out.println("No hay cookies para la URl:" +request.getRequestURL());
			}
		}

		return null;
	}

	private static Object getObjectfromSession(String name) {
		final FacesContext facesContext = FacesContext.getCurrentInstance();

		if (facesContext == null) {
			return null;
		}

		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		if (!sessionMap.containsKey(name)) {
			return null;
		}

		return sessionMap.get(name);
	}

	public static synchronized void setLocale(String localeStr) {
		System.setProperty("co.com.ingeneo.utils.messages.locale", localeStr);
		locale = buildLocale(localeStr);
	}

	public static Locale buildLocale(String localeStr) {
		String[] localeData = localeStr.split("_");
		if ((localeData == null) || (localeData.length == 0)) {
			throw new IllegalArgumentException("Invalid locale " + localeStr);
		}

		Locale localLocale = null;

		if (localeData.length == 1) {
			localLocale = new Locale(localeData[0].trim());
		} else if (localeData.length == 2) {
			localLocale = new Locale(localeData[0].trim(), localeData[1].trim());
		} else if (localeData.length == 3) {
			localLocale = new Locale(localeData[0].trim(), localeData[1].trim(), localeData[2].trim());
		}

		return localLocale;
	}
}

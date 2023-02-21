package co.com.ingeneo.utils.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LanguageFilter implements Filter {

	private static final String LANG = "lang";

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		// Setting the language attribute to session
		final HttpServletRequest httpReq = ((HttpServletRequest)req);
		final HttpSession session = httpReq.getSession();
		final String requestLang = (String)httpReq.getParameter(LANG);
		
		if (requestLang != null && !requestLang.isEmpty()) {
			session.setAttribute(LANG, requestLang);
		}
		
        chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

}

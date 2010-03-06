package org.stablylab.webui.server.util;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class LogOutListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		Map<String, Set<HttpSession>> sessionsMap = (Map<String, Set<HttpSession>>) event.getSession().getServletContext().getAttribute("sessionsMap");
		if(sessionsMap == null)
			return;
		Set<HttpSession> sessions = sessionsMap.get(event.getSession().getAttribute("organisationID"));
		if(sessions == null)
			return;
		if(!sessions.contains(event.getSession()))
			return;
		sessions.remove(event.getSession());
	}

}

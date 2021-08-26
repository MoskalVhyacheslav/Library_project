package listeners;

import org.apache.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Map;
import java.util.TreeMap;

@WebListener
public class SessionListener implements HttpSessionListener {
    private static final Logger LOGGER = Logger.getLogger(SessionListener.class);
    private static Map<String,HttpSession> sessionsKeeper = new TreeMap<>();

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        LOGGER.trace("Session have created -> "+session.getId());
        sessionsKeeper.put(session.getId(), session);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        LOGGER.trace("Session have destroyed -> "+event.getSession().getId());
        sessionsKeeper.remove(event.getSession().getId());
    }

    public static HttpSession find(final String sessionId) {
        return sessionsKeeper.get(sessionId);
    }

    public static boolean containsSession(final String sessionId) {
        return sessionsKeeper.containsKey(sessionId);
    }

}

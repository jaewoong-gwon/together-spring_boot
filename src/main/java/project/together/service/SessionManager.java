package project.together.service;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
    private static final String SESSION_COOKIE_NAME = "SessionId";

    private Map<String,Object> sessionStore = new ConcurrentHashMap<>();

    //세션 생성.
    public void createSession(Object value, HttpServletResponse response){
        //세션 id 생성.
        String sessionId = UUID.randomUUID().toString();
        //해당 값을 세션에 저장.
        sessionStore.put(sessionId,value);


        //생성한 세션 id로 쿠키 생성.
        Cookie sessionCookie = new Cookie(SESSION_COOKIE_NAME,sessionId);
        response.addCookie(sessionCookie);
    }

    //세션 조회
    public Object getSession(HttpServletRequest request){
        Cookie sessionCookie = findCookie(request,SESSION_COOKIE_NAME);
        if(sessionCookie == null) return null;

        return sessionStore.get(sessionCookie.getValue());
    }

    //세션 만료
    public void expire(HttpServletRequest request){
        Cookie sessionCookie = findCookie(request,SESSION_COOKIE_NAME);
        if(sessionCookie != null) sessionStore.remove(sessionCookie.getValue());
    }

    public Cookie findCookie(HttpServletRequest request, String cookieName){
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }
}

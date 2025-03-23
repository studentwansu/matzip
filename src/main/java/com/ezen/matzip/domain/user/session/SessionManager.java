package com.ezen.matzip.domain.user.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionManager {

    private static final String SESSION_KEY = "user";

    public static void createSession(HttpServletRequest request, String id, String role) {
        HttpSession session = request.getSession(true);
        session.setAttribute("id", id);
        session.setAttribute("role", role);
    }

    public static void invaildateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    public static boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("id") != null;
    }

    public static String getId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null) ? (String) session.getAttribute("id") : null;
    }

    public static String getRole(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null) ? (String) session.getAttribute("role") : null;
    }
}



package bm;

import Runnble_.thr;
import User.Check;
import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.*;
import java.util.concurrent.*;

@WebSocket
public class MsgSrv {

    // Store sessions if you want to, for example, broadcast a message to all users
    private static final ConcurrentMap<String, Session> sessions = new ConcurrentHashMap<>();

    @OnWebSocketConnect
    public void connected(Session session) {
        System.out.println("connected in");
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) {
        sessions.remove(session);
    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
        System.out.println("message in ->" + message);
        Thread thread = new thr(session,message);
        thread.start();
    }


}
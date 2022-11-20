package Runnble_;

import User.Check;
import org.eclipse.jetty.websocket.api.*;

import java.io.IOException;

public class thr extends Thread {
    public Session session;
    public String message;
    public thr(Session session, String message){
        super();
        this.session = session;
        this.message = message;
    }
    @Override
    public void run() {
        System.out.println("->线程中处理消息");
        try {
            Check.contribute(session, message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

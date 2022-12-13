import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/Chat")
public class Chat {
    
    private static Set<Session> userSessions = Collections.newSetFromMap(new ConcurrentHashMap<Session, Boolean>());
    
    @OnOpen
    public void onOpen(Session currSession) {
        userSessions.add(currSession);
    }
    
    @OnClose
    public void onClose(Session currSession) {
        userSessions.remove(currSession);
    }
    
    @OnMessage
    public void onMessage(String message, Session currSession) {
        for ( Session ses : userSessions ) {
            ses.getAsyncRemote().sendText(message);
        }
    }
}
package websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import websocket.message.MatchEvent;
import websocket.message.EventType;

import java.io.IOException;

public class EventClient {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        // connection url
        String uri = "ws://localhost:8090";

        StandardWebSocketClient client = new StandardWebSocketClient();
        WebSocketSession session = null;
        try {
            // The socket that receives events
            EventHandler socket = new EventHandler();
            // Make a handshake with server
            ListenableFuture<WebSocketSession> fut = client.doHandshake(socket, uri);
            // Wait for Connect
            session = fut.get();
            // Send a message
            session.sendMessage(new TextMessage("join htefl"));
            MatchEvent readyEvent = new MatchEvent(EventType.READY);
            session.sendMessage(new TextMessage(objectMapper.writeValueAsBytes(readyEvent)));
            // Close session
            session.close();

        } catch (Throwable t) {
            t.printStackTrace(System.err);
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (IOException ignored) {
            }
        }
    }
}

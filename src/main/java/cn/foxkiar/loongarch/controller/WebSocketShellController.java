package cn.foxkiar.loongarch.controller;

import com.pty4j.PtyProcess;
import com.pty4j.PtyProcessBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
@ServerEndpoint(value = "/websocket/shell")
public class WebSocketShellController {

    private Session session;
    private PtyProcess process;
    private PrintWriter writer;

    private void startOutputPump(InputStream input) {
        Thread thread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
                char[] buffer = new char[1024];
                int read;
                while ((read = reader.read(buffer)) != -1) {
                    String data = new String(buffer, 0, read);
                    session.getBasicRemote().sendText(data);
                }
            } catch (IOException e) {
                log.error("终端输出转发异常", e);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }


    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        String[] command = {"wsl.exe"};
        Map<String, String> env = new HashMap<>(System.getenv());
        env.put("TERM", "xterm-256color");
        this.process = new PtyProcessBuilder().setCommand(command).setEnvironment(env).start();
        this.writer = new PrintWriter(process.getOutputStream());

        startOutputPump(process.getInputStream());
        startOutputPump(process.getErrorStream());
    }

    @OnClose
    public void onClose(CloseReason closeReason) throws IOException {
        this.session.close(closeReason);
        if (this.process != null) this.process.destroy();
    }

    @OnError
    public void onError(Throwable throwable) throws IOException {
        this.session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, throwable.getMessage()));
    }

    @OnMessage
    public void onMessage(String message) {
        if (writer != null) {
            writer.print(message);
            writer.flush();
        }
    }
}
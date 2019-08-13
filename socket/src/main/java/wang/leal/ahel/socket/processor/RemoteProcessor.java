package wang.leal.ahel.socket.processor;

import java.util.List;

public interface RemoteProcessor {
    List<String> handleMessage(byte[] message);
}

package wang.leal.ahel.sample.socket;

import java.util.ArrayList;
import java.util.List;

import wang.leal.ahel.socket.processor.RemoteProcessor;

public class ReceiveProcessor implements RemoteProcessor {
    @Override
    public List<String> handleMessage(byte[] message) {
        List<String> messages = new ArrayList<>();
        String decodeMessage;
        while ((decodeMessage = CMDMessage.getInstance().getReceiveMessage(message))!=null){
            messages.add(decodeMessage);
        }
        return messages;
    }


}

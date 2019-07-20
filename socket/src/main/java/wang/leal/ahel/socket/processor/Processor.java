package wang.leal.ahel.socket.processor;

public interface Processor {

    String request(String message);
    String receive(String message);
}

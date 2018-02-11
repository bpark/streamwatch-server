package com.github.bpark.streamwatch;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DefaultMessageProducer implements MessageProducer {

    private BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>(1000);

    public void addMessage(String message) {
        messageQueue.offer(message);
    }

    public String next() {
        return messageQueue.poll();
    }
}

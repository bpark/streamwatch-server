package com.github.bpark.streamwatch;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

public class MessageProducerRegistry {

    private static ConcurrentMap<String, Session> sessionMap = new ConcurrentHashMap<>();
    private static List<MessageProducer> producers = new CopyOnWriteArrayList<>();

    private static ExecutorService executorService = Executors.newFixedThreadPool(4);

    public static DefaultMessageProducer createProducer() {
        DefaultMessageProducer messageProducer = new DefaultMessageProducer();
        producers.add(messageProducer);

        return messageProducer;
    }

    public static void registerProducer(MessageProducer messageProducer) {
        producers.add(messageProducer);
    }

    static void addSession(Session session) {
        if (session.isOpen()) {
            sessionMap.putIfAbsent(session.getId(), session);
        } else {
            sessionMap.remove(session.getId());
        }

    }

    static void removeSession(Session session) {
        sessionMap.remove(session.getId());
    }

    static void send() {
        executorService.submit(() -> {
           producers.forEach(producer -> {
               String next = producer.next();
               sessionMap.forEach((id, session) -> {
                   try {
                       session.getBasicRemote().sendText(next);
                   } catch (IOException e) {
                       throw new RuntimeException("Error sending message", e);
                   }
               });
           });
        });
    }

    private MessageProducerRegistry() {
    }

}

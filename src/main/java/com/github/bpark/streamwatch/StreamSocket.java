/*
 * Copyright 2018 Kurt Sparber
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.bpark.streamwatch;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;


@ServerEndpoint(value = "/streams", configurator = ServletAwareConfig.class)
public class StreamSocket {


    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        MessageProducerRegistry.addSession(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        session.getBasicRemote().sendText(message.toUpperCase());
    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {
        MessageProducerRegistry.removeSession(session);
    }

    @OnError
    public void onError(Throwable error) {
    }
}

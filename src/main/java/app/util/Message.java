package app.util;

import static app.Application.gson;

public class Message {
    public String head;
    public Object body;

    public Message(String json) {
        Message message = gson.fromJson(json, Message.class);
        this.head = message.head;
        this.body = message.body;
    }

    public Message(String head, Object body) {
        this.head = head;
        this.body = body;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}

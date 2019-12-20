package app.util;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Message {
    private String head;
    private JSONObject message;

    public Message() {
    }

    public Message(String head) {
        this.head = head;
        this.message = new JSONObject();
        this.message.put("head", head);
    }

    public static Message parse(String message) {
        Message msg = new Message();
        if (message == null) {
            msg.head = "Malformed message";
        } else {
            msg.message = (JSONObject) JSONValue.parse(message);
            msg.head = (String) msg.message.get("head");
        }
        return msg;
    }

    public String getValue(String field) {
        return (String) message.get(field);
    }

    public Message put(String field, String value) {
        this.message.put(field, value);
        return this;
    }

    public String getHead() {
        return head;
    }

    @Override
    public String toString() {
        return message.toJSONString();
    }
}

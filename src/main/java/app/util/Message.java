package app.util;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

public class Message {
    transient private String head;
    transient private JsonObject body;

    public Message() {
    }

    /**
     * Instantiate a new Message.
     *
     * @param head Header of new Message.
     */
    public Message(String head) {
        this.head = head;
        this.body = new JsonObject();
        this.body.put("head", head);
    }

    /**
     * Parse a received message, and instantiate a Message object.
     *
     * @param message The received json message.
     * @return The instance of Message.
     */
    public static Message parse(String message) {
        Message msg = new Message();
        if (message == null) {
            msg.head = "Malformed message";
        } else {
            try {
                msg.body = (JsonObject) Jsoner.deserialize(message);
            } catch (JsonException e) {
                e.printStackTrace();
                msg.body = new JsonObject();
            }
            msg.head = (String) msg.body.get("head");
        }
        return msg;
    }

    public String getValue(String field) {
        return (String) body.get(field);
    }

    public Message put(String field, String value) {
        this.body.put(field, value);
        return this;
    }

    public String getHead() {
        return head;
    }

    @Override
    public String toString() {
        return body.toJson();
    }
}

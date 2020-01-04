package app.util;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

public class Message {
    private String head;
    private JsonObject message;

    public Message() {
    }

    /**
     * Instantiate a new Message.
     *
     * @param head Header of new Message.
     */
    public Message(String head) {
        this.head = head;
        this.message = new JsonObject();
        this.message.put("head", head);
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
                msg.message = (JsonObject) Jsoner.deserialize(message);
            } catch (JsonException e) {
                e.printStackTrace();
                msg.message = null;
            }
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
        return message.toJson();
    }
}

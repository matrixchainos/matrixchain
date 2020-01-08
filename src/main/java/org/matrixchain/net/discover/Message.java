package org.matrixchain.net.discover;

public class Message {
    private int type;
    private String msg;

    public Message() {
    }

    public Message(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type='" + type + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}

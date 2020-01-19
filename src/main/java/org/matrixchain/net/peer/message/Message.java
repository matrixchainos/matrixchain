package org.matrixchain.net.peer.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Message {
    protected final static Logger logger = LoggerFactory.getLogger("p2p message");

    protected boolean parsed;
    protected byte[] encoded;
    protected byte code;

    public Message(){

    }

    public Message(byte[] encoded){
        this.encoded = encoded;
        parsed = false;
    }

    public abstract byte[] getEncoded();

    public abstract Class<?> getAnswerMessage();

    public abstract String toString();

    public abstract Enum getCommand();

    public byte getCode() {
        return code;
    }
}

package org.matrixchain.net.peer.message;

import java.util.HashMap;
import java.util.Map;

public enum P2pMessageCodes {

    HELLO(0x01),

    DISCONNECT(0x01),

    PING(0x02),

    PONG(0x03),

    GET_PEERS(0x04),

    PEERS(0x05),

    USER(0x0F);

    private final int cmd;

    private static final Map<Integer, P2pMessageCodes> map = new HashMap<>();

    static {
        for (P2pMessageCodes code : P2pMessageCodes.values()) {
            map.put(code.cmd, code);
        }
    }

    P2pMessageCodes(int cmd) {
        this.cmd = cmd;
    }

    public static P2pMessageCodes fromBytes(byte cmd) {
        return map.get(cmd);
    }

    public static boolean inRange(byte cmd) {
        return cmd >= HELLO.asByte() && cmd <= USER.asByte();
    }

    public byte asByte() {
        return (byte) cmd;
    }
}

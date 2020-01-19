package org.matrixchain.net.peer.handler;

import org.matrixchain.crypto.ECKey;
import org.spongycastle.math.ec.ECPoint;
import sun.nio.ch.Secrets;

import java.security.SecureRandom;

public class EncryptionHandshake {

    private final static int NONCE_SIZE = 32;
    private final static int MAC_SIZE = 256;
    private final static int SECRET_SIZE = 32;
    private SecureRandom random = new SecureRandom();
    private boolean isInitiator;
    private ECKey ephemeralKey;
    private ECPoint remotePublicKey;
    private ECPoint remoteEphemeralKey;
    private byte[] initiatorNonce;
    private byte[] responderNonce;
    private Secrets secrets;

    public EncryptionHandshake(ECPoint remotePublicKey){
        this.remotePublicKey = remotePublicKey;
        ephemeralKey = new ECKey(random);
        initiatorNonce = new byte[NONCE_SIZE];
        random.nextBytes(initiatorNonce);
        isInitiator = true;
    }

    public EncryptionHandshake(ECPoint remotePublicKey, ECKey ephemeralKey, byte[] initiatorNonce, byte[] responderNonce, boolean isInitiator){
        this.remotePublicKey = remotePublicKey;
        this.ephemeralKey = ephemeralKey;
        this.initiatorNonce = initiatorNonce;
        this.responderNonce = responderNonce;
        this.isInitiator = isInitiator;
    }

    public EncryptionHandshake(){
        ephemeralKey = new ECKey(random);
        responderNonce = new byte[NONCE_SIZE];
        random.nextBytes(responderNonce);
        isInitiator = false;
    }


}

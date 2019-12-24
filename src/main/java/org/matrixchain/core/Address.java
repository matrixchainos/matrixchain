package org.matrixchain.core;

import org.matrixchain.crypto.ECKey;
import org.spongycastle.util.encoders.Hex;

import java.math.BigInteger;

public class Address {

    private String value;
    public static final int LENGTH = 160;
    public static final Address DEFAULT = new Address(BigInteger.ZERO);

    public String getValue(){
        return value;
    }

    public Address(String value){
        this.value = value;
    }

    public Address(BigInteger privateKey){
        ECKey ecKey = ECKey.fromPrivate(privateKey);
        this.value = Hex.toHexString(ecKey.getAddress());
    }

    public static Address fromPrivate(BigInteger privateKey){
        return fromPrivate(privateKey);
    }

    public static Address fromPrivate(String privateKey){
        return fromPrivate(Hex.decode(privateKey));
    }

    public static Address fromPrivate(byte[] privateKey){

        return new Address(new BigInteger(privateKey));
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Address address = (Address) o;

        return value != null ? value.equals(address.value) : address.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

}

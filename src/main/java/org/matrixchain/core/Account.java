package org.matrixchain.core;

import org.spongycastle.util.encoders.Hex;

public class Account {

    private static final int LENGTH = 42;
    private static final String PREFIX = "0x";

    private String address;
    private long balance;
    private final long nonce;

    public String getAddress() {
        return this.address;
    }

    public byte[] getAddressBytes() {
        return Hex.decode(this.address);
    }

    private Account(String address, long balance, long nonce) {
        this.address = address;
        this.balance = balance;
        this.nonce = nonce;
    }

    public static Account create(String address, long balance, long nonce) {
        if (!isValid(address) || balance < 0)
            return null;
        return new Account(address, balance, nonce);
    }

    public static Account create(String address, long balance) {
        return create(address, balance, 0);
    }

    public static boolean isValid(String address) {
        try {
            if (address.length() != LENGTH) {
                throw new Exception("address's length should be 42.");
            }
            if (!address.startsWith("0x")) {
                throw new Exception("address's start with '0x'.");
            }
            try {
                Hex.decode(address.substring(2));
            }catch (Exception e){
                throw new Exception("address's should be valid hex string.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "address='" + address + '\'' +
                ", balance=" + balance +
                ", nonce=" + nonce +
                '}';
    }
}

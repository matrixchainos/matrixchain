package org.matrixchain.core;

import org.junit.Test;
import org.matrixchain.crypto.ECKey;

import java.math.BigInteger;

import static org.apache.commons.codec.digest.DigestUtils.sha256;

public class AccountTest {

    @Test
    public void account(){
        Account account = Account.create("a284c5935e33ec2c363913b6cf628da5c81defc2f96afb64690ae7a2f5535620");
        Account account1 = Account.create("a284c5935e33ec2c363213b6cf628da5c81defc2f96afb64690ae7a2f5535620");
        System.out.println(account.getAddress());
        System.out.println(account1.getAddress());
        System.out.println(account.equals(account1));


        Contract transfer = Transfer.create(Account.ZERO_ADDRESS,
                10000L,
                "I am reward to kay, for thank him help me.");

        Transaction transaction = new Transaction(account.getAddress(), transfer);

        account.signTransaction(transaction);

        ECKey ecKey = ECKey.fromPrivate(new BigInteger("a284c5935e33ec2c363913b6cf628da5c81defc2f96afb64690ae7a2f5535620", 16));
        ECKey.ECDSASignature signature = ecKey.sign(sha256(transaction.getContract().toString()));
        String sign = signature.toHex();

//        ECKey.ECDSASignature signature1 = new ECKey.ECDSASignature(Hex.decode(sign));
//        System.out.println(signature1.toHex());
    }

    @Test
    public void getAddress() {
    }

    @Test
    public void getAddressBytes() {
    }

    @Test
    public void createAccount() {
    }

    @Test
    public void signTransaction() {
    }

    @Test
    public void signBlockHeader() {
    }

    @Test
    public void getECDSASignature() {
    }

    @Test
    public void equals() {
    }

}
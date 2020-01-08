package org.matrixchain.core;

import org.junit.Test;
import org.matrixchain.crypto.ECKey;
import org.matrixchain.util.AccountUtil;

import java.math.BigInteger;

import static org.apache.commons.codec.digest.DigestUtils.sha256;

public class AccountUtilTest {

    @Test
    public void account(){
        AccountUtil accountUtil = AccountUtil.create("a284c5935e33ec2c363913b6cf628da5c81defc2f96afb64690ae7a2f5535620");
        AccountUtil accountUtil1 = AccountUtil.create("a284c5935e33ec2c363213b6cf628da5c81defc2f96afb64690ae7a2f5535620");
        System.out.println(accountUtil.getAddress());
        System.out.println(accountUtil1.getAddress());
        System.out.println(accountUtil.equals(accountUtil1));


        Contract transfer = Transfer.create(AccountUtil.ZERO_ADDRESS,
                10000L,
                "I am reward to kay, for thank him help me.");

        Transaction transaction = new Transaction(accountUtil.getAddress(), transfer);

        accountUtil.signTransaction(transaction);

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
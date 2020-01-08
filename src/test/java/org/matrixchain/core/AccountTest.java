package org.matrixchain.core;

import org.junit.Test;
import org.matrixchain.util.AccountUtil;

import static org.junit.Assert.*;

public class AccountTest {

    @Test
    public void create() {
        AccountUtil accountUtil = AccountUtil.create("FD5BB82CBCB378740082FD9E8DC3CC3A959FCD8176CFD829D403D1AF3ED0DDA8");
        System.out.println(accountUtil.getAddress());
        System.out.println(Account.isValid(accountUtil.getAddress()));
        System.out.println(Account.isValid("0x3267aeb2dfaa6d586c896ef0b76eefbd828db2b8"));

        System.out.println("0x3267aeb2dfaa6d586c896ef0b76eefbd828db2b8".length());
        Account account = Account.create("0x3267aeb2dfaa6d586c896ef0b76eefbd828db2b8", 2);
        System.out.println(account.toString());
    }

    @Test
    public void isValid() {
    }
}
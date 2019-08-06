package com.revo.lut.util;

import com.revo.lut.error.AccountDoesNotExistException;
import com.revo.lut.error.IllegalAmountException;

import java.math.BigDecimal;

public class Validator {
    public static void validateAccountId(String accountId) {
        if (accountId == null || accountId.length() == 0) {
            throw new AccountDoesNotExistException(String.format("Illegal AccountId %s provided", accountId));
        }
    }
    public static void validateAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalAmountException(String.format("Illegal Amount %d provided", amount));
        }
    }
}

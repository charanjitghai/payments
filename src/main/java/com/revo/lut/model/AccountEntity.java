package com.revo.lut.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;
import java.math.BigDecimal;

@AllArgsConstructor
public class AccountEntity {
    @Nonnull
    @Getter
    @Setter
    private String id;

    @Nonnull
    @Getter
    @Setter
    private BigDecimal balance;
}

package com.revo.lut;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.revo.lut.dal.AccountResourceDataProvider;
import com.revo.lut.ds.AccountDataStore;

public class PaymentsModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AccountDataStore.class).in(Singleton.class);
        bind(AccountResourceDataProvider.class).in(Singleton.class);
    }
}

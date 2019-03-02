package com.sourcey.materiallogindemo;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {

    @Override

    public void onCreate() {
        super.onCreate();
        Realm.init(this);

       /* RealmConfiguration config =
                new RealmConfiguration.Builder()
                        .deleteRealmIfMigrationNeeded()
                        .addModule(new LocalSyncModule())
                        .schemaVersion(BuildConfig.VERSION_CODE)
                        .build();

        Realm.setDefaultConfiguration(config);*/
    }
}

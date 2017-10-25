package me.hachy.routineweek


import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics

import io.realm.Realm
import io.realm.RealmConfiguration

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()
                .name("RoutineWeekAndroid")
                .schemaVersion(1)
                .build()
        //        Realm.deleteRealm(realmConfig); // Delete Realm between app restarts.
        Realm.setDefaultConfiguration(realmConfig)

        FirebaseAnalytics.getInstance(this)

        MobileAds.initialize(applicationContext, resources.getString(R.string.banner_ad_unit_id_test))
    }
}

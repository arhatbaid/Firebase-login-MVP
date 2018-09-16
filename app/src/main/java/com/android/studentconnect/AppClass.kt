package com.android.studentconnect

import android.app.Application
import android.content.ContextWrapper
import android.support.multidex.MultiDex
import com.pixplicity.easyprefs.library.Prefs
import uk.co.chrisjenx.calligraphy.CalligraphyConfig



class AppClass : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

        //Caligraphy
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build())

        // Initialize the Prefs class
        Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(packageName)
                .setUseDefaultSharedPreference(true)
                .build()
    }
}
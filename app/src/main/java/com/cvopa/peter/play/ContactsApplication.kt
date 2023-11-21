package com.cvopa.peter.play

import android.app.Application
import com.cvopa.peter.billduAsignment.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ContactsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    super.log(priority, LOG_TAG_PREFIX + tag!!, message, t)
                }
            })
        }
    }
}

private const val LOG_TAG_PREFIX = "contacts_"
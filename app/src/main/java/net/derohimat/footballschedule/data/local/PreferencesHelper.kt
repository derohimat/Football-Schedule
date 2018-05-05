package net.derohimat.footballschedule.data.local

import android.content.Context
import android.content.SharedPreferences
import net.derohimat.footballschedule.injection.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PreferencesHelper @Inject
constructor(@ApplicationContext context: Context) {

    private val mPref: SharedPreferences

    init {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun clear() {
        mPref.edit().clear().apply()
    }

    companion object {

        val PREF_FILE_NAME = "football_pref_file"
    }

}

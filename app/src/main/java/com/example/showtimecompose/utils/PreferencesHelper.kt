package com.example.showtimecompose.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferencesHelper @Inject constructor(@ApplicationContext context: Context) {

    private val mContext = context.applicationContext
    private val  mSharedPreferences : SharedPreferences = context.getSharedPreferences("SHOWTIME", Context.MODE_PRIVATE)
    private val  mEditor: SharedPreferences.Editor = mSharedPreferences.edit();


    fun  getStringPreference( key:String): String? {
        return mSharedPreferences.getString(key , null);
    }

    fun  setStringPreference( key: String ,  value:String){
        mEditor.putString(key , value);
        mEditor.commit();
    }

}

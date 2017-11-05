package com.exucodeiro.veterinarioapp.Services

import android.content.Context
import com.exucodeiro.veterinarioapp.Models.Login

/**
 * Created by vitor on 05/11/2017.
 */
class LoginSettings(context: Context) {
    val FILE_NAME = "com.exucodeiro.preferences"
    val preferences = context.getSharedPreferences(FILE_NAME, 0)

    var login: Login
        get() = Login(
                    preferences.getInt("login_id", 0),
                    preferences.getString("username", ""),
                    preferences.getString("password", ""),
                    preferences.getString("login_type", ""))
        set(value) {
            preferences.edit().putInt("login_id", value.id).apply()
            preferences.edit().putString("username", value.username).apply()
            preferences.edit().putString("password", value.password).apply()
            preferences.edit().putString("login_type", value.tipo).apply()
        }

    fun reset() {
        login = Login(0, "", "", "")
    }

}
package ch.ost.mge.mini.lanchat.model

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

object SettingsStore {
    var serverAddress: String = ""
    var username: String = ""

    private const val file = "ch.ost.rj.mge.v05.myapplication.preferences"
    private const val usernameKey = "settings.username"
    private const val serverKey = "settings.server"
    private const val mode: Int = Context.MODE_PRIVATE

    fun save(activity: AppCompatActivity) {
        val preferences = activity.getSharedPreferences(file, mode)
        val editor = preferences.edit()
        editor.putString(usernameKey, username)
        editor.putString(serverKey, serverAddress)
        editor.apply()
    }

    fun load(activity: AppCompatActivity) {
        val preferences = activity.getSharedPreferences(file, mode)
        username = preferences.getString(usernameKey, "Hansi").toString()
        serverAddress = preferences.getString(serverKey, "").toString()
    }
}

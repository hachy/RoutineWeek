package me.hachy.routineweek.util


import android.content.Context

class Prefs(private val context: Context) {

    var tagColorIdx: Int
        get() {
            val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return preferences.getInt(COLOR, 0)
        }
        set(color) {
            val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putInt(COLOR, color)
            editor.apply()
        }

    companion object {

        private val PREF_NAME = "routine_week"
        private val COLOR = "color"
    }

}

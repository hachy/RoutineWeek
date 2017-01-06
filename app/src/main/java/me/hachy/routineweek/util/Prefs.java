package me.hachy.routineweek.util;


import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private static final String PREF_NAME = "routine_week";
    private static final String COLOR = "color";
    private Context context;

    public Prefs(Context context) {
        this.context = context;
    }

    public void setTagColorIdx(int color) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(COLOR, color);
        editor.apply();
    }

    public int getTagColorIdx() {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(COLOR, 0);
    }

}

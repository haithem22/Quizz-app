package web.afor.innovation.quizapp.LocalData;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import web.afor.innovation.quizapp.R;

/**
 * Created by Administrateur on 28/07/2016.
 */
public class Preferences {
    //public static boolean firstTime;
    public static SharedPreferences sharedPreferences ;

    public static void saveBoolean( boolean value,String key, Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    public static boolean loadBoolean(Context context, String key, boolean defaultValue){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key,defaultValue);
    }

    public static void saveString( String value,String key, Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String loadString(Context context, String key, String defaulValue){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, defaulValue);
    }

    public static void saveInt(Context context, String key, int value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int loadInt(Context context, String key, int defautlValue){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(key, defautlValue);
    }

}//getApplicationContext()

package joaorodrigues.mobileimgur.controller;

import android.content.Context;
import android.content.SharedPreferences;

import joaorodrigues.mobileimgur.MobileImgur;

/**
 * Shared preferences controller for Mobile Imgur
 */
public class ImgurPreferencesController {

    private static final String API = "api";
    private static final String API_SECTION = "section";
    private static final String API_WINDOW = "window";
    private static final String API_SORT = "sort";
    private static final String API_VIRAL = "viral";

    private static SharedPreferences getSharedPreferences(String prefs) {
        Context context = MobileImgur.get();
        return context.getSharedPreferences(prefs, Context.MODE_PRIVATE);
    }

    public static void storeSection(String section) {
       getSharedPreferences(API)
               .edit()
               .putString(API_SECTION, section)
               .apply();
    }

    public static void storeWindow(String window) {
        getSharedPreferences(API)
                .edit()
                .putString(API_WINDOW, window)
                .apply();
    }

    public static void storeSort(String sort) {
        SharedPreferences prefs = getSharedPreferences(API);
        prefs.edit().putString(API_SORT, sort).apply();
    }

    public static void storeViral(boolean viral) {
        getSharedPreferences(API).
                edit()
                .putBoolean(API_VIRAL, viral)
                .apply();
    }

    public static String getApiSection() {
       return getSharedPreferences(API)
               .getString(API_SECTION, null);
    }

    public static String getApiWindow() {
        return getSharedPreferences(API)
                .getString(API_WINDOW, null);
    }

    public static String getApiSort() {
        return getSharedPreferences(API)
                .getString(API_SORT, null);
    }

    public static boolean getApiViral() {
        return getSharedPreferences(API)
                .getBoolean(API_VIRAL, true);
    }

}

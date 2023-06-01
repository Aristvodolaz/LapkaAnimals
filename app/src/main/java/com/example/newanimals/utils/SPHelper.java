package com.example.newanimals.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.newanimals.ApplicationLoader;

public class SPHelper {
    public static final String FILE_NAME = "lapka";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String PHONE = "PHONE";
    public static final String DATE = "DATE";
    public static final String CITY = "CITY";
    public static final String TYPE = "TYPE";
    public static final String NAMETYPE = "NAMETYPE";
    public static final String LOGIN = "LOGIN";

    public static final String LAT = "lat";
    public static final String LON = "lon";

    public static final String URL_PHOTO = "url_photo";

    private static SharedPreferences getPrefs() {
        return ApplicationLoader.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEdit() {
        return getPrefs().edit();
    }

    public static void setName(String name) {
        getEdit().putString(NAME, name).commit();
    }
    public static String getName() {
        return getPrefs().getString(NAME, "");
    }

    public static String getSurname() {
        return getPrefs().getString(SURNAME, "");
    }
    public static void setSurname(String name) {getEdit().putString(SURNAME, name).commit();}

    public static void setPhone(String phone) {
        getEdit().putString(PHONE, phone).commit();
    }
    public static String getPhone() {
        return getPrefs().getString(PHONE, "");
    }

    public static String getDate() {
        return getPrefs().getString(DATE, "");
    }
    public static void setDate(String date) {
        getEdit().putString(DATE, date).commit();
    }

    public static void setCity(String city) {
        getEdit().putString(CITY, city).commit();
    }
    public static String getCity() {
        return getPrefs().getString(CITY, "");
    }

    public static void setType(String type) {
        getEdit().putString(TYPE, type).commit();
    }
    public static String getType() {
        return getPrefs().getString(TYPE, "");
    }

    public static void setUrlPhoto(String type) {
        getEdit().putString(URL_PHOTO, type).commit();
    }
    public static String getUrlPhoto() {
        return getPrefs().getString(URL_PHOTO, "");
    }

    public static void setLon(Float lon) {
        getEdit().putFloat(LON, lon).commit();
    }
    public static Float getLon() {
        return getPrefs().getFloat(LON, 0);
    }

    public static void setLat(Float lat) {
        getEdit().putFloat(LAT, lat).commit();
    }
    public static Float getLat() {
        return getPrefs().getFloat(LAT, 0);
    }

    public static void setNametype(String lat) {
        getEdit().putString(NAMETYPE, lat).commit();
    }
    public static String getNametype() {
        return getPrefs().getString(NAMETYPE, "");
    }

    public static void setLogin(String lat) {
        getEdit().putString(LOGIN, lat).commit();
    }
    public static String getLogin() {
        return getPrefs().getString(LOGIN, "");
    }
}

package com.example.newanimals.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.newanimals.ApplicationLoader;

public class SPHelper {
    public static final String FILE_NAME = "lapka";

    public static final String DATE = "DATE";

    public static final String TYPE = "TYPE";
    public static final String NAMETYPE = "NAMETYPE";
    public static final String LOGIN = "LOGIN";

    public static final String PHOTO_URL_FOR_DOWNLOAD = "photo_for_download";


    public static final String LAT = "lat";
    public static final String LON = "lon";



    private static SharedPreferences getPrefs() {
        return ApplicationLoader.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEdit() {
        return getPrefs().edit();
    }


    public static class PersonInfo{
        public static final String NAME = "name";
        public static final String SURNAME = "surname";
        public static final String PHONE = "PHONE";
        public static final String CITY = "CITY";
        public static final String URL_PHOTO = "url_photo";
        public static final String LOGIN = "login";
        public static final String DATE_BIRTH = "date_birth";


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

        public static void setUrlPhoto(String type) {
            getEdit().putString(URL_PHOTO, type).commit();
        }
        public static String getUrlPhoto() {
            return getPrefs().getString(URL_PHOTO, "");
        }

        public static void setCity(String city) {
            getEdit().putString(CITY, city).commit();
        }
        public static String getCity() {
            return getPrefs().getString(CITY, "");
        }

        public static void setLogin(String phone) {
            getEdit().putString(LOGIN, phone).commit();
        }
        public static String getLogin() {
            return getPrefs().getString(LOGIN, "");
        }

        public static void setDateBirth(String phone) {
            getEdit().putString(DATE_BIRTH, phone).commit();
        }
        public static String getDateBirth() {
            return getPrefs().getString(DATE_BIRTH, "");
        }
    }



    public static void setPhotoUrlForDownload(String photo){getEdit().putString(PHOTO_URL_FOR_DOWNLOAD, photo).commit();}
    public static String getPhotoUrlForDownload(){return getPrefs().getString(PHOTO_URL_FOR_DOWNLOAD, "");}



    public static String getDate() {
        return getPrefs().getString(DATE, "");
    }
    public static void setDate(String date) {
        getEdit().putString(DATE, date).commit();
    }



    public static void setType(String type) {
        getEdit().putString(TYPE, type).commit();
    }
    public static String getType() {
        return getPrefs().getString(TYPE, "");
    }



    public static void setLat(float lat) {
        getEdit().putFloat(LAT, lat).commit();
    }

    public static Float getLat() {
        return getPrefs().getFloat(LAT, 0);
    }

    public static void setLon(float lon) {
        getEdit().putFloat(LON, lon).commit();
    }

    public static Float getLon() {
        return getPrefs().getFloat(LON, 0);
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



    public static class AdsHelper{
        public static final String NAME_ANIMALS = "name_animals"; // name
        public static final String PHOTO_ANIMALS = "photo_ads"; // photo ads for animals
        public static final String POL_ANIMALS ="pol_animals"; // male or famel
        public static final String TYPE_ANIMALS = "type_animals"; //cat or dog or other
        public static final String PORODA_ANIMALS = " poroda_animals";
        public static final String VID_ADD = "vid_ads";//kind hand and find home and other
        public static final String OPISANIE = "opisanie";
        public static final String LOCATION = "location";
        public static final String DATE_LOSE = "date_lose";
        public static final String OKRAS = "okras";

        public static final String AGE_ANIMALS = "age_animals";

        public static final String LAT_ADS = "lat_as";
        public static final String LON_ADS = "lon_as";

        public static void setNameAnimals(String name){
            getEdit().putString(NAME_ANIMALS, name).commit();
        }
        public static String getNameAnimals(){
            return getPrefs().getString(NAME_ANIMALS, "");
        }
        public static void setPhotoAnimals(String url){
            getEdit().putString(PHOTO_ANIMALS, url).commit();
        }
        public static String getPhotoAnimals(){
            return getPrefs().getString(PHOTO_ANIMALS, "");
        }
        public static void setPolAnimals(String polAnimals){
            getEdit().putString(POL_ANIMALS, polAnimals).commit();
        }
        public static String getPolAnimals(){
            return getPrefs().getString(POL_ANIMALS, "");
        }
        public static void setTypeAnimals(String type){
            getEdit().putString(TYPE_ANIMALS, type).commit();
        }
        public static String getTypeAnimals(){
            return getPrefs().getString(TYPE_ANIMALS, "");
        }

        public static void setPorodaAnimals(String name){
            getEdit().putString(PORODA_ANIMALS, name).commit();
        }
        public static String getPorodaAnimals(){
            return getPrefs().getString(PORODA_ANIMALS, "");
        }
        public static void setVidAdd(String url){
            getEdit().putString(VID_ADD, url).commit();
        }
        public static String getVidAdd(){
            return getPrefs().getString(VID_ADD, "");
        }
        public static void setOpisanie(String polAnimals){
            getEdit().putString(OPISANIE, polAnimals).commit();
        }
        public static String getOpisanie(){
            return getPrefs().getString(OPISANIE, "");
        }
        public static void setLocation(String type){
            getEdit().putString(LOCATION, type).commit();
        }
        public static String getLocation(){
            return getPrefs().getString(LOCATION, "");
        }
        public static void setDateLose(String type){
            getEdit().putString(DATE_LOSE, type).commit();
        }
        public static String getDateLose(){
            return getPrefs().getString(DATE_LOSE, "");
        }

        public static void setOkras(String type){
            getEdit().putString(OKRAS, type).commit();
        }
        public static String getOkras(){
            return getPrefs().getString(OKRAS, "");
        }

        public static void setAgeAnimals(String type){
            getEdit().putString(AGE_ANIMALS, type).commit();
        }
        public static String getAgeAnimals(){
            return getPrefs().getString(AGE_ANIMALS, "");
        }

        public static void setLatAds(float lat) {
            getEdit().putFloat(LAT_ADS, lat).commit();
        }

        public static Float getLatAds() {
            return getPrefs().getFloat(LAT_ADS, 0);
        }

        public static void setLonAds(float lon) {
            getEdit().putFloat(LON_ADS, lon).commit();
        }

        public static Float getLonAds() {
            return getPrefs().getFloat(LON_ADS, 0);
        }
    }

}

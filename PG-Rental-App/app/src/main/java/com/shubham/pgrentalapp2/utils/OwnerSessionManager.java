package com.shubham.pgrentalapp2.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class OwnerSessionManager {

    // ================= PREF =================
    private static final String PREF_NAME = "OwnerSession";

    // ================= KEYS =================
    private static final String KEY_IS_LOGGED_IN = "is_owner_logged_in";
    private static final String KEY_OWNER_ID = "owner_id";
    private static final String KEY_NAME = "owner_name";
    private static final String KEY_PG_NAME = "pg_name";
    private static final String KEY_EMAIL = "owner_email";
    private static final String KEY_MOBILE = "owner_mobile";
    private static final String KEY_ADDRESS = "owner_address";
    private static final String KEY_CITY = "owner_city";
    private static final String KEY_PASSWORD = "owner_password";

    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;

    public OwnerSessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // =================================================
    // 🔹 CREATE LOGIN SESSION (AFTER SQLITE LOGIN)
    // =================================================
    public void login(
            int ownerId,
            String name,
            String email,
            String mobile,
            String password
    ) {
        // 🔥 CLEAR OLD DATA FIRST (IMPORTANT)
        editor.clear();

        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putInt(KEY_OWNER_ID, ownerId);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_MOBILE, mobile);
        editor.putString(KEY_PASSWORD, password);

        // Initialize optional fields empty
        editor.putString(KEY_PG_NAME, "");
        editor.putString(KEY_ADDRESS, "");
        editor.putString(KEY_CITY, "");

        editor.apply();
    }

    // =================================================
    // 🔹 SAVE / UPDATE OWNER PROFILE
    // =================================================
    public void saveOwnerProfile(
            String name,
            String pgName,
            String email,
            String mobile,
            String address,
            String city
    ) {
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_PG_NAME, pgName);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_MOBILE, mobile);
        editor.putString(KEY_ADDRESS, address);
        editor.putString(KEY_CITY, city);
        editor.apply();
    }

    // =================================================
    // 🔹 UPDATE PASSWORD ONLY
    // =================================================
    public void updatePassword(String newPassword) {
        editor.putString(KEY_PASSWORD, newPassword);
        editor.apply();
    }

    // ================= LOGIN CHECK =================
    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // ================= GETTERS =================
    public int getOwnerId() {
        return pref.getInt(KEY_OWNER_ID, -1);
    }

    public String getName() {
        return pref.getString(KEY_NAME, "");
    }

    public String getPgName() {
        return pref.getString(KEY_PG_NAME, "");
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL, "");
    }

    public String getMobile() {
        return pref.getString(KEY_MOBILE, "");
    }

    public String getAddress() {
        return pref.getString(KEY_ADDRESS, "");
    }

    public String getCity() {
        return pref.getString(KEY_CITY, "");
    }

    public String getPassword() {
        return pref.getString(KEY_PASSWORD, "");
    }

    // ================= LOGOUT =================
    public void logout() {
        editor.clear();
        editor.apply();
    }
}

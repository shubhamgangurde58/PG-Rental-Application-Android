package com.shubham.pgrentalapp2.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shubham.pgrentalapp2.model.ReviewModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepository {

    private static final String PREF_NAME = "PG_REVIEWS";

    // 🔹 ADD REVIEW
    public static void addReview(Context context, String pgName, ReviewModel review) {

        if (pgName == null || review == null) return;

        List<ReviewModel> reviewList = getReviews(context, pgName);
        reviewList.add(review);

        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        prefs.edit()
                .putString(pgName, new Gson().toJson(reviewList))
                .apply();
    }

    // 🔹 GET REVIEWS
    public static List<ReviewModel> getReviews(Context context, String pgName) {

        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        String json = prefs.getString(pgName, null);

        if (json == null || json.isEmpty()) {
            return new ArrayList<>();
        }

        try {
            Type type = new TypeToken<List<ReviewModel>>() {}.getType();
            return new Gson().fromJson(json, type);
        } catch (Exception e) {
            // Corrupted JSON safety
            return new ArrayList<>();
        }
    }
}

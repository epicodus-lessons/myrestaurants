package com.epicodus.myrestaurants;

import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class YelpService {

    public static void findRestaurants(String location, Callback callback) {

        OkHttpClient client = new OkHttpClient.Builder()
                .build();
    }

}

package com.epicodus.myrestaurants.ui;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epicodus.myrestaurants.R;
import com.epicodus.myrestaurants.adapters.RestaurantListAdapter;
import com.epicodus.myrestaurants.models.Restaurant;
import com.epicodus.myrestaurants.services.YelpService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RestaurantListFragment extends Fragment {
    private RestaurantListAdapter mAdapter;
    public ArrayList<Restaurant> mRestaurants = new ArrayList<>();
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentAddress;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;


    public RestaurantListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mSharedPreferences.edit();
        // Following line instructs fragment to include menu options:
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_list, container, false);
    }

    public void getRestaurants(String location) {
        final YelpService yelpService = new YelpService();

        yelpService.findRestaurants(location, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                mRestaurants = yelpService.processResults(response);

                getActivity().runOnUiThread(new Runnable() {
                    // Line above states 'getActivity()' instead of previous 'RestaurantListActivity.this'
                    // because fragments do not have own context, and must inherit from corresponding activity.

                    @Override
                    public void run() {
                        mAdapter = new RestaurantListAdapter(getActivity(), mRestaurants);
                        // Line above states `getActivity()` instead of previous
                        // 'getApplicationContext()' because fragments do not have own context,
                        // must instead inherit it from corresponding activity.
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        // Line above states 'new LinearLayoutManager(getActivity());' instead of previous
                        // 'new LinearLayoutManager(RestaurantListActivity.this);' when method resided
                        // in RestaurantListActivity because Fragments do not have context
                        // and must instead inherit from corresponding activity.
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });
    }

}

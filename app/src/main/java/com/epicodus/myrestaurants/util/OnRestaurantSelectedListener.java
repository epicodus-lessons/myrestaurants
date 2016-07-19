package com.epicodus.myrestaurants.util;


import com.epicodus.myrestaurants.models.Restaurant;

import java.util.ArrayList;

public interface OnRestaurantSelectedListener {
    public void onRestaurantSelected(Integer position, ArrayList<Restaurant> restaurants, String source);

}

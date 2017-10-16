package com.epicodus.myrestaurants;

import android.os.Build;
import android.widget.ListView;

<<<<<<< HEAD:app/src/test/java/com/epicodus/myrestaurants/RestaurantListActivityTest.java
import com.epicodus.myrestaurants.ui.RestaurantListActivity;
=======
import com.epicodus.myrestaurants.ui.RestaurantsActivity;
>>>>>>> 2.7_images_with_picasso_v2:app/src/test/java/com/epicodus/myrestaurants/RestaurantsActivityTest.java

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)

public class RestaurantListActivityTest {
    private RestaurantListActivity activity;
    private ListView mRestaurantListView;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(RestaurantListActivity.class);
        mRestaurantListView = (ListView) activity.findViewById(R.id.listView);
    }

    @Test
    public void restaurantListViewPopulates() {
        assertNotNull(mRestaurantListView.getAdapter());
        assertEquals(mRestaurantListView.getAdapter().getCount(), 16);
    }

}

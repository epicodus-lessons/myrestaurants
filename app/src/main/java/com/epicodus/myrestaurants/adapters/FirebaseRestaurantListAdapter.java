package com.epicodus.myrestaurants.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.epicodus.myrestaurants.Constants;
import com.epicodus.myrestaurants.R;
import com.epicodus.myrestaurants.models.Restaurant;
import com.epicodus.myrestaurants.ui.RestaurantDetailActivity;
import com.epicodus.myrestaurants.ui.RestaurantDetailFragment;
import com.epicodus.myrestaurants.util.ItemTouchHelperAdapter;
import com.epicodus.myrestaurants.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseRestaurantListAdapter extends FirebaseRecyclerAdapter<Restaurant, FirebaseRestaurantViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;
    private ArrayList<Restaurant> mRestaurants = new ArrayList<>();
    private int mOrientation;


    public FirebaseRestaurantListAdapter(Class<Restaurant> modelClass, int modelLayout,
                                         Class<FirebaseRestaurantViewHolder> viewHolderClass,
                                         Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
    }

    @Override
    protected void populateViewHolder(final FirebaseRestaurantViewHolder viewHolder, Restaurant model, int position) {
        viewHolder.bindRestaurant(model);

        mOrientation = viewHolder.itemView.getResources().getConfiguration().orientation;
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            createDetailFragment(0);
        }

        viewHolder.mRestaurantImageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }

        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int itemPosition = viewHolder.getAdapterPosition();
                if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    createDetailFragment(itemPosition);
                } else {
                    Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                    intent.putExtra(Constants.EXTRA_KEY_RESTAURANTS, Parcels.wrap(mRestaurants));
                    mContext.startActivity(intent);
                }
            }
        });

    }

    private void createDetailFragment(int position) {
        RestaurantDetailFragment detailFragment = RestaurantDetailFragment.newInstance(mRestaurants, position);
        FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.restaurantDetailContainer, detailFragment);
        ft.commit();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }
}
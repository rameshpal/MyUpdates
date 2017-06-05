package com.example.pal.myupdates.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pal.myupdates.BusinessHeadlines;
import com.example.pal.myupdates.GadgetsHeadlines;
import com.example.pal.myupdates.GeographyHeadlines;
import com.example.pal.myupdates.MusicHeadlines;
import com.example.pal.myupdates.NewsHeadlines;
import com.example.pal.myupdates.R;
import com.example.pal.myupdates.SportsHeadlines;
import com.example.pal.myupdates.Utiles.SlideAnimationUtil;

/**
 * Created by PaL on 5/5/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Holder> {
    private static final int FADE_DURATION = 3000;
    Context mContext;
    String [] Categories={"Daily Updates","Business","Technology","National Geography","Entertainment","Sports"};
    int[] Cover={R.drawable.news,R.drawable.globalbusiness,R.drawable.technology,R.drawable.geography,R.drawable.music,R.drawable.sports};

    public CategoryAdapter(Context context) {
        mContext=context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category,parent,false));
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        int pos = position % 2;
        if (pos == 0) {
            holder._iv_cover.setBackgroundResource(Cover[position]);
            holder._tv_catName.setGravity(Gravity.RIGHT);
            holder._tv_catName.setText(Categories[position]);
        } else {
            holder._iv_cover.setBackgroundResource(Cover[position]);
            holder._tv_catName.setText(Categories[position]);
        }

//        setFadeAnimation(holder.itemView);
        SlideAnimationUtil.slideInFromRight(mContext,holder.itemView);
        holder._iv_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(mContext, NewsHeadlines.class);
                        mContext.startActivity(intent);
                        break;

                    case 1:
                        intent = new Intent(mContext, BusinessHeadlines.class);
                        mContext.startActivity(intent);
                        break;

                    case 2:
                        intent = new Intent(mContext, GadgetsHeadlines.class);
                        mContext.startActivity(intent);
                        break;

                    case 3:
                        intent = new Intent(mContext, GeographyHeadlines.class);
                        mContext.startActivity(intent);
                        break;

                    case 4:
                        intent = new Intent(mContext, MusicHeadlines.class);
                        mContext.startActivity(intent);
                        break;

                    case 5:
                        intent = new Intent(mContext, SportsHeadlines.class);
                        mContext.startActivity(intent);
                        break;
                }
            }
        });

//        switch (pos){
//            case 0:
//
//                break;
//
//            case 1:
//
//                break;
//            default:

     }

//        if (position/2==0){
//            holder._iv_cover.setBackgroundResource(Cover[position]);
//            holder._tv_catName.setGravity(Gravity.RIGHT);
//            holder._tv_catName.setText(Categories[position]);
//        }else {
//            holder._iv_cover.setBackgroundResource(Cover[position]);
//            holder._tv_catName.setText(Categories[position]);
//        }


    @Override
    public int getItemCount() {
        return Categories.length;
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView _iv_cover;
        TextView _tv_catName;
        public Holder(View itemView) {
            super(itemView);

            _iv_cover=(ImageView)itemView.findViewById(R.id.iv_category_image);
            _tv_catName=(TextView)itemView.findViewById(R.id.tv_category_name);
        }
    }
}

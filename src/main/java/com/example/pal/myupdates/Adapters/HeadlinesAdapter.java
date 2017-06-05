package com.example.pal.myupdates.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pal.myupdates.Beans.HeadlinesData;
import com.example.pal.myupdates.NewsDetails;
import com.example.pal.myupdates.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by PaL on 5/5/2017.
 */

public class HeadlinesAdapter extends RecyclerView.Adapter<HeadlinesAdapter.Holder> {
    private static final int FADE_DURATION = 3000;
    Context mContext;
    ArrayList<HeadlinesData> headlinesDatas=new ArrayList<>();


    public HeadlinesAdapter(Context context, ArrayList<HeadlinesData> dataArrayList) {
        mContext=context;
        headlinesDatas=dataArrayList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.headlines,parent,false));
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }


    @Override
    public void onBindViewHolder(Holder holder, int position) {
        HeadlinesData data=new HeadlinesData();
        data=headlinesDatas.get(position);
        holder._tv_catName.setText(data.getTitle().toString());
        holder._tv_title.setText(data.getAuthor().toString());
        holder._tv_date.setText(data.getPublish_at());
        holder._tv_description.setText(data.getDescription());
        final String imgUrl,url;
        url=data.getUrl().toString();
        imgUrl=data.getImg_url().toString();
        if (imgUrl !=null || !imgUrl.equals(null)) {
            Picasso.with(mContext).load(imgUrl).into(holder._iv_cover);
        }

        setFadeAnimation(holder.itemView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, NewsDetails.class);
                intent.putExtra("url",url);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return headlinesDatas.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView _iv_cover;
        TextView _tv_catName,_tv_date,_tv_title,_tv_description,_tv_url;
        public Holder(View itemView) {
            super(itemView);

            _iv_cover=(ImageView)itemView.findViewById(R.id.iv_headline_pic);
            _tv_catName=(TextView)itemView.findViewById(R.id.tv_headline_1);
            _tv_date=(TextView)itemView.findViewById(R.id.tv_headline_date);
            _tv_title=(TextView)itemView.findViewById(R.id.tv_headline);
            _tv_description=(TextView)itemView.findViewById(R.id.tv_headline_2);
        }
    }
}

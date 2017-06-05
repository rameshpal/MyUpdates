package com.example.pal.myupdates;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.pal.myupdates.Adapters.HeadlinesAdapter;
import com.example.pal.myupdates.Beans.HeadlinesData;
import com.example.pal.myupdates.Network.APICall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class SportsHeadlines extends AppCompatActivity {
    private RecyclerView _rv_gadgetHeading;
    private HeadlinesAdapter headlinesAdapter;
    private HeadlinesData headlinesData;
    private ProgressDialog dialog;
    private ArrayList<HeadlinesData> dataArrayList = new ArrayList<>();
    private OkHttpClient httpClient = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_headlines);
        setTitle("Sports");
        Initialization();
        httpClient=new OkHttpClient();
        gata gata=new gata();
        gata.execute();
    }

    protected void Initialization() {
        _rv_gadgetHeading = (RecyclerView) findViewById(R.id.rv_act_sportsheadlines_headlines);
        headlinesAdapter = new HeadlinesAdapter(this,dataArrayList);
        _rv_gadgetHeading.setLayoutManager(new LinearLayoutManager(this));
        _rv_gadgetHeading.setAdapter(headlinesAdapter);
    }


    public class gata extends AsyncTask<Void,Void,Void> {
        String response;
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                response = APICall.GET(httpClient,RequestBuilder.buildURL());
                Log.d("Response", response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(SportsHeadlines.this);
            dialog.setMessage("Loading....");
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
            JSONObject jObjectData=new JSONObject();
            JSONArray jArrayNews=new JSONArray();
            super.onPostExecute(aVoid);
            try {
if (response!=null){
                JSONObject jsonObject=new JSONObject(response);
                jArrayNews=jsonObject.getJSONArray("articles");
                for (int i=0;i<jArrayNews.length();i++) {
                    headlinesData = new HeadlinesData();
                    jObjectData = jArrayNews.getJSONObject(i);
                    headlinesData.setAuthor(jObjectData.getString("author"));
                    headlinesData.setTitle(jObjectData.getString("title"));
                    headlinesData.setDescription(jObjectData.getString("description"));
                    headlinesData.setImg_url(jObjectData.getString("urlToImage"));
                    headlinesData.setUrl(jObjectData.getString("url"));
                    headlinesData.setPublish_at(jObjectData.getString("publishedAt"));

                    dataArrayList.add(headlinesData);
                }
                }
                headlinesAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
}

    public static class RequestBuilder {
        public static RequestBody LoginBody() {
            return new FormBody.Builder()
                    .build();
        }

        public static HttpUrl buildURL() {
            return new HttpUrl.Builder()
                    .scheme("https") //http
                    .host("newsapi.org")
                    .addPathSegment("v1")//adds "/pathSegment" at the end of hostname
                    .addPathSegment("articles")
                    .addQueryParameter("source", "espn-cric-info") //add query parameters to the URL
                    .addEncodedQueryParameter("apiKey", "620e5a7ccf344d73b6d3e05b9e3c2f61")//add encoded query parameters to the URL
                    .build();
            /**
             * The return URL:
             * https://newsapi.org/v1/articles?source=techcrunch&apiKey=75f9e75560634b8eaced7c73766d0f8f
             * https://www.somehostname.com/pathSegment?param1=value1&encodedName=encodedValue
             */
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }
}

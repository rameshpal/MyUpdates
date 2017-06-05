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
import com.example.pal.myupdates.Network.RequestBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class GadgetsHeadlines extends AppCompatActivity {
    private String TAG = GadgetsHeadlines.class.getSimpleName();
    private RecyclerView _rv_gadgetHeading;
    private HeadlinesAdapter headlinesAdapter;
    private HeadlinesData headlinesData;
    private ProgressDialog dialog;
    private ArrayList<HeadlinesData> dataArrayList = new ArrayList<>();
    private OkHttpClient httpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gadgets_headlines);
        Initialization();
        setTitle("Gadgets");
//        new getHeadlines().execute();
//        USANewsAsyncTask getNewsUpdate = new USANewsAsyncTask();
//        getData();
        httpClient = new OkHttpClient();
//        loaddata();
        gata gata=new gata();
        gata.execute();
//        getMovieList();
    }

    protected void Initialization() {
        _rv_gadgetHeading = (RecyclerView) findViewById(R.id.rv_act_gadgetsheadlines_headlines);
        headlinesAdapter = new HeadlinesAdapter(this,dataArrayList);
        _rv_gadgetHeading.setLayoutManager(new LinearLayoutManager(this));
        _rv_gadgetHeading.setAdapter(headlinesAdapter);
    }

//
//    protected void  getData(){
//        final ProgressDialog pDialog = new ProgressDialog(this);
//        pDialog.setMessage("Loading...");
//        pDialog.show();
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
//                API.BASE_URL+GADGET_HEADLINES, null,
//                new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d(TAG, response.toString());
//                        pDialog.hide();
//                    }
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                // hide the progress dialog
//                pDialog.hide();
//            }
//        });
//
//// Adding request to request queue
//        NetworkRequests.getInstance().addToRequestQueue(jsonObjReq, TAG);
//    }


    public class gata extends AsyncTask<Void,Void,Void>{
        String response;
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                response = APICall.GET(httpClient, RequestBuilder.buildURL());
                Log.d("Response", response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(GadgetsHeadlines.this);
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
            if (response !=null){
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

//    protected void loaddata() {
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                try {
//                    String response = APICall.GET(httpClient, RequestBuilder.buildURL());
//                    Log.d("Response", response);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        }.execute();
//
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }


}



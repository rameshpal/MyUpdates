package com.example.pal.myupdates.Network;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.RequestBody;

/**
 * Created by PaL on 5/8/2017.
 */

public class RequestBuilder {
    public static RequestBody LoginBody() {
        return new FormBody.Builder()
                .build();
    }

    public static HttpUrl buildURL() {
        return new HttpUrl.Builder()
                .scheme("https") //http
                .host("newsapi.org")
                .addPathSegments("v1/articles")//adds "/pathSegment" at the end of hostname
//                .addPathSegment("articles")
                .addQueryParameter("source", "engadget") //add query parameters to the URL
                .addEncodedQueryParameter("apiKey", "620e5a7ccf344d73b6d3e05b9e3c2f61")//add encoded query parameters to the URL
                .build();
        /**
         * The return URL:
         * https://newsapi.org/v1/articles?source=techcrunch&apiKey=75f9e75560634b8eaced7c73766d0f8f
         * https://www.somehostname.com/pathSegment?param1=value1&encodedName=encodedValue
         */
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
//            progressBar.setVisibility(View.VISIBLE);
//            responseView.setText("");
        }

        protected String doInBackground(Void... urls) {
            // Do some validation here

            try {
                URL url = new URL( "https://newsapi.org/v1/articles" + "source=" + "the-next-web" + "&apiKey=" + "75f9e75560634b8eaced7c73766d0f8f");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
            Log.i("INFO", response);
        }
    }

}

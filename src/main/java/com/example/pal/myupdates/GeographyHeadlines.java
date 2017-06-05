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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static org.apache.http.conn.ssl.SSLSocketFactory.getSocketFactory;

public class GeographyHeadlines extends AppCompatActivity {
    private RecyclerView _rv_gadgetHeading;
    private HeadlinesAdapter headlinesAdapter;
    private HeadlinesData headlinesData;
    private ProgressDialog dialog;
    private ArrayList<HeadlinesData> dataArrayList = new ArrayList<>();
    private OkHttpClient httpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geography_headlines);
        setTitle("Natoinal Geography");
        Initialization();
        httpClient=new OkHttpClient();
        gata gata=new gata();
        gata.execute();
    }

    protected void Initialization() {
        _rv_gadgetHeading = (RecyclerView) findViewById(R.id.rv_act_geographyheadlines_headlines);
        headlinesAdapter = new HeadlinesAdapter(this,dataArrayList);
        _rv_gadgetHeading.setLayoutManager(new LinearLayoutManager(this));
        _rv_gadgetHeading.setAdapter(headlinesAdapter);
//        RetrieveFeedTask retrieveFeedTask=new RetrieveFeedTask();
//        retrieveFeedTask.execute();

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
            dialog=new ProgressDialog(GeographyHeadlines.this);
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
                    .addQueryParameter("source", "national-geographic") //add query parameters to the URL
                    .addEncodedQueryParameter("apiKey", "620e5a7ccf344d73b6d3e05b9e3c2f61")//add encoded query parameters to the URL
                    .build();
            /**
             * The return URL:
             * https://newsapi.org/v1/articles?source=techcrunch&apiKey=75f9e75560634b8eaced7c73766d0f8f
             * https://www.somehostname.com/pathSegment?param1=value1&encodedName=encodedValue
             */
        }

    }
/*

    public class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            dialog=new ProgressDialog(GeographyHeadlines.this);
            dialog.setMessage("Loading....");
            dialog.show();
        }

        protected String doInBackground(Void... urls) {
            // Do some validation here
// Load CAs from an InputStream
// (could be from a resource or ByteArrayInputStream or ...)
            CertificateFactory cf = null;
            SSLContext context = null;
            try {
                cf = CertificateFactory.getInstance("X.509");
                InputStream caInput = new BufferedInputStream(new FileInputStream("load-der.crt"));
                Certificate ca;
                try {
                    ca = cf.generateCertificate(caInput);
                    System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
                } finally {
                    caInput.close();
                }

// Create a KeyStore containing our trusted CAs
                String keyStoreType = KeyStore.getDefaultType();
                KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                keyStore.load(null, null);
                keyStore.setCertificateEntry("ca", ca);

// Create a TrustManager that trusts the CAs in our KeyStore
                String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                tmf.init(keyStore);

// Create an SSLContext that uses our TrustManager
                context = SSLContext.getInstance("TLS");
                context.init(null, tmf.getTrustManagers(), null);

            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
// From https://www.washington.edu/itconnect/security/ca/load-der.crt

            try {
                URL url = new URL( "https://newsapi.org/v1/articles" + "source=" + "the-next-web" + "&apiKey=" + "75f9e75560634b8eaced7c73766d0f8f");
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setSSLSocketFactory(context.getSocketFactory());

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
                dialog.dismiss();
                response = "THERE WAS AN ERROR";
            }
            Log.i("INFO", response);
        }
    }
*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }
}

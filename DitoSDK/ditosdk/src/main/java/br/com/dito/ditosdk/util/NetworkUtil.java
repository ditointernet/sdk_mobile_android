package br.com.dito.ditosdk.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import br.com.dito.ditosdk.constant.Constants;
import br.com.dito.ditosdk.exception.DitoSDKException;
import br.com.dito.ditosdk.interfaces.DitoSDKCallback;

/**
 * Created by gabriel.araujo on 06/04/15.
 */
public class NetworkUtil {


    /**
     * Method that returns the return a connection with any URL, this method
     * returns only the POST type connections
     *
     * @param url  Url from which to obtain the return
     * @param json JSON to be sent in the request body
     * @return String
     * @throws DitoSDKCallback
     */
    public static void requestPostMethod(final String url, final String json, final DitoSDKCallback callback) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Uri uri = Uri.parse(url);
                    HttpHost host = new HttpHost(uri.getHost());
                    HttpPost post = new HttpPost(uri.getPath());

                    post.addHeader(Constants.Headers.CONTENT_TYPE, Constants.Headers.CONTENT_TYPE_VALUE);
                    post.addHeader(Constants.Headers.USER_AGENT, Constants.Headers.USER_AGENT_MOBILE);


                    if (json != null) {
                        post.setEntity(new StringEntity(json, "UTF-8"));
                    }

                    post.setURI(new URI(url));

                    HttpClient client = new DefaultHttpClient();
                    client.getParams().setParameter("http.connection.timeout", Integer.valueOf(3000));
                    client.getParams().setParameter("http.socket.timeout", Integer.valueOf(60000));

                    HttpResponse response = client.execute(host, post);
                    BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));


                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = new BufferedReader(in);
                    String read = br.readLine();

                    while (read != null) {
                        sb.append(read);
                        read = br.readLine();
                    }

                    String line = "";
                    line = sb.toString();
                    in.close();


                    if (!TextUtils.isEmpty(line)) {
                        return line;
                    } else {
                        return "";
                    }
                } catch (Exception ex) {
                    return ex.toString();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (!TextUtils.isEmpty(result)) {
                    if (callback != null) {
                        callback.onSuccess(result);
                    }
                } else {
                    if (callback != null) {
                        callback.onError(new DitoSDKException("Json is empty"));
                    }
                }
            }
        }.execute();
    }


    /**
     * Method that returns whether the user has internet connection
     *
     * @return true | false
     */
    public static boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) Constants.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();

            if (networkInfo != null) {
                return networkInfo.isConnectedOrConnecting();
            }
        }
        return false;
    }

    /**
     * Method that returns the InputStream response of a connection with any
     * URL, this method returns only the GET type connections
     *
     * @param urlRequest Url from which to obtain the return
     * @return InputStream
     * @throws Exception
     */
    public static void requestGetMethod(final String urlRequest, final DitoSDKCallback callback) throws DitoSDKException {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    InputStream inputStream = null;
                    URL url = new URL(urlRequest);
                    URLConnection conn = url.openConnection();

                    HttpURLConnection httpConn = (HttpURLConnection) conn;
                    httpConn.setRequestMethod("GET");
                    httpConn.setRequestProperty(Constants.Headers.CONTENT_TYPE, Constants.Headers.CONTENT_TYPE_VALUE);
                    httpConn.setRequestProperty(Constants.Headers.USER_AGENT, Constants.Headers.USER_AGENT_MOBILE);

                    httpConn.connect();
                    inputStream = httpConn.getInputStream();
                    BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = new BufferedReader(in);
                    String read = br.readLine();
                    while (read != null) {
                        sb.append(read);
                        read = br.readLine();
                    }
                    String line = "";
                    line = sb.toString();
                    in.close();

                    if (!TextUtils.isEmpty(line)) {
                        return line;
                    } else {
                        return "";
                    }
                } catch (Exception ex) {
                    return ex.toString();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (!TextUtils.isEmpty(result)) {
                    if (callback != null) {
                        callback.onSuccess(result);
                    }
                } else {
                    if (callback != null) {
                        callback.onError(new DitoSDKException("Json is empty"));
                    }
                }
            }
        }.execute();
    }

    /**
     * Method that returns the return a connection with any URL, this method
     * returns only the DELETE type connections
     *
     * @param url  Url from which to obtain the return
     * @param json JSON to be sent in the request body
     * @return String
     * @throws Exception
     */
    public static void requestDeleteMethod(final String url, final String json, final DitoSDKCallback callback) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Uri uri = Uri.parse(url);
                    HttpHost host = new HttpHost(uri.getHost());
                    HttpDeleteWithBody delete = new HttpDeleteWithBody(uri.getPath());

                    delete.addHeader(Constants.Headers.CONTENT_TYPE, Constants.Headers.CONTENT_TYPE_VALUE);
                    delete.addHeader(Constants.Headers.USER_AGENT, Constants.Headers.USER_AGENT_MOBILE);

                    if (json != null) {
                        delete.setEntity(new StringEntity(json, "UTF-8"));
                    }

                    delete.setURI(new URI(url));

                    HttpClient client = new DefaultHttpClient();
                    client.getParams().setParameter("http.connection.timeout", Integer.valueOf(3000));
                    client.getParams().setParameter("http.socket.timeout", Integer.valueOf(60000));

                    HttpResponse response = client.execute(host, delete);
                    BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));


                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = new BufferedReader(in);
                    String read = br.readLine();

                    while (read != null) {
                        sb.append(read);
                        read = br.readLine();
                    }

                    String line = "";
                    line = sb.toString();
                    in.close();


                    if (!TextUtils.isEmpty(line)) {
                        return line;
                    } else {
                        return "";
                    }
                } catch (Exception ex) {
                    return ex.toString();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (!TextUtils.isEmpty(result)) {
                    if (callback != null) {
                        callback.onSuccess(result);
                    }
                } else {
                    if (callback != null) {
                        callback.onError(new DitoSDKException("Json is empty"));
                    }
                }
            }
        }.execute();
    }

    /**
     * Method that returns the return a connection with any URL, this method
     * returns only the PUT type connections
     *
     * @param paramUrl Url from which to obtain the return
     * @param json     JSON to be sent in the request body
     * @return String
     * @throws Exception
     */
    public static void requestPutMethod(final String paramUrl, final String json, final DitoSDKCallback callback) throws DitoSDKException {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... param) {
                try {
                    Uri uri = Uri.parse(paramUrl);

                    HttpHost host = new HttpHost(uri.getHost());
                    HttpPut put = new HttpPut(uri.getPath());

                    put.addHeader(Constants.Headers.CONTENT_TYPE, Constants.Headers.CONTENT_TYPE_VALUE);
                    put.addHeader(Constants.Headers.USER_AGENT, Constants.Headers.USER_AGENT_MOBILE);

                    if (json != null) {
                        put.setEntity(new StringEntity(json, "UTF-8"));
                    }

                    put.setURI(new URI(paramUrl));

                    HttpClient client = new DefaultHttpClient();
                    client.getParams().setParameter("http.connection.timeout", Integer.valueOf(3000));
                    client.getParams().setParameter("http.socket.timeout", Integer.valueOf(60000));

                    HttpResponse response = client.execute(host, put);
                    BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));

                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = new BufferedReader(in);
                    String read = br.readLine();

                    while (read != null) {
                        sb.append(read);
                        read = br.readLine();
                    }

                    String line = "";
                    line = sb.toString();
                    in.close();


                    if (!TextUtils.isEmpty(line)) {
                        return line;
                    } else {
                        return "";
                    }
                } catch (Exception ex) {
                    return ex.toString();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (!TextUtils.isEmpty(result)) {
                    if (callback != null) {
                        callback.onSuccess(result);
                    }
                } else {
                    if (callback != null) {
                        callback.onError(new DitoSDKException("Json is empty"));
                    }
                }
            }
        }.execute();
    }
}

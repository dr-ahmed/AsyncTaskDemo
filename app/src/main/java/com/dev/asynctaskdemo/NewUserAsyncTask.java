package com.dev.asynctaskdemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class NewUserAsyncTask extends AsyncTask<String, Integer, String> {

    private static final String TAG = "NewUserAsyncTask";
    private boolean exceptionOccurred = true;
    private NewUserActivity newUserActivity;
    private String ENCODING = "UTF-8";
    private ProgressDialog progressDialog;

    public NewUserAsyncTask(NewUserActivity newUserActivity) {
        this.newUserActivity = newUserActivity;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(newUserActivity, "", "Ajout des informations en cours ...",
                false, false);
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL insertURL = new URL("http://192.168.43.37/scripts/add_user.php");
            HttpURLConnection connection = (HttpURLConnection) insertURL.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            //connection.setDoInput(true);

            connection.connect();

            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, ENCODING));

            String post_data = URLEncoder.encode("login", ENCODING) + "=" + URLEncoder.encode(params[0], ENCODING)
                    + "&" + URLEncoder.encode("password", ENCODING) + "=" + URLEncoder.encode(params[1], ENCODING);
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            StringBuilder result = new StringBuilder();
            String line = "";

            while ((line = bufferedReader.readLine()) != null)
                result.append(line);

            bufferedReader.close();
            inputStream.close();
            connection.disconnect();

            if (result.toString().equals("done"))
                exceptionOccurred = false;
            else {
                Log.e(TAG, result.toString());
                return result.toString();
            }
            return "";
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        progressDialog.dismiss();
        newUserActivity.onLoginResponse(exceptionOccurred);
    }
}

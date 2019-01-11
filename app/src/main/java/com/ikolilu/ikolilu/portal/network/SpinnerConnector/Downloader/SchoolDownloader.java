package com.ikolilu.ikolilu.portal.network.SpinnerConnector.Downloader;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Spinner;
import android.widget.Toast;

import com.ikolilu.ikolilu.portal.network.SpinnerConnector.DataParser.SchoolParser;
import com.ikolilu.ikolilu.portal.network.SpinnerConnector.SchoolConnector;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by Genuis on 29/04/2018.
 */

public class SchoolDownloader extends AsyncTask<Void, Void, String> {
    Context context;
    String urlAddress;
    Spinner sp;
    ProgressDialog pd;

    public SchoolDownloader(Context context, String urlAddress, Spinner sp) {
        this.context = context;
        this.urlAddress = urlAddress;
        this.sp = sp;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setTitle("Downloading");
        pd.setMessage("Fetching ... Please wait");
        pd.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        return this.downloadData();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        pd.dismiss();
        if (s == null){
            Toast.makeText(context, "Unable to retrieve school list",Toast.LENGTH_LONG).show();
        }else{

            SchoolParser parser = new SchoolParser(context, sp, s);
            parser.execute();
        }
    }

    private String downloadData(){
        HttpURLConnection con = SchoolConnector.connect(urlAddress);
        if (con == null){
            return null;
        }
        InputStream is = null;
        try {
            is = new BufferedInputStream(con.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line = null;
            StringBuffer response = new StringBuffer();

            if (br != null){
                while((line = br.readLine()) != null){
                    response.append(line + "\n");
                }
                br.close();
            }else{
                return null;
            }
            return response.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}


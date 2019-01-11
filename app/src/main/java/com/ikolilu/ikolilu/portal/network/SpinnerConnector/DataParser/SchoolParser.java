package com.ikolilu.ikolilu.portal.network.SpinnerConnector.DataParser;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.ikolilu.ikolilu.portal.network.SpinnerConnector.SpinnerObject.SchoolModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Genuis on 29/04/2018.
 */

public class SchoolParser extends AsyncTask<Void,Void,Integer> {

    Context c;
    Spinner s;
    String jsonData;
    ProgressDialog pd;
    ArrayList<String> school = new ArrayList<>();
    Map<String, String> map = new HashMap<>();
    //SharedPreferences pref;
    //SharedPreferences.Editor editor = pref.edit();

    public SchoolParser(Context c, Spinner s, String jsonData) {
        this.c = c;
        this.s = s;
        this.jsonData = jsonData;
        //pref = c.getSharedPreferences("SchoolListPrefs", MODE_PRIVATE);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(c);
        pd.setTitle("Parse");
        pd.setMessage("Parsing ... Please wait");
        pd.show();
    }

    @Override
    protected Integer doInBackground(Void... params) {
        return this.parseData();
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        pd.dismiss();

        if (result == 0){
            Toast.makeText(c, "Unable to parse data", Toast.LENGTH_LONG).show();
        }else{
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(c, android.R.layout.simple_list_item_1, school);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(adapter);
        }
    }

    private int parseData(){
        try {
            JSONArray ja = new JSONArray(jsonData);
            JSONObject jo = null;

            school.clear();
            SchoolModel schoolModel = null;

            for (int i = 0; i < ja.length(); i++){
                jo = ja.getJSONObject(i);
                String id = jo.getString("szschoolid");
                String name = jo.getString("szschoolname");

                schoolModel = new SchoolModel();
                schoolModel.setSchoolStrId(id);
                schoolModel.setSchoolName(name);

                school.add(name);
                // Save the hash keys on SharedPreference
                //editor.putString(name, id);
            }
            //editor.commit();
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

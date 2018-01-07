package test.labs.aditya.labs.apptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Collection extends AppCompatActivity {

    ArrayList<String> ListData;
    ArrayAdapter<String> adapter;
    ListView listView;
    TextView cuisine;
    String cusine,key = "dc20fd0995190856df74daf1aa8dfb7b",place;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        listView=findViewById(R.id.listview);

        cuisine=findViewById(R.id.cusine);
        ListData=new ArrayList<String>();
        cusine=getIntent().getStringExtra("id");
        place=getIntent().getStringExtra("name");
        Log.e("id",cusine);
    }

    void showCusines()
    {
        AndroidNetworking.initialize(Collection.this);
        AndroidNetworking.get("https://developers.zomato.com/api/v2.1/cuisines?city_id="+cusine).setTag("Token").setPriority(Priority.MEDIUM).addHeaders("user-key", key).addHeaders("Accept", "application/json").build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    //Log.e("city", "" + city);
                    //Log.e("Response", response.toString());
                    JSONArray array = response.getJSONArray("cuisines");
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);
                        JSONObject object1=object.getJSONObject("cuisine");

                        ListData.add(object1.getString("cuisine_name"));


                        //Log.e("id", id_);

                        adapter = new ArrayAdapter<String>(Collection.this, android.R.layout.simple_list_item_1, ListData);
                        listView.setAdapter(adapter);
                        cuisine.setText("cuisines available in"+place);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(ANError anError) {
                Log.e("error", anError.toString());

            }

        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        showCusines();
    }
}
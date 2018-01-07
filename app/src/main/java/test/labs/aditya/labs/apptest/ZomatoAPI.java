package test.labs.aditya.labs.apptest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ZomatoAPI extends AppCompatActivity {

    public ArrayList<String> id;
    ListView list;
    String city, key = "dc20fd0995190856df74daf1aa8dfb7b";
    private ArrayList<String> itemArrey;
    private ArrayAdapter<String> itemAdapter;

    FrameLayout errorframe,listframe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zomato_api);
        list = findViewById(R.id.listview);
        itemArrey = new ArrayList<String>();
        errorframe=findViewById(R.id.errorframe);
        listframe=findViewById(R.id.listframe);
        id = new ArrayList<String>();
        itemArrey.clear();
        id.clear();
        final Intent intent = new Intent(ZomatoAPI.this, Collection.class);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long idd) {


                for (int i=0;i<itemArrey.size();i++)
                {
                    if (position == i) {

                        intent.putExtra("id", id.get(i));
                        intent.putExtra("name",itemArrey.get(i));
                        startActivity(intent);
                        break;
                    }
                }


            }
        });
    }


    void getItem() {
        AndroidNetworking.initialize(this);
        AndroidNetworking.get("https://developers.zomato.com/api/v2.1/cities?q=" + city).addHeaders("user-key", key).addHeaders("Accept", "application/json").setPriority(Priority.MEDIUM).setTag("Token").build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    //Log.e("city", "" + city);
                    //Log.e("Response", response.toString());
                    JSONArray array = response.getJSONArray("location_suggestions");
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        itemArrey.add(object.getString("name"));

                        String id_ = object.getString("id");
                        id.add(id_);
                        //Log.e("id", id_);

                        itemAdapter = new ArrayAdapter<String>(ZomatoAPI.this, android.R.layout.simple_list_item_1, itemArrey);
                        list.setAdapter(itemAdapter);
                        itemAdapter.notifyDataSetChanged();
                        if (itemArrey.isEmpty())
                        {
                            listframe.setVisibility(View.GONE);
                            errorframe.setVisibility(View.VISIBLE);
                            break;
                        }
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

        final EditText list_name = new EditText(ZomatoAPI.this);
        list_name.setTextColor(Color.BLACK);
        list_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                list_name.post(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(list_name, InputMethodManager.SHOW_IMPLICIT);
                    }
                });
            }
        });
        list_name.requestFocus();
        list_name.setHint("Enter city.....");

        new AlertDialog.Builder(ZomatoAPI.this).setTitle("Enter City").setView(list_name).setCancelable(false)
                .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        city = list_name.getText().toString();

                        getItem();
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();

    }
}



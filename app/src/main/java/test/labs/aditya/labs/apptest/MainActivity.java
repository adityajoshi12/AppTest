package test.labs.aditya.labs.apptest;

import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout coordinatorLayout;
    EditText list_name;
    Button showImage, zomato;
    Snackbar snackbar;
    public static String city=new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showImage = findViewById(R.id.showImage);
        zomato = findViewById(R.id.zomato);
        coordinatorLayout = findViewById(R.id.layout);
        showImage.setOnClickListener(this);
        zomato.setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isInternetOn()) {

            showSnackbar();
        }

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.showImage) {

            if (isInternetOn()) {

                Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                startActivity(intent);

            } else {
                showSnackbar();
            }

        }
        if (v.getId() == R.id.zomato) {
            if (isInternetOn()) {
                startActivity(new Intent(MainActivity.this,ZomatoAPI.class));



            } else {
                showSnackbar();
            }
        }


    }


    void showSnackbar() {
        Snackbar snack = Snackbar.make(coordinatorLayout, "No Internet Connection", Snackbar.LENGTH_LONG).setAction("Settings", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));

            }
        }).setActionTextColor(Color.YELLOW);

        View view = snack.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snack.show();

    }

    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            // if connected with internet

            //Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

            //Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }


}

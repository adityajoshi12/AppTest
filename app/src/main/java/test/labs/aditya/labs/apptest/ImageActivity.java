package test.labs.aditya.labs.apptest;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ImageActivity extends AppCompatActivity {

  ImageView imageView;
    String url="https://thumbs.dreamstime.com/b/hello-world-11059717.jpg";
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        imageView=findViewById(R.id.imageView);
        dialog=new ProgressDialog(this);
        dialog.setTitle("Please Wait......");
        dialog.setMessage("Image is loading");
        dialog.setCancelable(false);
        dialog.create();
        dialog.show();


        Toast.makeText(ImageActivity.this,"hello",Toast.LENGTH_LONG).show();
        Glide.with(this).load(url).placeholder(R.drawable.index).error(R.drawable.placeholder).fitCenter().into(imageView);

        Snackbar snack=Snackbar.make(findViewById(R.id.view),"Image Loaded Successfully",Snackbar.LENGTH_LONG);
        View view = snack.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snack.show();
        dialog.dismiss();





    }

    @Override
    protected void onStart() {

        super.onStart();


    }
}

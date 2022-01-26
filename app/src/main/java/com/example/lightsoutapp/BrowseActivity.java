package com.example.lightsoutapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

@RequiresApi(api = Build.VERSION_CODES.P)
public class BrowseActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

    }
    public void onButtonClick(View view) {
        Uri webpage = Uri.parse("http://www.zybooks.com/");
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(intent);
    }

    public void onCallClick(View view){
        Uri phoneNumber = Uri.parse("tel:111-222-3333");
        Intent intent = new Intent(Intent.ACTION_DIAL, phoneNumber);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void onClickMaps(View view){
        Uri location = Uri.parse("geo:0,0?q=1600+Pennsylvania+Ave+NW,+Washington,+DC");
        Intent intent = new Intent(Intent.ACTION_VIEW, location);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    public void onShare(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Helpful website");
        intent.putExtra(Intent.EXTRA_TEXT, "https://stackoverflow.com/");

        if (intent.resolveActivity(getPackageManager()) != null) {
            Intent chooser = Intent.createChooser(intent, "Share URL");
            startActivity(chooser);
        }
    }

    public void onFetch(View view){
        mGetImageContent.launch("image/*");
    }

    private final ActivityResultLauncher<String> mGetImageContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                try {
                    ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), uri);
                    Bitmap bitmap = ImageDecoder.decodeBitmap(source);
                    ImageView imageView = findViewById(R.id.image);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(this, "Cannot display image", Toast.LENGTH_SHORT).show();
                }
            });

}
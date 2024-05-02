package com.crealite.crealiteapp.vista;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.crealite.crealiteapp.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;



public class HomePageActivity extends AppCompatActivity {

    ImageView fotoPerfil;
    FloatingActionButton btnFotoPerfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_page_activity);

        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.orange)));

        fotoPerfil = findViewById(R.id.imagenDePerfil);
        btnFotoPerfil = findViewById(R.id.btnCambiarFotoPerfil);

        btnFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(HomePageActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();
        if(uri!= null){
            fotoPerfil.setImageURI(uri);

        }

    }
}

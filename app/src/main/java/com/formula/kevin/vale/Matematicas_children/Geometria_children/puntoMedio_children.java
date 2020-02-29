package com.formula.kevin.vale.Matematicas_children.Geometria_children;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.formula.kevin.vale.R;

import uk.co.senab.photoview.PhotoViewAttacher;

public class puntoMedio_children extends AppCompatActivity {
    ImageView imagen,imagen2;
    PhotoViewAttacher photo,photo2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punto_medio_children);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradiente));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //regresar...
                finish();
            }
        });
        imagen =(ImageView)findViewById(R.id.medio2);
        photo = new PhotoViewAttacher(imagen);

        imagen2 =(ImageView)findViewById(R.id.medio3);
        photo2 = new PhotoViewAttacher(imagen2);
    }
}

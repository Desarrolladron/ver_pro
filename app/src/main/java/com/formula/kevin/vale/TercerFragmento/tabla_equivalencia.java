package com.formula.kevin.vale.TercerFragmento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.formula.kevin.vale.R;

import uk.co.senab.photoview.PhotoViewAttacher;

public class tabla_equivalencia extends AppCompatActivity {

    ImageView imageView;
    PhotoViewAttacher photoViewAttacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //codigo para que abarque toda la pantalla
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_tabla_equivalencia);




         imageView =(ImageView) findViewById(R.id.eequiv);
        photoViewAttacher = new PhotoViewAttacher(imageView);
    }
}

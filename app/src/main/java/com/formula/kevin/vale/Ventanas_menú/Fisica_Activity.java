package com.formula.kevin.vale.Ventanas_menú;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.formula.kevin.vale.Activitis.MainActivity;
import com.formula.kevin.vale.ListView_primerFragment.CustomAdapter2;
import com.formula.kevin.vale.ListView_primerFragment.lista;
import com.formula.kevin.vale.Materias_Fisica.Dinamica;
import com.formula.kevin.vale.Materias_Fisica.Electricidad_Mag;
import com.formula.kevin.vale.Materias_Fisica.Mecanica_fluidos;
import com.formula.kevin.vale.Materias_Fisica.Mecanicaa;
import com.formula.kevin.vale.Materias_Fisica.Optica;
import com.formula.kevin.vale.Materias_Fisica.Termodinamica_fisi;
import com.formula.kevin.vale.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

public class Fisica_Activity extends AppCompatActivity {
    private ListView list;
    private CustomAdapter2 adaptador;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fisica_);



        //METODO DEL TOOLBAR PARA QUE REGRESE AL MENU PRINCIPAL
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8882667917768463/3391564183");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });
        list = (ListView) findViewById(R.id.item2);

        final ArrayList<lista> listItems = new ArrayList<>();

        listItems.add(new lista((R.drawable.gravity),  getString(R.string.dinamica) ));
        listItems.add(new lista((R.drawable.innovacion), getString(R.string.mecanica)));
        listItems.add(new lista((R.drawable.eyedropper),  getString(R.string.mecanica_fluidos)));

        listItems.add(new lista((R.drawable.termometro),getString(R.string.termodinamica)));
        listItems.add(new lista((R.drawable.magnet),  getString(R.string.Electricidad)));
        listItems.add(new lista((R.drawable.dispersion),  getString(R.string.optica)));

        adaptador = new CustomAdapter2(this, listItems);
        list.setAdapter(adaptador);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity main = new MainActivity();

                if(i == 4){
                    Intent intent = new Intent(Fisica_Activity.this, Electricidad_Mag.class);
                    startActivity(intent);
                }
               else if(i == 2){
                    Intent intent = new Intent(Fisica_Activity.this, Mecanica_fluidos.class);

                    if  (main.bandera == false) {
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        }
                    }
                    else if  (main.bandera == true){


                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }                    startActivity(intent);

                }
                else if(i == 1){
                    Intent intent = new Intent(Fisica_Activity.this, Mecanicaa.class);
                    if  (main.bandera == false) {
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        }
                    }
                    else if  (main.bandera == true){


                    }else   {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");

                    }

                    startActivity(intent);

                }
                else if(i == 0){
                    Intent intent = new Intent(Fisica_Activity.this, Dinamica.class);
                    startActivity(intent);
                }
                else if(i == 5){
                    Intent intent = new Intent(Fisica_Activity.this, Optica.class);
                    startActivity(intent);
                }
                else if(i == 3){
                    Intent intent = new Intent(Fisica_Activity.this, Termodinamica_fisi.class);
                    if  (main.bandera == false) {
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        }
                    }
                    else if  (main.bandera == true){


                    }else   {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");

                    }

                  startActivity(intent);

                }
            }
        });


    }
}

package com.formula.kevin.vale.AreayVolumen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.formula.kevin.vale.GridView_segundoFragment.Spacecraft;
import com.formula.kevin.vale.R;

import java.util.ArrayList;

public class Adapter2 extends BaseAdapter {

    private Context context;
    private ArrayList<Spacecraft> listItem;

    public Adapter2(Context context, ArrayList<Spacecraft> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int i) {
        return listItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Spacecraft Item =(Spacecraft)getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.modelo_gridview2,null);
        ImageView imagen = (ImageView)view.findViewById(R.id.imagen);
        TextView titulo = (TextView)view.findViewById(R.id.titulo);

        imagen.setImageResource(Item.getImgFoto());
        titulo.setText(Item.getTitulo());
        return view;


    }
}

package com.example.maanas.pruebasvolley;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorClima extends BaseAdapter {
    Context contexto;
    ArrayList<ClimaDia> lista_climas=new ArrayList<>();

    public AdaptadorClima(Context contexto, ArrayList<ClimaDia> lista_climas) {
        this.contexto = contexto;
        this.lista_climas = lista_climas;
    }

    public void setLista_climas(ArrayList<ClimaDia> lista_climas) {
        this.lista_climas = lista_climas;
    }

    public ArrayList<ClimaDia> getLista_climas() {
        return lista_climas;
    }

    @Override
    public int getCount() {
        return lista_climas.size();
    }

    @Override
    public Object getItem(int position) {
        return lista_climas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Inflar vista
        //Meter los valores en las vistas
        LayoutInflater inf=LayoutInflater.from(contexto);
        View vista=inf.inflate(R.layout.vistita_clima, null);
        TextView tv_fecha=vista.findViewById(R.id.tv_fecha);
        TextView tv_minima=vista.findViewById(R.id.tv_minima);
        TextView tv_maxima=vista.findViewById(R.id.tv_maxima);
        ClimaDia clima=lista_climas.get(position);
        int t_minima=clima.getT_minima();
        int t_maxima=clima.getT_maxima();
        int color_minima=0;
        int color_maxima=0;
        if(t_minima<8)
        {
            color_minima= Color.BLUE;
        }
        else
        {
            color_minima=Color.RED;
        }
        if(t_maxima<8)
        {
            color_maxima= Color.BLUE;
        }
        else
        {
            color_maxima=Color.RED;
        }
        tv_maxima.setTextColor(color_maxima);
        tv_minima.setTextColor(color_minima);
        tv_fecha.setText(clima.getFecha());
        tv_minima.setText(String.valueOf(clima.getT_minima()));
        tv_maxima.setText(String.valueOf(clima.getT_maxima()));
        return vista;
    }
}

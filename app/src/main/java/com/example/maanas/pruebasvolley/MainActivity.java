package com.example.maanas.pruebasvolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {
HashMap mapa_ciudades=new HashMap();
    ListView lv_clima;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapa_ciudades.put("Seleccione una ciudad", "0");
        mapa_ciudades.put("Madrid", "28079");
        mapa_ciudades.put("Barcelona", "08019");
        mapa_ciudades.put("Sevilla", "41091");
        Set conjunto_llaves=mapa_ciudades.keySet();
        Iterator it=conjunto_llaves.iterator();
        ArrayList<String> lista_ciudades=new ArrayList<>();
        lv_clima=findViewById(R.id.lv_clima);
        while(it.hasNext())
        {
            String key=(String)it.next();
            lista_ciudades.add(key);
        }
        final Spinner spn_localidad=findViewById(R.id.spn_localidad);
        ArrayAdapter adaptador=new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, lista_ciudades);
        spn_localidad.setAdapter(adaptador);
        spn_localidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if (spn_localidad.getSelectedItemPosition()!=0) {
                   String ciudad_seleccionada = (String) spn_localidad.getSelectedItem();
                   String codigo = (String) mapa_ciudades.get(ciudad_seleccionada);

                   AdaptadorClima ad=(AdaptadorClima)lv_clima.getAdapter();
                   if (ad!=null) {
                       ArrayList lista = ad.getLista_climas();
                       lista.clear();
                       ad.setLista_climas(lista);
                       ad.notifyDataSetChanged();
                   }
                           hacerPeticion(codigo);
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





    }

    private void hacerPeticion(String codigo) {
        RequestQueue cola= Volley.newRequestQueue(this);
        String url="http://www.aemet.es/xml/municipios/localidad_"+codigo+".xml";
        Response.Listener oyente_ok=new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ArrayList<ClimaDia> lista_dias=ParseoXML.tratarXML((String)response);
                poblarListView(lista_dias);

            }
        };
        Response.ErrorListener oyente_error=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };

        StringRequest stringRequest = new StringRequest(url, oyente_ok,oyente_error );
        cola.add(stringRequest);

    }

    private void poblarListView(ArrayList<ClimaDia> lista_dias) {

        AdaptadorClima adaptador=new AdaptadorClima(this, lista_dias);
        lv_clima.setAdapter(adaptador);
    }


}

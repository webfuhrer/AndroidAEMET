package com.example.maanas.pruebasvolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.widget.ListView;

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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestQueue cola= Volley.newRequestQueue(this);

        String url="http://www.aemet.es/xml/municipios/localidad_28079.xml";
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
        ListView lv_clima=findViewById(R.id.lv_clima);
        AdaptadorClima adaptador=new AdaptadorClima(this, lista_dias);
        lv_clima.setAdapter(adaptador);
    }


}

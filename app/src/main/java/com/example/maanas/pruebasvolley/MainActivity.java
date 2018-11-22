package com.example.maanas.pruebasvolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;

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
                tratarXML((String)response);
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

    private void tratarXML(String response) {
        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();

        DocumentBuilder db= null;
        try {
            db = dbf.newDocumentBuilder();
            Document doc=db.parse(new InputSource(new StringReader(response)));
            NodeList lista_predicciones=doc.getElementsByTagName("prediccion");
            for (int i=0; i<lista_predicciones.getLength(); i++)
            {
                Node nodo_prediccion=lista_predicciones.item(i);
                NodeList hijos_prediccion=nodo_prediccion.getChildNodes();
                for (int j=0; j<hijos_prediccion.getLength(); j++)
                {

                    Node nodo_hijo_prediccion=hijos_prediccion.item(j);
                    if (nodo_hijo_prediccion.getNodeName().equals("dia"))
                    {
                        Node nodo_attr=nodo_hijo_prediccion.getAttributes().getNamedItem("fecha");
                        String fecha=nodo_attr.getTextContent();
                        Log.d("etiqueta", "FECHA: "+fecha);
                        NodeList hijos_dia=nodo_hijo_prediccion.getChildNodes();
                        for (int z=0; z<hijos_dia.getLength(); z++)
                        {
                            Node nodo=hijos_dia.item(z);
                            if (nodo.getNodeName().equals("temperatura"))
                            {
                                NodeList hijos_temp=nodo.getChildNodes();
                                for (int y=0; y<hijos_temp.getLength(); y++)
                                {
                                    Node nodo_hijo_temp=hijos_temp.item(y);
                                    if (nodo_hijo_temp.getNodeName().equals("maxima"))
                                    {
                                        String valor=nodo_hijo_temp.getTextContent();
                                        Log.d("etiqueta: ", "TEMPERATURA:"+valor);
                                    }

                                }
                            }

                        }

                    }


                    Log.d("etiqueta", nodo_hijo_prediccion.getNodeName()+" -"+nodo_hijo_prediccion.getNodeType());
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

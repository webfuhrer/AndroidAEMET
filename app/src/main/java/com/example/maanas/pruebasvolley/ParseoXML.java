package com.example.maanas.pruebasvolley;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ParseoXML {

    public static ArrayList<ClimaDia> tratarXML(String response) {
        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
        ArrayList<ClimaDia> lista_dias=new ArrayList<>();
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
                        ClimaDia cd=new ClimaDia();

                        Node nodo_attr=nodo_hijo_prediccion.getAttributes().getNamedItem("fecha");
                        String fecha=nodo_attr.getTextContent();
                        Log.d("etiqueta", "FECHA: "+fecha);
                        cd.setFecha(fecha);
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
                                        String maxima=nodo_hijo_temp.getTextContent();
                                        int t_maxima=Integer.parseInt(maxima);
                                        cd.setT_maxima(t_maxima);

                                    }
                                    if (nodo_hijo_temp.getNodeName().equals("minima"))
                                    {
                                        String minima=nodo_hijo_temp.getTextContent();
                                        int t_minima=Integer.parseInt(minima);
                                        cd.setT_minima(t_minima);


                                    }

                                }
                            }

                        }
                        lista_dias.add(cd);
                    }



                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
return lista_dias;
    }
}

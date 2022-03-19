/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reto11maven;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * lo que estoy haciendo aca es qeu el archivo btc nos devuelva por lineas esto
 * significaraque para crear un un archivo btc solo contiene un string de cada
 * cosa date,allto...tc por medio de un index, para luego en el control poderlos
 * meter 1 a 1 en la tabla a que por arraylistno encontre solucion
 */
public class archivoBTC {

    Path rutaBTC = Paths.get("BTC-USD.csv"); // ruta del archivo dentro del proyecto
    String fechas;
    String abierto;
    String alto;
    String bajo;
    String cierre;
    String adjcierre;
    String volumen;

    public archivoBTC(int i) throws IOException {
        //leemos la linea unica que queremos y la agregamos como el unico valor que se leda al objeto
        //de esta manera se ahce automaticamente con elarchivo
        this.fechas = fecha(Linea(i));
        this.abierto = abierto(Linea(i));
        this.alto = alto(Linea(i));
        this.bajo = bajo(Linea(i));
        this.cierre = cierre(Linea(i));
        this.adjcierre = adjcierre(Linea(i));
        this.volumen = volume(Linea(i));
    }

    // retornamos una linea dependiendo del indice qeu queremos
    public static String Linea(int j) throws IOException {
        Path ruta = Paths.get("BTC-USD.csv");

        ArrayList<String> linea = new ArrayList<>();
        List<String> documento = Files.readAllLines(ruta);
        for (int i = 0; i < documento.size(); i++) {
            linea.add(documento.get(i));
        }
        return linea.get(j);
    }

    public static String fecha(String linea) {
        String fecha = "";
        // aca cogemos la linea y la ponemos comouna fecha
        fecha = linea.split(",")[0];
        return fecha;
    }

    public static String abierto(String linea) {
        String abierto = "";
        // aca cogemos la linea y la ponemos como su unica abierto
        abierto = linea.split(",")[1];
        return abierto;
    }

    public static String alto(String linea) {
        String alto = "";
        // aca cogemos la linea ponemos como unico alto el de su linea
        alto = linea.split(",")[2];
        return alto;
    }

    public static String bajo(String linea) {
        String bajo = "";
        // aca cogemos la linea ponemos como unico bajo el de su linea
        bajo = linea.split(",")[3];
        return bajo;
    }
    public static String cierre(String linea) {
        String cierre = "";
        // aaca cogemos la linea ponemos como unico cierre el de su linea
        cierre = linea.split(",")[4];
        return cierre;
    }
    
    public static String adjcierre(String linea) {
        String adjcierre = "";
        // aca cogemos la linea ponemos como unico adjcierre el de su linea
        adjcierre = linea.split(",")[4];
        return adjcierre;
    }

    public static String volume(String linea) {
        String volume = "";
        // aca cogemos la linea ponemos como unico volumen el de su linea
        volume = linea.split(",")[5];
        return volume;
    }

//-------------------------------------------------------------------------------------------------------------------
    //por este lado dejo todos los metodos que retornanenlos botones ------------------------------------------
    // me parece que para los botones seria buenaidea usar un cuadro de alerta qeu imprima cierto string que devuelve cada metodo
    // https://www.youtube.com/watch?v=KzxE3ZcSIvQ aca enese tutrial hay uno para alerta
        public static double MediaBTC(String ruta1) throws IOException {
        Path ruta = Paths.get(ruta1);
        String[] linea;
        double media = 0;
        List<String> documento = Files.readAllLines(ruta);

        for (int i = 1; i < documento.size(); i++) {
            linea = documento.get(i).split(",");
            double valor = Double.parseDouble(linea[1]);
            media += valor;
        }
        return (media / documento.size());

    }

    public static double DesviacionEstandar(String Ruta) throws IOException {
        Path ruta = Paths.get(Ruta);
        String[] linea;
        double desviacion;
        double varianza = 0;
        List<String> documento = Files.readAllLines(ruta);
        for (int i = 1; i < documento.size(); i++) {
            linea = documento.get(i).split(",");
            double rango;
            rango = Math.pow(Double.parseDouble(linea[1]) - MediaBTC(Ruta), 2f);
            varianza = varianza + rango;
        }
        varianza = varianza / documento.size();
        desviacion = Math.sqrt(varianza);
        return desviacion;
    }

    public static void PrecioMasAlto(String ruta1) throws IOException {

        Path ruta = Paths.get(ruta1);
        String[] linea;

        ArrayList<Double> Mayores = new ArrayList();

        List<String> documento = Files.readAllLines(ruta);
        for (int i = 1; i < documento.size(); i++) {
            linea = documento.get(i).split(",");
            double high = Double.parseDouble(linea[2]);

            Mayores.add(high);
        }
        // aprovecahmos el collecions que nos saca el valor maximo de una lista
        System.out.println("el mayor valor del btc fue: " + Collections.max(Mayores));

    }

    public static void PrecioMasBajo(String ruta1) throws IOException {
        Path ruta = Paths.get(ruta1);
        String[] linea;
        ArrayList<Double> Minimos = new ArrayList();

        List<String> documento = Files.readAllLines(ruta);
        for (int i = 1; i < documento.size(); i++) {
            linea = documento.get(i).split(",");
            double minimoDeLaLinea = Double.parseDouble(linea[2]);

            Minimos.add(minimoDeLaLinea);
        }
        // aprovecahmos el collecions que nos saca el valor minimo de una lista
        System.out.println("el menor precio del BTC fue: " + Collections.min(Minimos));
    }
    
    
    
    
    
    
    
    
    
    
    
    public String getFechas() {
        return fechas;
        
    }

    public Path getRutaBTC() {
        return rutaBTC;
    }

    public String getAbierto() {
        return abierto;
    }

    public String getAlto() {
        return alto;
    }

    public String getBajo() {
        return bajo;
    }

    public String getCierre() {
        return cierre;
    }

    public String getAdjcierre() {
        return adjcierre;
    }

    public String getVolumen() {
        return volumen;
    }
    
    

}

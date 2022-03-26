package com.mycompany.reto11maven;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

public class SecondaryController implements Initializable {

    @FXML
    private Button secondaryButton;
    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    public ObservableList Listar() throws IOException {//el throws es porque lo pide al ser parte de archivo btc qeu hacemos lectura
        Path ruta = Paths.get("BTC-USD.csv");
        List<String> documento = Files.readAllLines(ruta);
        ObservableList<archivoBTC> list = FXCollections.observableArrayList(
                new archivoBTC(0)); // agregamos el primer archivo pa qeu pueda detectar la lisa
        for (int i = 1; i < documento.size(); i++) { // en un bucle agregamos el resto elementos creando n archivos btc, donde cada uno se le asigna su respectiva linea
            archivoBTC archivo = new archivoBTC(i);
            list.add(archivo); // lo agregamos a la ObserveList
        }
        return list;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lineChart.setTitle("Precio Bitcoin 2022");

        XYChart.Series series = new XYChart.Series();

        series.setName("Fecha");

        Path ruta = Paths.get("BTC-USD.csv");
        List<String> documento = null;
        try {
            documento = Files.readAllLines(ruta);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        for (int i = 0; i < documento.size(); i = i + 10) { // lo hacemos saltando de 10 en 10 para que no quede muy apeñuscado
            archivoBTC archivo = null;
            try {
                archivo = new archivoBTC(i);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            series.getData().add(new XYChart.Data(archivo.getFechas(), Double.parseDouble(archivo.getAlto())));  //agregamos cada dato del archivo (fecha, precio) a una nueva lista del tipo serie
        }

        //agregamos al lineChart
        lineChart.getData().add(series); //lo agregamos al linechart

    }

}

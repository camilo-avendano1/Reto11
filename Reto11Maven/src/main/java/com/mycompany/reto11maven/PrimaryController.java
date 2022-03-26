package com.mycompany.reto11maven;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.list;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PrimaryController implements Initializable {

    @FXML
    private TableView<archivoBTC> tableView;

    @FXML
    private TableColumn<archivoBTC, String> date;
    @FXML
    private TableColumn<archivoBTC, String> open;
    @FXML
    TableColumn<archivoBTC, String> high;
    @FXML
    TableColumn<archivoBTC, String> low;
    @FXML
    TableColumn<archivoBTC, String> close;
    @FXML
    TableColumn<archivoBTC, String> adjClose;
    @FXML
    TableColumn<archivoBTC, String> volume;
    @FXML
    private Button botonPromedio;
    @FXML
    private Button botonMayor;
    @FXML
    private Button botonMenor;
    @FXML
    private Button botonDesviacion;
    @FXML
    private Button primaryButton;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    //creamos la listaobservable que eslo que aceptael table view
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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        high.setCellValueFactory(new PropertyValueFactory<archivoBTC, String>("alto")); //asignamos cadacolumna al respectivo campo llamado de su propiedad en el objeto archivobtc
        low.setCellValueFactory(new PropertyValueFactory<archivoBTC, String>("bajo"));
        close.setCellValueFactory(new PropertyValueFactory<archivoBTC, String>("cierre"));
        adjClose.setCellValueFactory(new PropertyValueFactory<archivoBTC, String>("adjcierre"));
        volume.setCellValueFactory(new PropertyValueFactory<archivoBTC, String>("volumen"));
        date.setCellValueFactory(new PropertyValueFactory<archivoBTC, String>("fechas"));
        open.setCellValueFactory(new PropertyValueFactory<archivoBTC, String>("abierto"));

        try {
            tableView.setItems(Listar()); // por ultimo al tableview asignamos los items de la lista observale
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void alertPromedio(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Promedio");
        alert.setHeaderText("Promedio del BTC durante 2022");
        alert.setContentText(String.valueOf(MediaBTC("BTC-USD.csv")));
        alert.showAndWait();

    }

    @FXML
    private void alertMayor(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Mayor");
        alert.setHeaderText("Mayor Precio del BTC durante 2022");
        alert.setContentText(String.valueOf(PrecioMasAlto("BTC-USD.csv")));
        alert.showAndWait();
    }

    @FXML
    private void alertMenor(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Menor");
        alert.setHeaderText("Menor precio del BTC durante 2022");
        alert.setContentText(String.valueOf(PrecioMasBajo()));
        alert.showAndWait();
    }

    @FXML
    private void alertDesEstand(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(
                "Desviacion estandar");
        alert.setHeaderText(
                "Desviacion Estandar del BTC durante 2022");
        alert.setContentText(
                String.valueOf(DesviacionEstandar("BTC-USD.csv")));
        alert.showAndWait();
    }

    //metodos a usar para sacar los botones
    public static Double PrecioMasBajo() throws IOException {
        Path ruta = Paths.get("BTC-USD.csv");
        String[] linea;
        ArrayList<Double> Minimos = new ArrayList();

        List<String> documento = Files.readAllLines(ruta);
        for (int i = 1; i < documento.size(); i++) {
            linea = documento.get(i).split(",");
            double minimoDeLaLinea = Double.parseDouble(linea[2]);

            Minimos.add(minimoDeLaLinea);
        }
        // aprovecahmos el collecions que nos saca el valor minimo de una lista
        return Collections.min(Minimos);
    }

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

    public static double PrecioMasAlto(String ruta1) throws IOException {

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
        return Collections.max(Mayores);

    }

}

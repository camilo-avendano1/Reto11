package com.mycompany.reto11maven;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
        for(int i = 1; i < documento.size(); i++){ // en un bucle agregamos el resto elementos creando n archivos btc, donde cada uno se le asigna su respectiva linea
            archivoBTC archivo =  new archivoBTC(i);
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

}

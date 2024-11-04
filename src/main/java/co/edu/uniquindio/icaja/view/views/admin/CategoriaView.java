package co.edu.uniquindio.icaja.view.views.admin;

import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.icaja.controller.CategoriaController;
import co.edu.uniquindio.icaja.model.enums.TipoCategoria;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;

import co.edu.uniquindio.icaja.mapping.dto.CategoriaDto;
import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.utils.ViewTools;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import static co.edu.uniquindio.icaja.model.enums.TipoCategoria.*;

public class CategoriaView {

    CategoriaController categoriaController = new CategoriaController();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCrearCategoriaAdmin;

    @FXML
    private Button btnEliminarCategoriaAdmin;

    @FXML
    private Button btnConsultarCategoriaAdmin;

    @FXML
    private AnchorPane categorialpanel;

    @FXML
    private ComboBox<TipoCategoria> cbxTipoCategoriaAdmin;

    @FXML
    private TableColumn<Categoria, String> tcDescripcionCategoriaAdmin;

    @FXML
    private TableColumn<Categoria, String> tcNombreCategoriaAdmin;

    @FXML
    private TableColumn<Categoria, TipoCategoria> tcTipoCategoriaAdmin;

    @FXML
    private TableView<Categoria> tvCategoriaAdmin;

    @FXML
    private TextArea txaDescripcionCategoriaAdmin;

    @FXML
    private TextField txtNombreCategoriaAdmin;



    @FXML
    void consultarCategoriaAction() {

    }

    @FXML
    void limpiarCamposCategoriaAction() {
//Funcionando
        ViewTools.limpiarCampos(txtNombreCategoriaAdmin, 
                txaDescripcionCategoriaAdmin);

        cbxTipoCategoriaAdmin.getSelectionModel().select(NINGUNO);

    }

    @FXML
    void crearCategoriaAction() {
        String nombre = txtNombreCategoriaAdmin.getText();
        String descripcion = txaDescripcionCategoriaAdmin.getText();
        TipoCategoria tipo = cbxTipoCategoriaAdmin.getValue();


        if (ViewTools.NoHayCamposVacios(nombre, descripcion)) {
            if (!tipo.equals(TipoCategoria.NINGUNO)) {

                CategoriaDto categoriaDto = new CategoriaDto(nombre, descripcion, tipo);

                try {
                    categoriaController.crear(categoriaDto);
                    String msj = "Se ha creado la categoria con exito " + nombre + ".";
                    ViewTools.mostrarMensaje("Informacion: ", null, msj, Alert.AlertType.INFORMATION);
                } catch (ElementoYaExiste e) {
                    ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
                }
            }else {
                ViewTools.mostrarMensaje("Error", null, "El tipo de categoria no puede ser NINGUNO", Alert.AlertType.ERROR);
            }
        } else {
            ViewTools.mostrarMensaje("Error", null, "Hay campos vacios", Alert.AlertType.ERROR);

        }

        ViewTools.limpiarCampos(txtNombreCategoriaAdmin, 
                txaDescripcionCategoriaAdmin);


    }

    @FXML
    void eliminarCategoriaAction() {

        String nombre = txtNombreCategoriaAdmin.getText();

        if (ViewTools.NoHayCamposVacios(nombre)) {
            try {
                categoriaController.eliminar(nombre);
                String msj = "Se ha eliminado la categoria con exito " + nombre + ".";
                ViewTools.mostrarMensaje("Informacion: ", null, msj, Alert.AlertType.INFORMATION);
            } catch (ElementoNoExiste e) {
                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
            }

        } else {
            ViewTools.mostrarMensaje("Error", null, "Hay campos vacios", Alert.AlertType.ERROR);
        }


        ViewTools.limpiarCampos(txtNombreCategoriaAdmin,
                txaDescripcionCategoriaAdmin);


    }

    @FXML
    void initialize() {
        cbxTipoCategoriaAdmin.getItems().addAll(TipoCategoria.values());
        initview();
    }

    private void initview() {
        initDataBinging();
        tvCategoriaAdmin.getItems().clear();
        tvCategoriaAdmin.setItems(categoriaController.getListaCategoriasObservable());
        listenerSelectionCategorias();
    }

    private void initDataBinging() {
        tcNombreCategoriaAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tcTipoCategoriaAdmin.setCellValueFactory(cellData -> new SimpleObjectProperty<TipoCategoria>(cellData.getValue().getTipoCategoria()));
        tcDescripcionCategoriaAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
    }

    private void listenerSelectionCategorias() {

        tvCategoriaAdmin.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)
                -> this.mostrarInformacion((Categoria)newSelection));
    }

    private void mostrarInformacion(Categoria seleccionado) {

        if (seleccionado != null) {

            txtNombreCategoriaAdmin.setText(seleccionado.getNombre());
            txaDescripcionCategoriaAdmin.setText(seleccionado.getDescripcion());
            cbxTipoCategoriaAdmin.setValue(seleccionado.getTipoCategoria());
        }
    }



}

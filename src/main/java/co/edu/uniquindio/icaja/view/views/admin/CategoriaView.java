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
import javafx.beans.property.SimpleStringProperty;

import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private TableColumn<Categoria, String> tcDescripcionCategoriaAdmin;

    @FXML
    private TableColumn<Categoria, String> tcNombreCategoriaAdmin;

    @FXML
    private TableColumn<Categoria, String> tcTipoCategoriaAdmin;

    @FXML
    private TableView<Categoria> tvCategoriaAdmin;

    @FXML
    private TextArea txaDescripcionCategoriaAdmin;

    @FXML
    private MFXTextField txtNombreCategoriaAdmin;

    @FXML
    private MFXTextField txtTipoCategoriaAdmin;



    @FXML
    void consultarCategoriaAction(ActionEvent event) {

    }

    @FXML
    void crearCategoriaAction(ActionEvent event) {
        String nombre = txtNombreCategoriaAdmin.getText();
        String descripcion = txaDescripcionCategoriaAdmin.getText();
        String tipo = txtTipoCategoriaAdmin.getText();


        if (ViewTools.NoHayCamposVacios(nombre, descripcion, tipo)) {
            CategoriaDto categoriaDto = new CategoriaDto(nombre, descripcion, TipoCategoria.AHORRO);

            if (tipo.equals("gastos")) {

                categoriaDto = new CategoriaDto(nombre, descripcion, GASTO);

            } else if (tipo.equals("ingresos")) {

                categoriaDto = new CategoriaDto(nombre, descripcion, TipoCategoria.INGRESO);

            } else if (tipo.equals("ahorros")) {

                categoriaDto = new CategoriaDto(nombre, descripcion, AHORRO);
            }
            try {
                categoriaController.crear(categoriaDto);
                String msj = "Se ha creado la categoria con exito " + nombre + ".";
                ViewTools.mostrarMensaje("Informacion: ", null, msj, Alert.AlertType.INFORMATION);
            } catch (ElementoYaExiste e) {
                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
            }

        } else {
            ViewTools.mostrarMensaje("Error", null, "Hay campos vacios", Alert.AlertType.ERROR);

        }

        ViewTools.limpiarCampos(txtNombreCategoriaAdmin,
                txtTipoCategoriaAdmin);

        ViewTools.limpiarCamposArea(txaDescripcionCategoriaAdmin);

    }

//    @FXML
//    void actualizarCategoriaAction(ActionEvent event) {
//
//        String nombre = txtNombreCategoriaAdmin.getText();
//        String descripcion = txaDescripcionCategoriaAdmin.getText();
//        String tipo = txtTipoCategoriaAdmin.getText();
//
//        TipoCategoria tipoReal = null;
//
//        if (tipo.equals("gastos")) {
//
//            tipoReal = TipoCategoria.GASTO;
//
//        } else if (tipo.equals("ingresos")) {
//
//            tipoReal = TipoCategoria.INGRESO;
//
//        } else if (tipo.equals("ahorros")) {
//
//            tipoReal = TipoCategoria.AHORRO;
//        }
//
//        if (ViewTools.NoHayCamposVacios(nombre, descripcion, tipo)) {
//            CategoriaDto categoriaDto = new CategoriaDto(nombre, descripcion, tipoReal);
//
//            try {
//                categoriaController.actualizar(categoriaDto);
//                String msj = "Se ha actualizado la categoria con exito " + nombre + ".";
//                ViewTools.mostrarMensaje("Informacion: ", null, msj, Alert.AlertType.INFORMATION);
//            } catch (ElementoNoExiste e) {
//                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
//            }
//
//        } else {
//            ViewTools.mostrarMensaje("Error", null, "Hay campos vacios", Alert.AlertType.ERROR);
//        }
//
//        ViewTools.limpiarCampos(txtNombreCategoriaAdmin,
//                txtTipoCategoriaAdmin);
//
//        ViewTools.limpiarCamposArea(txaDescripcionCategoriaAdmin);
//    }

    @FXML
    void EliminarCategoriaAction(ActionEvent event) {

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
                txtTipoCategoriaAdmin);

        ViewTools.limpiarCamposArea(txaDescripcionCategoriaAdmin);

    }

    @FXML
    void initialize() {
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
        tcTipoCategoriaAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoCategoria().toString()));
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
            txtTipoCategoriaAdmin.setText(seleccionado.getTipoCategoria().toString());
        }
    }

    @FXML
    void limpiarCamposCategoriaAction(ActionEvent event) {
//Funcionando
        ViewTools.limpiarCampos(txtNombreCategoriaAdmin,
                txtTipoCategoriaAdmin);

//        ViewTools.limpiarCamposArea(txaDescripcionCategoriaAdmin);

    }

}

package co.edu.uniquindio.icaja.view.views.tooltips;

import co.edu.uniquindio.icaja.controller.CategoriaController;
import co.edu.uniquindio.icaja.controller.CuentaBancariaController;
import co.edu.uniquindio.icaja.controller.TransaccionController;
import co.edu.uniquindio.icaja.mapping.dto.TransferenciaDto;
import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.CuentaBancaria;
import co.edu.uniquindio.icaja.utils.ViewTools;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import java.util.*;

public class RealizarTransferenciaAdminView {
    
    private final CategoriaController categoriaController = new CategoriaController();
    private final ArrayList<String> listaNombresCategoria = new ArrayList<>();
    private final CuentaBancariaController cuentaBancariaController = new CuentaBancariaController();
    private final TransaccionController transaccionController = TransaccionController.getInstance();


    @FXML
    private ComboBox<String> cbxCuentaDestinoTransferencia;

    @FXML
    private ListView<CheckBox> lvListaCategoriaTransferencia;

    @FXML
    void realizarTransferencia() {
        
        try {
            TransferenciaDto transferencia = new TransferenciaDto(null,
                    transaccionController.getTransaccionPendiente().monto(),
                    nombreToCategoria(listaNombresCategoria),
                    transaccionController.getTransaccionPendiente().cuenta(),
                    transaccionController.getTransaccionPendiente().motivo(),
                    false,
                    cuentaBancariaController.consultar(cbxCuentaDestinoTransferencia.getValue())
            );

            transaccionController.crear(transferencia);
            ViewTools.mostrarMensaje("¡Genial!", null, "Se ha realizado una transacción correctamente", Alert.AlertType.INFORMATION);
            ViewTools.cerrarVentana(cbxCuentaDestinoTransferencia);

        } catch (Exception e) {
            Seguimiento.registrarLog(3, e.getMessage());
            ViewTools.mostrarMensaje("!Lo sentimos¡", null, "No se ha podido realizar la transferencia por un error interno", Alert.AlertType.ERROR);
            ViewTools.cerrarVentana(cbxCuentaDestinoTransferencia);
        }

    }


    public void initialize() {
        cargarCategoria();
        cargarlistaCuentaBancaria();
    }

    private void cargarCategoria() {
        List<String> categorias = categoriaToNombre(categoriaController.getListaCategoriasObservable());
        ObservableList<CheckBox> checkBoxes = FXCollections.observableArrayList();

        for (String nombre : categorias) {
            CheckBox checkBox = new CheckBox(nombre);

            checkBox.setOnAction(event -> {
                if (checkBox.isSelected()) {
                        listaNombresCategoria.add(nombre);
                } else if (!checkBox.isSelected()) {
                        listaNombresCategoria.remove(nombre);
                }
            });

            checkBoxes.add(checkBox);
        }

        lvListaCategoriaTransferencia.setItems(checkBoxes);
    }

    private List<String> categoriaToNombre(List<Categoria>  listaCategorias) {
        List<String> lista = new ArrayList<>();

        for (Categoria categoria : listaCategorias) {
            lista.add(categoria.getNombre());
        }

        return lista;
    }
    
    private Categoria[] nombreToCategoria(List<String> listaString) {
        Categoria[] lista = new Categoria[listaString.size()];
        List<Categoria> listaOriginal = categoriaController.getListaCategoriasObservable();
        for (String nombre : listaString) {
            for (Categoria categoria : listaOriginal){
                if (categoria.getNombre().equals(nombre)) {
                    lista[listaString.indexOf(nombre)] = categoria;
                }
            }
        }
        return  lista;
    }
    
    private void cargarlistaCuentaBancaria() {
        List<CuentaBancaria> listaCuentaBancaria = cuentaBancariaController.getListaCuentaBancariaObservable();
        listaCuentaBancaria.removeIf(cuenta -> cuenta.equals(transaccionController.getTransaccionPendiente().cuenta()));

        List<String> nuevaLista = new ArrayList<>();

        for (CuentaBancaria cuentaBancaria : listaCuentaBancaria) {
            nuevaLista.add(cuentaBancaria.getNumeroCuenta());
        }


        cbxCuentaDestinoTransferencia.getItems().addAll(nuevaLista);
    }

}
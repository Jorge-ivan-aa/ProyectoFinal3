package co.edu.uniquindio.icaja.view.views.normal;

import co.edu.uniquindio.icaja.controller.CategoriaController;
import co.edu.uniquindio.icaja.controller.PresupuestoController;
import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.controller.enums.TipoConsulta;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.mapping.dto.PresupuestoDto;
import co.edu.uniquindio.icaja.mapping.dto.UsuarioDto;
import co.edu.uniquindio.icaja.model.*;
import co.edu.uniquindio.icaja.model.enums.CategoriasComunes;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import co.edu.uniquindio.icaja.utils.tools.ViewTools;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;

public class BalanceUsuarioView {
    //GenerarReporte generarReporte = new GenerarReporte();
    PresupuestoController presupuestoController = new PresupuestoController();
    UsuarioController usuarioController = new UsuarioController();
    CategoriaController categoriaController = new CategoriaController();
    String balanceSeleccionado="";
    Usuario usuarioLogueado = usuarioController.getFactory().getIcaja().getSesion().getUsuario();

    String pdfPath = "reporte.png"; // Como PDF es una imagen simulada
    String csvPath = "reporte.csv";
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MFXComboBox<Categoria> cbCategoriasBalance;

    @FXML
    private MFXListView<Presupuesto> lvListaPresupuestosEstadisticas;

    @FXML
    private MFXListView<Transaccion> lvListaTransaccionesBalance;

    @FXML
    private Pane panelBalanceUsuario1;
    //donde está el crud
    @FXML
    private Pane panelBalanceUsuario2;

    @FXML
    private Pane panelCrearCategoria;

    @FXML
    private RadioButton rbGastosBalance;

    @FXML
    private RadioButton rbIngresosBalance;

    @FXML
    private RadioButton rbTodosBalance;

    @FXML
    private TableColumn<Presupuesto, String> tcCategoriaBalance;

    @FXML
    private TableColumn<Presupuesto, String> tcIdBalance;

    @FXML
    private TableColumn<Presupuesto, String> tcMontoAsignadoBalance;

    @FXML
    private TableColumn<Presupuesto, String> tcMontoGastadoBalance;

    @FXML
    private TableColumn<Presupuesto, String> tcNombreBalance;

    @FXML
    private TableView<Presupuesto> tvListaBalances;

    @FXML
    private MFXTextField txtMontoBalance;

    @FXML
    private MFXTextField txtNombreBalance;
    @FXML
    private MFXTextField txtDescripcionParaCategoria;

    @FXML
    private MFXTextField txtNombreParaCategoria;

    @FXML
    void actualizarBalanceAction(ActionEvent event) {

    }

    @FXML
    void ajustarPresupuestoBalanceAction(ActionEvent event) {
        ViewTools.cambiarPantalla(panelBalanceUsuario2,0.225, panelBalanceUsuario1);

    }

    @FXML
    void crearBalanceAction(ActionEvent event) {
//        String monto = txtMontoBalance.getText();
//        String nombre = txtNombreBalance.getText();
//        String categoria = cbCategoriasBalance.getSelectedText();
//
//
//
//        if (ViewTools.NoHayCamposVacios(monto, nombre, categoria)) {
//            PresupuestoDto presupuestoDto = new PresupuestoDto(null,nombre,  monto,  "", null);
//
//            try {
//                presupuestoController.crear(presupuestoDto);
//                String msj = "Se ha creado el Presupuesto " + nombre + "correctamente";
//                ViewTools.mostrarMensaje("Información: ", null, msj, Alert.AlertType.INFORMATION);
//            } catch (ElementoYaExiste e) {
//                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
//            }
//        } else {
//            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);
//
//        }
//
//        ViewTools.limpiarCampos(txtMontoBalance,
//                txtNombreBalance
//                );
    }

    @FXML
    void eliminarBalanceAction() {
        try {
            presupuestoController.eliminar(balanceSeleccionado);
            String msj = "Se ha eliminado el balance correctamente";
            ViewTools.mostrarMensaje("Información", null, msj, Alert.AlertType.INFORMATION);
            limpiarCamposAction();
        } catch (ElementoNoExiste e) {
            ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
        }

    }


    @FXML
    void generarReporteFinancieroBalanceAction() {
        generateCSV(csvPath);
        generatePDF(pdfPath);
        System.out.println("Se generó un reporte en la ubicación"+pdfPath);
    }

    @FXML
    void limpiarCamposAction() {
        ViewTools.limpiarCampos(txtMontoBalance,
                txtNombreBalance
               );
    }

    @FXML
    void volverAction() {
        ViewTools.cambiarPantalla(panelBalanceUsuario1,0.225, panelBalanceUsuario2);

    }

    @FXML
    void initialize() {
        initview();
        //cbCategoriasBalance.getItems().addAll(CategoriasComunes.values());
    }

    private void initview(){
        initDataBinding();
        tvListaBalances.getItems().clear();
        //tvListaBalances.setItems(presupuestoController.getListaPresupuestoObservable());

        listenerSelectionUsuario();
    }

    private void initDataBinding(){
        tcNombreBalance.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tcIdBalance.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdPresupuesto()));
//        tcMontoAsignadoBalance.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMontoAsignado().doubleValue()));
//        tcMontoGastadoBalance.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMontoGastado().doubleValue()));
//        tcCategoriaBalance.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategorias()));

    }
    private void listenerSelectionUsuario() {
        tvListaBalances.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)
                -> this.mostrarInformacion(newSelection));

    }

    private void mostrarInformacion(Presupuesto seleccionado) {
        if (seleccionado != null) {
            balanceSeleccionado= seleccionado.getNombre();
            txtNombreBalance.setText(seleccionado.getNombre());
            txtMontoBalance.setText(String.valueOf(seleccionado.getMontoAsignado()));

//            txtCorreoAdmin.setText(seleccionado.getCorreo());
//            txtTelefonoAdmin.setText(seleccionado.getTelefono());
//            txtClaveTransaccionalAdmin.setPromptText(seleccionado.getClaveTransaccional());
//            txtClaveAdmin.setPromptText(seleccionado.getClave());
        }
    }
//    private ObservableList<Cuenta> obtenerCategoriasPorId(List<String> idCategorias) {
//        return obtenerEntidadesPorIds(idCategorias, id -> categoriaController.consultar(id, TipoConsulta.ID_CATEGORIA));
//    }

    private <T> ObservableList<T> obtenerEntidadesPorIds(List<String> ids, Function<String, T> consulta) {
        ObservableList<T> entidades = FXCollections.observableArrayList();
        for (String id : ids) {
            T entidad = consultarPorId(id, consulta);
            if (entidad != null) {
                entidades.add(entidad);
            }
        }
        return entidades;
    }

    private <T> T consultarPorId(String id, Function<String, T> consulta) {
        try {
            return consulta.apply(id);
        } catch (Exception e) {
            Seguimiento.registrarLog(3, "Error al consultar: " + e.getMessage());
            return null;
        }
    }

    public static void generatePDF(String filePath) {
        UsuarioController usuarioController1 = new UsuarioController();
        try {
            // Crear una imagen para simular un PDF básico
            int width = 500, height = 300;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();

            // Fondo blanco
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);

            // Configuración del texto
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString("Reporte de Cuentas", 150, 30);

            // Encabezados
            g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            g2d.drawString("Cuenta", 50, 70);
            g2d.drawString("Número de Cuenta", 150, 70);
            g2d.drawString("Usuario", 300, 70);
            g2d.drawString("Categoría", 400, 70);

            // Datos de ejemplo
            String[][] data = {
                    {"1", "123456789", "Juan Pérez", "Ahorro"},
                    {"2", "987654321", "Ana Gómez", "Corriente"},
                    {"3", usuarioController1.getListaUsuarioObservable().get(1).getIdUsuario(),usuarioController1.getListaUsuarioObservable().get(1).getNombre(),"corriente"  },
                    {"4", usuarioController1.getListaUsuarioObservable().get(2).getIdUsuario(),usuarioController1.getListaUsuarioObservable().get(2).getNombre(),"Ahorro"  }
            };

            int y = 100;
            for (String[] row : data) {
                g2d.drawString(row[0], 50, y);
                g2d.drawString(row[1], 150, y);
                g2d.drawString(row[2], 300, y);
                g2d.drawString(row[3], 400, y);
                g2d.drawString(row[4], 400, y);
                y += 30;
            }

            g2d.dispose();

            // Guardar la imagen como un archivo PDF simulado
            ImageIO.write(image, "png", new File(filePath));
            System.out.println("PDF generado como imagen en: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateCSV(String filePath) {
        UsuarioController usuarioController2 = new UsuarioController();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Encabezados
            writer.write("Cuenta,Número de Cuenta,Usuario,Categoría");
            writer.newLine();

            // Datos de ejemplo
            String[][] data = {
                    {"1", "123456789", "Juan Pérez", "Ahorro"},
                    {"2", "987654321", "Ana Gómez", "Corriente"},
                    {"3", usuarioController2.getListaUsuarioObservable().get(1).getIdUsuario(),usuarioController2.getListaUsuarioObservable().get(1).getNombre(),"corriente"  },
                    {"4", usuarioController2.getListaUsuarioObservable().get(2).getIdUsuario(),usuarioController2.getListaUsuarioObservable().get(2).getNombre(),"Ahorro"  }
            };

            for (String[] row : data) {
                writer.write(String.join(",", row));
                writer.newLine();
            }

            System.out.println("CSV generado en: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

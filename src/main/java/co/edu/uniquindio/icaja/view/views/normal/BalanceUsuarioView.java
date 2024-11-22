package co.edu.uniquindio.icaja.view.views.normal;

import co.edu.uniquindio.icaja.controller.CategoriaController;
import co.edu.uniquindio.icaja.controller.PresupuestoController;
import co.edu.uniquindio.icaja.controller.TransaccionController;
import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.controller.enums.TipoConsulta;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.mapping.dto.PresupuestoDto;
import co.edu.uniquindio.icaja.mapping.dto.UsuarioDto;
import co.edu.uniquindio.icaja.model.*;
import co.edu.uniquindio.icaja.model.enums.CategoriasComunes;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import co.edu.uniquindio.icaja.utils.tools.NumTool;
import co.edu.uniquindio.icaja.utils.tools.ViewTools;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;

public class BalanceUsuarioView {
    //GenerarReporte generarReporte = new GenerarReporte();
    PresupuestoController presupuestoController = new PresupuestoController();
    TransaccionController transaccionController = new TransaccionController();
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
    private MFXFilterComboBox<String> cbCategoriasBalance;

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
    private TableColumn<Categoria, String> tcCategoriaBalance;

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
    void actualizarBalanceAction() {
        String monto = txtMontoBalance.getText();
        String nombre = txtNombreBalance.getText();
        String[] categoria = new String[]{String.valueOf(cbCategoriasBalance.getValue())};

        if (ViewTools.NoHayCamposVacios(monto, nombre)) {
            PresupuestoDto presupuestoDto = new PresupuestoDto(null,nombre,  monto,  "", categoria);

            try {
                presupuestoController.actualizar(presupuestoDto);
                String msj = "Se ha actualizado el Presupuesto " + nombre + "correctamente";
                ViewTools.mostrarMensaje("Información: ", null, msj, Alert.AlertType.INFORMATION);
            } catch (ElementoYaExiste e) {
                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);

        }

        ViewTools.limpiarCampos(txtMontoBalance,
                txtNombreBalance
        );
    }

    @FXML
    void ajustarPresupuestoBalanceAction() {
        ViewTools.cambiarPantalla(panelBalanceUsuario2,0.225, panelBalanceUsuario1);

    }

    @FXML
    void crearBalanceAction() {
        String monto = txtMontoBalance.getText();
        String nombre = txtNombreBalance.getText();
        String[] categoria = new String[]{String.valueOf(cbCategoriasBalance.getValue())};



        if (ViewTools.NoHayCamposVacios(monto, nombre)) {
            PresupuestoDto presupuestoDto = new PresupuestoDto(null,nombre,  monto,  "", categoria);

            try {
                presupuestoController.crear(presupuestoDto);
                String msj = "Se ha creado el Presupuesto " + nombre + "correctamente";
                ViewTools.mostrarMensaje("Información: ", null, msj, Alert.AlertType.INFORMATION);
            } catch (ElementoYaExiste e) {
                ViewTools.mostrarMensaje("Error", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            ViewTools.mostrarMensaje("Error", null, "Hay campos vacíos", Alert.AlertType.ERROR);

        }

        ViewTools.limpiarCampos(txtMontoBalance,
                txtNombreBalance
                );
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
        ViewTools.inicializarComboBox(cbCategoriasBalance, obtenerCategoriasPorId(usuarioLogueado.getIdCategorias()), Categoria::getNombre);
        llenarListaTransaccionesUsuario();
        llenarListaPresupuestosUsuario();
    }

    private void initview(){
        initDataBinding();
        tvListaBalances.getItems().clear();
        tvListaBalances.setItems(presupuestoController.getListaPresupuestoObservable());
        listenerSelectionUsuario();
    }

    private void initDataBinding(){
        tcNombreBalance.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tcIdBalance.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdPresupuesto()));
        tcMontoAsignadoBalance.setCellValueFactory(cellData -> new SimpleStringProperty(NumTool.formatearMonto(cellData.getValue().getMontoAsignado())));
        tcMontoGastadoBalance.setCellValueFactory(cellData -> new SimpleStringProperty(NumTool.formatearMonto(cellData.getValue().getMontoGastado())));
        tcCategoriaBalance.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(getCategoriaPropietario((cellData.getValue().getIdCategoria())))));

    }
    private String getCategoriaPropietario(String idCategoria) {
        try {
            return categoriaController.consultar(idCategoria, TipoConsulta.ID_CATEGORIA).getNombre();

        } catch (Exception e) {
            Seguimiento.registrarLog(3, "Ocurrio un error en la consulta de categorias: " + e.getMessage());
        }
        return "categoria No encontrada";
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
    private ObservableList<Categoria> obtenerCategoriasPorId(List<String> idCategorias) {
        return obtenerEntidadesPorIds(idCategorias, id -> categoriaController.consultar(id, TipoConsulta.ID_CATEGORIA));
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
    private void llenarListaTransaccionesUsuario() {
        // Obtener la lista de transacciones del usuario
        List<String> transaccionesUsuario = new ArrayList<>(usuarioLogueado.getIdTransacciones());
        Collections.reverse(transaccionesUsuario);

        // Convertir la lista a un ObservableList
        ObservableList<Transaccion> transaccionesObservableBalance = FXCollections.observableArrayList();
        List<Transaccion> transacciones = transaccionController.getListaTransaccionObservable();

        for (String id: transaccionesUsuario) {
            for (Transaccion transaccion : transacciones) {
                if (transaccion.getIdTransaccion().equals(id)) {
                    transaccionesObservableBalance.add(transaccion);
                }
            }
        }

        // Asignar la lista al ListView
        lvListaTransaccionesBalance.setItems(transaccionesObservableBalance);

        // Configurar la forma en que se muestran las transacciones
        lvListaTransaccionesBalance.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Transaccion transaccion, boolean empty) {
                super.updateItem(transaccion,empty);

                if (empty || transaccion == null) {
                    setGraphic(null); // No mostramos nada si está vacío o es nulo.
                    setText(null);
                } else {
                    // Crear los Labels
                    String tipoYMonto = transaccion.getTipo() + " de " + NumTool.formatearMonto(transaccion.getMonto());
                    javafx.scene.control.Label lblTipoMonto = new javafx.scene.control.Label(tipoYMonto);

                    String categoria;
                    try {
                        Categoria categoriaObj = categoriaController.consultar(transaccion.getIdCategoria(), TipoConsulta.ID_CATEGORIA);
                        categoria = categoriaObj.getNombre();
                    } catch (Exception e) {
                        categoria = "Categoría no encontrada";
                    }
                    javafx.scene.control.Label lblCategoria = new Label(categoria);

                    // Estilo para los Labels
                    lblTipoMonto.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #666");
                    lblCategoria.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");

                    // Crear el VBox y configurar estilo
                    VBox vbox = new VBox(5, lblTipoMonto, lblCategoria);
                    vbox.setPadding(new Insets(5));
                    vbox.setAlignment(Pos.CENTER_LEFT);

                    // Asignar el VBox como gráfico de la celda
                    setGraphic(vbox);
                    setText(null); // Eliminar texto por defecto
                }
            }
        });
    }
    private void llenarListaPresupuestosUsuario(){
        // Obtener la lista de transacciones del usuario
        List<String> presupuestoUsuario = new ArrayList<>(usuarioLogueado.getIdPresupuestos());
        Collections.reverse(presupuestoUsuario);

        // Convertir la lista a un ObservableList
        ObservableList<Presupuesto> presupuestosObservableBalance = FXCollections.observableArrayList();
        List<Presupuesto> presupuesto = presupuestoController.getListaPresupuestoObservable();

        for (String id: presupuestoUsuario) {
            for (Presupuesto presupuestos : presupuesto) {
                if (presupuestos.getIdPresupuesto().equals(id)) {
                    presupuestosObservableBalance.add(presupuestos);
                }
            }
        }

        // Asignar la lista al ListView
        lvListaPresupuestosEstadisticas.setItems(presupuestosObservableBalance);
        // Configurar la forma en que se muestran las transacciones
        lvListaTransaccionesBalance.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Presupuesto presupuesto1, boolean empty) {
                super.updateItem(presupuesto1,empty);

                if (empty || presupuesto1 == null) {
                    setGraphic(null); // No mostramos nada si está vacío o es nulo.
                    setText(null);
                } else {
                    // Crear los Labels
                    String NombreYMonto = presupuesto1.getNombre() + " de " + NumTool.formatearMonto(presupuesto1.getMontoAsignado());
                    javafx.scene.control.Label lblTipoMonto = new javafx.scene.control.Label(NombreYMonto);

                    String presupuestico;
                    try {
                        Presupuesto presupuestoObj = presupuestoController.consultar(presupuesto1.getIdPresupuesto(), TipoConsulta.ID_PRESUPUESTO);
                        presupuestico = presupuestoObj.getNombre();
                    } catch (Exception e) {
                        presupuestico = "presupuesto no encontrado";
                    }
                    javafx.scene.control.Label lblCategoria = new Label(presupuestico);

                    // Estilo para los Labels
                    lblTipoMonto.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #666");
                    lblCategoria.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");

                    // Crear el VBox y configurar estilo
                    VBox vbox = new VBox(5, lblTipoMonto, lblCategoria);
                    vbox.setPadding(new Insets(5));
                    vbox.setAlignment(Pos.CENTER_LEFT);

                    // Asignar el VBox como gráfico de la celda
                    setGraphic(vbox);
                    setText(null); // Eliminar texto por defecto
                }
            }
        });
    }



}

package co.edu.uniquindio.icaja.view.views.admin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import co.edu.uniquindio.icaja.controller.CategoriaController;
import co.edu.uniquindio.icaja.controller.CuentaController;
import co.edu.uniquindio.icaja.controller.TransaccionController;
import co.edu.uniquindio.icaja.controller.UsuarioController;
import co.edu.uniquindio.icaja.controller.enums.TipoConsulta;
import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.Cuenta;
import co.edu.uniquindio.icaja.model.Transaccion;
import co.edu.uniquindio.icaja.model.Usuario;
import co.edu.uniquindio.icaja.model.enums.CategoriasComunes;
import co.edu.uniquindio.icaja.model.enums.TipoTransaccion;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import co.edu.uniquindio.icaja.utils.tools.NumTool;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;

public class EstadisticaView {

    private final TransaccionController transaccionController = new TransaccionController();
    private final CategoriaController categoriaController = new CategoriaController();
    private final UsuarioController usuarioController = new UsuarioController();
    private final CuentaController cuentaController = new CuentaController();

    private final ObservableList<String> gastosPorCategoria = FXCollections.observableArrayList();
    private final ObservableList<Usuario> usuarios = usuarioController.getListaUsuarioObservable();

    @FXML
    private AnchorPane estadisticasPanel;

    @FXML
    private Label lbSaldoPromedioUsuario;

    @FXML
    private Label lbUsuarioMayorSaldoNombre;

    @FXML
    private Label lbUsuarioMayorSaldoSaldo;

    @FXML
    private PieChart pcGraficaUno;

    @FXML
    private TableColumn<String, String> tcGastosPorCategoria;

    @FXML
    private TableColumn<String, String> tcPorcentajePorCategoria;

    @FXML
    private TableColumn<String, String> tcCantidadTransacciones;

    @FXML
    private TableColumn<String, String> tcTransaccionesPorUsuario;

    @FXML
    private TableView<String> tvGastosPorCategoria;

    @FXML
    private TableView<UsuarioTransaccionesCount> tvTransaccionesPorUsuario;


    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;


    // Propiedades observables para actualizar los labels
    private final SimpleDoubleProperty saldoPromedioProperty = new SimpleDoubleProperty();
    private final SimpleStringProperty usuarioMayorSaldoNombreProperty = new SimpleStringProperty();
    private final SimpleStringProperty usuarioMayorSaldoSaldoProperty = new SimpleStringProperty();

    @FXML
    void initialize() {
        inicializarVista();
        actualizarEstadisticas();
        llenarTablaTransaccionesPorUsuario();

        // Escuchar los cambios en la lista de usuarios
        usuarios.addListener((ListChangeListener<Usuario>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    actualizarEstadisticas();
                }
            }
        });
    }

    private void inicializarVista() {
        configurarDataBinding();
        List<String> categoriasGasto = calcularGastosPorCategoria();
        crearGraficoCategorias(categoriasGasto);
        actualizarTablaGastosPorCategoria(categoriasGasto);
    }

    private void configurarDataBinding() {
        tcGastosPorCategoria.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().split("@@")[0]));
        tcPorcentajePorCategoria.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().split("@@")[1]));
    }

    private void crearGraficoCategorias(List<String> categorias) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (String categoria : categorias) {
            String[] parts = categoria.split("@@");
            if (parts.length == 2) {
                String nombreCategoria = parts[0];
                try {
                    String porcentajeStr = NumTool.formatearNumero(parts[1]);
                    if (porcentajeStr != null && !porcentajeStr.isEmpty()) {
                        double porcentaje = Double.parseDouble(porcentajeStr);
                        pieChartData.add(new PieChart.Data(nombreCategoria, porcentaje));
                    }
                } catch (NumberFormatException e) {
                    Seguimiento.registrarLog(3, "Error al convertir porcentaje en crear gráfico de categorías: " + e.getMessage());
                }
            }
        }

        pcGraficaUno.setData(pieChartData);
    }


    private List<String> calcularGastosPorCategoria() {
        List<String> categoriasUsuarios = obtenerCategoriasUsuarios();
        return CategoriasComunes.calcularEstadisticas(categoriasUsuarios);
    }

    private List<String> obtenerCategoriasUsuarios() {
        List<Transaccion> transaccionesFiltradas = filtrarTransacciones();
        List<String> categorias = new ArrayList<>();

        for (Transaccion transaccion : transaccionesFiltradas) {
            try {
                Categoria categoriaTransaccion = categoriaController.consultar(transaccion.getIdCategoria(), TipoConsulta.ID_CATEGORIA);
                categorias.add(categoriaTransaccion.getNombre());
            } catch (Exception e) {
                Seguimiento.registrarLog(3, "Error al filtrar la categoría: " + e.getMessage());
            }
        }

        return categorias;
    }

    private List<Transaccion> filtrarTransacciones() {
        List<Transaccion> transaccionesFiltradas = new ArrayList<>();

        for (Transaccion transaccion : transaccionController.getListaTransaccionObservable()) {
            if (transaccion.getTipo() == TipoTransaccion.RETIRO || esTransferenciaEntreUsuarios(transaccion)) {
                transaccionesFiltradas.add(transaccion);
            }
        }

        return transaccionesFiltradas;
    }

    private boolean esTransferenciaEntreUsuarios(Transaccion transaccion) {
        if (transaccion.getTipo().equals(TipoTransaccion.TRANSFERENCIA)) {
            Cuenta cuentaOrigen = cuentaController.consultar(transaccion.getIdCuentas()[0], TipoConsulta.ID_CUENTA);
            Cuenta cuentaDestino = cuentaController.consultar(transaccion.getIdCuentas()[1], TipoConsulta.ID_CUENTA);
            return !cuentaOrigen.getIdpropietario().equals(cuentaDestino.getIdpropietario());
        }
        return false;
    }

    private void actualizarTablaGastosPorCategoria(List<String> categorias) {
        gastosPorCategoria.clear();
        gastosPorCategoria.setAll(categorias);
        tvGastosPorCategoria.setItems(gastosPorCategoria);
    }

    private void actualizarEstadisticas() {
        // Obtener el saldo promedio
        double saldoPromedio = calcularSaldoPromedio();

        // Usar BigDecimal para redondear a 2 decimales
        BigDecimal saldoPromedioRedondeado = NumTool.parseToDinero(String.valueOf(saldoPromedio)).setScale(0, RoundingMode.HALF_UP);

        // Ejecutar el código en el hilo de la UI
        Platform.runLater(() -> {
            // Actualizar el Label con el saldo promedio redondeado
            lbSaldoPromedioUsuario.setText(NumTool.formatearMonto(saldoPromedioRedondeado.toString()));

            // Obtener el usuario con el mayor saldo y actualizar los labels
            Usuario usuarioConMayorSaldo = obtenerUsuarioConMayorSaldo();
            if (usuarioConMayorSaldo != null) {
                lbUsuarioMayorSaldoNombre.setText("Nombre: " + usuarioConMayorSaldo.getNombre());
                lbUsuarioMayorSaldoSaldo.setText("Saldo: " + NumTool.formatearMonto(usuarioConMayorSaldo.getSaldoTotal()));
            } else {
                lbUsuarioMayorSaldoNombre.setText("No hay usuarios.");
                lbUsuarioMayorSaldoSaldo.setText("No disponible.");
            }
        });

    }

    private double calcularSaldoPromedio() {
        if (usuarios == null || usuarios.isEmpty()) {
            return 0.0;
        }

        double totalSaldo = 0;
        for (Usuario usuario : usuarios) {

            try {
                String saldo = usuario.getSaldoTotal();
                if (saldo != null && !saldo.isEmpty()) {
                    totalSaldo += Double.parseDouble(saldo);
                }
            } catch (NumberFormatException e) {
                Seguimiento.registrarLog(3, "Error al convertir saldo a número: " + e.getMessage());
            }
        }


        return totalSaldo / usuarios.size();
    }


    private Usuario obtenerUsuarioConMayorSaldo() {
        if (usuarios == null || usuarios.isEmpty()) {
            return null;
        }

        return usuarios.stream()
                .max(Comparator.comparingInt(usuario -> {
                    if (!usuario.getCedula().equals("admin")) {
                        try {
                            return Integer.parseInt(usuario.getSaldoTotal());
                        } catch (NumberFormatException e) {
                            Seguimiento.registrarLog(3, "Error al convertir saldo total a número para el usuario: " + usuario.getCedula());
                            return 0; // Si ocurre un error de conversión, asigna 0 para evitar la excepción
                        }
                    }
                    return 0;
                }))
                .orElse(null);
    }


    private void llenarTablaTransaccionesPorUsuario() {
        // Paso 1: Crear la lista observable que refleja la cantidad de transacciones por usuario
        ObservableList<UsuarioTransaccionesCount> listaUsuariosConTransacciones = FXCollections.observableArrayList();

        // Paso 2: Inicializar la lista de usuarios con las transacciones actuales
        // Esto se hace una vez al inicio para cargar los datos por primera vez
        actualizarListaUsuariosConTransacciones(listaUsuariosConTransacciones);

        // Paso 3: Mostrar la lista en la tabla
        tvTransaccionesPorUsuario.setItems(listaUsuariosConTransacciones);

        // Configurar las columnas de la tabla
        tcTransaccionesPorUsuario.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        tcCantidadTransacciones.setCellValueFactory(new PropertyValueFactory<>("cantidadTransacciones"));

        // Paso 4: Configurar el gráfico de barras
        actualizarGraficoDeBarras(listaUsuariosConTransacciones);

        // Paso 5: Escuchar los cambios en la lista observable de transacciones
        transaccionController.getListaTransaccionObservable().addListener((ListChangeListener<Transaccion>) change -> {
            // Cada vez que la lista de transacciones cambie, actualizamos la lista de usuarios
            actualizarListaUsuariosConTransacciones(listaUsuariosConTransacciones);

            // Actualizamos la tabla y el gráfico de barras
            tvTransaccionesPorUsuario.setItems(listaUsuariosConTransacciones);
            actualizarGraficoDeBarras(listaUsuariosConTransacciones);
        });
    }

    // Método para actualizar la lista de usuarios con la cantidad de transacciones
    private void actualizarListaUsuariosConTransacciones(ObservableList<UsuarioTransaccionesCount> listaUsuariosConTransacciones) {
        // Contar las transacciones por usuario
        List<UsuarioTransaccionesCount> listaContada = contarTransaccionesPorUsuario();

        // Ordenar la lista de mayor a menor
        listaContada.sort((u1, u2) -> Integer.compare(u2.getCantidadTransacciones(), u1.getCantidadTransacciones()));

        // Limpiar la lista observable
        listaUsuariosConTransacciones.clear();

        // Agregar todos los usuarios con transacciones contadas a la lista observable
        listaUsuariosConTransacciones.addAll(listaContada);
    }

    // Método para contar las transacciones por usuario
    private List<UsuarioTransaccionesCount> contarTransaccionesPorUsuario() {
        List<UsuarioTransaccionesCount> resultado = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            int cantidadTransacciones = usuario.getIdTransacciones().size();
            resultado.add(new UsuarioTransaccionesCount(usuario.getCedula(), cantidadTransacciones));
        }
        return resultado;
    }

    // Método para actualizar el gráfico de barras
    private void actualizarGraficoDeBarras(ObservableList<UsuarioTransaccionesCount> listaUsuariosConTransacciones) {
        // Crear las series de datos para el gráfico de barras
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Usuarios");

        // Llenar la serie con los datos de la lista observable
        for (UsuarioTransaccionesCount item : listaUsuariosConTransacciones) {
            series.getData().add(new XYChart.Data<>(item.getCedula(), item.getCantidadTransacciones()));
        }

        // Limpiar y agregar la nueva serie al gráfico
        barChart.getData().clear();
        barChart.getData().add(series);
    }


    // Clase auxiliar para contener la cédula y el número de transacciones de cada usuario
    @Getter
    public static class UsuarioTransaccionesCount {
        private String cedula;
        private int cantidadTransacciones;

        public UsuarioTransaccionesCount(String cedula, int cantidadTransacciones) {
            this.cedula = cedula;
            this.cantidadTransacciones = cantidadTransacciones;
        }

    }

}

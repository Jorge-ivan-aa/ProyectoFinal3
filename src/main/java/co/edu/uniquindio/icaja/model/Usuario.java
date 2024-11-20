package co.edu.uniquindio.icaja.model;
import co.edu.uniquindio.icaja.exception.login.CredencialesNoCoinciden;
import co.edu.uniquindio.icaja.model.enums.TipoUsuario;
import co.edu.uniquindio.icaja.model.services.Login;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import co.edu.uniquindio.icaja.utils.tools.NumTool;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

@Getter
@Setter
@NoArgsConstructor
public class Usuario implements Serializable, Login {
    private String idUsuario;
    private String nombre;
    private String cedula;
    private String correo;
    private String telefono;
    private String clave;
    private String claveTransaccional;
    private BigDecimal saldoTotal = new BigDecimal(BigInteger.ZERO);
    private BigDecimal ingresos = new BigDecimal(BigInteger.ZERO);
    private BigDecimal gastos = new BigDecimal(BigInteger.ZERO);
    private TipoUsuario tipoUsuario = TipoUsuario.NORMAL;
    private ArrayList<String> idCuentas = new ArrayList<>();
    private ArrayList<String> idPresupuestos = new ArrayList<>();
    private ArrayList<String> idCategorias =  new ArrayList<>();
    private List<String> idTransacciones = Collections.unmodifiableList(new ArrayList<>());
    public static final long serialVersionID = 5L;

    public Usuario(String nombre, String cedula, String correo, String telefono, String clave, String claveTransaccional) {
        this.idUsuario = generarId();
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
        this.telefono = telefono;
        this.clave = encriptarClave(clave);
        this.claveTransaccional = encriptarClave(claveTransaccional);
        this.saldoTotal = BigDecimal.ZERO;
        this.ingresos = BigDecimal.ZERO;
        this.gastos = BigDecimal.ZERO;
    }

    private String generarId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public TipoUsuario ingresar(String clave_ingresada) throws CredencialesNoCoinciden {
        if (verificarCredenciales(this.getClave(), clave_ingresada)) {
            Seguimiento.registrarLog(1, "El usuario " + nombre + " ingresó satisfactoriamente");
        } else {
            throw new CredencialesNoCoinciden("Contraseña incorrecta, intenta nuevamente.");
        }

        return getTipoUsuario();
    }

    public boolean verificarCredenciales(String hash_almacenado, String clave) {
        return BCrypt.checkpw(clave, hash_almacenado);
    }

    public String encriptarClave(String clave) {
        return BCrypt.hashpw(clave, BCrypt.gensalt());
    }

    public void setAdministrador() {
        this.tipoUsuario = TipoUsuario.ADMINISTRADOR;
    }

    public void setHashclave(String hashclave) {
        this.clave = encriptarClave(hashclave);
    }

    public void  setHashclaveTransaccional(String hashclaveTransaccional) {
        this.claveTransaccional = encriptarClave(hashclaveTransaccional);
    }

    public void setTransacciones(List<String> transacciones) {
        this.idTransacciones = Collections.unmodifiableList(transacciones);
    }

    public void calcularIngresos(BigDecimal monto) {
        ingresos = ingresos.add(monto);
    }

    public void calcularGastos(BigDecimal monto) {
        gastos = gastos.add(monto);
    }

    public void sumarSaldoTotal(BigDecimal monto) {
        calcularIngresos(monto);
        saldoTotal = saldoTotal.add(monto);
    }

    public void restarSaldoTotal(BigDecimal monto) {
        calcularGastos(monto);
        this.saldoTotal = this.saldoTotal.subtract(monto);
    }

    public void agregarTransaccion(Transaccion transaccion) {
        List<String> nuevaLista = new ArrayList<>(this.idTransacciones);
        nuevaLista.add(transaccion.getIdTransaccion());

        // Asigna la nueva lista como una lista inmutable
        this.idTransacciones = Collections.unmodifiableList(nuevaLista);
    }
}
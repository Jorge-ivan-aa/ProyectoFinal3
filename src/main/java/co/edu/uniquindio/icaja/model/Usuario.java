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
    private ArrayList<Cuenta> cuentas = new ArrayList<>();
    private ArrayList<String> idCuentas = new ArrayList<>();
    private ArrayList<Presupuesto> presupuestos = new ArrayList<>();
    private ArrayList<Categoria> categorias =  new ArrayList<>();
    private List<Transaccion> transacciones = Collections.unmodifiableList(new ArrayList<>());
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

    public void setTransacciones(List<Transaccion> transacciones) {
        this.transacciones = Collections.unmodifiableList(transacciones);
    }

    public void calcularSaldoTotal() {
        for (Cuenta cuenta : cuentas) {
            saldoTotal = saldoTotal.add(cuenta.getSaldo());
        }
    }

    public void agregarTransaccion(Transaccion transaccion) {
        List<Transaccion> nuevaLista = new ArrayList<>(this.transacciones);
        nuevaLista.add(transaccion);

        // Asigna la nueva lista como una lista inmutable
        this.transacciones = Collections.unmodifiableList(nuevaLista);
    }
}
package co.edu.uniquindio.icaja.model;
import co.edu.uniquindio.icaja.exception.login.CredencialesNoCoinciden;
import co.edu.uniquindio.icaja.model.enums.TipoUsuario;
import co.edu.uniquindio.icaja.model.services.Login;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
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
    private String direccion;
    private String clave;
    private String claveTransaccional;
    private double saldoTotal;
    private double ingresos;
    private double gastos;
    //private ArrayList<Presupuesto> presupuestos = new ArrayList<>();
    private TipoUsuario tipoUsuario;
    private ArrayList<Cuenta> cuentas = new ArrayList<>();
    public static final long serialVersionID = 5L;

    public Usuario(String nombre, String cedula, String correo, String telefono, String clave, String claveTransaccional) {
        this.idUsuario = generarId();
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
        this.telefono = telefono;
        this.clave = encriptarClave(clave);
        this.claveTransaccional = encriptarClave(claveTransaccional);
        this.saldoTotal = 0;
        this.ingresos = 0;
        this.gastos = 0;
        this.tipoUsuario = TipoUsuario.NORMAL;
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

    public void addCuenta(Cuenta cuenta) {
        this.cuentas.add(cuenta);
    }

    public void removeCuenta(Cuenta cuenta) {
        this.cuentas.remove(cuenta);
    }

    public void setClaveTransaccional(String claveTransaccional) {
    }

    public void setHashclave(String hashclave) {
        this.clave = encriptarClave(hashclave);
    }

    public void  setHashclaveTransaccional(String hashclaveTransaccional) {
        this.claveTransaccional = encriptarClave(hashclaveTransaccional);
    }
}
package co.edu.uniquindio.icaja.model;

import co.edu.uniquindio.icaja.exception.login.CredencialesNoCoinciden;
import co.edu.uniquindio.icaja.model.enums.TipoUsuario;
import co.edu.uniquindio.icaja.model.services.Login;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.edu.uniquindio.icaja.model.services.Persistible;
import co.edu.uniquindio.icaja.utils.Seguimiento;
import co.edu.uniquindio.icaja.utils.Persistencia;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Usuario implements Serializable, Login, Persistible<Usuario> {
    private String nombre;
    private String cedula;
    private String correo;
    private String telefono;
    private String clave;
    private String claveTransaccional;
    private double saldoTotal;
    private double ingresos;
    private double gastos;
    private double presupuestoMensual;
    private TipoUsuario tipoUsuario;
    private ArrayList<CuentaBancaria> listaCuentas;
    public static final long serialVersionID = 5L;

    public Usuario(String nombre, String cedula, String correo, String telefono, String clave, String claveTransaccional, double presupuestoMensual) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
        this.telefono = telefono;
        this.clave = clave;
        this.claveTransaccional = claveTransaccional;
        this.saldoTotal = 0;
        this.ingresos = 0;
        this.gastos = 0;
        this.presupuestoMensual = presupuestoMensual;
        this.listaCuentas = new ArrayList<>();
        this.tipoUsuario = TipoUsuario.NORMAL;
    }

    @Override
    public TipoUsuario ingresar(String clave) throws CredencialesNoCoinciden {
        if (verificarCredenciales(this, clave)) {
            Seguimiento.registrarLog(1, "El usuario " + nombre + " ingresó satisfactoriamente");
        } else {
            throw new CredencialesNoCoinciden("Contraseña incorrecta, intenta nuevamente.");
        }

        return getTipoUsuario();
    }

    public boolean verificarCredenciales(Usuario usuario, String clave) {
        return usuario.getClave().equals(clave);
    }


    @Override
    public void guardar(List<Usuario> usuarios) throws IOException {
        String contenido = "";
        for(Usuario usuario:usuarios)
        {
            contenido= usuario.getNombre()+
                    "@@"+usuario.getCedula()+
                    "@@"+usuario.getCorreo()+
                    "@@"+usuario.getTelefono()+
                    "@@"+usuario.getClave()+
                    "@@"+usuario.getClaveTransaccional()+
                    "@@"+usuario.getSaldoTotal()+
                    "@@"+usuario.getIngresos()+
                    "@@"+usuario.getGastos()+
                    "@@"+usuario.getPresupuestoMensual()+
                    "@@"+usuario.getTipoUsuario()+"\n";
        }
        Persistencia.guardarArchivo("usuario.txt", contenido, false);
    }

    @Override
    public List<Usuario> leer(String ruta) throws IOException {
        ArrayList<Usuario> usuarios =new ArrayList<Usuario>();
        ArrayList<String> contenido = Persistencia.leerArchivo(ruta);
        String[] linea;
        for (String texto : contenido) {
            linea = texto.split("@@");
            Usuario usuario = new Usuario();
            usuario.setNombre(linea[0]);
            usuario.setCedula(linea[1]);
            usuario.setCorreo(linea[2]);
            usuario.setTelefono(linea[3]);
            usuario.setClave(linea[4]);
            usuario.setClaveTransaccional(linea[5]);
            usuario.setSaldoTotal(Double.parseDouble(linea[6]));
            usuario.setIngresos(Double.parseDouble(linea[7]));
            usuario.setGastos(Double.parseDouble(linea[8]));
            usuario.setTipoUsuario(TipoUsuario.valueOf(linea[10]));
            usuarios.add(usuario);
        }
        return usuarios;
    }

    public void setAdministrador() {
        this.tipoUsuario = TipoUsuario.ADMINISTRADOR;
    }

    public void setNormal() {
        this.tipoUsuario = TipoUsuario.NORMAL;
    }

    public void addCuenta(CuentaBancaria cuenta) {
        this.listaCuentas.add(cuenta);
    }

    public void removeCuenta(CuentaBancaria cuenta) {
        this.listaCuentas.remove(cuenta);
    }

}
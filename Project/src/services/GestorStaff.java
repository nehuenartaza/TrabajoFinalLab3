package services;
import java.io.Serializable;
import users.Persona;

public class GestorStaff implements Serializable {
    private String rol;
    private Persona datos;

    public GestorStaff(Persona datos, String rol) {
        this.datos = datos;
        this.rol = rol;
    }
    
    public GestorStaff() {
    	
    }

    public String getRol() {
        return rol;
    }

    public Persona getDatos() {
        return datos;
    }
    
    public void setDatos(Persona datos) {
    	this.datos = datos;
    }

    @Override
    public String toString() {
        return "Rol: " + rol + " |Datos: " + datos;
    }

}

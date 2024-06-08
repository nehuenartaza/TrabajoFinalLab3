public class GestorStaff {
    private String rol;
    private Persona datos;

    public GestorStaff(Persona datos, String rol) {
        this.datos = datos;
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

    public Persona getDatos() {
        return datos;
    }

    @Override
    public String toString() {
        return "Rol: " + rol + " |Datos: " + datos;
    }

}

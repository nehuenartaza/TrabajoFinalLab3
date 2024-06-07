public class Persona {

    private String nombre;
    private String apellido;
    private String dni;

    public Persona(String nombre, String apellido, String dni) {
        this.nombre = nombre;
        this.dni = dni;
        this.apellido = apellido;
    }

    public Persona() {
        this.nombre = " ";
        this.dni = " ";
        this.apellido = " ";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "\nPersona:" +
                "\nnombre: " + nombre +
                "\napellido: " + apellido +
                 "\ndni: " + dni ;
    }
}

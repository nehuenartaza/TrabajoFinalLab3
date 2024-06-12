import java.util.ArrayList;

public class Pasajero extends Persona {
    private String localidad;
    private String domicilio;
    ArrayList<Historial> historial = new ArrayList<Historial>();

    public Pasajero(String nombre, String apellido, String dni, String localidad, String domicilio, ArrayList<Historial> historial)  {
        super(nombre, apellido, dni);
        this.localidad = localidad;
        this.domicilio = domicilio;
        this.historial = historial;
    }

    public Pasajero(String nombre, String apellido, String dni, String localidad, String domicilio)  {
        super(nombre, apellido, dni);
        this.localidad = localidad;
        this.domicilio = domicilio;
    }

    public String getLocalidad() {
        return localidad;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public ArrayList<Historial> getHistorial() {
        return historial;
    }
    
    public void agregarHistorial(Historial nuevo) {
    	historial.add(nuevo);
    }

    @Override
    public String toString() {
        return "Pasajero Nombre: " + getNombre() + " |Apellido: " + getApellido() + " |Dni: " + getDni() + " |Localidad: " + localidad + " |Domicilio: " + domicilio;
    }
    
    public void printHistorial() {
        for ( Historial i : historial ) {
            if ( i != null ) {
                System.out.println(i);
            }
        }
    }
}

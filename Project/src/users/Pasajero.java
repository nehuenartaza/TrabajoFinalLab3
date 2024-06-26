package users;
import java.io.Serializable;
import java.util.ArrayList;
import services.Historial;
public class Pasajero extends Persona implements Serializable {
    private String localidad;
    private String domicilio;
    private ArrayList<Historial> historial = new ArrayList<Historial>();

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
    
    public Pasajero() {
    	
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
    
    public void setHistorial(ArrayList<Historial> historial) {
    	this.historial = historial;
    }

    @Override
    public String toString() {
        return "Pasajero Nombre: " + getNombre() + " |Apellido: " + getApellido() + " |Dni: " + getDni() + " |Localidad: " + localidad + " |Domicilio: " + domicilio;
    }
    
    public void printHistorial() {
        if ( !historial.isEmpty() ) {
            for ( Historial i : historial ) {
                if ( i != null ) {
                    System.out.println(i);
                }
            }
        } else {
            System.out.println("No hay historial");
        }
    }
}

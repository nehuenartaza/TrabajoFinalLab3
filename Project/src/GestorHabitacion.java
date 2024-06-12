import java.util.ArrayList;

public class GestorHabitacion {
    private ArrayList<Habitacion> habitaciones = new ArrayList<Habitacion>();

    public GestorHabitacion(ArrayList<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public GestorHabitacion() {

    }

    public void registrarHabitacion(Habitacion nueva) {
        if ( !habitacionExiste(nueva.getNumero()) ) {
        	habitaciones.add(nueva);
        }
    }
    
    public void eliminarHabitacion(int numero) {
        for ( Habitacion i : habitaciones ) {
            if ( i != null && i.getNumero() == numero ) {
                habitaciones.remove(habitaciones.indexOf(i));
            }
        }
    }

    public void verEstadoHabitacion(int numero) {
        for ( Habitacion i : habitaciones ) {
            if ( i != null && i.getNumero() == numero ) {
                System.out.println(i);
            }
        }
    }

    public void cambiarEstadoHabitacion(int numero, HabitacionStatus nuevoEstado) {
        for ( Habitacion i : habitaciones ) {
            if ( i != null && i.getNumero() == numero ) {
                i.setEstado(nuevoEstado);
            }
        }
    }

    public void agregarConsumo(int numero, double precio) {
        for ( Habitacion i : habitaciones ) {
            if ( i != null && i.getNumero() == numero ) {
                i.setCuenta(i.getCuenta() + precio);
            }
        }
    }
    
    public boolean habitacionExiste(int numero) {
    	for ( Habitacion i : habitaciones ) {
    		if ( i != null && i.getNumero() == numero ) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public Habitacion getHabitacionPorNumero(int numero) {
    	Habitacion aux = new Habitacion();
    	for ( Habitacion i : habitaciones ) {
    		if ( i != null && i.getNumero() == numero )
    			aux = i;
    		break;
    	}
    	return aux;
    }
    
    public void printHabitaciones() {
        for ( Habitacion i : habitaciones ) {
            if ( i != null ) {
                System.out.println(i);
            }
        }
    }
}

import java.util.ArrayList;

public class GestorHabitacion {
    private ArrayList<Habitacion> habitaciones = new ArrayList<Habitacion>();

    public GestorHabitacion(ArrayList<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public GestorHabitacion() {

    }

    public void registrarHabitacion(Habitacion nueva) { //validar que no se agregue una habitacion con mismo numero, se podría hacer un LinkedHashMap de habitaciones para que no hayan repetidos, clave: num habitacion
        habitaciones.add(nueva);
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
}

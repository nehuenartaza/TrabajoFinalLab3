package users;
import java.io.Serializable;
import java.util.ArrayList;
import services.Habitacion;
import services.Reserva;
import utilities.ReservaStatus;
import utilities.HabitacionStatus;
import services.GestorStaff;

public class Administrador extends Persona implements Administrable, Serializable {

    public Administrador(String nombre, String apellido, String dni) {
        super(nombre, apellido, dni);
    }
    
    public Administrador() {
    	
    }

    @Override
    public String toString() {
        return "Administrador: Nombre: " + getNombre() + " |Apellido: " + getApellido() + " |Dni: " + getDni();
    }

    @Override
    public void checkin(Habitacion habitacion, Reserva reserva) {
    	habitacion.setEstado(HabitacionStatus.OCUPADO);
    	reserva.setEstado(ReservaStatus.EN_PROCESO);
    }

    @Override
    public void checkout(Habitacion habitacion, Reserva reserva) {
    	habitacion.setEstado(HabitacionStatus.EN_LIMPIEZA);
    	habitacion.setCuenta(0.0);
    	reserva.setEstado(ReservaStatus.FINALIZADA);
    }

    @Override
    public void cancelarReserva(Reserva reserva) {
        reserva.setEstado(ReservaStatus.CANCELADA);
    }

    @Override
    public void cambiarEstadoHabitacion(Habitacion habitacion, HabitacionStatus nuevoEstado) {
    	if ( habitacion.getEstado() != HabitacionStatus.OCUPADO ) {
    		habitacion.setEstado(nuevoEstado);
    	}
    }
    
    @Override
    public void mostrarStaff(ArrayList<GestorStaff> staff) {
    	for ( GestorStaff i : staff ) {
    		System.out.println(i);
    	}
    }
    
    @Override
    public boolean cambiarStaff(GestorStaff modificado, ArrayList<GestorStaff> staff) {
    	for ( GestorStaff i : staff ) {
    		if ( i != null && i.getDatos().getDni().equals(modificado.getDatos().getDni()) ) {
    			i = modificado;
    			return true;
    		}
    	}
    	return false;
    }

    @Override
    public void borrarStaff(String dni, ArrayList<GestorStaff> staff) {
        for ( GestorStaff i : staff ) {
        	if ( i != null && i.getDatos().getDni().equals(dni) ) {
        		staff.remove(i);
        	}
        }
    }

    
}

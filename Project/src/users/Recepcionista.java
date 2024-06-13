package users;
import java.io.Serializable;
import services.Habitacion;
import services.Reserva;
import utilities.HabitacionStatus;
import utilities.ReservaStatus;

public class Recepcionista extends Persona implements Recepcionable, Serializable {

    public Recepcionista(String nombre, String apellido, String dni) {
        super(nombre, apellido, dni);
    }
    
    public Recepcionista() {
    	
    }

    @Override
    public String toString() {
        return "Recepcionista: Nombre: " + getNombre() + " |Apellido: " + getApellido() + " |Dni: " + getDni();
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

    
}

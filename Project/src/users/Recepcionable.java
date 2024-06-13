package users;
import services.Habitacion;
import services.Reserva;
import utilities.HabitacionStatus;
public interface Recepcionable {
    public void checkin(Habitacion habitacion, Reserva reserva);
    public void checkout(Habitacion habitacion, Reserva reserva);
    public void cancelarReserva(Reserva reserva);
    public void cambiarEstadoHabitacion(Habitacion habitacion, HabitacionStatus nuevoEstado);
}

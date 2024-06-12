public interface Recepcionable {
    public void checkin(Habitacion habitacion, Reserva reserva);
    public void checkout(Habitacion habitacion, Reserva reserva);
    public boolean hacerReserva(Reserva reserva);
    public void cancelarReserva(Reserva reserva);
    public void cambiarEstadoHabitacion(Habitacion habitacion, HabitacionStatus nuevoEstado);
    public void agregarHistorial(Historial nuevo, Pasajero pasajero);
    public boolean registrarPasajero(Pasajero nuevo);
}

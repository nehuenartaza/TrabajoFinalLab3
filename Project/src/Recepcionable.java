public interface Recepcionable {
    public void checkin();
    public void checkout();
    public boolean hacerReserva(Reserva reserva);
    public void cancelarReserva(Reserva reserva);
    public void estadoHabitacion(int numero);
    public void cambiarEstadoHabitacion(int numero, HabitacionStatus nuevoEstado);
    public void agregarHistorial(Historial nuevo, Pasajero pasajero);
    public boolean registrarPasajero(Pasajero nuevo);
}

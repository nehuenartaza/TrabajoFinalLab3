public enum ReservaStatus {
    FINALIZADA, //la reserva terminó sin ningún problema
    ACTIVA,     //el pasajero ocupó la habitación pero aún no terminó la reserva
    EN_PROCESO, //la reserva sigue vigente pero aún no llegó el día
    CANCELADA;  //el pasajero canceló la reserva
}
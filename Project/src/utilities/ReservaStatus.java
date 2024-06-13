package utilities;
import java.io.Serializable;

public enum ReservaStatus implements Serializable {
    FINALIZADA, //la reserva terminó sin ningún problema
    ACTIVA,     //la reserva sigue vigente pero aún no llegó el día
    EN_PROCESO, //el pasajero ocupó la habitación pero aún no terminó la reserva
    CANCELADA;  //el pasajero canceló la reserva
}
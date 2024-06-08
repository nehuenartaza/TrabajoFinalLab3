import java.util.ArrayList;

public class GestorReserva {
    ArrayList<Reserva> reservas = new ArrayList<Reserva>();


    public GestorReserva() {

    }

    public boolean hacerReserva(Reserva reserva) {  //hacer validaciones de ingresos y egresos y que dicha reserva no sea de un Pasajero con otra reserva pendiente acá o en donde sea
        reservas.add(reserva);
        return true;
    }

    public void cancelarReserva(Reserva reserva) {
        for ( Reserva i : reservas ) {
            if ( i != null && i.getDni().equals(reserva.getDni()) ) {
                reservas.remove(reserva);
            }
        }
    }

    public void printReservas() {
        for ( Reserva i : reservas ) {
            if ( i != null ) {
                System.out.println(i);
            }
        }
    }

    
}

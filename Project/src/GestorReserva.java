import java.util.ArrayList;

public class GestorReserva {
    ArrayList<Reserva> reservas = new ArrayList<Reserva>();


    public GestorReserva() {

    }
    
    public ArrayList<Reserva> getReservas() {
    	return reservas;
    }

    public void hacerReserva(Reserva reserva) {
        reservas.add(reserva);
    }
    
    public Reserva getReservaPorDni(String dni) {
    	Reserva aux = new Reserva();
    	for ( Reserva i : reservas ) {
    		if ( i != null && i.getDni().equals(dni) ) {
    			aux = i;
    		}
    	}
    	return aux;
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

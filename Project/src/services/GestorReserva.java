package services;
import java.io.Serializable;
import java.util.ArrayList;
import utilities.ReservaStatus;
public class GestorReserva implements Serializable {
    ArrayList<Reserva> reservas = new ArrayList<Reserva>();	//las unicas reservas que no maneja son las que estan canceladas o finalizadas


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
    			break;
    		}
    	}
    	return aux;
    }
    
    public void eliminarReservaPorDni(String dni) {
    	for ( Reserva i : reservas ) {
    		if ( i != null && i.getDni().equals(dni) ) {
    			reservas.remove(i);
    			break;
    		}
    	}
    }
    
    public boolean eliminarReservaPorNumeroHabitacion(int numHabitacion) {
    	for ( Reserva i : reservas ) {
    		if ( i != null && i.getNumeroHabitacion() == numHabitacion ) {
    			System.out.println("Se eliminó: " + i.toString());
    			reservas.remove(i);
    			return true;
    		}
    	}
    	return false;
    }
    
    public Reserva extraerPorDni(String dni) {
    	Reserva r = new Reserva();
    	for ( Reserva i : reservas ) {
    		if ( i != null && i.getDni().equals(dni) ) {
    			r = i;
    			reservas.remove(i);
    			break;
    		}
    	}
    	return r;
    }
    
    public boolean reservaExiste(String dni) {
    	for ( Reserva i : reservas ) {
    		if ( i != null && i.getDni().equals(dni) ) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public void cancelarReserva(Reserva reserva) {
        for ( Reserva i : reservas ) {
            if ( i != null && i.getDni().equals(reserva.getDni()) ) {
                reservas.remove(reserva);
                break;
            }
        }
    }

    public void printReservas() {
        for ( Reserva i : reservas ) {
            if ( i != null ) {
                System.out.println(i);
            }else{
				System.out.println("\nNo han encontrado reservas...");
			}
        }
    }
    
    public void printReservas(ReservaStatus estado) {
    	for ( Reserva i : reservas ) {
    		if ( i != null & i.getEstado() == estado ) {
    			System.out.println(i);
    		}
    	}
    }

    
}

import java.util.ArrayList;

public class Hotel {
    private GestorHabitacion gestorHabitacion;
    private ArrayList<GestorStaff> staff = new ArrayList<GestorStaff>();
    private GestorReserva gestorReserva;
    private ArrayList<Pasajero> pasajeros = new ArrayList<Pasajero>();
    private ArrayList<Producto> productos = new ArrayList<Producto>();

    public Hotel() {
    	gestorHabitacion = new GestorHabitacion();
    	gestorReserva = new GestorReserva();
    }

    public GestorHabitacion getGestorHabitacion() {
        return gestorHabitacion;
    }

    public ArrayList<GestorStaff> getStaff() {
        return staff;
    }

    public GestorReserva getGestorReserva() {
        return gestorReserva;
    }

    public ArrayList<Pasajero> getPasajeros() {
        return pasajeros;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }
    
    public Habitacion getHabitacionPorNumero(int numero) {
    	return gestorHabitacion.getHabitacionPorNumero(numero);
    }

    public void registrarHabitacion(Habitacion nueva) {
        gestorHabitacion.registrarHabitacion(nueva);
    }

    public void eliminarHabitacion(int numero) {
        gestorHabitacion.eliminarHabitacion(numero);
    }

    public void estadoHabitacion(int numero) {
        gestorHabitacion.verEstadoHabitacion(numero);
    }
    
    public void mostrarHabitaciones() {
    	gestorHabitacion.printHabitaciones();
    }

    public void cambiarEstadoHabitacion(int numero, HabitacionStatus nuevoEstado) {
        gestorHabitacion.cambiarEstadoHabitacion(numero, nuevoEstado);
    }

    public void agregarConsumoEnHabitacion(int numero, double precio) {
        gestorHabitacion.agregarConsumo(numero, precio);
    }

    public void registrarStaff(GestorStaff staff) { //el nuevo staff viene del método crearStaff de Administrador
        if ( !staffExiste(staff) ) {
        	this.staff.add(staff);
        }
    }

    public boolean cambiarStaff(GestorStaff staffModificado) {
    	for ( GestorStaff i : staff ) {
    		if ( i != null && i.getDatos().getDni().equals(staffModificado.getDatos().getDni()) ) {
    			i = staffModificado;
    			return true;
    		}
    	}
        return false;
    }
    
    public boolean aumentarRangoARecepcionista(String dniRecepcionista) {
    	for ( GestorStaff i : staff ) {
    		if ( i != null ) {
    			Persona datos = i.getDatos();
    			if ( datos.getDni().equals(dniRecepcionista) ) {
    				Administrador admin = new Administrador(datos.getNombre(), datos.getApellido(), dniRecepcionista);
    				GestorStaff nuevoAdmin = new GestorStaff(admin, "Administrador");
    				staff.set(staff.indexOf(i), nuevoAdmin);
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    public boolean bajarRangoAAdministrador(String dniAdministrador) {
    	for ( GestorStaff i : staff ) {
    		if ( i != null ) {
    			Persona datos = i.getDatos();
    			if ( datos.getDni().equals(dniAdministrador) ) {
    				Recepcionista recep = new Recepcionista(datos.getNombre(), datos.getApellido(), dniAdministrador);
    				GestorStaff nuevoRecep = new GestorStaff(recep, "Recepcionistas");
    				staff.set(staff.indexOf(i), nuevoRecep);
    				return true;
    			}
    		}
    	}
    	return false;
    }

    public void borrarStaff(String dni) {	//validar en main que no se borre un administrador a si mismo
    	for ( GestorStaff i : this.staff ) {
    		if ( i != null && i.getDatos().getDni().equals(dni) ) {
    			staff.remove(i);
    			break;
    		}
    	}
    }

    public void mostrarStaff() {
    	for ( GestorStaff i : staff ) {
    		if ( i != null ) {
    			System.out.println(i);
    		}
    	}
    }
    
    public boolean staffExiste(GestorStaff nuevo) {
    	for ( GestorStaff i : staff ) {
    		if ( i != null && i.getDatos().getDni().equals(nuevo.getDatos().getDni()) ) {
    			return true;
    		}
    	}
    	return false;
    }

    public boolean hacerReserva(Reserva reserva) {
    	boolean fueReservado = false;
    	Habitacion habitacionDeseada = getHabitacionPorNumero(reserva.getNumeroHabitacion());
    	if ( habitacionDeseada.getCapacidad() < reserva.getCantidad() ) {
    		return fueReservado;
    	}
    	if ( habitacionDeseada.getEstado() != HabitacionStatus.DISPONIBLE ) {
    		return fueReservado;
    	}
    	boolean conflictoFechas = false;
    	Reserva temp = gestorReserva.getReservaPorDni(reserva.getDni());	//si retorna una reserva vacía entonces el pasajero no tiene una reserva pendiente
    	if ( !temp.getDni().isBlank() ) {
    		return fueReservado;
    	} else {
    		/* EN ESTE PUNTO SOLO SE VALIDAN LAS RESERVAS DE LA HABITACIÓN CORRESPONDIENTE */
    		int numHabitacion = reserva.getNumeroHabitacion();
    		for ( Reserva i : gestorReserva.getReservas() ) {
    			if ( i.getNumeroHabitacion() != numHabitacion ) {	//si no es la misma, vuelve a iterar sin terminar el ciclo
    				continue;
    			}//si ambas fechas son anteriores o posteriores a alguna reserva, se seguirá evaluando, sino termina bucle
    			if ( !( reserva.getIngreso().compareTo(i.getIngreso()) < 0 && reserva.getEgreso().compareTo(i.getEgreso()) < 0 ) ||
    				!( reserva.getIngreso().compareTo(i.getIngreso()) > 0 && reserva.getEgreso().compareTo(i.getEgreso()) > 0 ) ) {
    				conflictoFechas = true;
    				break;
    			}
    		}
    	}
    	if ( !conflictoFechas ) {
    		fueReservado = true;
    		gestorReserva.hacerReserva(reserva);
    	}
        return fueReservado;
    }
    
    public void mostrarReservas() {
    	gestorReserva.printReservas();
    }
    
    public Reserva getReservaPorDni(String dni) {
    	return gestorReserva.getReservaPorDni(dni);
    }

    public void agregarHistorial(Historial nuevo, String dni) {
    	for ( Pasajero i : pasajeros ) {
    		if ( i != null && i.getDni().equals(dni) ) {
    			i.agregarHistorial(nuevo);
    		}
    	}
    }

    public boolean registrarPasajero(Pasajero nuevo) {
    	if ( !pasajeroExiste(nuevo) ) {
    		pasajeros.add(nuevo);
    		return true;
    	}
        return false;
    }
    
    public boolean pasajeroExiste(Pasajero p) {
    	for ( Pasajero i : pasajeros ) {
    		if ( i != null && i.getDni().equals(p.getDni()) ) {
    			return true;
    		}
    	}
    	return false;
    }

    public void registrarProducto(Producto nuevo) {
    	if ( !productoExiste(nuevo) ) {
    		productos.add(nuevo);
    	}
    }

    public void eliminarProducto(Producto p) {
    	for ( Producto i : productos ) {
    		if ( i != null && i.getNombre().equalsIgnoreCase(p.getNombre()) ) {
    			productos.remove(i);
    		}
    	}
    }

    public void cambiarProducto(Producto p) {
    	for ( Producto i : productos ) {
    		if ( i != null && i.getNombre().equalsIgnoreCase(p.getNombre()) ) {
    			i = p;
    			break;
    		}
    	}
    }
    
    public boolean productoExiste(Producto p) {
    	for ( Producto i : productos ) {
    		if ( i != null && i.getNombre().equalsIgnoreCase(p.getNombre()) ) {
    			return true;
    		}
    	}
    	return false;
    }

    /*
     * métodos para guardar y cargar json
     */

    //TODO puede que hayan métodos que no hagan falta en Hotel, hablando de los métodos de Administrador y Recepcionista.
    //TODO por ejemplo, cambiarStaff en Hotel. Desde el main el Administrador podría llamar a su propio método y Hotel lo único que hace es pasarle el arraylist de GestorStaff,
    //TODO cuando el Administrador termina de cambiar al staff, le manda el arraylist modificado a hotel.
}

package services;
import users.*;
import utilities.ReservaStatus;
import utilities.HabitacionStatus;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import com.fasterxml.jackson.core.type.TypeReference;   //usado para castear en ObjectMapper.readValue
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public boolean habitacionExiste(int num) {
    	return gestorHabitacion.habitacionExiste(num);
    }
    public void agregarConsumoEnHabitacion(int numero, double precio) {
        gestorHabitacion.agregarConsumo(numero, precio);
    }

    public void registrarStaff(GestorStaff staff) { //el nuevo staff viene del método crearStaff de Administrador
        if ( !staffExiste(staff.getDatos().getDni()) ) {
        	this.staff.add(staff);
        }
    }
    
    public GestorStaff obtenerStaff(String dni) {
    	GestorStaff staff = new GestorStaff();
    	for ( GestorStaff i : this.staff ) {
    		if ( i != null && i.getDatos().getDni().equals(dni) ) {
    			staff = i;
    			break;
    		}
    	}
    	return staff;
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
    				GestorStaff nuevoRecep = new GestorStaff(recep, "Recepcionista");
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
    
    public boolean staffExiste(String dni) {
    	for ( GestorStaff i : staff ) {
    		if ( i != null && i.getDatos().getDni().equals(dni) ) {
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
    
    public void mostrarReservas(ReservaStatus estado) {
    	gestorReserva.printReservas(estado);
    }
    
    public Reserva getReservaPorDni(String dni) {
    	return gestorReserva.getReservaPorDni(dni);
    }
    
    public Reserva extraerReserva(String dni) {
    	Reserva r = new Reserva();
    	if ( !dni.isBlank() ) {
    		r = gestorReserva.extraerPorDni(dni);
    	}
    	return r;
    }
    
    public boolean reservaExiste(String dni) {
    	return gestorReserva.reservaExiste(dni);
    }

    public void agregarHistorial(Historial nuevo, String dni) {
    	for ( Pasajero i : pasajeros ) {
    		if ( i != null && i.getDni().equals(dni) ) {
    			i.agregarHistorial(nuevo);
    		}
    	}
    }
    
    public void verHistorial(String dni) {
    	for ( Pasajero i : pasajeros ) {
    		if ( i != null && i.getDni().equals(dni) ) {
    			System.out.println(i);
    			i.printHistorial();
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
    
    public void mostrarPasajeros() {
    	for ( Pasajero i : pasajeros ) {
    		if ( i != null ) {
    			System.out.println(i);
    		}
    	}
    }
    
    public Pasajero obtenerPasajero(String dni) {
    	Pasajero pasajero = new Pasajero();
    	for ( Pasajero i : pasajeros ) {
    		if ( i != null && i.getDni().equals(dni) ) {
    			pasajero = i;
    			break;
    		}
    	}
    	return pasajero;
    }
    
    public boolean pasajeroExiste(String dni) {
    	for ( Pasajero i : pasajeros ) {
    		if ( i != null && i.getDni().equals(dni) ) {
    			return true;
    		}
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

    public void eliminarProducto(String nombre) {
    	for ( Producto i : productos ) {
    		if ( i != null && i.getNombre().equalsIgnoreCase(nombre) ) {
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
    
    public boolean productoExiste(String nombre) {
    	for ( Producto i : productos ) {
    		if ( i != null && i.getNombre().equalsIgnoreCase(nombre) ) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public void mostrarProductos() {
    	int subI = 0;
    	for ( Producto i : productos ) {
    		subI++;
    		System.out.println(subI + ". " + i);
    	}
    }
    
    public double getPrecioProducto(String nombre) {
    	double precio = 0.0;
    	for ( Producto i : productos ) {
    		if ( i != null && i.getNombre().equals(nombre) ) {
    			precio = i.getPrecio();
    			break;
    		}
    	}
    	return precio;
    }
    
    public boolean hayStaff() {
    	return !staff.isEmpty();
    }
    
    public void guardado() {
    	guardarPasajeros();
    	guardarStaff();
    	guardarProductos();
    	guardarHabitaciones();
    	guardarReservas();
    }
    
    public void guardarPasajeros() {
    	File file = new File("pasajeros.json");
    	ObjectMapper map = new ObjectMapper();
    	try {
    		map.writeValue(file, pasajeros);	//ArrayList
    	} catch ( IOException e ) {
    		System.out.println("Hubo un error en el guardado de pasajeros");
    	}
    }
    
    public void guardarStaff() {
    	File file = new File("staff.json");
    	ObjectMapper map = new ObjectMapper();
    	try {
    		map.writeValue(file, staff);	//ArrayList
    	} catch ( IOException e ) {
    		System.out.println("Hubo un error en el guardado de staff");
    	}
    }
    
    public void guardarProductos() {
    	File file = new File("productos.json");
    	ObjectMapper map = new ObjectMapper();
    	try {
    		map.writeValue(file, productos);	//ArrayList
    	} catch ( IOException e ) {
    		System.out.println("Hubo un error en el guardado de productos");
    	}
    }
    
    public void guardarHabitaciones() {
    	File file = new File("habitaciones.json");
    	ObjectMapper map = new ObjectMapper();
    	try {
    		map.writeValue(file, gestorHabitacion);	
    	} catch ( IOException e ) {
    		System.out.println("Hubo un error en el guardado de habitaciones");
    	}
    }
    
    public void guardarReservas() {
    	File file = new File("reservas.json");
    	ObjectMapper map = new ObjectMapper();
    	try {
    		map.writeValue(file, gestorReserva);
    	} catch ( IOException e ) {
    		System.out.println("Hubo un error en el guardado de reservas");
    	}
    }
    
    public void cargar() {
    	cargarPasajeros();
    	cargarStaff();
    	cargarProductos();
    	cargarHabitaciones();
    	cargarReservas();
    }
    
    public void cargarPasajeros() {
    	File file = new File("pasajeros.json");
    	ObjectMapper map = new ObjectMapper();
    	try {
    		pasajeros = map.readValue(file, new TypeReference<ArrayList<Pasajero>>(){});
    	} catch ( FileNotFoundException e ) {
    		System.out.println("El archivo de pasajeros no existe");
    	} catch (IOException e) {
    		System.out.println("Hubo un error en la lectura");
		}
    }
    
    public void cargarStaff() {
    	File file = new File("staff.json");
    	ObjectMapper map = new ObjectMapper();
    	try {
    		staff = map.readValue(file, new TypeReference<ArrayList<GestorStaff>>() {});
    	} catch ( FileNotFoundException e ) {
    		System.out.println("El archivo de staff no existe");
    	} catch (IOException e) {
    		System.out.println("Hubo un error en la lectura");
		}
    }
    
    public void cargarProductos() {
    	File file = new File("productos.json");
    	ObjectMapper map = new ObjectMapper();
    	try {
    		productos = map.readValue(file, new TypeReference<ArrayList<Producto>>() {});
    	} catch ( FileNotFoundException e ) {
    		System.out.println("El archivo de productos no existe");
    	} catch (IOException e) {
    		System.out.println("Hubo un error en la lectura");
		}
    }
    
    public void cargarHabitaciones() {
    	File file = new File("habitaciones.json");
    	ObjectMapper map = new ObjectMapper();
    	try {
    		gestorHabitacion = map.readValue(file, new TypeReference<GestorHabitacion>() {});
    	} catch ( FileNotFoundException e ) {
    		System.out.println("El archivo de habitaciones no existe");
    	} catch (IOException e) {
    		System.out.println("Hubo un error en la lectura");
		}
    }
    
    public void cargarReservas() {
    	File file = new File("reservas.json");
    	ObjectMapper map = new ObjectMapper();
    	try {
    		gestorReserva = map.readValue(file, new TypeReference<GestorReserva>() {});
    	} catch ( FileNotFoundException e ) {
    		System.out.println("El archivo de reservas no existe");
    	} catch (IOException e) {
    		System.out.println("Hubo un error en la lectura");
		}
    }
}

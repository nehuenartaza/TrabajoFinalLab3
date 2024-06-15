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
    
    public HabitacionStatus getEstadoHabitacion(int numero) {
    	return gestorHabitacion.getEstadoHabitacion(numero);
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
    			i.setDatos(staffModificado.getDatos());
    			return true;
    		}
    	}
        return false;
    }
    
    public boolean esAdministrador(String dni) {
    	for ( GestorStaff i : staff ) {
    		if ( i != null && i.getDatos().getDni().equals(dni) ) {
    			if ( i.getDatos() instanceof Administrador ) {
    				return true;
    			} else {
    				return false;	//es recepcionista
    			}
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
    			System.out.println("Se borró el staff con dni: " + dni);
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
    		System.out.println("Habitacion no disponible");
    		return fueReservado;
    	}
    	boolean conflictoFechas = false;
    	Reserva temp = gestorReserva.getReservaPorDni(reserva.getDni());	//si retorna una reserva vacía entonces el pasajero no tiene una reserva pendiente
    	if ( !temp.getDni().isBlank() ) {
    		System.out.println("Pasajero con dni: " + reserva.getDni() + " tiene reserva pendiente");
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
    				System.out.println("Conflicto de reservas, alguien reservó la habitación durante el período deseado");
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
    
    public void eliminarReservaPorDni(String dni) {
    	gestorReserva.eliminarReservaPorDni(dni);
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
    			break;
    		}
    	}
    }
    
    public void cambiarProducto(Producto p) {
    	for ( Producto i : productos ) {
    		if ( i != null && i.getNombre().equalsIgnoreCase(p.getNombre()) ) {
    			i.setNombre(p.getNombre());
    			i.setPrecio(p.getPrecio());
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
    	/*
    	File file2 = new File("historial.json");
    	ObjectMapper map2 = new ObjectMapper();
    	ArrayList<Historial> historiales = new ArrayList<Historial>();
    	try {
    		for ( Pasajero i : pasajeros ) {	//guardamos los historiales aparte porque hace conflicto al intentar leer todo junto en pasajeros.json
    			if ( i != null ) {
    				historiales.addAll(i.getHistorial());	//juntamos todos los historiales en una coleccion
    			}
    		}
    		map2.writeValue(file2, historiales);
    	} catch ( IOException e ) {
    		System.out.println("Hubo un error en el guardado de historiales de pasajeros");
    	}*/
    	
    	
    }
    
    public void guardarStaff() {
    	File file = new File("administradores.json");
    	ObjectMapper map = new ObjectMapper();
    	ArrayList<Administrador> admins = new ArrayList<Administrador>();
    	for ( GestorStaff i : staff ) {
    		if ( i != null && i.getDatos() instanceof Administrador ) {
    			admins.add((Administrador)i.getDatos());
    		}
    	}
    	try {
    		map.writeValue(file, admins);	//ArrayList
    	} catch ( IOException e ) {
    		System.out.println("Hubo un error en el guardado de admins");
    	}
    	
    	File file2 = new File("recepcionistas.json");
    	ObjectMapper map2 = new ObjectMapper();
    	ArrayList<Recepcionista> recepcionistas = new ArrayList<Recepcionista>();
    	for ( GestorStaff i : staff ) {
    		if ( i != null && i.getDatos() instanceof Recepcionista ) {
    			recepcionistas.add((Recepcionista)i.getDatos());
    		}
    	}
    	try {
    		map2.writeValue(file2, recepcionistas);	//ArrayList
    	} catch ( IOException e ) {
    		System.out.println("Hubo un error en el guardado de recepcionistas");
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
    		map.writeValue(file, gestorHabitacion.getHabitaciones());	
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
    		System.out.println("Hubo un error en la lectura de pasajeros");
		}
    	/* hay un error que no permite leer el archivo de pasajeros
    	 * se intentó solucionar guardando pasajeros e historiales de forma aparte
    	 * pero no resultó ser la solución
    	 * 
    	 */
    	
    	
    	/*
    	ArrayList<Historial> historiales = new ArrayList<Historial>();
    	File file2 = new File("historial.json");
    	ObjectMapper map2 = new ObjectMapper();
    	try {
    		historiales = map2.readValue(file2, new TypeReference<ArrayList<Historial>>(){});
    	} catch ( IOException e ) {
    		System.out.println("Hubo un error en la lectura de historiales de pasajeros");
    	}
    	
    	for ( Pasajero i : pasajeros ) {	//por cada pasajero se recorre los historiales buscando por dni
    		if ( i != null ) {
    			for ( Historial j : historiales ) {
    				if ( j != null && i.getDni().equals(j.getDniPasajero()) ) {
    					i.agregarHistorial(j);
    				}
    			}
    		}
    	}*/
    	
    	
    }
    
    public void cargarStaff() {
    	File file = new File("administradores.json");
    	ObjectMapper map = new ObjectMapper();
    	ArrayList<Administrador> admins = new ArrayList<Administrador>();
    	try {
    		admins = map.readValue(file, new TypeReference<ArrayList<Administrador>>() {});
    	} catch ( FileNotFoundException e ) {
    		System.out.println("El archivo de administradores no existe");
    	} catch (IOException e) {
    		System.out.println("Hubo un error en la lectura de administradores");
		}
    	for ( Administrador i : admins ) {
    		if ( i != null ) {
    			staff.add(new GestorStaff(i, "Administrador"));
    		}
    	}
    	
    	File file2 = new File("recepcionistas.json");
    	ObjectMapper map2 = new ObjectMapper();
    	ArrayList<Recepcionista> recepcionistas = new ArrayList<Recepcionista>();
    	try {
    		recepcionistas = map2.readValue(file2, new TypeReference<ArrayList<Recepcionista>>() {});
    	} catch ( FileNotFoundException e ) {
    		System.out.println("El archivo de recepcionistas no existe");
    	} catch (IOException e) {
    		System.out.println("Hubo un error en la lectura de recepcionistas");
		}
    	for ( Recepcionista i : recepcionistas ) {
    		if ( i != null ) {
    			staff.add(new GestorStaff(i, "Recepcionista"));
    		}
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
    		System.out.println("Hubo un error en la lectura de productos");
		}
    }
    
    public void cargarHabitaciones() {
    	File file = new File("habitaciones.json");
    	ObjectMapper map = new ObjectMapper();
    	try {
    		gestorHabitacion.setHabitaciones(map.readValue(file, new TypeReference<ArrayList<Habitacion>>() {}));
    	} catch ( FileNotFoundException e ) {
    		System.out.println("El archivo de habitaciones no existe");
    	} catch (IOException e) {
    		System.out.println("Hubo un error en la lectura de habitaciones");
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
    		System.out.println("Hubo un error en la lectura de reservas");
		}
    }
}

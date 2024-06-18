import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;
import users.*;
import utilities.*;
import services.*;

public class App {
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
    	Recepcionista recepcionista = new Recepcionista();
    	Pasajero pasajero = new Pasajero();
    	Administrador administrador = new Administrador();
    	Hotel hotel = new Hotel();
    	hotel.cargar();
    	String nombre = null, apellido = null, dni = null, localidad = null, domicilio = null, fechaIngreso = null, fechaEgreso = null, nombreProducto = null;
    	int opcion = 0, seleccionUsuario = 1, numHabitacion = 0, cantidadPersonas = 0, capacidad = 0;
    	double precio = 0.0;
    	boolean datosValidos = false, usuarioEncontrado = false, habitacionEncontrada = false, cantidadValida = false, reservaEncontrada = false;
    	/* VALIDACIÓN POR SI NO HAY ADMINISTRADORES */
    	if ( !hotel.hayStaff() ) {
    		System.out.println("Bienvenido, debido a la nula cantidad de administradores registrados, usted será el 1ro, se le pedirá sus datos");
    		do {
    			System.out.println("Ingrese su nombre");
    			nombre = scan.nextLine();
    			System.out.println("Ingrese su apellido");
    			apellido = scan.nextLine();
    			System.out.println("Ingrese su dni");
    			dni = scan.nextLine();
    			if ( contieneNumero(nombre) || contieneNumero(apellido) ) {
    				System.out.println("Parámetros incorrectos, nombre y apellido no pueden tener numeros");
    			} else {
    				if ( !hotel.pasajeroExiste(dni) ) {
    					datosValidos = true;
    				} else {
    					System.out.println("El dni pertenece a un pasajero registrado");
    				}
    			}
    		} while ( !datosValidos );
    		hotel.registrarStaff(new GestorStaff(new Administrador(nombre, apellido, dni), "Administrador"));
    		nombre = null;
    		apellido = null;
    		dni = null;
    	}
    	
    	System.out.println("Si quiere hacer una reserva remota como pasajero y aún no está registrado, ingrese 1 para registrarse, de lo contrario ingrese otro numero");
    	System.out.println("Una vez se haya registrado, se le pedirá ingresar el dni para iniciar sesion");
    	try {
    		seleccionUsuario = scan.nextInt();
    	} catch ( InputMismatchException e ) {
    		System.out.println("Dato no valido");
    	} finally {
    		scan.nextLine();
    	}
    	if ( seleccionUsuario == 1 ) {
    		do {
        		System.out.println("Por favor pasajero, ingrese su dni");
        		dni = scan.nextLine();
        		if ( !hotel.staffExiste(dni) && !hotel.pasajeroExiste(dni) ) {
        			datosValidos = true;
        		} else {
        			System.out.println("El dni ingresado ya existe, para intentar de nuevo ingrese un numero, para salir ingrese 0");
        			try {
        				seleccionUsuario = scan.nextInt();
        			} catch ( InputMismatchException e ) {
        				System.out.println("Dato no valido");
        			} finally {
        				scan.nextLine();
        			}
        		}
        	} while ( !datosValidos && seleccionUsuario != 0 );
        	if ( datosValidos ) {
        		datosValidos = false;
        		do {
        			System.out.println("Ingrese su nombre");
        			nombre = scan.nextLine();
        			System.out.println("Ingrese su apellido");
        			apellido = scan.nextLine();
        			if ( contieneNumero(nombre) || contieneNumero(apellido) ) {
        				System.out.println("Parámetros incorrectos, nombre y apellido no pueden tener numeros");
        			} else {
        				datosValidos = true;
        			}
            	} while ( !datosValidos );
        		System.out.println("Ingrese su domicilio");
        		domicilio = scan.nextLine();
        		System.out.println("Ingrese su localidad");
        		localidad = scan.nextLine();
        		hotel.registrarPasajero(new Pasajero(nombre, apellido, dni, localidad, domicilio));
        	}
        	
        	dni = null;
        	datosValidos = false;
        	nombre = null;
        	apellido = null;
        	domicilio = null;
        	localidad = null;
    	}
    	seleccionUsuario = 1;
    	/* SE PEDIRÁ EL DNI PARA BUSCARLO ENTRE STAFF Y PASAJEROS Y SEGÚN DÓNDE SE ENCUENTRE SERÁ LOS PERMISOS QUE TENDRÁ EL USUARIO */
    	do {
    		System.out.println("Para iniciar sesión, ingrese su dni");
    		dni = scan.nextLine();
    		if ( hotel.staffExiste(dni) ) {
    			if ( hotel.esAdministrador(dni) ) {
    				administrador = (Administrador) hotel.obtenerStaff(dni).getDatos();
    			} else {
    				recepcionista = (Recepcionista) hotel.obtenerStaff(dni).getDatos();
    			}
    			break;
    		}
    		if ( hotel.pasajeroExiste(dni) ) {
    			pasajero = hotel.obtenerPasajero(dni);
    			break;
    		}
    		System.out.println("No se encontró ningún usuario registrado con ese dni");
    		System.out.println("Para intentar de nuevo ingrese un numero, para cerrar el programa ingrese 0");
    		try {
    			seleccionUsuario = scan.nextInt();
    		} catch ( InputMismatchException e ) {
    			System.out.println("Valor incorrecto");
    		} finally {
    			scan.nextLine();
    		}
    	} while ( seleccionUsuario != 0 );
    	
    	if ( seleccionUsuario != 0 ) { //si no es 0 es porque el usuario pudo iniciar sesión
    		if ( !administrador.getDni().isBlank() ) {
    			bienvenida(administrador.getNombre());
    			do {
    				menuAdministrador();
    				try {
    					opcion = scan.nextInt();
    				} catch ( InputMismatchException e ) {
    					System.out.println("Opcion no valida");
    				} finally {
    					scan.nextLine();
    				}
    				switch ( opcion ) {
    				case 1:						/* CheckIn */				//funciona
    					hotel.mostrarReservas(ReservaStatus.ACTIVA);
    					System.out.println("Ingrese dni de la reserva a hacer checkin");
    					dni = scan.nextLine();
    					Reserva r1 = hotel.getReservaPorDni(dni);
    					if ( r1.getNumeroHabitacion() != 0 ) {
    						Habitacion h1 = hotel.getHabitacionPorNumero(r1.getNumeroHabitacion());
    						//el pasajero no puede entrar a la habitacion hasta que no esté disponible por mantenimiento
    						if ( r1.getEstado() == ReservaStatus.ACTIVA && 
    						h1.getEstado() == HabitacionStatus.DISPONIBLE ) {
    							administrador.checkin(h1, r1);
    						}
    					}
    					
    					dni = null;
                        break;
                    case 2:						/* CheckOut */				//funciona
                    	hotel.mostrarReservas(ReservaStatus.EN_PROCESO);
                    	System.out.println("Ingrese dni de la reserva a hacer checkout");
                    	dni = scan.nextLine();
                    	Reserva r2 = hotel.extraerReserva(dni);
                    	Habitacion h2 = hotel.getHabitacionPorNumero(r2.getNumeroHabitacion());
                    	if ( r2.getEstado() == ReservaStatus.EN_PROCESO && 
                    		h2.getEstado() == HabitacionStatus.OCUPADO ) {
                    		administrador.checkout(h2, r2);
                    		hotel.agregarHistorial(new Historial(r2.getIngreso(), r2.getEgreso(), h2), dni);
                    	}
                    	dni = null;
                        break;
                    case 3:						/* HACER RESERVA */			//funciona
                    	/* ALGORITMO DE INGRESO DE DNI */
                    	do {
                    		System.out.println("Ingrese dni del pasajero");
                    		dni = scan.nextLine();
                    		usuarioEncontrado = hotel.pasajeroExiste(dni);
                    		if ( !usuarioEncontrado ) {
                    			System.out.println("No se encontró un pasajero con ese dni. Para volver a intentar ingrese un numero, de lo contrario ingrese 0");
                    			try {
                    				seleccionUsuario = scan.nextInt();
                    			} catch ( InputMismatchException e ) {
                    				System.out.println("Valor incorrecto");
                    			} finally {
                    				scan.nextLine();
                    			}
                    		}
                    	} while ( !usuarioEncontrado && seleccionUsuario != 0 );
                    	/* SE VALIDA QUE NO HAYA CANCELADO LA RESERVA, LUEGO ALGORITMO PARA NUMERO DE HABITACION Y CANTIDAD DE PERSONAS */
                    	if ( seleccionUsuario != 0 ) {
                    		do {
                    			System.out.println("Ingrese numero de habitacion a reservar");
                    			try {
                    				numHabitacion = scan.nextInt();
                    				scan.nextLine();
                    				System.out.println("Ingrese la cantidad de personas que vienen con el pasajero");
                    				cantidadPersonas = scan.nextInt();
                    			} catch ( InputMismatchException e ) {
                    				System.out.println("Valor incorrecto");
                    			} finally {
                    				scan.nextLine();
                    			}
                    			habitacionEncontrada = hotel.habitacionExiste(numHabitacion);
                    			if ( habitacionEncontrada ) {
                    				if ( hotel.getHabitacionPorNumero(numHabitacion).getCapacidad() >= cantidadPersonas && cantidadPersonas > 0 ) {
                    					cantidadValida = true;
                    				} else {
                    					System.out.println("La habitacion no tiene capacidad suficiente para " + cantidadPersonas + " personas");
                    					habitacionEncontrada = false;
                    				}
                    			} else {
                    				System.out.println("La habitacion no se encontró");
                    			}
                    			if ( !habitacionEncontrada || !cantidadValida ) {
                    				System.out.println("Para volver a intentar ingrese un numero, de lo contrario ingrese 0");
                    				try {
                        				seleccionUsuario = scan.nextInt();
                        			} catch ( InputMismatchException e ) {
                        				System.out.println("Valor incorrecto");
                        			} finally {
                        				scan.nextLine();
                        			}
                    			}
                    		} while ( !habitacionEncontrada || !cantidadValida && seleccionUsuario != 0 );
                    	}
                    	/* ALGORITMO FECHA INGRESO-EGRESO */
                    	/* Validacion de conflictos entre fechas de ingreso y egreso en Hotel.hacerReserva() */
                    	if ( seleccionUsuario != 0 ) {
                    		System.out.println("Ingrese fecha de ingreso");
                    		fechaIngreso = seleccionarFecha("Ingreso");
                    		System.out.println("Ingrese fecha de egreso");
                    		fechaEgreso = seleccionarFecha("Egreso");
							if ( fechaIngreso.compareTo(fechaEgreso) < 0 ) {
								System.out.println("Se pudo hacer la reserva? " + hotel.hacerReserva(new Reserva(fechaIngreso, fechaEgreso, cantidadPersonas, dni, ReservaStatus.ACTIVA, numHabitacion)));
							} else {
								System.out.println("El ingreso no puede ser posterior al egreso");
							}
						}
                        seleccionUsuario = 1;
                        fechaIngreso = null;
                        fechaEgreso = null;
                        habitacionEncontrada = false;
                        cantidadValida = false;
                        cantidadPersonas = 0;
                        numHabitacion = 0;
                        dni = null;
                        break;
                    case 4:		/* ALGORITMO DE CANCELACION DE RESERVA */	//funciona
                    	System.out.println("Ingrese dni de la reserva a cancelar");
                    	dni = scan.nextLine();
                    	reservaEncontrada = hotel.reservaExiste(dni);
                    	if ( reservaEncontrada ) {
                    		Reserva aBorrar = hotel.getReservaPorDni(dni);
                    		Habitacion h4 = hotel.getHabitacionPorNumero(aBorrar.getNumeroHabitacion());
                    		
                    		/* los pasajeros solo pueden cancelar reservas si aun no ingresaron a la habitacion
                    		 * si quieren irse debe ser por medio de un checkout porque si el pasajero cancela la reserva
                    		 * mientras la habitacion está ocupada, es posible que otra persona sea la que esté ocupando la habitacion */
                    		if ( h4.getEstado() != HabitacionStatus.OCUPADO ) {		//si se cancela la reserva
                    			hotel.eliminarReservaPorDni(dni);
                    			administrador.cancelarReserva(aBorrar);													/* LE MANDA LA HABITACION */
                    			Historial paraAgregar = new Historial(aBorrar.getIngreso(), aBorrar.getEgreso(), hotel.getHabitacionPorNumero(aBorrar.getNumeroHabitacion()));
                    			hotel.agregarHistorial(paraAgregar, dni);
                    		} else {
                    			System.out.println("No se puede cancelar la reserva porque la habitacion esta ocupada");
                    		}
                    	}
                    	dni = null;
                    	reservaEncontrada = false;
                        break;
                    case 5:		/* VER ESTADO HABITACION */					//funciona
                    	System.out.println("Ingrese numero de habitacion para ver su estado");
                    	try {
                    		numHabitacion = scan.nextInt();
                    		hotel.estadoHabitacion(numHabitacion);
                    	} catch ( InputMismatchException e ) {
                    		System.out.println("Dato no valido");
                    	} finally {
                    		scan.nextLine();
                    	}
                    	numHabitacion = 0;
                    	break;
                    case 6:			/* REGISTRAR NUEVO PRODUCTO */			//funciona
                    	System.out.println("Ingrese el nombre del nuevo producto");
                    	nombreProducto = scan.nextLine();
                    	try {
                    		System.out.println("Ingrese el precio");
                    		precio = scan.nextDouble();
                    	} catch ( InputMismatchException e ) {
                    		System.out.println("Dato no valido");
                    	} finally {
                    		scan.nextLine();
                    	}
                    	
                    	if ( precio != 0.0 ) {
                    		hotel.registrarProducto(new Producto(nombreProducto, precio));
                    	}
                    	nombreProducto = null;
                    	precio = 0.0;
                    	break;
                    case 7:			/* CAMBIAR PRODUCTO */					//funciona
                    	hotel.mostrarProductos();
                    	System.out.println("Ingrese el nombre del producto a cambiar");
                    	nombreProducto = scan.nextLine();
                    	try {
                    		System.out.println("Ingrese el nuevo precio");
                    		precio = scan.nextDouble();
                    	} catch ( InputMismatchException e ) {
                    		System.out.println("Dato no valido");
                    	} finally {
                    		scan.nextLine();
                    	}
                    	if ( precio != 0.0 ) {
                    		hotel.cambiarProducto(new Producto(nombreProducto, precio));
                    	}
                    	nombreProducto = null;
                    	precio = 0.0;
                    	break;
                    case 8:			/* BORRAR PRODUCTO */					//funciona
                    	hotel.mostrarProductos();
                    	System.out.println("Ingrese el nombre del producto a borrar");
                    	nombre = scan.nextLine();
                    	hotel.eliminarProducto(nombre);
                    	nombre = null;
                    	break;
                    case 9:			/* CREAR STAFF */						//funciona					
                    	do {
                			System.out.println("Ingrese nombre del nuevo staff");
                			nombre = scan.nextLine();
                			System.out.println("Ingrese apellido del nuevo staff");
                			apellido = scan.nextLine();
                			System.out.println("Ingrese dni del nuevo staff");
                			dni = scan.nextLine();
                			if ( contieneNumero(nombre) || contieneNumero(apellido) ) {
                				System.out.println("Parámetros incorrectos, nombre y apellido no pueden tener numeros");
                			} else {
                				if ( !hotel.pasajeroExiste(dni) && !hotel.staffExiste(dni) ) {
                					datosValidos = true;
                				} else {
                					System.out.println("El dni pertenece a un pasajero o staff ya existente");
                				}
                			}
                			if ( !datosValidos ) {
                				System.out.println("Para intentar de nuevo ingrese un numero, para salir ingrese 0");
                				seleccionUsuario = scan.nextInt();
                				scan.nextLine();
                			}
                		} while ( !datosValidos && seleccionUsuario != 0 );
                    	if ( seleccionUsuario != 0 ) {
                    		hotel.registrarStaff(new GestorStaff(new Recepcionista(nombre, apellido, dni), "Recepcionista"));
                    		System.out.println("Se creó un nuevo recepcionista");
                    	}
                    	nombre = null;
                    	apellido = null;
                    	dni = null;
                    	datosValidos = false;
                    	seleccionUsuario = 1;
                    	break;
                    case 10:		/* REGISTRAR PASAJERO */				//funciona
                    	do {
                    		System.out.println("Ingrese el dni del pasajero a registrar");
                    		dni = scan.nextLine();
                    		if ( !hotel.staffExiste(dni) && !hotel.pasajeroExiste(dni) ) {
                    			datosValidos = true;
                    		} else {
                    			System.out.println("El dni ingresado ya existe, para intentar de nuevo ingrese un numero, para salir ingrese 0");
                    			try {
                    				seleccionUsuario = scan.nextInt();
                    			} catch ( InputMismatchException e ) {
                    				System.out.println("Dato no valido");
                    			} finally {
                    				scan.nextLine();
                    			}
                    		}
                    	} while ( !datosValidos && seleccionUsuario != 0 );
                    	if ( datosValidos ) {
                    		datosValidos = false;
                    		do {
                    			System.out.println("Ingrese nombre del pasajero");
	                			nombre = scan.nextLine();
	                			System.out.println("Ingrese apellido del pasajero");
	                			apellido = scan.nextLine();
	                			if ( contieneNumero(nombre) || contieneNumero(apellido) ) {
	                				System.out.println("Parámetros nombre y apellido no pueden contener numeros");
	                			} else {
	                				datosValidos = true;
	                			}
	                    	} while ( !datosValidos );
                    		System.out.println("Ingrese domicilio del pasajero");
                    		domicilio = scan.nextLine();
                    		System.out.println("Ingrese localidad del pasajero");
                    		localidad = scan.nextLine();
                    		hotel.registrarPasajero(new Pasajero(nombre, apellido, dni, localidad, domicilio));
                    	}
                    	dni = null;
                    	datosValidos = false;
                    	seleccionUsuario = 1;
                    	nombre = null;
                    	apellido = null;
                    	domicilio = null;
                    	localidad = null;
                    	break;
                    case 11:			/* CAMBIAR ESTADO HABITACION */		//funciona
                    	System.out.println("Ingrese numero de habitacion a cambiar de estado (no se podrá cambiar de estado si la habitacion está ocupada)");
                    	try {
                    		numHabitacion = scan.nextInt();
                    	} catch ( InputMismatchException e ) {
                    		System.out.println("Dato no valido");
                    	} finally {
                    		scan.nextLine();
                    	}
                    	habitacionEncontrada = hotel.habitacionExiste(numHabitacion);
                    	if ( habitacionEncontrada ) {
                    		Habitacion h11 = hotel.getHabitacionPorNumero(numHabitacion);
                    		if ( h11.getEstado() != HabitacionStatus.OCUPADO ) {
                    			System.out.println("1- poner habitacion en limpieza");
                    			System.out.println("2- poner habitacion en reparacion");
                    			System.out.println("3- poner habitacion en desinfeccion");
                    			System.out.println("4- poner habitacion disponible");
                    			try {
                    				seleccionUsuario = scan.nextInt();
                    			} catch ( InputMismatchException e ) {
                    				System.out.println("Dato no valido");
                    			} finally {
                    				scan.nextLine();
                    			}
                    			switch ( seleccionUsuario ) {
                    			case 1:
                    				administrador.cambiarEstadoHabitacion(h11, HabitacionStatus.EN_LIMPIEZA);
                    				break;
                    			case 2:
                    				administrador.cambiarEstadoHabitacion(h11, HabitacionStatus.EN_REPARACION);
                    				break;
                    			case 3:
                    				administrador.cambiarEstadoHabitacion(h11, HabitacionStatus.EN_DESINFECCION);
                    				break;
                    			case 4:
                    				administrador.cambiarEstadoHabitacion(h11, HabitacionStatus.DISPONIBLE);
                    				break;
                    			}
                    		} else {
                    			System.out.println("La habitacion está ocupada");
                    		}
                    	} else {
                    		System.out.println("No se encontró la habitación");
                    	}
                    	numHabitacion = 0;
                    	habitacionEncontrada = false;
                    	seleccionUsuario = 1;
                    	break;
                    case 12:			/* REGISTRAR HABITACION */			//funciona
                    	System.out.println("Ingrese numero para la nueva habitacion (numero no puede ser 0 o menor)");
                    	try {
                    		numHabitacion = scan.nextInt();
                    	} catch ( InputMismatchException e ) {
                    		System.out.println("Dato no valido");
                    	} finally {
                    		scan.nextLine();
                    	}
                    	/* numero de habitacion nunca puede ser 0 */
                    	if ( numHabitacion > 0 && !hotel.habitacionExiste(numHabitacion) ) {
                    		try {
                    			System.out.println("Ingrese capacidad maxima de personas para la habitacion");
                    			capacidad = scan.nextInt();
                    		} catch ( InputMismatchException e ) {
                    			System.out.println("Dato no valido");
                    		} finally {
                    			scan.nextLine();
                    		}
                    		if ( capacidad > 0 ) {
                    			hotel.registrarHabitacion(new Habitacion(numHabitacion, capacidad, 0.0, HabitacionStatus.DISPONIBLE));
                    		} else {
                    			System.out.println("Dato ingresado no es valido");
                    		}
                    	} else {
                    		System.out.println("Dato ingresado no valido o habitacion ya existe");
                    	}
                    	numHabitacion = 0;
                    	capacidad = 0;
                    	break;
                    case 13:			/* MOSTRAR HABITACIONES */			//funciona
                    	hotel.mostrarHabitaciones();
                    	break;
                    case 14:			/* MOSTRAR RESERVAS */				//funciona
                    	hotel.mostrarReservas();
                    	break;
                    case 15:			/* MOSTRAR PASAJEROS */				//funciona
                    	hotel.mostrarPasajeros();
                    	break;
                    case 16:			/* MOSTRAR PRODUCTOS */				//funciona
                    	hotel.mostrarProductos();
                    	break;
                    case 17:			/* VER HISTORIAL DE UN PASAJERO */	//funciona
                    	System.out.println("Ingrese dni del pasajero a ver su historial");
                    	dni = scan.nextLine();
                    	hotel.verHistorial(dni);
                    	dni = null;
                    	break;
                    case 18:			/* MOSTRAR STAFF */					//funciona
                    	administrador.mostrarStaff(hotel.getStaff());
                    	break;
                    case 19:			/* MODIFICAR STAFF */				//funciona
                    	System.out.println("Ingrese dni del staff a modificar");
                    	dni = scan.nextLine();
                    	if ( hotel.staffExiste(dni) ) {
                    		GestorStaff aCambiar = hotel.obtenerStaff(dni);
                    		Persona datosACambiar = aCambiar.getDatos();
                    		do {												//en caso de que el staff se cambie legalmente el nombre o apellido
                    			System.out.println("Ingrese nuevo nombre del staff");
	                			nombre = scan.nextLine();
	                			System.out.println("Ingrese nuevo apellido del staff");
	                			apellido = scan.nextLine();
	                			if ( contieneNumero(nombre) || contieneNumero(apellido) ) {
	                				System.out.println("Parámetros incorrectos, nombre y apellido no pueden contener numeros");
	                			} else {
	                				datosValidos = true;
	                			}
                    		} while ( !datosValidos );
                    		datosACambiar.setNombre(nombre);
                    		datosACambiar.setApellido(apellido);
                    	} else {
                    		System.out.println("No se encontró el staff buscado");
                    	}
                    	dni = null;
                    	nombre = null;
                    	apellido = null;
                    	break;
                    case 20:					/* BORRAR STAFF */			//funciona
                    	System.out.println("Ingrese dni del staff a borrar");
                    	dni = scan.nextLine();
                    	if ( !dni.equals(administrador.getDni()) ) {	//valida que no se borre a si mismo
                    		usuarioEncontrado = hotel.staffExiste(dni);
	                    	if ( usuarioEncontrado ) {
	                    		administrador.borrarStaff(dni, hotel.getStaff());
	                    	} else {
	                    		System.out.println("No se encontró el staff buscado");
	                    	}
                    	} else {
                    		System.out.println("Por seguridad no se permite que un administrador se borre a si mismo");
                    	}
                    	dni = null;
                    	break;
                    case 21:			/* PEDIR CONSUMO A PASAJERO */		//funciona
                    	System.out.println("Ingrese dni del pasajero que pidió un consumo");
                    	dni = scan.nextLine();
                    	Reserva temp = hotel.getReservaPorDni(dni);
                    	if ( temp.getEstado() == ReservaStatus.EN_PROCESO ) {	//valida que el pasajero que pidió un consumo esté en la habitacion
                    		if ( temp.getNumeroHabitacion() != 0 ) {
	                    		System.out.println("Ingrese el nombre del producto solicitado");
	                    		nombreProducto = scan.nextLine();
	                    		if ( hotel.productoExiste(nombreProducto) ) {
	                    			precio = hotel.getPrecioProducto(nombreProducto);
	                    			hotel.agregarConsumoEnHabitacion(temp.getNumeroHabitacion(), precio);
	                    		} else {
	                    			System.out.println("El producto solicitado no existe");
	                    		}
	                    	} else {
	                    		System.out.println("No existe una reserva");
	                    	}
                    	} else {
                    		System.out.println("No se puede concretar el consumo porque el pasajero no puede consumir un producto sin antes haber ocupado la habitacion");
                    	}
                    	dni = null;
                    	nombreProducto = null;
                    	precio = 0.0;
                    	break;
                    case 22:			/* SUBIR RANGO A STAFF */			//funciona
                    	System.out.println("Ingrese dni del staff a subir de rango (recepcionista a administrador)");
                    	dni = scan.nextLine();
                    	if ( !dni.equals(administrador.getDni()) ) {
                    		if ( hotel.aumentarRangoARecepcionista(dni) ) {
                    			System.out.println("El staff ahora es administrador");
                    		} else {
                    			System.out.println("No se encontró el staff con dni: " + dni);
                    		}
                    	} else {
                    		System.out.println("No está permitido modificarse el rango a si mismo");
                    	}
                    	dni = null;
                    	break;
                    case 23:			/* BAjAR RANGO A STAFF */			//funciona
                    	System.out.println("Ingrese dni del staff a bajar de rango (administrador a recepcionista)");
                    	dni = scan.nextLine();
                    	if ( !dni.equals(administrador.getDni()) ) {
                    		if ( hotel.bajarRangoAAdministrador(dni) ) {
                    			System.out.println("El staff ahora es recepcionista");
                    		} else {
                    			System.out.println("No se encontró el staff con dni: " + dni);
                    		}
                    	} else {
                    		System.out.println("No está permitido modificarse el rango a si mismo");
                    	}
                    	dni = null;
                    	break;
                    case 24:			/* BORRAR HABITACION */
                    	hotel.mostrarHabitaciones();
                    	System.out.println("Ingrese numero de habitacion a borrar");
                    	try {
                    		numHabitacion = scan.nextInt();
                    	} catch ( InputMismatchException e ) {
                    		System.out.println("No se permiten letras, solo numeros");
                    	} finally {
                    		scan.nextLine();
                    	}
                    	habitacionEncontrada = hotel.habitacionExiste(numHabitacion);
                    	if ( habitacionEncontrada ) {
                    		if ( hotel.getEstadoHabitacion(numHabitacion) != HabitacionStatus.OCUPADO ) {
                    			hotel.eliminarHabitacion(numHabitacion);
                    			hotel.eliminarReservasPorNumeroHabitacion(numHabitacion);
                    		} else {
                    			System.out.println("No se puede borrar la habitacion porque está ocupada");
                    		}
                    	} else {
                    		System.out.println("La habitacion no se encontró");
                    	}
                    	
                    	break;
    				}
    				
    			} while ( opcion != 0 );
    		} else if ( !recepcionista.getDni().isBlank() ) {
    			bienvenida(recepcionista.getNombre());
    			do {
    				menuRecepcionista();
    				try {
    					opcion = scan.nextInt();
    				} catch ( InputMismatchException e ) {
    					System.out.println("Opcion no valida");
    				} finally {
    					scan.nextLine();
    				}
    				switch ( opcion ) {
    				case 1:				/* Check In */
    					hotel.mostrarReservas(ReservaStatus.ACTIVA);
    					System.out.println("Ingrese dni de la reserva a hacer checkin");
    					dni = scan.nextLine();
    					Reserva res1 = hotel.getReservaPorDni(dni);
    					if ( res1.getNumeroHabitacion() != 0 ) {
    						Habitacion hab1 = hotel.getHabitacionPorNumero(res1.getNumeroHabitacion());
    						
    						if ( res1.getEstado() == ReservaStatus.ACTIVA && 
    						hab1.getEstado() == HabitacionStatus.DISPONIBLE ) {
    							recepcionista.checkin(hab1, res1);
    						}
    					}
    					dni = null;
    					break;
					case 2:				/* Check Out */
						hotel.mostrarReservas(ReservaStatus.EN_PROCESO);
                    	System.out.println("Ingrese dni de la reserva a hacer checkout");
                    	dni = scan.nextLine();
                    	Reserva res2 = hotel.extraerReserva(dni);
                    	Habitacion hab2 = hotel.getHabitacionPorNumero(res2.getNumeroHabitacion());
                    	if ( res2.getEstado() == ReservaStatus.EN_PROCESO && 
                    		hab2.getEstado() == HabitacionStatus.OCUPADO ) {
                    		recepcionista.checkout(hab2, res2);
                    		hotel.agregarHistorial(new Historial(res2.getIngreso(), res2.getEgreso(), hab2), dni);
                    	}
                    	dni = null;
					    break;
					case 3:				/* HACER RESERVA */
                    	do {			/* ALGORITMO DE INGRESO DE DNI */
                    		System.out.println("Ingrese dni del pasajero");
                    		dni = scan.nextLine();
                    		usuarioEncontrado = hotel.pasajeroExiste(dni);
                    		if ( !usuarioEncontrado ) {
                    			System.out.println("No se encontró un pasajero con ese dni. Para volver a intentar ingrese un numero, de lo contrario ingrese 0");
                    			try {
                    				seleccionUsuario = scan.nextInt();
                    			} catch ( InputMismatchException e ) {
                    				System.out.println("Valor incorrecto");
                    			} finally {
                    				scan.nextLine();
                    			}
                    		}
                    	} while ( !usuarioEncontrado && seleccionUsuario != 0 );
                    	/* SE VALIDA QUE NO HAYA CANCELADO LA RESERVA, LUEGO ALGORITMO PARA NUMERO DE HABITACION Y CANTIDAD DE PERSONAS */
                    	if ( seleccionUsuario != 0 ) {
                    		do {
                    			System.out.println("Ingrese numero de habitacion a reservar");
                    			try {
                    				numHabitacion = scan.nextInt();
                    				scan.nextLine();
                    				System.out.println("Ingrese la cantidad de personas que vienen con el pasajero");
                    				cantidadPersonas = scan.nextInt();
                    			} catch ( InputMismatchException e ) {
                    				System.out.println("Valor incorrecto");
                    			} finally {
                    				scan.nextLine();
                    			}
                    			habitacionEncontrada = hotel.habitacionExiste(numHabitacion);
                    			if ( habitacionEncontrada ) {
                    				if ( hotel.getHabitacionPorNumero(numHabitacion).getCapacidad() >= cantidadPersonas && cantidadPersonas > 0 ) {
                    					cantidadValida = true;
                    				} else {
                    					System.out.println("La habitacion no tiene capacidad suficiente para " + cantidadPersonas + " personas");
                    					habitacionEncontrada = false;
                    				}
                    			} else {
                    				System.out.println("La habitacion no se encontró");
                    			}
                    			if ( !habitacionEncontrada || !cantidadValida ) {
                    				System.out.println("Para volver a intentar ingrese un numero, de lo contrario ingrese 0");
                    				try {
                        				seleccionUsuario = scan.nextInt();
                        			} catch ( InputMismatchException e ) {
                        				System.out.println("Valor incorrecto");
                        			} finally {
                        				scan.nextLine();
                        			}
                    			}
                    		} while ( !habitacionEncontrada || !cantidadValida && seleccionUsuario != 0 );
                    	}
                    	/* ALGORITMO FECHA INGRESO-EGRESO */
                    	/* Validacion de conflictos entre fechas de ingreso y egreso en Hotel.hacerReserva() */
                    	if ( seleccionUsuario != 0 ) {
                    		System.out.println("Ingrese fecha de ingreso");
                    		fechaIngreso = seleccionarFecha("Ingreso");
                    		System.out.println("Ingrese fecha de egreso");
                    		fechaEgreso = seleccionarFecha("Egreso");
							if ( fechaIngreso.compareTo(fechaEgreso) < 0 ) {
								System.out.println("Se pudo hacer la reserva? " + hotel.hacerReserva(new Reserva(fechaIngreso, fechaEgreso, cantidadPersonas, dni, ReservaStatus.ACTIVA, numHabitacion)));
							} else {
								System.out.println("El ingreso no puede ser posterior al egreso");
							}
						}
                        seleccionUsuario = 1;
                        fechaIngreso = null;
                        fechaEgreso = null;
                        habitacionEncontrada = false;
                        cantidadValida = false;
                        cantidadPersonas = 0;
                        numHabitacion = 0;
                        dni = null;
						break;
					case 4:				/* CANCELAR RESERVA */
						System.out.println("Ingrese dni de la reserva a cancelar");
                    	dni = scan.nextLine();
                    	reservaEncontrada = hotel.reservaExiste(dni);
                    	if ( reservaEncontrada ) {
                    		Reserva aBorrar = hotel.getReservaPorDni(dni);
                    		Habitacion h4 = hotel.getHabitacionPorNumero(aBorrar.getNumeroHabitacion());
                    		if ( h4.getEstado() != HabitacionStatus.OCUPADO ) {		//si se cancela la reserva
                    			hotel.eliminarReservaPorDni(dni);
                    			recepcionista.cancelarReserva(aBorrar);													/* LE MANDA LA HABITACION */
                    			Historial paraAgregar = new Historial(aBorrar.getIngreso(), aBorrar.getEgreso(), hotel.getHabitacionPorNumero(aBorrar.getNumeroHabitacion()));
                    			hotel.agregarHistorial(paraAgregar, dni);
                    		} else {
                    			System.out.println("No se puede cancelar la reserva porque la habitacion esta ocupada");
                    		}
                    	}
                    	dni = null;
                    	reservaEncontrada = false;
						break;
					case 5:				/* VER ESTADO HABITACION */
						System.out.println("Ingrese numero de habitacion para ver su estado");
                    	try {
                    		numHabitacion = scan.nextInt();
                    		hotel.estadoHabitacion(numHabitacion);
                    	} catch ( InputMismatchException e ) {
                    		System.out.println("Dato no valido");
                    	} finally {
                    		scan.nextLine();
                    	}
                    	numHabitacion = 0;
						break;
					case 6:				/* MOSTRAR HABITACIONES */
						hotel.mostrarHabitaciones();
						break;
					case 7:				/* MOSTRAR RESERVAS */
						hotel.mostrarReservas();
						break;
					case 8:				/* REGISTRAR PASAJERO */
						do {
                    		System.out.println("Ingrese el dni del pasajero a registrar");
                    		dni = scan.nextLine();
                    		if ( !hotel.staffExiste(dni) && !hotel.pasajeroExiste(dni) ) {
                    			datosValidos = true;
                    		} else {
                    			System.out.println("El dni ingresado ya existe, para intentar de nuevo ingrese un numero, para salir ingrese 0");
                    			try {
                    				seleccionUsuario = scan.nextInt();
                    			} catch ( InputMismatchException e ) {
                    				System.out.println("Dato no valido");
                    			} finally {
                    				scan.nextLine();
                    			}
                    		}
                    	} while ( !datosValidos && seleccionUsuario != 0 );
                    	if ( datosValidos ) {
                    		datosValidos = false;
                    		do {
                    			System.out.println("Ingrese nombre del pasajero");
	                			nombre = scan.nextLine();
	                			System.out.println("Ingrese apellido del pasajero");
	                			apellido = scan.nextLine();
	                			if ( contieneNumero(nombre) || contieneNumero(apellido) ) {
	                				System.out.println("Parámetros nombre y apellido no pueden contener numeros");
	                			} else {
	                				datosValidos = true;
	                			}
	                    	} while ( !datosValidos );
                    		System.out.println("Ingrese domicilio del pasajero");
                    		domicilio = scan.nextLine();
                    		System.out.println("Ingrese localidad del pasajero");
                    		localidad = scan.nextLine();
                    		hotel.registrarPasajero(new Pasajero(nombre, apellido, dni, localidad, domicilio));
                    	}
                    	dni = null;
                    	datosValidos = false;
                    	seleccionUsuario = 1;
                    	nombre = null;
                    	apellido = null;
                    	domicilio = null;
                    	localidad = null;
						break;
					case 9:				/* MOSTRAR PASAJEROS */
						hotel.mostrarPasajeros();
						break;
    				}
    			} while ( opcion != 0 );
    		} else if ( !pasajero.getDni().isBlank() ) {
    			bienvenida(pasajero.getNombre());
    			do {
    				menuPasajero();
    				try {
    					opcion = scan.nextInt();
    				} catch ( InputMismatchException e ) {
    					System.out.println("opcion no valida");
    				} finally {
    					scan.nextLine();
    				}
    				switch ( opcion ) {
    				case 1:				/* CREAR RESERVA */
    					if ( hotel.reservaExiste(pasajero.getDni()) ) {	// no se hará reserva si ya hay una reserva pendiente
    						System.out.println("Ya tiene una reserva pendiente");
    						break;
    					}
    					do {
    						hotel.mostrarHabitaciones();
                			System.out.println("Ingrese numero de habitacion a reservar");
                			try {
                				numHabitacion = scan.nextInt();
                				scan.nextLine();
                				System.out.println("Ingrese la cantidad de personas que vendrán con ustéd");
                				cantidadPersonas = scan.nextInt();
                			} catch ( InputMismatchException e ) {
                				System.out.println("Dato no valido");
                			} finally {
                				scan.nextLine();
                			}
                			habitacionEncontrada = hotel.habitacionExiste(numHabitacion);
                			if ( habitacionEncontrada ) {
                				if ( hotel.getHabitacionPorNumero(numHabitacion).getCapacidad() >= cantidadPersonas && cantidadPersonas > 0 ) {
                					cantidadValida = true;
                				} else {
                					System.out.println("La habitacion no tiene capacidad suficiente para " + cantidadPersonas + " personas");
                					habitacionEncontrada = false;
                				}
                			} else {
                				System.out.println("La habitacion no se encontró");
                			}
                			if ( !habitacionEncontrada || !cantidadValida ) {
                				System.out.println("Para volver a intentar ingrese un numero, de lo contrario ingrese 0");
                				try {
                    				seleccionUsuario = scan.nextInt();
                    			} catch ( InputMismatchException e ) {
                    				System.out.println("Valor incorrecto");
                    			} finally {
                    				scan.nextLine();
                    			}
                			}
                		} while ( ( !habitacionEncontrada || !cantidadValida ) && seleccionUsuario != 0 );
	                	
	                	/* ALGORITMO FECHA INGRESO-EGRESO */
	                	if ( seleccionUsuario != 0 ) {
	                		System.out.println("Ingrese fecha de ingreso");
	                		fechaIngreso = seleccionarFecha("Ingreso");
	                		System.out.println("Ingrese fecha de egreso (si la fecha de egreso es anterior a la de ingreso, se invertirán los valores)");
	                		fechaEgreso = seleccionarFecha("Egreso");
	                		if ( fechaIngreso.compareTo(fechaEgreso) >= 0 ) {		/* Validacion de conflictos entre fechas de ingreso y egreso en Hotel.hacerReserva() */
	                			System.out.println("Se pudo hacer la reserva? " + hotel.hacerReserva(new Reserva(fechaIngreso, fechaEgreso, cantidadPersonas, pasajero.getDni(), ReservaStatus.ACTIVA, numHabitacion)));
	                		} else {
	                			System.out.println("Se pudo hacer la reserva? " + hotel.hacerReserva(new Reserva(fechaEgreso, fechaIngreso , cantidadPersonas, pasajero.getDni(), ReservaStatus.ACTIVA, numHabitacion)));
	                		}
	                	}
	                    seleccionUsuario = 1;
	                    fechaIngreso = null;
	                    fechaEgreso = null;
	                    habitacionEncontrada = false;
	                    cantidadValida = false;
	                    cantidadPersonas = 0;
	                    numHabitacion = 0;
	                    break;
    				case 2:
    					dni = pasajero.getDni();
    					reservaEncontrada = hotel.reservaExiste(dni);
                    	if ( reservaEncontrada ) {
                    		Reserva aBorrar = hotel.extraerReserva(dni);
                    		aBorrar.setEstado(ReservaStatus.CANCELADA);									/* LE MANDA LA HABITACION */
                    		Historial paraAgregar = new Historial(aBorrar.getIngreso(), aBorrar.getEgreso(), hotel.getHabitacionPorNumero(aBorrar.getNumeroHabitacion()));
                    		hotel.agregarHistorial(paraAgregar, dni);
                    	} else {
                    		System.out.println("No tiene una reserva pendiente para cancelar");
                    	}
                    	dni = null;
                    	reservaEncontrada = false;
    					break;
    				case 3:
                    	Reserva temp = hotel.getReservaPorDni(pasajero.getDni());
                    	if ( temp.getEstado() == ReservaStatus.EN_PROCESO ) {	//valida que el pasajero que pidió un consumo esté en la habitacion
                    		if ( temp.getNumeroHabitacion() != 0 ) {
	                    		System.out.println("Ingrese el nombre que desea");
	                    		hotel.mostrarProductos();
	                    		nombreProducto = scan.nextLine();
	                    		if ( hotel.productoExiste(nombreProducto) ) {
	                    			precio = hotel.getPrecioProducto(nombreProducto);
	                    			hotel.agregarConsumoEnHabitacion(temp.getNumeroHabitacion(), precio);
	                    		} else {
	                    			System.out.println("El producto solicitado no existe");
	                    		}
	                    	} else {
	                    		System.out.println("No existe una reserva");
	                    	}
                    	} else {
                    		System.out.println("No se puede concretar el consumo porque el pasajero no puede consumir un producto sin antes haber ocupado la habitacion");
                    	}
                    	nombreProducto = null;
                    	precio = 0.0;
    					break;
    				case 4:
    					hotel.verHistorial(pasajero.getDni());
    					break;
    				}
    			} while ( opcion != 0 );
    		}
    	}	//fin del if de seleccionUsuario
    	System.out.println("Cerrando programa");
    	hotel.guardado();	//backup completo
    }
    
    public static void bienvenida(String nombre) {
    	System.out.println("Bienvenido: " + nombre);
    }
    
    public static boolean contieneNumero(String str) {
    	for ( int i = 0; i < str.length(); i++ ) {
    		if ( Character.isDigit(str.charAt(i)) ) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public static String seleccionarFecha(String accion) {
    	boolean opcionValida = false;
    	int año = 0, mes = 0, dia = 0;
    	LocalDate dt = LocalDate.now();
    	int añoLocal = dt.getYear(), mesLocal = dt.getMonthValue(), diaLocal = dt.getDayOfMonth();
    	do {
    		System.out.println("Ingrese año de " + accion);
    		try {
    			año = scan.nextInt();
    		} catch ( InputMismatchException e ) {
    			System.out.println("Dato no valido");
    		} finally {
    			scan.nextLine();
    		}
    		if ( año >= añoLocal ) {
    			opcionValida = true;
    		} else {
    			System.out.println("Año no puede ser menor al actual");
    		}
    	} while ( !opcionValida );
    	opcionValida = false;
    	do {
    		System.out.println("Ingrese mes de " + accion);
    		try {
    			mes = scan.nextInt();
    		} catch ( InputMismatchException e ) {
    			System.out.println("Dato no valido");
    		} finally {
    			scan.nextLine();
    		}
    		if ( mes < 13 && mes > 0 ) {
    			if ( año == añoLocal ) {	//si el año que eligió es igual al actual, se valida que el mes seleccionado aun no haya pasado
    				if ( mes >= mesLocal ) {
    					opcionValida = true;
    				} else {
    					System.out.println("Mes no puede ser menor al actual");
    				}
    			}
    		} else {
    			System.out.println("Dato fuera de rango");
    		}
    	} while ( !opcionValida );
    	opcionValida = false;
    	do {
    		System.out.println("Ingrese dia de " + accion);
    		try {
    			dia = scan.nextInt();
    		} catch ( InputMismatchException e ) {
    			System.out.println("Dato no valido");
    		} finally {
    			scan.nextLine();
    		}
    		if ( dia > 0 && dia <= Mes.getDiasDeMes(mes) ) {
    			if ( mes == mesLocal ) {	//si el mes que eligió es igual al actual, se valida que el dia seleccionado aun no haya pasado
    				if ( dia >= diaLocal ) {
    					opcionValida = true;
    				} else {
    					System.out.println("Dia no puede ser menor al actual");
    				}
    			} else {
    				opcionValida = true;
    			}
    		} else {
    			System.out.println("Dato fuera de rango");
    		}
    	} while ( !opcionValida );
		String fecha;
		if ( dia <= 9 ) {
			fecha = "0" + Integer.toString(dia);
		} else {
			fecha = Integer.toString(dia);
		}
		if ( mes <= 9) {
			fecha = fecha + "-0" + Integer.toString(mes);
		} else {
			fecha = fecha + "-" + Integer.toString(mes);
		}
		fecha = fecha + "-" + Integer.toString(año);
    	return fecha;
    }

    public static void menuPasajero() {
        System.out.println("╔═════════════════════════╗");
        System.out.println("║  Bienvenido al sistema  ║");
        System.out.println("║   de gestión de Hotel   ║");
        System.out.println("╠═════════════════════════╣");
        System.out.println("║ 1. Hacer reserva        ║");
        System.out.println("║ 2. Cancelar reserva     ║");
        System.out.println("║ 3. Pedir consumo        ║");
        System.out.println("║ 4. Mostrar mi historial ║");
        System.out.println("║ 0. Salir                ║");
        System.out.println("╚═════════════════════════╝");
        System.out.println("Seleccione una opción: ");
    }

    public static void menuRecepcionista() {
        System.out.println("╔════════════════════════════════╗");
        System.out.println("║      Bienvenido al sistema     ║");
        System.out.println("║     de gestión de Recepción    ║");
        System.out.println("╠════════════════════════════════╣");
        System.out.println("║ 1. Check In de pasajero        ║");
        System.out.println("║ 2. Check Out de pasajero       ║");
        System.out.println("║ 3. Hacer reserva               ║");
        System.out.println("║ 4. Cancelar reserva            ║");
        System.out.println("║ 5. Ver estado de la habitación ║");
        System.out.println("║ 6. Mostrar habitaciones        ║");
        System.out.println("║ 7. Mostrar reservas            ║");
        System.out.println("║ 8. Registrar pasajero          ║");
        System.out.println("║ 9. Mostrar pasajeros           ║");
        System.out.println("║ 0. Salir                       ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.println("Seleccione una opción: ");
    }

    public static void menuAdministrador() {
        System.out.println("╔═════════════════════════════════╗");
        System.out.println("║    Bienvenido al sistema de     ║");
        System.out.println("║    gestión de Administración    ║");
        System.out.println("╠═════════════════════════════════╣");
        System.out.println("║ 1. Check In de pasajero         ║");
        System.out.println("║ 2. Check Out de pasajero        ║");
        System.out.println("║ 3. Hacer reserva                ║");
        System.out.println("║ 4. Cancelar reserva             ║");
        System.out.println("║ 5. Ver estado de la habitación  ║");
        System.out.println("║ 6. Registrar nuevo producto     ║");
        System.out.println("║ 7. Cambiar producto             ║");
        System.out.println("║ 8. Borrar producto              ║");
        System.out.println("║ 9. Crear Staff                  ║");
        System.out.println("║ 10. Registrar Pasajero          ║");
        System.out.println("║ 11. Cambiar estado habitación   ║");
        System.out.println("║ 12. Registrar habitación        ║");
        System.out.println("║ 13. Mostrar habitaciones        ║");
        System.out.println("║ 14. Mostrar reservas            ║");
        System.out.println("║ 15. Mostrar pasajeros           ║");
        System.out.println("║ 16. Mostrar productos           ║");
        System.out.println("║ 17. Ver historial de pasajero   ║");
        System.out.println("║ 18. Mostrar staff               ║");
        System.out.println("║ 19. Modificar staff             ║");
        System.out.println("║ 20. Borrar staff                ║");
        System.out.println("║ 21. Pedir consumo para pasajero ║");
        System.out.println("║ 22. Subir rango a staff         ║");
        System.out.println("║ 23. Bajar rango a staff         ║");
        System.out.println("║ 24. Borrar habitacion           ║");
        System.out.println("║ 0. Salir                        ║");
        System.out.println("╚═════════════════════════════════╝");
        System.out.println("Seleccione una opción: ");
    }
}
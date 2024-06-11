import java.util.Scanner;

public class App {
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        menuPasajero();
        menuRecepcionista();
        menuAdministrador();

    }

    public static void menuPasajero() {
        int opcion;
        do {
            System.out.println("╔═════════════════════════════════════════╗");
            System.out.println("║        Bienvenido al sistema de         ║");
            System.out.println("║                  gestión de             ║");
            System.out.println("║                  Hotel                  ║");
            System.out.println("╠═════════════════════════════════════════╣");
            System.out.println("║ 1. Hacer reserva                        ║");
            System.out.println("║ 2. Cancelar reserva                     ║");
            System.out.println("║ 3. Pedir consumo                        ║");
            System.out.println("║ 4. Mostrar historial                    ║");
            System.out.println("║ 5. Salir                                ║");
            System.out.println("╚═════════════════════════════════════════╝");
            System.out.print("Seleccione una opción: ");
            opcion = scan.nextInt();

            switch (opcion) {
                case 1:
                    hacerReserva("Pasajero");
                    break;
                case 2:
                    cancelarReserva("Pasajero");
                    break;
                case 3:
                    pedirConsumoPasajero();
                    break;
                case 4:
                    mostrarHistorialPasajero();
                    break;
                case 5:
                    System.out.println("Gracias por utilizar nuestro sistema. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        } while (opcion != 5);
    }



    public static void pedirConsumoPasajero() {
        System.out.println("─────────────────────────────────────────────");
        System.out.println("             PEDIR CONSUMO                   ");
        System.out.println("─────────────────────────────────────────────");
        // Lógica para pedir consumo
    }

    public static void mostrarHistorialPasajero() {
        System.out.println("─────────────────────────────────────────────");
        System.out.println("           MOSTRAR HISTORIAL                 ");
        System.out.println("─────────────────────────────────────────────");
        // Lógica para mostrar historial
    }


    public static void menuRecepcionista() {

        int opcion;

        do {
            System.out.println("╔═════════════════════════════════════════╗");
            System.out.println("║   Bienvenido al sistema de gestión de   ║");
            System.out.println("║               Recepción                 ║");
            System.out.println("╠═════════════════════════════════════════╣");
            System.out.println("║ 1. Check In                             ║");
            System.out.println("║ 2. Check Out                            ║");
            System.out.println("║ 3. Hacer reserva                        ║");
            System.out.println("║ 4. Cancelar reserva                     ║");
            System.out.println("║ 5. Ver estado de la habitación          ║");
            System.out.println("║ 6. Agregar historial de pasajero        ║");
            System.out.println("║ 7. Salir                                ║");
            System.out.println("╚═════════════════════════════════════════╝");
            System.out.print("Seleccione una opción: ");
            opcion = scan.nextInt();

            switch (opcion) {
                case 1:
                    checkIn("Recepcionista");
                    break;
                case 2:
                    checkOut("Recepcionista");
                    break;
                case 3:
                    hacerReserva("Recepcionista");
                    break;
                case 4:
                    cancelarReserva("Recepcionista");
                    break;
                case 5:
                    verEstadoHabitacion("Recepcionista");
                    break;
                case 6:
                    agregarHistorialPasajero("Recepcionista");
                    break;
                case 7:
                    System.out.println("Gracias por utilizar nuestro sistema. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        } while (opcion != 7);
    }

    public static void menuAdministrador() {
        int opcion;

        do {
            System.out.println("╔═════════════════════════════════════════╗");
            System.out.println("║   Bienvenido al sistema de gestión de   ║");
            System.out.println("║              Administración             ║");
            System.out.println("╠═════════════════════════════════════════╣");
            System.out.println("║ 1. Check In                             ║");
            System.out.println("║ 2. Check Out                            ║");
            System.out.println("║ 3. Hacer reserva                        ║");
            System.out.println("║ 4. Cancelar reserva                     ║");
            System.out.println("║ 5. Ver estado de la habitación          ║");
            System.out.println("║ 6. Agregar historial a pasajero         ║");
            System.out.println("║ 7. Salir                                ║");
            System.out.println("╚═════════════════════════════════════════╝");
            System.out.print("Seleccione una opción: ");
            opcion = scan.nextInt();

            switch (opcion) {
                case 1:
                    checkIn("Administrador");
                    break;
                case 2:
                    checkOut("Administrador");
                    break;
                case 3:
                    hacerReserva("Administrador");
                    break;
                case 4:
                    cancelarReserva("Administrador");
                    break;
                case 5:
                    verEstadoHabitacion("Administrador");
                    break;
                case 6:
                    agregarHistorialPasajero("Administrador");
                    break;
                case 7:
                    System.out.println("Gracias por utilizar nuestro sistema. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        } while (opcion != 7);
    }

    public static void checkIn(String rol) {
        System.out.println("─────────────────────────────────────────────");
        System.out.println("                 CHECK IN                     ");
        System.out.println("─────────────────────────────────────────────");
        // Lógica para check in según el rol
        switch (rol) {
            case "Administrador":
                // Lógica específica para check-in de administrador
                System.out.println("Realizando check-in como Administrador...");
                break;
            case "Recepcionista":
                // Lógica específica para check-in de recepcionista
                System.out.println("Realizando check-in como Recepcionista...");
                break;
            default:
                System.out.println("Rol de usuario no válido.");
                break;
        }
    }

    public static void checkOut(String rol) {
        System.out.println("─────────────────────────────────────────────");
        System.out.println("                CHECK OUT                     ");
        System.out.println("─────────────────────────────────────────────");
        // Lógica para check out según el rol
        switch (rol) {
            case "Administrador":
                // Lógica específica para check-out de administrador
                System.out.println("Realizando check-out como Administrador...");
                break;
            case "Recepcionista":
                // Lógica específica para check-out de recepcionista
                System.out.println("Realizando check-out como Recepcionista...");
                break;
            default:
                System.out.println("Rol de usuario no válido.");
                break;
        }
    }

    public static void hacerReserva(String rol) {
        System.out.println("─────────────────────────────────────────────");
        System.out.println("               HACER RESERVA                  ");
        System.out.println("─────────────────────────────────────────────");
        // Lógica para hacer reserva según el rol
        switch (rol) {
            case "Administrador":
                // Lógica específica para hacer reserva como administrador
                System.out.println("Realizando reserva como Administrador...");
                break;
            case "Recepcionista":
                // Lógica específica para hacer reserva como recepcionista
                System.out.println("Realizando reserva como Recepcionista...");
                break;
            case "Pasajero":
                // Lógica específica para hacer reserva como pasajero
                System.out.println("Realizando reserva como Pasajero...");
                break;
            default:
                System.out.println("Rol de usuario no válido.");
                break;
        }
    }

    public static void cancelarReserva(String rol) {
        System.out.println("─────────────────────────────────────────────");
        System.out.println("            CANCELAR RESERVA                  ");
        System.out.println("─────────────────────────────────────────────");
        // Lógica para cancelar reserva según el rol
        switch (rol) {
            case "Administrador":
                // Lógica específica para cancelar reserva como administrador
                System.out.println("Cancelando reserva como Administrador...");
                break;
            case "Recepcionista":
                // Lógica específica para cancelar reserva como recepcionista
                System.out.println("Cancelando reserva como Recepcionista...");
                break;
            case "Pasajero":
                // Lógica específica para cancelar reserva como pasajero
                System.out.println("Cancelando reserva como Pasajero...");
                break;
            default:
                System.out.println("Rol de usuario no válido.");
                break;
        }
    }

    public static void verEstadoHabitacion(String rol) {
        System.out.println("─────────────────────────────────────────────");
        System.out.println("        VER ESTADO DE LA HABITACIÓN           ");
        System.out.println("─────────────────────────────────────────────");
        // Lógica para ver estado de la habitación según el rol
        switch (rol) {
            case "Administrador":
                // Lógica específica para ver estado de la habitación como administrador
                System.out.println("Verificando estado de la habitación como Administrador...");
                break;
            case "Recepcionista":
                // Lógica específica para ver estado de la habitación como recepcionista
                System.out.println("Verificando estado de la habitación como Recepcionista...");
                break;
            default:
                System.out.println("Rol de usuario no válido.");
                break;
        }
    }

    public static void agregarHistorialPasajero(String rol) {
        System.out.println("─────────────────────────────────────────────");
        System.out.println("       AGREGAR HISTORIAL A PASAJERO          ");
        System.out.println("─────────────────────────────────────────────");
        // Lógica para agregar historial a pasajero según el rol
        switch (rol) {
            case "Administrador":
                // Lógica específica para agregar historial a pasajero como administrador
                System.out.println("Agregando historial a pasajero como Administrador...");
                break;
            case "Recepcionista":
                // Lógica específica para agregar historial a pasajero como recepcionista
                System.out.println("Agregando historial a pasajero como Recepcionista...");
                break;
            default:
                System.out.println("Rol de usuario no válido.");
                break;
        }
    }
}



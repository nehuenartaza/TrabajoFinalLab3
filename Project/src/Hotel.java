import java.util.ArrayList;

public class Hotel {
    private GestorHabitacion gestorHabitacion;
    private ArrayList<GestorStaff> staff = new ArrayList<GestorStaff>();
    private GestorReserva gestorReserva;
    private ArrayList<Pasajero> pasajeros = new ArrayList<Pasajero>();
    private ArrayList<Producto> productos = new ArrayList<Producto>();

    public Hotel() {

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

    public void registrarHabitacion(Habitacion nueva) { //validar que no se agregue una habitacion ya existente
        gestorHabitacion.registrarHabitacion(nueva);
    }

    public void eliminarHabitacion(int numero) {
        gestorHabitacion.eliminarHabitacion(numero);
    }

    public void estadoHabitacion(int numero) {
        gestorHabitacion.verEstadoHabitacion(numero);
    }

    public void cambiarEstadoHabitacion(int numero, HabitacionStatus nuevoEStado) {
        gestorHabitacion.cambiarEstadoHabitacion(numero, nuevoEStado);
    }

    public void agregarConsumoEnHabitacion(int numero, double precio) {
        gestorHabitacion.agregarConsumo(numero, precio);
    }

    public void crearStaff(GestorStaff staff) { //el nuevo staff viene del método crearStaff de Administrador
        this.staff.add(staff);
    }

    public boolean cambiarStaff(GestorStaff staff) { //TODO falta algoritmo, llamar al método de Administrador

        return true;
    }

    public void borrarStaff(GestorStaff staff) {

    }

    public void mostrarStaff() {

    }

    public boolean hacerReserva(Reserva reserva) {

        return true;
    }

    public void agregarHistorial() {

    }

    public boolean registrarPasajero(Pasajero nuevo) {

        return true;
    }

    public void registrarProducto(Producto nuevo) {

    }

    public void eliminarProducto(Producto p) {

    }

    public void cambiarProducto(Producto p) {

    }

    /*
     * métodos para guardar y cargar json
     */

    //TODO puede que hayan métodos que no hagan falta en Hotel, hablando de los métodos de Administrador y Recepcionista.
    //TODO por ejemplo, cambiarStaff en Hotel. Desde el main el Administrador podría llamar a su propio método y Hotel lo único que hace es pasarle el arraylist de GestorStaff,
    //TODO cuando el Administrador termina de cambiar al staff, le manda el arraylist modificado a hotel.
}

import java.util.ArrayList;

public class Administrador extends Persona implements Administrable {

    public Administrador(String nombre, String apellido, String dni) {
        super(nombre, apellido, dni);
    }

    @Override
    public String toString() {
        return "Administrador: Nombre: " + getNombre() + " |Apellido: " + getApellido() + " |Dni: " + getDni();
    }






    //TODO implementaciones 
    @Override
    public void checkin() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkin'");
    }

    @Override
    public void checkout() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkout'");
    }

    @Override
    public boolean hacerReserva(Reserva reserva) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hacerReserva'");
    }

    @Override
    public void cancelarReserva(Reserva reserva) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelarReserva'");
    }

    @Override
    public void estadoHabitacion(int numero) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'estadoHabitacion'");
    }

    @Override
    public void cambiarEstadoHabitacion(int numero, HabitacionStatus nuevoEstado) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cambiarEstadoHabitacion'");
    }

    @Override
    public void agregarHistorial(Historial nuevo, Pasajero pasajero) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'agregarHistorial'");
    }

    @Override
    public boolean registrarPasajero(Pasajero nuevo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registrarPasajero'");
    }

    @Override
    public void crearStaff() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'crearStaff'");
    }

    @Override
    public boolean cambiarStaff(GestorStaff gestor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cambiarStaff'");
    }

    @Override
    public void borrarStaff() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'borrarStaff'");
    }

    @Override
    public void mostrarStaff(ArrayList<GestorStaff> gestorStaff) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarStaff'");
    }

    @Override
    public void registrarProducto(Producto nuevo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registrarProducto'");
    }

    @Override
    public void eliminarProducto(Producto producto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarProducto'");
    }

    @Override
    public void cambiarProducto(Producto producto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cambiarProducto'");
    }
    
}

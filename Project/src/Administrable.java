import java.util.ArrayList;

public interface Administrable extends Recepcionable {
    public void crearStaff();
    public boolean cambiarStaff(GestorStaff gestor);
    public void borrarStaff();
    public void mostrarStaff(ArrayList<GestorStaff> gestorStaff);
    public void registrarProducto(Producto nuevo);
    public void eliminarProducto(Producto producto);
    public void cambiarProducto(Producto producto);
}

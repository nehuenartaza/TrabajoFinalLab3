package users;
import java.util.ArrayList;
import services.GestorStaff;
public interface Administrable extends Recepcionable {
    public boolean cambiarStaff(GestorStaff modificado, ArrayList<GestorStaff> staff);
    public void borrarStaff(String dni, ArrayList<GestorStaff> staff);
    public void mostrarStaff(ArrayList<GestorStaff> staff);
}

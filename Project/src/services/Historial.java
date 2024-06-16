package services;
import java.io.Serializable;

public class Historial implements Serializable {
    private String ingreso;
    private String egreso;
    private Habitacion habitacion;

    public Historial(String ingreso, String egreso, Habitacion habitacion) {
        this.ingreso = ingreso;
        this.egreso = egreso;
        this.habitacion = habitacion;
    }

    public String getIngreso() {
        return ingreso;
    }

    public String getEgreso() {
        return egreso;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }
    

	@Override
    public String toString() {
        return "Historial: Ingreso: " + ingreso + " |Egreso: " + egreso + " |Habitacion: " + habitacion.getNumero();
    }
}

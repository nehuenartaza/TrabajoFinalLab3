package services;
import java.io.Serializable;
import utilities.HabitacionStatus;
public class Habitacion implements Serializable {
    private int numero;
    private HabitacionStatus estado;
    private int capacidad;
    private double cuenta;

    public Habitacion(int num, int capacidad, double cuenta, HabitacionStatus estado) {
        this.numero = num;
        this.capacidad = capacidad;
        this.cuenta = cuenta;
        this.estado = estado;
    }

    public Habitacion() {
        this.numero = 0;
        this.capacidad = 0;
        this.cuenta = 0;
        this.estado = HabitacionStatus.DISPONIBLE;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int num) {
        this.numero = num;
    }

    public HabitacionStatus getEstado() {
        return estado;
    }

    public void setEstado(HabitacionStatus estado) {
        this.estado = estado;
    }

    public double getCuenta() {
        return cuenta;
    }

    public void setCuenta(double cuenta) {
        this.cuenta = cuenta;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    @Override
    public String toString() {
        return "N°Habitación: " + numero + " |Capacidad: " + capacidad + " |Estado: " + estado + " |Cuenta: " + cuenta;
    }
}

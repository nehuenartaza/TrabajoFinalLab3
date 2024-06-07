public class Habitacion {

    private int num;
    private HabitacionStatus estado;
    private int capacidad;
    private double cuenta;

    public enum HabitacionStatus{
        ENLIMPIEZA,
        EN_REPARACION,
        EN_DESINFECCION,
        DISPONIBLE;
    }

    public Habitacion(int num, int capacidad, double cuenta, HabitacionStatus estado) {
        this.num = num;
        this.capacidad = capacidad;
        this.cuenta = cuenta;
        this.estado = estado;
    }

    public Habitacion() {
        this.num = 0 ;
        this.capacidad = 0;
        this.cuenta = 0;
        this.estado = HabitacionStatus.DISPONIBLE;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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
}

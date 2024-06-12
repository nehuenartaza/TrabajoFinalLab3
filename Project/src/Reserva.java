public class Reserva {
    private String ingreso;
    private String egreso;
    private int cantidad;
    private String dni;
    private ReservaStatus estado;
    private int numeroHabitacion;

    public Reserva(String ingreso, String egreso, int cantidad, String dni, ReservaStatus estado, int numeroHabitacion) {
        this.ingreso = ingreso;
        this.egreso = egreso;
        this.cantidad = cantidad;
        this.dni = dni;
        this.estado = estado;
        this.numeroHabitacion = numeroHabitacion;
    }

    public Reserva() {
        this.ingreso = "";
        this.egreso = "";
        this.cantidad = 0;
        this.dni = "";
        this.estado = ReservaStatus.ACTIVA;
        this.numeroHabitacion = 0;
    }

    public String getIngreso() {
        return ingreso;
    }

    public void setIngreso(String ingreso) {
        this.ingreso = ingreso;
    }

    public String getEgreso() {
        return egreso;
    }

    public void setEgreso(String egreso) {
        this.egreso = egreso;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public ReservaStatus getEstado() {
        return estado;
    }

    public void setEstado(ReservaStatus estado) {
        this.estado = estado;
    }

    public int getNumeroHabitacion() {
		return numeroHabitacion;
	}

	public void setNumeroHabitacion(int numeroHabitacion) {
		this.numeroHabitacion = numeroHabitacion;
	}

	@Override
    public String toString() {
        return "Reserva: Ingreso: " + ingreso + " |Egreso: " + egreso + " |Cantidad: " + cantidad + " |Dni: " + dni + " |Estado: " + estado;
    }
    
}

package utilities;
import java.io.Serializable;

public enum HabitacionStatus implements Serializable {
    EN_LIMPIEZA,
    EN_REPARACION,
    EN_DESINFECCION,
    DISPONIBLE,
	OCUPADO;
}

package utilities;
public enum Mes {
	ENERO(1, 31),
	FEBRERO(2, 29),
	MARZO(3, 31),
	ABRIL(4, 30),
	MAYO(5, 31),
	JUNIO(6, 30),
	JULIO(7, 31),
	AGOSTO(8, 31),
	SEPTIEMBRE(9, 30),
	OCTUBRE(10, 31),
	NOVIEMBRE(11, 30),
	DICIEMBRE(12, 31);
	
	private int mes;
	private int dias;
	
	private Mes(int mes, int dias) {
		this.mes = mes;
		this.dias = dias;
	}
	
	public int getMes() {
		return mes;
	}
	
	public int getDias() {
		return dias;
	}
	
	public static int getDiasDeMes(int mes) {
		switch ( mes ) {
		case 1:
			return 31;
		case 2:
			return 29;
		case 3:
			return 31;
		case 4:
			return 30;
		case 5:
			return 31;
		case 6:
			return 30;
		case 7:
			return 31;
		case 8:
			return 31;
		case 9:
			return 30;
		case 10:
			return 31;
		case 11:
			return 30;
		case 12:
			return 31;
		default:
			return 0;
		}
	}
}





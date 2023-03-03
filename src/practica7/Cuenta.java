package practica7;

import java.io.Serializable;

public class Cuenta implements Serializable {
	public int  saldo;
	public String tipo_moneda, tipo_cuenta,iban;

	public Cuenta() {
	};
	public Cuenta(String iban, String tipo_moneda, String tipo_cuenta) {
		this.iban = iban;
		this.saldo = 0;
		this.tipo_moneda = tipo_moneda;
		this.tipo_cuenta = tipo_cuenta;
		
	}
	public Cuenta(String iban, int saldo, String tipo_moneda, String tipo_cuenta) {
		super();
		this.iban = iban;
		this.saldo = saldo;
		this.tipo_moneda = tipo_moneda;
		this.tipo_cuenta = tipo_cuenta;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public int getSaldo() {
		return saldo;
	}

	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}

	public String getTipo_moneda() {
		return tipo_moneda;
	}

	public void setTipo_moneda(String tipo_moneda) {
		this.tipo_moneda = tipo_moneda;
	}

	public String getTipo_cuenta() {
		return tipo_cuenta;
	}

	public void setTipo_cuenta(String tipo_cuenta) {
		this.tipo_cuenta = tipo_cuenta;
	}

	public void ingresar_dinero(int dinero) {
		saldo += dinero;

	}

	public void retirar_dinero(int dinero) {
		saldo -= dinero;
		int distancia=0;
		int[] monedas€ = { 500, 200, 100, 50, 20, 10 };
		if(this.tipo_moneda.equals("dollar")) {
			distancia=3;
		}
		int[] devolver€ = { 0, 0, 0, 0, 0, 0 };
		
		
			for (int i = distancia; i <monedas€.length; i++) {
				if (dinero >= monedas€[i]) {
					devolver€[i] = (int) Math.floor(dinero / monedas€[i]);
					dinero = (dinero - (devolver€[i] * monedas€[i]));
				}
			}
			System.out.println("billetes:");
			for (int i = 0; i < monedas€.length; i++) {
				if (devolver€[i] > 0) {
					System.out.println( devolver€[i] + " de " + monedas€[i] );
				}
			}
		

	}
	public void retirar_saldo(int dinero) {
		saldo -= dinero;	
	}
	@Override
	public String toString() {
		String cadena = saldo + "",moneda="";
		char[] array = cadena.toCharArray();
		char invertido[] = new char[array.length];
		for (int i = invertido.length - 1, j = 0; i >= 0; i--, j++) {
			invertido[i] = array[j];
		}
		StringBuilder sb = new StringBuilder();
		for (int i = invertido.length - 1; i >= 0; i--) {
			sb.append(invertido[i]);
			if (i == 0) {

			} else if (i % 3 == 0) {
				sb.append(".");

			}
		}
		if (tipo_moneda.equals("dollar")) {
			moneda="$";

		} else {
			moneda="€";
		}
		return "Cuenta " + tipo_cuenta + ": IBAN:" + iban + ", saldo:" + sb + moneda ;
	}

}

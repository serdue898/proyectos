package practica7;

import java.io.Serializable;
//hecho por sergio ramiro bahillo
public class Deposito implements Serializable{
	public int iban,saldo,plazo;
	public double tae;
    public String tipo_deposito;
    public Deposito() {}
    
	public Deposito(int iban, int saldo, int plazo, double tae, String tipo_deposito) {
		super();
		this.iban = iban;
		this.saldo = saldo;
		this.plazo = plazo;
		this.tae = tae;
		this.tipo_deposito = tipo_deposito;
	}

	public Deposito(int iban, int saldo, String tipo_deposito) {
		super();
		this.iban = iban;
		this.saldo = saldo;
		this.tipo_deposito = tipo_deposito;
	}
	
	
	public int getPlazo() {
		return plazo;
	}

	public void setPlazo(int plazo) {
		this.plazo = plazo;
	}

	public double getTae() {
		return tae;
	}

	public void setTae(double tae) {
		this.tae = tae;
	}

	public int getIban() {
		return iban;
	}
	public void setIban(int iban) {
		this.iban = iban;
	}
	public int getSaldo() {
		return saldo;
	}
	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}
	public String getTipo_deposito() {
		return tipo_deposito;
	}
	public void setTipo_deposito(String tipo_deposito) {
		this.tipo_deposito = tipo_deposito;
	}
	@Override
	public String toString() {
		
		return "Deposito "+tipo_deposito + ": saldo:" + saldo+"€" ;
	};
    
}

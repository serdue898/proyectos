package practica7;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
public class Cliente implements Serializable{
	private String usuario, nombre,apellidos,sexo,dni,pin;
	private LocalDate fechanac;
	public List <Deposito> depositos = new ArrayList<Deposito>();
	public List <Cuenta> cuentas = new ArrayList<Cuenta>();
	
	
	public Cliente(String nombre, String apellidos,String sexo, LocalDate i,  String dni,String usuario,String pin) {
		super();
		this.usuario = usuario;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.sexo = sexo;
		this.dni = dni;
		this.fechanac = i;
		this.pin = pin;
																
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public LocalDate getFechanac() {
		return fechanac;
	}
	public void setFechanac(LocalDate fechanac) {
		this.fechanac = fechanac;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}

	
	public void nuevacuenta(String iban,int saldo,String moneda,String tipocuenta) {
		cuentas.add(new Cuenta(iban,saldo,moneda,tipocuenta));	
		
	}
	//metodo para eliminar una cuenta segun el iban
	public void eliminarcuenta(int cuenta) {
		cuentas.remove(cuenta);
	}
/*metodo para crear nuevo deposito*/
	public void nuevodeposito(int iban, int saldo, int plazo, double tae, String tipo_deposito) {
		depositos.add(new Deposito(iban,saldo,plazo,tae,tipo_deposito));	
		
	}
	public void usuario_nuevacuenta(String iban, String tipo_moneda, String tipo_cuenta) {
		cuentas.add(new Cuenta(iban,tipo_moneda,tipo_cuenta));	
		
		
	}
	//metodo para transferir dinero etre tus cuentas
	public void transferencia(int cuenta1 ,int cuenta2,int dinero,String tipo_moneda1,String tipo_moneda2) {
		cuentas.get(cuenta1).retirar_saldo(dinero);
		if(tipo_moneda1.equals(tipo_moneda2))
			cuentas.get(cuenta2).ingresar_dinero(dinero);
		else if(tipo_moneda1.equals("euro")) {
			dinero*=1.22;
			cuentas.get(cuenta2).ingresar_dinero(dinero);
			
		}
		else if(tipo_moneda1.equals("dollar")) {
			dinero/=1.22;
			cuentas.get(cuenta2).ingresar_dinero(dinero);
			
		}
		else {
			System.out.println("Ha habido un problema");
			
		}
		
		
	}
	
	//metodo para tranferir a cuentas de otros usuarios
	public void transferenciaExterna(int cuenta1 ,int cuenta2,int dinero,String tipo_moneda1,String tipo_moneda2,int cliente1,int cliente2) {
		cuentas.get(cuenta1).retirar_saldo(dinero);
		if(tipo_moneda1.equals(tipo_moneda2)) {
			cuentas.get(cuenta2).ingresar_dinero(dinero);
		}
		else if(tipo_moneda1.equals("euro")) {
			dinero*=1.22;
			cuentas.get(cuenta2).ingresar_dinero(dinero);
			
		}
		else if(tipo_moneda1.equals("dollar")) {
			dinero/=1.22;
			cuentas.get(cuenta2).ingresar_dinero(dinero);
			
		}
		else {
			System.out.println("Ha habido un problema");
			
		}
		
		
	}
	@Override
	public String toString() {
		return "Cliente [usuario=" + usuario + ", cuentas="
				+ cuentas + "]";
	}
	
	
}

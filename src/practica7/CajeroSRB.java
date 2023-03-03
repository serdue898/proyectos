package practica7;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
// hecho por sergio ramiro bahillo
public class CajeroSRB implements Serializable {
	// revisar el ingreso de dinero de mark lenders y negativos
	// revisar al MALDITO MARK LENDERS!!!!!!!!!
	private static List<Cliente> clientes = new ArrayList<Cliente>();
	private static int cliente = 0;
	private static HashMap<String, String> opciones = new HashMap<String, String>();
	private static HashMap<Integer, String> tipos_depositos = new HashMap<Integer, String>();
	private static HashMap<Integer, Integer> cuentaAvaladora = new HashMap<Integer, Integer>();
	private static int num_pregunta = 1;
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws ClassNotFoundException {

		loggear();
		imprimir_cuentas();
		imprimir_depositos();
		elegir_opcion(); 
		guardarDatos();

	}

//imprimir datos
	
	public static void elegir_opcion() {
		// dar las opciones que puede realizar
		int opcion;
		boolean error=false;
				do {
					System.out.println("");
					error = true;
					num_pregunta = 1;
					imprimir_opciones();
					try {
						opcion = sc.nextInt();

						switch (opciones.get(opcion + "")) {
						case "retirar":
							retirar();
							break;
						case "ingresar":
							ingresar();
							break;
						case "transferirI":
							transferir_cuentas_internas();
							break;
						case "transferirE":
							transferir_cuentas_externas();
							break;
						case "contratarDep":
							contratar_depositos();
							break;
						case "mostrardatos":
							imprimir_cuentas();
							imprimir_depositos();
							break;
						case "crearcuenta":
							crear_cuenta();
							break;
						case "eliminarcuenta":
							eliminar_cuenta();
							break;
						case "alterarceunta":
							alterar_cuentas();
							break;
						case "salir":
							error = false;
							break;
							
							
						default:

							System.out.println("Tiene que ser un número entre 1 y " + num_pregunta);
						}
					} catch (Exception e) {
						System.out.println("Tiene que ser un número entre 1 y " + num_pregunta);
						error = true;
						sc.next();

					}
				} while (error);
		
	}
	
	
	public static void imprimir_opciones() {
		System.out.println("Opciones:");
		if (validacion_dinero(10)) {
			opciones.put(num_pregunta + "", "retirar");
			num_pregunta++;
		}

		System.out.println(num_pregunta + ".Ingresar dinero");
		opciones.put(num_pregunta + "", "ingresar");
		num_pregunta++;

		if (validacion_dinero(0)) {
			System.out.println(num_pregunta + ".Transferir dinero entre cuentas del usuario");
			opciones.put(num_pregunta + "", "transferirI");
			num_pregunta++;
		}
		if (validacion_dinero(0)) {
			System.out.println(num_pregunta + ".Transferir dinero a otras cuentas externas");
			opciones.put(num_pregunta + "", "transferirE");
			num_pregunta++;
		}
		if (clientes.get(cliente).depositos.size() < 2 && validacion_dinero(0)) {
			System.out.println(num_pregunta + ".Contratar un depósito bancario");
			opciones.put(num_pregunta + "", "contratarDep");
			num_pregunta++;
		}
		
		
		if(clientes.get(cliente).cuentas.size() < 3 && clientes.get(cliente).cuentas.size() > 1) {
			System.out.println(num_pregunta + ".crear/eliminar cuenta");
			opciones.put(num_pregunta + "", "alterarceunta");
			num_pregunta++;
		}else {
			if (clientes.get(cliente).cuentas.size() < 3) {
				System.out.println(num_pregunta + ".Crear cuenta");
				opciones.put(num_pregunta + "", "crearcuenta");
				num_pregunta++;
			}
			if (clientes.get(cliente).cuentas.size() > 1) {
				System.out.println(num_pregunta + ".eliminar cuenta");
				opciones.put(num_pregunta + "", "eliminarcuenta");
				num_pregunta++;
			}
		}
		System.out.println(num_pregunta + ".Mostrar datos");
		opciones.put(num_pregunta + "", "mostrardatos");
		num_pregunta++;
		System.out.println(num_pregunta + ".Salir");
		opciones.put(num_pregunta + "", "salir");

	}

	public static void imprimir_tipo_deposito() {

		System.out.println("Opciones:");
		LocalDate fechaHoy = LocalDate.now();
		Period periodo = Period.between(clientes.get(cliente).getFechanac(), fechaHoy);
		int num_pregunta = 1;

		if (validacion_dinero(500)) {
			System.out.println(num_pregunta + ".Depósito Junior(mínimo 500€-todos los usuarios)");
			tipos_depositos.put(num_pregunta, "junior");
			num_pregunta++;
		}
		if (periodo.getYears() > 18) {
			if (validacion_dinero(5000)) {
				System.out.println(num_pregunta + ".Depósito Senior(mínimo 5.000€-adultos) ");
				tipos_depositos.put(num_pregunta, "senior");
				num_pregunta++;
			}
			if (validacion_dinero(20000)) {
				System.out.println(num_pregunta + ".Depósito MAX(mínimo 20.000€-adultos) ");
				tipos_depositos.put(num_pregunta, "MAX");
				num_pregunta++;
			}
		}
		System.out.println(num_pregunta + ".Salir");
		tipos_depositos.put(num_pregunta, "salir");

	}

	public static void imprimir_depositos() {
		System.out.println("");
		for (int i = 0; i < clientes.get(cliente).depositos.size(); i++) {
			if (i == 0) {
				System.out.println("Depósitos:");

			}
			System.out.println((i + 1) + "-" + clientes.get(cliente).depositos.get(i));

		}

	}
	// hecho por sergio ramiro bahillo
	public static void imprimir_cuentas() {
		System.out.println("");
		for (int i = 0; i < clientes.get(cliente).cuentas.size(); i++) {
			if (i == 0) {
				System.out.println("Cuentas:");

			}
			System.out.println((i + 1) + "-" + clientes.get(cliente).cuentas.get(i));

		}
		

	}

	public static void imprimir_cuentas_disponibles(int minimo) {
		int cuenta = 0;
		for (int i = 0; i < clientes.get(cliente).cuentas.size(); i++) {
			if (clientes.get(cliente).cuentas.get(i).getSaldo() > minimo) {
				cuenta++;
				System.out.println((cuenta) + "-" + clientes.get(cliente).cuentas.get(i));
				cuentaAvaladora.put(cuenta, i);
			}
		}

	}
	public static void imprimir_clientes() {
		for (int j = 0; j < clientes.size(); j++) {
			System.out.println((j + 1) + "-" + clientes.get(j));

		}

	}
	public static boolean validacion_dinero(int minimo) {
		boolean dinero = true;
		for (int i = 0; i < clientes.get(cliente).cuentas.size(); i++) {
			if (clientes.get(cliente).cuentas.get(i).getSaldo() > minimo) {
				dinero = true;
				break;
			} else {
				dinero = false;
			}
		}
		if (dinero) {
			return true;

		} else {
			return false;
		}
	}
	public static boolean validacion_numpositivo(int numero) {
		if(numero>=0) {
			return true;
		}else {
			return false;
		}
	}
//metodos de cuentas
	/* terminado 
	 * solucionado error de numero negativo,cuenta inexistente,cuenta a 0 ,
	 * introducido una letra en lugar de numero*/
	public static void ingresar() {
		int pregunta = 0, dinero;
		boolean error = true;
		
		imprimir_cuentas();
		System.out.println("¿En qué cuenta vas a realizar el ingreso?");
		do {
			error = false;
			try {
				pregunta = sc.nextInt();
				pregunta -= 1;
				if (pregunta <= clientes.get(cliente).cuentas.size()-1 && pregunta >= 0) {

				} else if(clientes.get(cliente).cuentas.size()==1){
					System.out.println("Tiene que ser 1");
					error=true;
				}else {
					error = true;
					System.out.println("Tiene que ser un numero entre 1 y " + clientes.get(cliente).cuentas.size());
				}
			} catch (Exception e) {
				error = true;
				System.out.println("Tiene que ser un numero entero");
				sc.next();
			}
		} while (error);
		do {

			error = false;
			try {

				System.out.println("¿Cuánto va a ingresar?");
				dinero = sc.nextInt();
				if ((clientes.get(cliente).cuentas.get(pregunta).getSaldo()==0||(dinero < (clientes.get(cliente).cuentas.get(pregunta).getSaldo() * 10) / 100)) && validacion_numpositivo(dinero)) {
					clientes.get(cliente).cuentas.get(pregunta).ingresar_dinero(dinero);
					guardarDatos();
					System.out.println("realizado con exito");
				} else if(validacion_numpositivo(dinero)){
					System.out.println("No se puede realizar porque supera al 10% de su saldo");
					error=true;
				}
				if(!validacion_numpositivo(dinero)){
					error = true;
					System.out.println("el nummero introducido es negativo");
				}
			} catch (Exception e) {
				error = true;
				System.out.println("Tiene que ser un numero entero");
				sc.next();

			}
		} while (error);

	}

	/* terminado 
	 * solucionados errores dew billetes de retiradas negativas de cuentas erroneas ... */
	public static void retirar() {
		HashMap<Integer, Integer> numcuenta = new HashMap<Integer, Integer>();
		int cuenta = 0, pregunta, dinero;
		String tipo_cuenta;
		boolean error = true, error2 = true;
		for (int i = 0; i < clientes.get(cliente).cuentas.size(); i++) {
			if (clientes.get(cliente).cuentas.get(i).getSaldo() > 0) {
				cuenta++;
				System.out.println((cuenta) + "-" + clientes.get(cliente).cuentas.get(i));
				numcuenta.put(cuenta, i);
			}
		}

		System.out.println("¿en que cuenta vas a realizar la retirada?");
		do {
			error = false;
			try {
				pregunta = sc.nextInt();

				if (pregunta <= clientes.get(cliente).cuentas.size() && pregunta > 0) {
					cuenta = numcuenta.get(pregunta);
					tipo_cuenta = clientes.get(cliente).cuentas.get(cuenta).getTipo_cuenta();
					do {
						System.out.println("¿cuanto va a retirar?");
						error2 = false;
						try {
							dinero = sc.nextInt();
							if ( validacion_numpositivo(dinero) && validar_minimo(dinero)){
								if ((tipo_cuenta.equals("nomina") && dinero <= 300)
										|| (tipo_cuenta.equals("corriente") && dinero <= 500)) {
									if (dinero <= clientes.get(cliente).cuentas.get(cuenta).getSaldo()) {
										if (dinero < (clientes.get(cliente).cuentas.get(cuenta).getSaldo() * 25)
												/ 100) {
											clientes.get(cliente).cuentas.get(cuenta).retirar_dinero(dinero);
											guardarDatos();
										} else {
											System.out.println("no se puede realizar porque supera al 25% de su saldo");
											error2 = true;
										}
									} else {
										System.out.println("no tiene suficiente saldo");
										error2 = true;
									}
								} else {
									if (tipo_cuenta.equals("nomina")) {
										System.out.println("la retirada no puede ser superior a 300");
										error2 = true;
									} else {
										System.out.println("la retirada no puede ser superior a 500");
										error2 = true;
									}

								}
							} else if(validacion_numpositivo(dinero)){
								System.out.println("El numero tiene que terminar en 0 y ser superior a 10");
								error2 = true;

							}
							if(!validacion_numpositivo(dinero)){
								error2 = true;
								System.out.println("el nummero introducido es negativo");
							}
						} catch (Exception e) {
							error2 = true;
							System.out.println("tiene que ser un numero entero ");
							sc.next();

						}
					} while (error2);
				} else {
					error = true;
					System.out.println("tiene que ser un numero entre 1 y " + clientes.get(cliente).cuentas.size());
				}
			} catch (Exception e) {
				error = true;
				System.out.println("tiene que ser un numero entre 1 y " + clientes.get(cliente).cuentas.size());
				sc.next();
			}
		} while (error);

	}

	/* terminado */public static boolean validar_minimo(int numero) {

		if (numero % 10 == 0) {
			return true;
		} else {
			return false;
		}
	}

	/* terminado 
	 * solucionado el error de transferir a la misma cuenta desde la que se realiza la retirada*/
	public static void transferir_cuentas_internas() {
		// hecho por sergio ramiro bahillo
		HashMap<Integer, Integer> numcuenta1 = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> numcuenta2 = new HashMap<Integer, Integer>();
		int cuenta1 = 0, cuenta2 = 0, pregunta, dinero;
		String tipo_moneda1 = "", tipo_moneda2 = "";
		boolean error = true;
		for (int i = 0; i < clientes.get(cliente).cuentas.size(); i++) {
			if (clientes.get(cliente).cuentas.get(i).getSaldo() > 0) {
				cuenta1++;
				System.out.println((cuenta1) + "-" + clientes.get(cliente).cuentas.get(i));
				numcuenta1.put(cuenta1, i);
			}
		}

		System.out.println("¿Desde qué cuenta vaas a realizar la transferencia?");

		do {
			error = false;
			try {
				pregunta = sc.nextInt();

				if (pregunta <= clientes.get(cliente).cuentas.size() && pregunta > 0) {
					cuenta1 = numcuenta1.get(pregunta);
					tipo_moneda1 = clientes.get(cliente).cuentas.get(cuenta1).getTipo_moneda();
				} else {
					error = true;
					System.out.println("Tiene que ser un número entre 1 y " + clientes.get(cliente).cuentas.size());
				}
			} catch (Exception e) {
				error = true;
				System.out.println("Tiene que ser un número entre 1 y " + clientes.get(cliente).cuentas.size());
				sc.next();
			}
		} while (error);

		System.out.println("¿A qué cuenta vas a realizarlo?");
		for (int i = 0; i < clientes.get(cliente).cuentas.size(); i++) {
			if (i != cuenta1) {
				cuenta2++;
				System.out.println((cuenta2) + "-" + clientes.get(cliente).cuentas.get(i));
				numcuenta2.put(cuenta2, i);
			}
		}

		do {
			error = false;
			try {
				pregunta = sc.nextInt();

				if (pregunta <= cuenta2 && pregunta > 0) {
					cuenta2 = numcuenta2.get(pregunta);
					tipo_moneda2 = clientes.get(cliente).cuentas.get(cuenta2).getTipo_moneda();
				} else {
					error = true;
					System.out.println("Tiene que ser un número entre 1 y " + cuenta2);
				}
			} catch (Exception e) {
				error = true;
				System.out.println("Tiene que ser un número entre 1 y " + cuenta2);
				sc.next();
			}
		} while (error);
		do {
			error = false;
			System.out.println("¿Cuánto va a transferir?");
			try {
				dinero = sc.nextInt();
				if (validacion_numpositivo(dinero) && dinero <= clientes.get(cliente).cuentas.get(cuenta1).getSaldo()) {
					clientes.get(cliente).transferencia(cuenta1, cuenta2, dinero, tipo_moneda1, tipo_moneda2);
					System.out.println("realizado con exito");
				} else if(validacion_numpositivo(dinero)) {
					System.out.println("No tiene suficiente saldo");
					error = true;

				}
				if(!validacion_numpositivo(dinero)){
					error = true;
					System.out.println("el nummero introducido es negativo");
				}
			} catch (Exception e) {
				error = true;
				System.out.println("Tiene que ser un número entero ");
				sc.next();
			}
		} while (error);
	}

	/* terminado 
	 * solucionado iban inexistente,iban del mismo usuario introducido*/
	public static void transferir_cuentas_externas() {
		HashMap<Integer, Integer> numcuenta1 = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> numcuenta2 = new HashMap<Integer, Integer>();
		int cuenta1 = 0, cuenta2 = 0, pregunta, dinero, cliente2 = 0, justificantePrecio = 0;
		String tipo_moneda1 = "", tipo_moneda2 = "", iban, preguntaU = "";
		boolean error = true, elegido = false, correcto = false, justificante = false, urgente = true, mismo = false;
		for (int i = 0; i < clientes.get(cliente).cuentas.size(); i++) {
			if (clientes.get(cliente).cuentas.get(i).getSaldo() > 0) {
				cuenta1++;
				System.out.println((cuenta1) + "-" + clientes.get(cliente).cuentas.get(i));
				numcuenta1.put(cuenta1, i);
			}
		}

		do {
			try {
				System.out.println("¿Desde qué cuenta vas a realizar la transferencia?");
				error = false;
				pregunta = sc.nextInt();

				if (pregunta <= clientes.get(cliente).cuentas.size() && pregunta > 0) {
					cuenta1 = numcuenta1.get(pregunta);
					tipo_moneda1 = clientes.get(cliente).cuentas.get(cuenta1).getTipo_moneda();
				} else {
					error = true;
					System.out.println("Tiene que ser un número entre 1 y " + clientes.get(cliente).cuentas.size());
				}
			} catch (Exception e) {
				error = true;
				System.out.println("Tiene que ser un número entre 1 y " + clientes.get(cliente).cuentas.size());
				sc.next();
			}

		} while (error);
		do {
			System.out.println("Introduzca el IBAN");
			mismo = false;
			iban = sc.next();
			for (int j = 0; j < clientes.size(); j++) {
				for (int i = 0; i < clientes.get(j).cuentas.size(); i++) {
					try {
						if (clientes.get(cliente).cuentas.get(i).getIban().equals(iban)) {
							mismo = true;
							break;
						}
					} catch (Exception e) {
					}

					if (clientes.get(j).cuentas.get(i).getIban().equals(iban)) {
						cuenta2 = i;
						cliente2 = j;
						elegido = true;
						break;
					}

				}
				if (elegido) {
					break;
				}
			}
			if (elegido) {
				do {
					System.out.println("¿Cuánto va a transferir?");
					error = false;
					try {
						dinero = sc.nextInt();
						if (validacion_numpositivo(dinero) && dinero <= clientes.get(cliente).cuentas.get(cuenta1).getSaldo()) {
							System.out.println("¿Es urgente la transacción?(S o  N)");
							do {
								sc.nextLine();
								correcto = false;
								preguntaU = sc.next();
								if (preguntaU.equalsIgnoreCase("S")) {
									urgente = true;
								} else if (preguntaU.equalsIgnoreCase("N")) {

									urgente = false;
								} else {
									correcto = true;
									System.out.println("Tiene que ser S o N");
								}

							} while (correcto);
							System.out.println("¿Quiere justificante?");
							do {
								sc.nextLine();
								correcto = false;
								preguntaU = sc.next();
								if (preguntaU.equalsIgnoreCase("S")) {
									justificante = true;
									justificantePrecio = 3;
								} else if (preguntaU.equalsIgnoreCase("N")) {
								} else {
									correcto = true;
									System.out.println("Tiene que ser S o N");
								}

							} while (correcto);
							float preciourgencia = 0;
							preciourgencia = (float) (dinero * 0.03);
							if (urgente && preciourgencia < 3.95) {
								preciourgencia = (float) 3.95;

							} else if (!urgente && preciourgencia < 20) {
								preciourgencia = (float) 20;
							}
							int dineroquitado = (int) (dinero + justificantePrecio + preciourgencia);
							clientes.get(cliente).cuentas.get(cuenta1).retirar_saldo(dineroquitado);
							if (tipo_moneda1.equals(tipo_moneda2)) {
								clientes.get(cliente2).cuentas.get(cuenta2).ingresar_dinero(dinero);
								imprimir_clientes();
								System.out.println("realizado con exito");
							} else if (tipo_moneda1.equals("euro")) {
								dinero *= 1.22;
								clientes.get(cliente2).cuentas.get(cuenta2).ingresar_dinero(dinero);
								imprimir_clientes();
								System.out.println("realizado con exito");
							} else if (tipo_moneda1.equals("dollar")) {
								dinero /= 1.22;
								clientes.get(cliente2).cuentas.get(cuenta2).ingresar_dinero(dinero);
								imprimir_clientes();
								System.out.println("realizado con exito");
							} else {
								System.out.println("ha habido un problema");
								error=true;
							}
							if(dinero==0) {
								System.out.println("el numero tiene que ser superior a 10");
								error=true;
								
							}
							if (justificante) {

								System.out.println("justificante realizado ");

							}

						} else if(validacion_numpositivo(dinero)){
							System.out.println("no tiene suficiente saldo");
							error=true;

						}
						if(!validacion_numpositivo(dinero)){
							error = true;
							System.out.println("el nummero introducido es negativo");
						}
					} catch (Exception e) {
						error = true;
						System.out.println("tiene que introducir un numero entero");
						sc.next();
					}
				} while (error);
			} else if (mismo) {
				System.out.println("no puedes poner un iban de tus cuentas");
				error = true;

			} else {
				System.out.println("no existe ese iban");
				error = true;
			}
			
		} while (error);
	}

	/* terminado 
	 * solucionado error de numero negativo como ingreso*/
	public static void contratar_depositos() {
		
		String[][] datos = { { "junior", "18", "0.5", "500" }, { "senior", "25", "0.65", "5000" },
				{ "MAX", "36", "0.8", "20000" } };
		// Depósito MAX(mínimo 20.000€-adultos) 36 meses al 0,8% TAE*
		double tae = 0, interes, beneficiobruto;
		int pregunta, dinero, numdeposito = 0, minimo = 0, plazo = 0;
		String tipodeposito = "";
		boolean error = false, salir = false;
		imprimir_tipo_deposito();
		do {
			error = false;
			try {
				pregunta = sc.nextInt();
				tipodeposito = tipos_depositos.get(pregunta);
				for (int i = 0; i < datos.length; i++) {
					if (tipodeposito.equals(datos[i][0])) {
						plazo = Integer.parseInt(datos[i][1]);
						tae = Double.parseDouble(datos[i][2]);
						minimo = Integer.parseInt(datos[i][3]);
					}
				}
			} catch (Exception e) {
				System.out.println("Solo puede ser entre 1 y " + tipos_depositos.size());
				error = true;
				sc.nextLine();
			}
		} while (error);
		if (!tipodeposito.equals("salir")) {

			imprimir_cuentas_disponibles(minimo);
			do {
				System.out.println("¿Qué cuenta vas a usar para avalar el dinero?");
				error = false;
				try {
					pregunta = sc.nextInt();
					int cuenta = cuentaAvaladora.get(pregunta);
					System.out.println("¿Cuánto vas a ingresar?(mínimo " + minimo + "€)");
					do {
						error = false;
						try {
							dinero = sc.nextInt();
							if (dinero < minimo) {
								if(dinero>=0) {
								System.out.println("Tiene que ser más de " + minimo);
								}else {
									System.out.println("Tiene que ser un numero positivo");
								}
								error = true;
							} else if (dinero > clientes.get(cliente).cuentas.get(cuenta).getSaldo()) {
								System.out.println("No tiene suficiente saldo");
								error = true;
							} else {
								numdeposito = clientes.get(cliente).depositos.size() - 1;
								clientes.get(cliente).nuevodeposito(numdeposito, dinero, plazo, tae, tipodeposito);
								clientes.get(cliente).cuentas.get(cuenta).retirar_saldo(dinero);
								System.out.println("Interés total");
								interes = plazo * tae / 12;
								System.out.println(interes + "%");
								System.out.println("Beneficio total");
								beneficiobruto = dinero + dinero * interes;
								System.out.println(beneficiobruto - (beneficiobruto * 21 / 100));
								imprimir_depositos();
								System.out.println("realizado con exito");
							}
						} catch (Exception e) {
							System.out.println("Solo puedes meter números enteros");
							error = true;
							sc.next();
						}
					} while (error);
				} catch (Exception e) {
					System.out.println("Solo puedes introducir entre 1 y " + cuentaAvaladora.size());
					error = true;
					sc.nextLine();
				}
			} while (error);
		}

	}

	/* realizado iban auto pasa a fase de pruebas pruebas */
	public static void crear_cuenta() {
		String letras = "ABCDEFGHIJKLMÑOPQRSTUVWXYZ",letra_iban="",iban_cuenta;
		letra_iban=letras.charAt(cliente)+"";
		StringBuilder sb = new StringBuilder();
		sb.append(clientes.get(cliente).cuentas.size()+1);
		sb.append(letra_iban);
		iban_cuenta=sb.toString();
		List<String> preguntas = new ArrayList<String>();
		int contador = 0, pregunta;
		String tipo = "", tipomoneda = "";
		boolean error = true;
		System.out.println("1.Crear cuenta corriente");
		preguntas.add("corriente");
		for (int i = 0; i < clientes.get(cliente).cuentas.size(); i++) {
			if (!clientes.get(cliente).cuentas.get(i).getTipo_cuenta().equals("nomina")) {
				contador++;

			}
			if (contador == clientes.get(cliente).cuentas.size()) {
				System.out.println("2.Crear cuenta nómina");
				preguntas.add("nomina");

			}
		}
		do {
			error = false;
			try {
				pregunta = sc.nextInt();
				switch (preguntas.get(pregunta - 1)) {
				case "corriente":
					tipo = "corriente";
					break;
				case "nomina":
					tipo = "nomina";
					break;

				}
			} catch (Exception e) {
				if (preguntas.size() == 1) {
					System.out.println("Solo puede escoger la opción 1");
				} else {
					System.out.println("Solo puede escoger entre 1 y 2");
				}
				sc.nextLine();
				error = true;
			}
		} while (error);

		do {
			error=false;
			System.out.println("Elija:");
			System.out.println("1.Cuenta en euros");
			System.out.println("2.Cuenta en dollar");
			try {
				pregunta = sc.nextInt();
				switch (pregunta) {
				case 1:
					tipomoneda = "euro";
					break;
				case 2:
					tipomoneda = "dollar";
					break;
				default:
					if (preguntas.size() == 1) {
						System.out.println("Solo puede escoger la opción 1");
						error=true;
					} else {
						System.out.println("Solo puede escoger entre 1 y 2");
						error=true;
					}
				}
			} catch (Exception e) {
				if (preguntas.size() == 1) {
					System.out.println("Solo puede escoger la opción 1");
					error=true;
				} else {
					System.out.println("Solo puede escoger entre 1 y 2");
					error=true;
				}
				sc.nextLine();

			}
		} while (error);
		// public Cuenta(String iban, String tipo_moneda, String tipo_cuenta) {
		clientes.get(cliente).usuario_nuevacuenta(iban_cuenta, tipomoneda, tipo);
		System.out.println("realizado con exito");

	}
	
	public static void eliminar_cuenta() {
		HashMap<Integer, Integer> numcuenta1 = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> numcuenta2 = new HashMap<Integer, Integer>();
		int cuenta1 = 0, cuenta2 = 0, pregunta, dinero;
		String tipo_moneda1 = "", tipo_moneda2 = "";
		boolean error = true;
		for (int i = 0; i < clientes.get(cliente).cuentas.size(); i++) {
			
				System.out.println((i+1) + "-" + clientes.get(cliente).cuentas.get(i));

			
		}

		System.out.println("¿que cuenta vas a eliminar?");

		do {
			error = false;
			try {
				pregunta = sc.nextInt();

				if (pregunta <= clientes.get(cliente).cuentas.size() && pregunta > 0) {
					cuenta1 =pregunta-1;
					tipo_moneda1 = clientes.get(cliente).cuentas.get(cuenta1).getTipo_moneda();
				} else {
					error = true;
					System.out.println("Tiene que ser un número entre 1 y " + clientes.get(cliente).cuentas.size());
				}
			} catch (Exception e) {
				error = true;
				System.out.println("Tiene que ser un número entre 1 y " + clientes.get(cliente).cuentas.size());
				sc.next();
			}
		} while (error);
		
		if(clientes.get(cliente).cuentas.get(cuenta1).getSaldo()>0) {
			System.out.println("¿A qué cuenta vas a transferirlo el dinero de esa cuenta?");
			for (int i = 0; i < clientes.get(cliente).cuentas.size(); i++) {
				if (i != cuenta1) {
					cuenta2++;
					System.out.println((cuenta2) + "-" + clientes.get(cliente).cuentas.get(i));
					numcuenta2.put(cuenta2, i);
				}
			}
	
			do {
				error = false;
				try {
					pregunta = sc.nextInt();
	
					if (pregunta <= cuenta2 && pregunta > 0) {
						cuenta2 = numcuenta2.get(pregunta);
						tipo_moneda2 = clientes.get(cliente).cuentas.get(cuenta2).getTipo_moneda();
					} else {
						error = true;
						System.out.println("Tiene que ser un número entre 1 y " + cuenta2);
					}
				} catch (Exception e) {
					error = true;
					System.out.println("Tiene que ser un número entre 1 y " + cuenta2);
					sc.next();
				}
			} while (error);
		
					dinero = clientes.get(cliente).cuentas.get(cuenta1).getSaldo();
					clientes.get(cliente).transferencia(cuenta1, cuenta2, dinero, tipo_moneda1, tipo_moneda2);
					System.out.println("realizado con exito");

		
	}
	clientes.get(cliente).eliminarcuenta(cuenta1);	
	}
	public static void alterar_cuentas() {
		int pregunta;
		boolean error=false;
		System.out.println("1.eliminar cuenta");
		System.out.println("2.crear cuenta");
		do {
			error=false;
			try {
				pregunta=sc.nextInt();
				switch(pregunta) {
				case 1:
					eliminar_cuenta();
					break;
				case 2:
					crear_cuenta();
					break;
				default:
					System.out.println("tiene que ser entre 1 y 2");
				
				}
				}catch(Exception e) {
				error=true;
				System.out.println("tiene que ser entre 1 y 2");
				sc.nextLine();
				
			}
		}while(error);
		
	}
	
	
//cargar y guardar datos
	public static void guardarDatos() {

		try {
			FileOutputStream fichero = null;
			fichero = new FileOutputStream(".\\src\\practica7\\cuentas.dat");
			ObjectOutputStream escribiendoFichero = new ObjectOutputStream(fichero);
			escribiendoFichero.writeObject(clientes);
			escribiendoFichero.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	public static void crear_usuarios() {
		// cliente 0
		String pregunta="";
		boolean correcto;
		System.out.println("¿quiere cargar los datos del fichero?(si no se sobrescribiran los datos por defecto)");
		do {
			System.out.println("responda S o N");
			correcto = false;
			pregunta = sc.next();
			if (pregunta.equalsIgnoreCase("S")) {
				cargar_usuarios();
			} else if (pregunta.equalsIgnoreCase("N")) {
				usuarios();
			} else {
				correcto = true;
				System.out.println("Tiene que ser S o N");
			}

		} while (correcto);

	}
	public static void cargar_usuarios() {
		FileInputStream ficheroEntrada = null;
		try {
			ficheroEntrada = new FileInputStream(".\\src\\practica7\\cuentas.dat");
			ObjectInputStream entradaDatos = new ObjectInputStream(ficheroEntrada);
			clientes = (List<Cliente>) entradaDatos.readObject();
		} catch (Exception e) {
			System.out.println("Fallo en carga del fichero \n Pasando a carga secundaria");
			usuarios();
		}
		
		
	}
	public static void usuarios() {
		
		
		clientes.add(new Cliente("Jeff ", "Preston Bezos", "Hombre", LocalDate.of(1964, 01, 12), "72343698G",
				"amazon", "0666"));
		// cuentas
		clientes.get(0).nuevacuenta("1A", 350000, "dollar", "nomina");
		clientes.get(0).nuevacuenta("2A", 1000000, "dollar", "corriente");
		clientes.get(0).nuevacuenta("3A", 565000, "euro", "corriente");
		// depositos
		// int iban, int saldo, int plazo, double tae, String tipo_deposito
		clientes.get(0).nuevodeposito(1, 375000, 36, 0.8, "MAX");
		// -----------------------------
		// cliente 1
		clientes.add(new Cliente("Ana Patricia ", "Botín Sanz", "Mujer", LocalDate.of(1960, 10, 04), "08269547N",
				"santander", "1234"));
		// cuentas
		clientes.get(1).nuevacuenta("1B", 15000, "euro", "corriente");
		clientes.get(1).nuevacuenta("2B", 182000, "euro", "corriente");
		// depositos
		clientes.get(1).nuevodeposito(1, 56754, 25, 0.65, "Senior");
		// -----------------------------
		// cliente 2
		clientes.add(
				new Cliente("Mark ", "Lenders", "Hombre", LocalDate.of(2007, 07, 07), "94827645W", "toho", "9999"));
		// cuentas
		clientes.get(2).nuevacuenta("1C", 0, "euro", "corriente");
	
		
	}
	public static void loggear() {
		String usuario, pregunta, contraseña;
		int opcion;
		boolean error = true, continuar = true, correcto = true;

		crear_usuarios();
		guardarDatos();

		// comprobar si el usuario existe y la contraseña

		do {
			System.out.println("Introduzca su usuario");
			usuario = sc.next().toLowerCase();
			for (int j = 0; j < clientes.size(); j++) {
				if (clientes.get(j).getUsuario().equals(usuario)) {

					do {
						System.out.println("Introduzca su contraseña");
						contraseña = sc.next();
						if (clientes.get(j).getPin().equals(contraseña)) {
							error = false;
							continuar = false;
							cliente = j;

						} else {

							System.out.println("Contraseña incorrecta");
							System.out.println("¿Desea volver a intentarlo?(S o N)");
							do {
								correcto = false;
								pregunta = sc.next();
								if (pregunta.equalsIgnoreCase("S")) {
									continuar = true;
								} else if (pregunta.equalsIgnoreCase("N")) {
									continuar = false;
									error = true;
								} else {
									correcto = true;
									System.out.println("Tiene que ser S o N");
								}

							} while (correcto);
						}
					} while (continuar);
					break;

				} else if (j == clientes.size() - 1) {
					error = true;
					System.out.println("No existe ese usuario");
				}
			}

		} while (error);

		// imprimir los depositos y las cuentas
		System.out.println("Bienvenido " + clientes.get(cliente).getNombre() + clientes.get(cliente).getApellidos());
		
		
		
	}
}

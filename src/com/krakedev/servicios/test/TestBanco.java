package com.krakedev.servicios.test;

import com.krakedev.financiero.entidades.Cliente;
import com.krakedev.financiero.entidades.Cuenta;
import com.krakedev.financiero.servicios.Banco;

public class TestBanco {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Cliente cl1 = new Cliente();
		Cliente cl2 = new Cliente("1727392035","Brayan","Narváez");
		
		Banco banco = new Banco();
		
		Cuenta cuentaCreada1 = banco.crearCuenta(cl1);
		cuentaCreada1.imprimir();
		
		Cuenta cuentaCreada2 = banco.crearCuenta(cl2);
		cuentaCreada2.imprimir();
		
		System.out.println("----DEPOSITO----");
		banco.depositar(1000, cuentaCreada1);
		cuentaCreada1.imprimir();
		
		System.out.println("----DEPOSITO2----");
		banco.depositar(500, cuentaCreada2);
		cuentaCreada2.imprimir();
		
		boolean resultado;
		
		System.out.println("----RETIRO----");
		resultado = banco.retirar(300, cuentaCreada2);
		System.out.println(resultado);
		cuentaCreada2.imprimir();
		
		System.out.println("----RETIRO2----");
		resultado = banco.retirar(300, cuentaCreada2);
		System.out.println(resultado);
		cuentaCreada2.imprimir();
		
		System.out.println("----TRANSFERENCIA----");
		resultado = banco.transferir(cuentaCreada1, cuentaCreada2, 200);
		System.out.println(resultado);
		cuentaCreada1.imprimir();
		cuentaCreada2.imprimir();
		
		System.out.println("----TRANSFERENCIA2----");
		resultado = banco.transferir(cuentaCreada1, cuentaCreada2, 1000);
		System.out.println(resultado);
		cuentaCreada1.imprimir();
		cuentaCreada2.imprimir();
		
		
	}

}

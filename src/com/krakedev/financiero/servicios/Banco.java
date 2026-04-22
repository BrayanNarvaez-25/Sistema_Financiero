package com.krakedev.financiero.servicios;

import com.krakedev.financiero.entidades.Cliente;
import com.krakedev.financiero.entidades.Cuenta;

public class Banco {
	private int ultimoCodigo = 1000;

	public Banco() {
	}

	public int getUltimoCodigo() {
		return ultimoCodigo;
	}

	public void setUltimoCodigo(int ultimoCodigo) {
		this.ultimoCodigo = ultimoCodigo;
	}

	public Cuenta crearCuenta(Cliente cliente) {
		String codigoStr = ultimoCodigo + "";
		Cuenta cuenta = new Cuenta(codigoStr);

		ultimoCodigo++;

		cuenta.setPropietario(cliente);
		return cuenta;
	}

	public boolean depositar(double monto, Cuenta cuenta) {
		double saldoActual = cuenta.getSaldoActual();
		if (monto > 0) {
			saldoActual += monto;
			cuenta.setSaldoActual(saldoActual);
			return true;
		} else {
			return false;
		}
	}

	public boolean retirar(double monto, Cuenta cuenta) {
		double saldoActual = cuenta.getSaldoActual();
		if (monto > 0 && monto <= saldoActual) {
			double nuevoSaldo = saldoActual - monto;
			cuenta.setSaldoActual(nuevoSaldo);
			return true;
		} else {
			return false;
		}
	}

	public boolean transferir(Cuenta origen, Cuenta destino, double monto) {
		double saldoOrigen = origen.getSaldoActual();
		// double saldoDestino = destino.getSaldoActual();
		if (monto > 0 && monto <= saldoOrigen && !origen.getId().equals(destino.getId())) {
			// RETIRAR DE ORIGEN
			// double nuevoSaldoOrigen = saldoOrigen - monto;
			// origen.setSaldoActual(nuevoSaldoOrigen);
			// AGREGAR A DESTINO
			// saldoDestino += monto;
			// destino.setSaldoActual(saldoDestino);
			return retirar(monto, origen) && depositar(monto, destino);
		} else {
			return false;
		}
	}

}

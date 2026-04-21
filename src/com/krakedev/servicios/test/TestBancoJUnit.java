package com.krakedev.servicios.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.krakedev.financiero.entidades.Cliente;
import com.krakedev.financiero.entidades.Cuenta;
import com.krakedev.financiero.servicios.Banco;

public class TestBancoJUnit {

    private Banco banco;
    private Cliente cliente1;
    private Cliente cliente2;
    private Cuenta cuenta1;
    private Cuenta cuenta2;

    private static final double DELTA = 0.0001;

    @BeforeEach
    public void setUp() {
        // Se inicializa el banco y clientes antes de cada prueba
        banco = new Banco();

        cliente1 = new Cliente("0102030405", "Juan", "Perez");
        cliente2 = new Cliente("0607080910", "Maria", "Lopez");

        // Se crean cuentas para cada cliente
        cuenta1 = banco.crearCuenta(cliente1);
        cuenta2 = banco.crearCuenta(cliente2);
    }

    // ============================
    // PRUEBA CREAR CUENTA
    // ============================
    @Test
    public void testCrearCuenta() {
        // Contexto: Se crea una cuenta y se valida que tenga datos correctos

        assertNotNull(cuenta1);
        assertEquals("1000", cuenta1.getId()); // primer código
        assertEquals(cliente1, cuenta1.getPropietario());

        // Verificamos que el código incrementa
        assertEquals(1002, banco.getUltimoCodigo());
    }

    // ============================
    // PRUEBAS DEPOSITAR
    // ============================
    @Test
    public void testDepositoValido() {
        // Contexto: depósito positivo debe aumentar saldo

        boolean resultado = banco.depositar(100, cuenta1);

        assertTrue(resultado);
        assertEquals(100, cuenta1.getSaldoActual(), DELTA);
    }

    @Test
    public void testDepositoInvalido() {
        // Contexto: depósito negativo NO debe afectar saldo

        boolean resultado = banco.depositar(-50, cuenta1);

        assertFalse(resultado);
        assertEquals(0, cuenta1.getSaldoActual(), DELTA);
    }

    // ============================
    // PRUEBAS RETIRAR
    // ============================
    @Test
    public void testRetiroValido() {
        // Contexto: primero depositamos, luego retiramos un monto válido

        banco.depositar(200, cuenta1);
        boolean resultado = banco.retirar(100, cuenta1);

        assertTrue(resultado);
        assertEquals(100, cuenta1.getSaldoActual(), DELTA);
    }

    @Test
    public void testRetiroSaldoInsuficiente() {
        // Contexto: intentar retirar más de lo que hay

        banco.depositar(50, cuenta1);
        boolean resultado = banco.retirar(100, cuenta1);

        assertFalse(resultado);
        assertEquals(50, cuenta1.getSaldoActual(), DELTA);
    }

    @Test
    public void testRetiroMontoNegativo() {
        // Contexto: monto negativo no permitido

        banco.depositar(100, cuenta1);
        boolean resultado = banco.retirar(-20, cuenta1);

        assertFalse(resultado);
        assertEquals(100, cuenta1.getSaldoActual(), DELTA);
    }

    // ============================
    // PRUEBAS TRANSFERIR
    // ============================
    @Test
    public void testTransferenciaValida() {
        // Contexto: transferencia correcta entre dos cuentas distintas

        banco.depositar(200, cuenta1);

        boolean resultado = banco.transferir(cuenta1, cuenta2, 100);

        assertTrue(resultado);
        assertEquals(100, cuenta1.getSaldoActual(), DELTA);
        assertEquals(100, cuenta2.getSaldoActual(), DELTA);
    }

    @Test
    public void testTransferenciaSaldoInsuficiente() {
        // Contexto: no debe permitir transferir más de lo disponible

        banco.depositar(50, cuenta1);

        boolean resultado = banco.transferir(cuenta1, cuenta2, 100);

        assertFalse(resultado);
        assertEquals(50, cuenta1.getSaldoActual(), DELTA);
        assertEquals(0, cuenta2.getSaldoActual(), DELTA);
    }

    @Test
    public void testTransferenciaMismoId() {
        // Contexto: no debe permitir transferir a la misma cuenta

        banco.depositar(100, cuenta1);

        boolean resultado = banco.transferir(cuenta1, cuenta1, 50);

        assertFalse(resultado);
        assertEquals(100, cuenta1.getSaldoActual(), DELTA);
    }

    @Test
    public void testTransferenciaMontoNegativo() {
        // Contexto: monto inválido

        banco.depositar(100, cuenta1);

        boolean resultado = banco.transferir(cuenta1, cuenta2, -50);

        assertFalse(resultado);
    }
}
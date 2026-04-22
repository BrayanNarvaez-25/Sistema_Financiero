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
        banco = new Banco();

        cliente1 = new Cliente("0102030405", "Juan", "Perez");
        cliente2 = new Cliente("0607080910", "Maria", "Lopez");

        cuenta1 = banco.crearCuenta(cliente1);
        cuenta2 = banco.crearCuenta(cliente2);
    }

    // ============================
    // CREAR CUENTA
    // ============================
    @Test
    public void testCrearCuenta() {
        assertNotNull(cuenta1);
        assertEquals("1000", cuenta1.getId());
        assertEquals(cliente1, cuenta1.getPropietario());
        assertEquals(1002, banco.getUltimoCodigo());
    }

    // ============================
    // SETTER
    // ============================
    @Test
    public void testSetUltimoCodigo() {
        banco.setUltimoCodigo(2000);
        assertEquals(2000, banco.getUltimoCodigo());
    }

    // ============================
    // DEPOSITAR
    // ============================
    @Test
    public void testDepositoValido() {
        assertTrue(banco.depositar(100, cuenta1));
        assertEquals(100, cuenta1.getSaldoActual(), DELTA);
    }

    @Test
    public void testDepositoNegativo() {
        assertFalse(banco.depositar(-50, cuenta1));
        assertEquals(0, cuenta1.getSaldoActual(), DELTA);
    }

    @Test
    public void testDepositoCero() {
        assertFalse(banco.depositar(0, cuenta1));
    }

    // ============================
    // RETIRAR
    // ============================
    @Test
    public void testRetiroValido() {
        banco.depositar(200, cuenta1);

        assertTrue(banco.retirar(100, cuenta1));
        assertEquals(100, cuenta1.getSaldoActual(), DELTA);
    }

    @Test
    public void testRetiroExacto() {
        banco.depositar(100, cuenta1);

        assertTrue(banco.retirar(100, cuenta1));
        assertEquals(0, cuenta1.getSaldoActual(), DELTA);
    }

    @Test
    public void testRetiroExcedeSaldo() {
        banco.depositar(50, cuenta1);

        assertFalse(banco.retirar(100, cuenta1));
        assertEquals(50, cuenta1.getSaldoActual(), DELTA);
    }

    @Test
    public void testRetiroNegativo() {
        banco.depositar(100, cuenta1);

        assertFalse(banco.retirar(-20, cuenta1));
    }

    @Test
    public void testRetiroCero() {
        banco.depositar(100, cuenta1);

        assertFalse(banco.retirar(0, cuenta1));
    }

    // ============================
    // TRANSFERIR
    // ============================
    @Test
    public void testTransferenciaValida() {
        banco.depositar(200, cuenta1);

        assertTrue(banco.transferir(cuenta1, cuenta2, 100));

        assertEquals(100, cuenta1.getSaldoActual(), DELTA);
        assertEquals(100, cuenta2.getSaldoActual(), DELTA);
    }

    @Test
    public void testTransferenciaSaldoInsuficiente() {
        banco.depositar(50, cuenta1);

        assertFalse(banco.transferir(cuenta1, cuenta2, 100));
    }

    @Test
    public void testTransferenciaMismaCuenta() {
        banco.depositar(100, cuenta1);

        assertFalse(banco.transferir(cuenta1, cuenta1, 50));
    }

    @Test
    public void testTransferenciaMontoNegativo() {
        banco.depositar(100, cuenta1);

        assertFalse(banco.transferir(cuenta1, cuenta2, -50));
    }

    @Test
    public void testTransferenciaMontoCero() {
        banco.depositar(100, cuenta1);

        assertFalse(banco.transferir(cuenta1, cuenta2, 0));
    }
}
package org.iesalandalus.programacion.alquilervehiculos;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.ModeloCascada;
import org.iesalandalus.programacion.alquilervehiculos.vista.FactoriaVista;

public class MainApp {

	public static void main(String[] args) {
		Controlador controlador = new Controlador(new ModeloCascada(FactoriaFuenteDatos.FICHEROS),
				FactoriaVista.TEXTO.crear());
		controlador.comenzar();
	}

}

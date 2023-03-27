package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;

public class Alquileres implements IAlquileres {

	private List<Alquiler> coleccionAlquileres;

	public Alquileres() {
		coleccionAlquileres = new ArrayList<>();
	}

	public List<Alquiler> get() {
		return new ArrayList<>(coleccionAlquileres);
	}

	public List<Alquiler> get(Cliente cliente) {
		ArrayList<Alquiler> arrayAlquileresCliente = new ArrayList<>();

		for (Alquiler alquilerLista1 : coleccionAlquileres) {
			if (alquilerLista1.getCliente().equals(cliente)) {
				arrayAlquileresCliente.add(alquilerLista1);
			}
		}

		return arrayAlquileresCliente;
	}

	public List<Alquiler> get(Vehiculo vehiculo) {
		ArrayList<Alquiler> arrayAlquileresCliente = new ArrayList<>();

		for (Alquiler alquilerLista2 : coleccionAlquileres) {
			if (alquilerLista2.getVehiculo().equals(vehiculo)) { // MOdificado
				arrayAlquileresCliente.add(alquilerLista2);
			}
		}

		return arrayAlquileresCliente;
	}

	public int getCantidad() {
		return coleccionAlquileres.size();
	}

	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede insertar un alquiler nulo.");
		}
		comprobarAlquiler(alquiler.getCliente(), alquiler.getVehiculo(), alquiler.getFechaAlquiler()); // Modificado
		coleccionAlquileres.add(alquiler);
	}

	private void comprobarAlquiler(Cliente cliente, Vehiculo vehiculo, LocalDate fechaAlquiler)
			throws OperationNotSupportedException {
		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getFechaDevolucion() == null) {
				// Sin devolver
				if (alquiler.getCliente().equals(cliente)) {
					throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver.");
				} else if (alquiler.getVehiculo().equals(vehiculo)) {
					throw new OperationNotSupportedException("ERROR: El turismo está actualmente alquilado.");
					// MENSAJE DE ERROR INVALIDO, no es un turismo, es un vehiculo.
				}
			} else {
				// Devueltos
				if ((alquiler.getCliente().equals(cliente))
						&& (alquiler.getFechaDevolucion().compareTo(fechaAlquiler) >= 0)) {
					throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");
				} else if ((alquiler.getVehiculo().equals(vehiculo))
						&& (alquiler.getFechaDevolucion().compareTo(fechaAlquiler) >= 0)) {
					throw new OperationNotSupportedException("ERROR: El turismo tiene un alquiler posterior.");
					// MENSAJE DE ERROR INVALIDO, no es un turismo, es un vehiculo.
				}
			}
		}
	}

	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un cliente nulo.");
		}

		Alquiler alquiler = getAlquilerAbierto(cliente);
		if (alquiler == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese cliente.");
		}
		alquiler.devolver(fechaDevolucion);
	}

	private Alquiler getAlquilerAbierto(Cliente cliente) {
		Alquiler alquilerEncontrado = null;
		Iterator<Alquiler> iterator = coleccionAlquileres.iterator();

		while (iterator.hasNext() && alquilerEncontrado == null) {
			Alquiler alquilerRecorrido = iterator.next(); //

			if (alquilerRecorrido.getCliente().equals(cliente) && alquilerRecorrido.getFechaDevolucion() == null) {
				alquilerEncontrado = alquilerRecorrido;
			}
		}

		return alquilerEncontrado;
	}

	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un vehículo nulo.");
		}

		Alquiler alquiler = getAlquilerAbierto(vehiculo);
		if (alquiler == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese vehículo.");
		}
		alquiler.devolver(fechaDevolucion);
	}

	private Alquiler getAlquilerAbierto(Vehiculo vehiculo) {
		Alquiler alquilerEncontrado = null;
		Iterator<Alquiler> iterador = coleccionAlquileres.iterator();

		while (iterador.hasNext() && alquilerEncontrado == null) {
			Alquiler alquilerRecorrido = iterador.next();
			if (alquilerRecorrido.getVehiculo().equals(vehiculo)) {
				alquilerEncontrado = alquilerRecorrido;
			}
		}

		return alquilerEncontrado;
	}

	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede borrar un alquiler nulo.");
		}

		if (buscar(alquiler) == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
		}
		coleccionAlquileres.remove(alquiler);
	}

	public Alquiler buscar(Alquiler alquiler) {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede buscar un alquiler nulo.");
		}
		int indice = coleccionAlquileres.indexOf(alquiler);
		return (indice == -1) ? null : coleccionAlquileres.get(indice);
	}
}

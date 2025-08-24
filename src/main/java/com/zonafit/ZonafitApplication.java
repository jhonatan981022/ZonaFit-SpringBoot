package com.zonafit;

import com.zonafit.modelo.Cliente;
import com.zonafit.servicio.IClienteServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ZonafitApplication implements CommandLineRunner {

	@Autowired
	private IClienteServicio clienteServicio;
	private static final Logger logger = LoggerFactory.getLogger(ZonafitApplication.class);

	public static void main(String[] args) {
		logger.info("Iniciando la aplicacion!");
		//Levantar la fabrica de Spring
		SpringApplication.run(ZonafitApplication.class, args);
		logger.info("Aplicacion finalizada!");
	}

	@Override
	public void run(String... args) throws Exception {
		zonaFitApp();
	}

	private void zonaFitApp() {
		logger.info("--- Aplicacion Zona Fit (GYM) ---");
		Scanner input = new Scanner(System.in);
		boolean salir = false;

		//Menu
		while (!salir) {
			int opcion = mostrarOpciones(input);

			switch (opcion) {
				case 1:
					listarClientes();
					break;
				case 2:
					buscarClientePorId(input);
					break;
				case 3:
					agregarCliente(input);
					break;
				case 4:
					actualizarCliente(input);
					break;
				case 5:
					eliminarCliente(input);
					break;
				case 6:
					logger.info("Hasta pronto!");
					salir = true;
					break;
			}
		}
	}

	private int mostrarOpciones(Scanner input) {
		while (true) {
			System.out.println("--- Menu Zona Fit ---");
			System.out.println("1. Listar clientes");
			System.out.println("2. Buscar cliente por ID");
			System.out.println("3. Agregar cliente");
			System.out.println("4. Actualizar cliente");
			System.out.println("5. Eliminar cliente");
			System.out.println("6. Salir");
			System.out.print("Elige una opcion: ");

			try {
				int opcion = Integer.parseInt(input.nextLine());
				if (opcion >= 1 && opcion <= 6) {
					return opcion;
				} else {
					logger.info("Opcion fuera de rango: 1 - 6");
				}
			} catch (Exception e) {
				logger.info("Opcion invalida: solo numeros enteros entre 1 - 6");
			}
		}
	}

	//Metodos
	//Listar clientes
	private void listarClientes() {
		logger.info("--- Lista de clientes ---");
		List<Cliente> clientes = clienteServicio.listarClientes();
		if (!clientes.isEmpty()) {
			clientes.forEach(cliente -> logger.info(cliente.toString()));
		} else {
			logger.info("No hay clientes en la lista!");
		}
	}

	//Buscar cliente por ID
	private void buscarClientePorId(Scanner input) {
		logger.info("--- Buscar cliente por ID ---");
		System.out.print("Ingrese el ID del cliente que desea buscar: ");
		int id_buscado = Integer.parseInt(input.nextLine());
		Cliente buscado = clienteServicio.buscarClientePorId(id_buscado);
		if (buscado != null) {
			logger.info("Cliente: " + buscado);
		} else {
			logger.info("No se ha encontrado un cliente con ese ID");
		}
	}

	//Agregar cliente
	private void agregarCliente(Scanner input) {
		logger.info("--- Agregar cliente ---");
		System.out.print("Ingrese nombre: ");
		String nombre = input.nextLine();
		System.out.print("Ingrese apellido: ");
		String apellido = input.nextLine();
		System.out.print("Ingrese membresía: ");
		int membresia = Integer.parseInt(input.nextLine());
		Cliente nuevo = new Cliente(null, nombre, apellido, membresia);
		clienteServicio.guardarCliente(nuevo);
		logger.info("Cliente agregado con éxito.");
	}

	//Actualizar cliente
	private void actualizarCliente(Scanner input) {
		logger.info("--- Modificar cliente ---");
		System.out.print("ID del cliente que desea actualizar: ");
		int id_actualizado = Integer.parseInt(input.nextLine());
		Cliente actualizado = clienteServicio.buscarClientePorId(id_actualizado);

		if (actualizado != null) {
			System.out.println("Cliente actual: " + actualizado);
			System.out.print("Ingrese nuevo nombre: ");
			String nuevoNombre = input.nextLine();
			System.out.print("Ingrese nuevo apellido: ");
			String nuevoApellido = input.nextLine();
			System.out.print("Ingrese nueva membresía: ");
			int nuevaMembresia = Integer.parseInt(input.nextLine());
			actualizado = new Cliente(id_actualizado, nuevoNombre, nuevoApellido, nuevaMembresia);
			clienteServicio.guardarCliente(actualizado);
			logger.info("Cliente actualizado con exito.");
		} else {
			System.out.println("Cliente no encontrado con ese ID");
		}
	}

	//Eliminar cliente
	private void eliminarCliente(Scanner input) {
		logger.info("--- Eliminar cliente ---");
		System.out.print("ID del cliente que desea eliminar: ");
		int id_eliminado = Integer.parseInt(input.nextLine());
		Cliente eliminado = clienteServicio.buscarClientePorId(id_eliminado);
		if (eliminado != null) {
			System.out.println("Cliente a eliminar: " + eliminado);
			System.out.print("Estas seguro que deseas eliminarlo? (s/n): ");
			String confirmacion = input.nextLine();
			if (confirmacion.equalsIgnoreCase("s")) {
				clienteServicio.eliminarCliente(id_eliminado);
				logger.info("Cliente eliminado con éxito.");
			} else {
				System.out.println("Operacion cancelada!");
			}
		} else {
			System.out.println("Cliente no encontrado con ese ID");
		}
	}
}

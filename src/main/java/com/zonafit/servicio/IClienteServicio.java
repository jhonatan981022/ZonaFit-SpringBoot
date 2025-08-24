package com.zonafit.servicio;

import com.zonafit.modelo.Cliente;

import java.util.List;

public interface IClienteServicio {

    List<Cliente> listarClientes();

    Cliente buscarClientePorId(Integer id);

    void guardarCliente(Cliente cliente);

    void eliminarCliente(Integer id);
}

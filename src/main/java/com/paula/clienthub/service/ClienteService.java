package com.paula.clienthub.service;

import com.paula.clienthub.model.Cliente;
import com.paula.clienthub.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listar(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return clienteRepository.findAll();
        }
        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id " + id));
    }

    public Cliente crear(Cliente cliente) {
        if (clienteRepository.existsByDni(cliente.getDni())) {
            throw new RuntimeException("Ya existe un cliente con el DNI " + cliente.getDni());
        }
        cliente.setFechaAlta(LocalDate.now());
        return clienteRepository.save(cliente);
    }

    public Cliente actualizar(Long id, Cliente datos) {
        Cliente cliente = buscarPorId(id);
        cliente.setNombre(datos.getNombre());
        cliente.setApellidos(datos.getApellidos());
        cliente.setEmail(datos.getEmail());
        cliente.setTelefono(datos.getTelefono());
        return clienteRepository.save(cliente);
    }

    public void eliminar(Long id) {
        Cliente cliente = buscarPorId(id);
        clienteRepository.delete(cliente);
    }
}

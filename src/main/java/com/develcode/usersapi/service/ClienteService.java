package com.develcode.usersapi.service;

import com.develcode.usersapi.dao.ClienteRepository;
import com.develcode.usersapi.dto.AtualizarClienteRequest;
import com.develcode.usersapi.dto.IncluirClienteRequest;
import com.develcode.usersapi.entity.Cliente;
import com.develcode.usersapi.exception.ClienteNaoEncontradoException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;
    private  ObjectMapper mapper = new ObjectMapper();


    public ClienteService(com.develcode.usersapi.dao.ClienteRepository clienteRepository) {
        this.repository = clienteRepository;
    }

    public List<Cliente> listar() {
        return repository.findAll();
    }

    public Cliente getCliente(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado " + id));
    }

    public Cliente incluir(String clienteData, MultipartFile file) throws IOException {

        byte[] bytes = file.getBytes();

        IncluirClienteRequest incluirClienteRequest = mapper.readValue(clienteData, IncluirClienteRequest.class);
        incluirClienteRequest.setFoto(bytes);

        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(incluirClienteRequest, cliente);


        repository.save(cliente);

        return cliente;
    }

    public Cliente atualizar(AtualizarClienteRequest atualizarClienteRequest) {
        Cliente cliente = repository.findById(atualizarClienteRequest.getId()).get();

        BeanUtils.copyProperties(atualizarClienteRequest, cliente);
        repository.save(cliente);
        return cliente;
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}

package com.develcode.usersapi.controller;

import com.develcode.usersapi.dto.AtualizarClienteRequest;
import com.develcode.usersapi.dto.IncluirClienteResponse;
import com.develcode.usersapi.entity.Cliente;
import com.develcode.usersapi.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private  ClienteService clienteService;
    private  ObjectMapper mapper = new ObjectMapper();

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping()
    public ResponseEntity<List<Cliente>> listar() {
        return new ResponseEntity<>(clienteService.listar(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> ler(@PathVariable("id") Long id) {
        return new ResponseEntity<>(clienteService.getCliente(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<IncluirClienteResponse> incluir(@RequestParam String clienteData, @RequestParam(value ="file") MultipartFile file) throws IOException {

        Cliente cliente = clienteService.incluir(clienteData, file);

        IncluirClienteResponse clienteResponse = new IncluirClienteResponse();
        BeanUtils.copyProperties(cliente, clienteResponse);

        return new ResponseEntity<>(clienteResponse, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<Cliente> atualizar(@RequestParam String clienteData, @RequestParam(value = "file", required = false) final MultipartFile file ) throws IOException {
        byte[] bytes = file.getBytes();

        AtualizarClienteRequest atualizarClienteRequest = mapper.readValue(clienteData, AtualizarClienteRequest.class);

        if (file != null){
            atualizarClienteRequest.setFoto(bytes);
        }

        Cliente cliente = clienteService.atualizar(atualizarClienteRequest);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
        clienteService.deletar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

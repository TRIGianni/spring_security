package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }

    record HelloRequest(String username){}
    @PostMapping("/private/hello")
    @PreAuthorize("hasAuthority('WRITE')")
    public String privateHello(@RequestBody HelloRequest request){
        return "Hello "+request.username;
    }

    @GetMapping("/private/hello")
    @PreAuthorize("hasAuthority('READ')")
    public String apiHello(){
        return "🔐 Hello World 🔐";
    }

    public record Document(Long id, String owner, String content) {}
    private static final Map<Long, Document> documentsInMemory = Map.of(
            1L, new Document(1L, "admin", "Document confidentiel Admin"),
            2L, new Document(2L, "user", "Document confidentiel de user")
    );

    @GetMapping("/private/documents/{id}")
    @PreAuthorize("hasAuthority('READ')")
    @PostAuthorize("returnObject.owner() == authentication.name or hasRole('ADMIN')")
    public Document getDocument(@PathVariable Long id) {
        System.out.println("Méthode exécutée pour le document " + id);
        Document doc = documentsInMemory.get(id);
        if (doc == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return doc;
    }

}

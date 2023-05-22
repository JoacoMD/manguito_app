package app.manguito.backend.controllers;

import app.manguito.backend.dto.UpdateCategoriasDTO;
import app.manguito.backend.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/categorias")
@RestController
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<String>> getAllCategorias() {
        return ResponseEntity.ok(categoriaService.getAll());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> updateCategorias(@RequestBody UpdateCategoriasDTO dto) {
        this.categoriaService.updateCategorias(dto);
        return ResponseEntity.ok("Categorias actualizadas");
    }
}

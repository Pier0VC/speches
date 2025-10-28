package com.speches.pwc.controller;

import com.speches.pwc.dtos.CreateSpeechRequest;
import com.speches.pwc.dtos.SpeechListDTO;
import com.speches.pwc.entities.Speech;
import com.speches.pwc.services.SpeechService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/speeches")
@CrossOrigin
public class SpeechController {

    private final SpeechService service;

    public SpeechController(SpeechService service) { this.service = service; }

    // ✅ Listar SOLO por grupo
    @GetMapping
    public List<Speech> listarPorGrupo(@RequestParam Long grupoId) {
        return service.listarPorGrupo(grupoId);
    }

    // ✅ Crear: grupoId como query param, body con {titulo, contenido}
    @PostMapping
    public Speech crear(@RequestParam Long grupoId, @RequestBody @Valid CreateSpeechRequest body) {
        Speech s = new Speech();
        s.setTitulo(body.titulo);
        s.setContenido(body.contenido);
        // estado/idioma/createdAt se setean con defaults (ver abajo)
        return service.crear(grupoId, s);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    // Listar TODOS con (titulo, contenido, grupo)
    @GetMapping("/all")
    public List<SpeechListDTO> listarTodosConGrupo() {
        return service.listarTodosConGrupo();
    }
}

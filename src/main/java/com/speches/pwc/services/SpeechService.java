package com.speches.pwc.services;

import com.speches.pwc.dtos.SpeechListDTO;
import com.speches.pwc.entities.Grupo;
import com.speches.pwc.entities.Speech;
import com.speches.pwc.repositories.GrupoRepository;
import com.speches.pwc.repositories.SpeechRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeechService {

    private final SpeechRepository repo;
    private final GrupoRepository grupoRepo;

    public SpeechService(SpeechRepository repo, GrupoRepository grupoRepo) {
        this.repo = repo;
        this.grupoRepo = grupoRepo;
    }

    public List<Speech> listarPorGrupo(Long grupoId) {
        return repo.findByGrupoId(grupoId);
    }

    public Speech crear(Long grupoId, Speech s) {
        Grupo g = grupoRepo.findById(grupoId).orElseThrow();
        s.setId(null);
        s.setGrupo(g);
        return repo.save(s);
    }

    public Speech obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public List<SpeechListDTO> listarTodosConGrupo() {
        return repo.findAllWithGrupo();
    }


}

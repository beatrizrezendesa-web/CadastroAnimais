package br.com.projeto.cadastro_animais.repository;


import br.com.projeto.cadastro_animais.model.Adotante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdotanteRepository extends JpaRepository<Adotante, Long> {
    boolean existsByCpf(String cpf);
}


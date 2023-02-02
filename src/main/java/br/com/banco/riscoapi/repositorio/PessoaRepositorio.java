package br.com.banco.riscoapi.repositorio;

import br.com.banco.riscoapi.modelo.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepositorio extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findByCpfCnpj(String cpfCnpj);
}

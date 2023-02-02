package br.com.banco.riscoapi.repositorio;

import br.com.banco.riscoapi.modelo.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepositorio extends JpaRepository<Empresa, Long> {
    Optional<Empresa> findByPessoaJuridicaId(Long id);
}

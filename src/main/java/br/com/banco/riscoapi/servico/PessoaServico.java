package br.com.banco.riscoapi.servico;

import br.com.banco.riscoapi.exceptopn.RegraDeNegocioException;
import br.com.banco.riscoapi.modelo.Pessoa;
import br.com.banco.riscoapi.repositorio.PessoaRepositorio;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaServico extends ResponseEntityExceptionHandler {

    @Autowired
    private PessoaRepositorio pessoaRepositorio;

    @Transactional
    public Pessoa salvar(Pessoa pessoa){
        if(existePessoaComEsteCpfCnpj(pessoa.getCpfCnpj()))
            throw new RuntimeException("Conflito: CPF ou CNPJ ja cadastrado!");
        return pessoaRepositorio.save(pessoa);
    }

    private boolean existePessoaComEsteCpfCnpj(String cpfCnpj) {
        Optional<Pessoa> pessoaOptional = pessoaRepositorio.findByCpfCnpj(cpfCnpj);
        return pessoaOptional.isPresent();
    }

    public Pessoa buscarPessoaPorCpfCnpj(String cpfCnpj) {
        Optional<Pessoa> pessoaOptional = pessoaRepositorio.findByCpfCnpj(cpfCnpj);
        if(pessoaOptional.isEmpty())
            throw new RegraDeNegocioException(String.format("Pessoa %s não encontrada!", cpfCnpj));
        return pessoaOptional.get();
    }
    public List<Pessoa> buscarTodos() {
        return pessoaRepositorio.findAll();
    }

    public Optional<Pessoa> buscarPorId(Long id){
        return pessoaRepositorio.findById(id);
    }

    @Transactional
    public Pessoa atualizar(Long id, Pessoa pessoa){
        Optional<Pessoa> pessoaOptional = buscarPorId(id);
        if(pessoaOptional.isPresent()){
            var pessoaAtualizar = pessoaOptional.get();
            BeanUtils.copyProperties(pessoa, pessoaAtualizar, "cpfCnpj");
            return pessoaRepositorio.save(pessoaAtualizar);
        } else {
            throw new RegraDeNegocioException(
                    String.format("Pessoa %s não encontrada!", id));
        }
    }

}

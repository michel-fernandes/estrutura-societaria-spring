package br.com.banco.riscoapi.servico;

import br.com.banco.riscoapi.excecao.RegraDeNegocioException;
import br.com.banco.riscoapi.modelo.Empresa;
import br.com.banco.riscoapi.modelo.Pessoa;
import br.com.banco.riscoapi.repositorio.EmpresaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpresaServico {
    @Autowired
    private EmpresaRepositorio empresaRepositorio;
    @Autowired
    private PessoaServico pessoaServico;
    @Autowired
    CalculadoraComprometimentoServico calculadora;

    @Transactional
    public Empresa salvarEmpresa(Long id, List<String> pessoasDTO) {
        List<Pessoa> socios = pessoasDTO.stream()
                .map(p->pessoaServico.buscarPessoaPorCpfCnpj(p)).collect(Collectors.toList());
        Optional<Pessoa> pessoaJuridica = pessoaServico.buscarPorId(id);
        if(pessoaJuridica.isPresent()){
            Empresa empresa = Empresa.builder()
                    .pessoaJuridica(pessoaJuridica.get())
                    .quadroSocietario(socios)
                    .build();
            return empresaRepositorio.save(empresa);
        } else {
            throw new RegraDeNegocioException(
                    String.format("Empresa %s não encontrada!", id));
        }

    }

    public Empresa buscarEmpresaPorId(long id) {
        Optional<Empresa> empresaOptional = empresaRepositorio.findById(id);
        if(empresaOptional.isPresent()){
            return empresaOptional.get();
        } else {
            throw new RegraDeNegocioException(
                    String.format("Empresa %s não encontrada!", id));
        }
    }

/*    public List<Pessoa> buscarSociosPorEmpresaId(long id) {
        Optional<Empresa> empresaOptional = empresaRepositorio.findById(id);
        if(empresaOptional.isPresent()){
            return empresaOptional.get().getQuadroSocietario();
        } else {
            throw new RegraDeNegocioException(
                    String.format("Empresa %s não encontrada!", id));
        }
    }*/

/*    public List<Empresa> buscarTodos() {
        return empresaRepositorio.findAll();
    }*/

/*    @Transactional
    public Empresa atualizar(Long id, List<String> pessoasDTO){
        Optional<Empresa> empresaOptional = empresaRepositorio.findById(id);
        Pessoa pessoaPJ = empresaOptional.get().getPessoaJuridica();
        if(empresaOptional.isPresent()){
            List<Pessoa> socios = pessoasDTO.stream()
                    .map(p->pessoaServico.buscarPessoaPorCpfCnpj(p)).collect(Collectors.toList());
            Empresa empresaAtualizar = Empresa.builder()
                    .pessoaJuridica(pessoaPJ)
                    .quadroSocietario(socios)
                    .build();
            return empresaRepositorio.save(empresaAtualizar);
        } else {
            throw new RegraDeNegocioException(
                    String.format("Empresa %s não encontrada!", id));
        }
    }*/

    public Empresa calcularPorId(Long id) {
        Optional<Empresa> empresaOptional = empresaRepositorio.findById(id);
        if(empresaOptional.isPresent()){
            empresaOptional.get().setComprometimentoFinanceiro(calculadora
                    .calcularComprometimentoFinanceiro(empresaOptional.get()));
            return empresaRepositorio.save(empresaOptional.get());
        } else {
            throw new RegraDeNegocioException(
                    String.format("Empresa %s não encontrada!", id));
        }
    }

/*    public Empresa buscarEmpresaPorPessoaJuridicaId(Long id) {
        Optional<Empresa> empresaOptional = empresaRepositorio.findByPessoaJuridicaId(id);
        if(empresaOptional.isPresent()){
            return empresaOptional.get();
        } else {
            throw new RegraDeNegocioException(
                    String.format("Empresa %s não encontrada!", id));
        }
    }*/
}

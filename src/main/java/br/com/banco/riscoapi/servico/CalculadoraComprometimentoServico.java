package br.com.banco.riscoapi.servico;

import br.com.banco.riscoapi.excecao.RegraDeNegocioException;
import br.com.banco.riscoapi.modelo.Empresa;
import br.com.banco.riscoapi.modelo.Pessoa;
import br.com.banco.riscoapi.repositorio.EmpresaRepositorio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class CalculadoraComprometimentoServico {
    private List<String> pessoaFisicas = new ArrayList<>();
    private List<String> pessoaJuridicas = new ArrayList<>();
    private Pessoa pessoaJuridica;
    private Double comprometimentoFinanceiro;
    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    public Double calcularComprometimentoFinanceiro(Empresa empresa) {
        pessoaFisicas.clear();
        pessoaJuridicas.clear();
        comprometimentoFinanceiro = 0.;
        pessoaJuridicas.add(empresa.getPessoaJuridica().getCpfCnpj());
        pessoaJuridica = empresa.getPessoaJuridica();
        comprometimentoFinanceiro = empresa.getPessoaJuridica().getBemImovel();
        comprometimentoFinanceiro = calcularComprometimentoFinanceiro(empresa.getQuadroSocietario());
        return comprometimentoFinanceiro;
    }
    private Double calcularComprometimentoFinanceiro(List<Pessoa> socios){
        List<Pessoa> pessoasFisica = socios.stream()
                .filter(pessoa -> pessoa.getCpfCnpj().length() == 14).collect(Collectors.toList());
        pessoasFisica.forEach(pf -> incluirPFPilha(pf));
        List<Pessoa> pessoasJuridica = socios.stream()
                .filter(pessoa -> pessoa.getCpfCnpj().length() == 18).collect(Collectors.toList());
        pessoasJuridica.forEach(pj -> incluirPJNaPilha(pj));

        return comprometimentoFinanceiro;
    }

    private void incluirPFPilha(Pessoa pessoaFisica){
        if(pessoaFisicas.isEmpty()){
            pessoaFisicas.add(pessoaFisica.getCpfCnpj());
            comprometimentoFinanceiro += pessoaFisica.getBemImovel();
        } else {
            if(!pessoaFisicas.contains(pessoaFisica.getCpfCnpj())){
                pessoaFisicas.add(pessoaFisica.getCpfCnpj());
                comprometimentoFinanceiro += pessoaFisica.getBemImovel();
            }
        }
    }

    private void incluirPJNaPilha(Pessoa pessoaJuridica) {
        if(!pessoaJuridicas.contains(pessoaJuridica.getCpfCnpj())){
            pessoaJuridicas.add(pessoaJuridica.getCpfCnpj());
            comprometimentoFinanceiro += pessoaJuridica.getBemImovel();
            Optional<Empresa> empresaOptional = empresaRepositorio.findByPessoaJuridicaId(pessoaJuridica.getId());
            if(empresaOptional.isPresent()){
                calcularComprometimentoFinanceiro(empresaOptional.get().getQuadroSocietario());;
            } else {
                throw new RegraDeNegocioException(
                        String.format("Empresa %s não encontrada!", pessoaJuridica.getId()));
            }
        }

    }
}

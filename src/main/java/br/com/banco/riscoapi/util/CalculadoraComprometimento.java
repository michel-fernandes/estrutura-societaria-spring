package br.com.banco.riscoapi.util;

import br.com.banco.riscoapi.modelo.Empresa;
import br.com.banco.riscoapi.modelo.Pessoa;
import br.com.banco.riscoapi.servico.EmpresaServico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculadoraComprometimento {
    private List<String> pessoaFisicas;
    private List<String> pessoaJuridicas;
    private Pessoa pessoaJuridica;
    private Double comprometimentoFinanceiro;
    @Autowired
    private EmpresaServico empresaServico;
    public CalculadoraComprometimento(Empresa empresa) {
        this.pessoaJuridicas.add(empresa.getPessoaJuridica().getCpfCnpj());
        this.pessoaJuridica = empresa.getPessoaJuridica();
        this.comprometimentoFinanceiro = empresa.getPessoaJuridica().getBemImovel();
        this.comprometimentoFinanceiro = calcularComprometimentoFinanceiro(empresa.getQuadroSocietario());
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
            Empresa pessoaJuridicaNova = empresaServico.buscarEmpresaPorPessoaJuridicaId(pessoaJuridica.getId());
            calcularComprometimentoFinanceiro(pessoaJuridicaNova.getQuadroSocietario());
        }

    }
}

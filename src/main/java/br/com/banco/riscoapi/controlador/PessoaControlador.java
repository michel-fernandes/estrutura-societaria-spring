package br.com.banco.riscoapi.controlador;

import br.com.banco.riscoapi.dto.request.EmpresaDTO;
import br.com.banco.riscoapi.dto.request.PessoaDTO;
import br.com.banco.riscoapi.modelo.Pessoa;
import br.com.banco.riscoapi.servico.EmpresaServico;
import br.com.banco.riscoapi.servico.PessoaServico;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "Pessoa")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/pessoa")
public class PessoaControlador {

    @Autowired
    PessoaServico pessoaServico;
    @Autowired
    EmpresaServico empresaServico;

    @ApiOperation(value = "Criar", nickname = "criaPessoa")
    @PostMapping
    public ResponseEntity<Object> salvarPessoa(@RequestBody @Valid PessoaDTO pessoaDTO){
        var pessoa = new Pessoa();
        BeanUtils.copyProperties(pessoaDTO, pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaServico.salvar(pessoa));
    }

    @ApiOperation(value = "Listar", nickname = "listarTodasPessoas")
    @GetMapping
    public ResponseEntity<List<Pessoa>> buscarTodos(){
        return ResponseEntity.status(HttpStatus.OK).body((pessoaServico.buscarTodos()));
    }

    @ApiOperation(value = "Buscar por ID", nickname = "buscarPessoasPorID")
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable(value = "id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body((pessoaServico.buscarPorId(id)));
    }

    @ApiOperation(value = "Atualizar por ID", nickname = "atualizarPessoaPorID")
    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarPessoaJuridica(@PathVariable(value = "id") Long id,
                                                        @RequestBody @Valid PessoaDTO pessoaDTO){
        var pessoa = new Pessoa();
        BeanUtils.copyProperties(pessoaDTO, pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaServico.atualizar(id, pessoa));
    }

    @ApiOperation(value = "Criar empresa", nickname = "criarEmpresa")
    @PostMapping("/{id}/empresa")
    public ResponseEntity<Object> salvarEmpresa(@PathVariable(value = "id") Long id,
                                                                 @RequestBody @Valid EmpresaDTO empresaDTO){
        if (empresaDTO.getPessoasDTO().size()<1)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: deve conter ao menos um socio.");
        return ResponseEntity.status(HttpStatus.CREATED).body(
                empresaServico.salvarEmpresa(id, empresaDTO.getPessoasDTO()));
    }

    @ApiOperation(value = "Calcular Comprometimento Financeiro", nickname = "calcularCompreometimentoFinanceiro")
    @GetMapping("/empresa/{id}/comprometimentoFinanceiro")
    public ResponseEntity<Object> calcularPorId(@PathVariable(value = "id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body((empresaServico.calcularPorId(id)));
    }

    @ApiOperation(value = "Buscar empresa", nickname = "buscarEmpresa")
    @GetMapping("/empresa/{id}")
    public ResponseEntity<Object> salvarPessoaFisica(@PathVariable(value = "id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(
                empresaServico.buscarEmpresaPorId(id));
    }

}

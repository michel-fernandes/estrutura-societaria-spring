package br.com.banco.riscoapi.excecao;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice // semelhante há um listener
public class GestaoVendasTratamentoExcecao extends ResponseEntityExceptionHandler {
    /**
     *
     */
    private static final String CONSTANT_VALIDATION_NOT_BLANK = "NotBlank";
    private static final String CONSTANT_VALIDATION_NOT_NULL = "NotNull";
    private static final String CONSTANT_VALIDATION_LENGTH = "Length";
    private static final String CONSTANT_VALIDATION_PATTERN = "Pattern";
    private static final String CONSTANT_VALIDATION_MIN = "Min";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<Erro> erros = gerarListaDeErros(ex.getBindingResult());

        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    //como o método não existe em ResponseEntityExceptionHandler é necessario criar um novo e indicar para o contralador por meio do
    //@ @ExceptionHandler indicando a classe erro que está sendo ouvida
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
            WebRequest request) {
        String msgUsuario = "Recurso não encontrado.";
        String msgDesenvolvedor = ex.toString();
        List<Erro> erros = Arrays.asList(new Erro(msgUsuario, msgDesenvolvedor));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
            WebRequest request) {
        String msgUsuario = "Recurso não encontrado.";
        String msgDesenvolvedor = ex.toString();
        List<Erro> erros = Arrays.asList(new Erro(msgUsuario, msgDesenvolvedor));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<Object> handleRegraDeNegocioException(RegraDeNegocioException ex,
            WebRequest request) {
        String msgUsuario = ex.getMessage();
        String msgDesenvolvedor = ex.getMessage();
        List<Erro> erros = Arrays.asList(new Erro(msgUsuario, msgDesenvolvedor));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private List<Erro> gerarListaDeErros(BindingResult bindingResult) {
        List<Erro> erros = new ArrayList<Erro>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            String msgUsuario = tratarMensagemDeErroParaUsuario(fieldError);
            String msgDesenvolvedor = fieldError.toString();
            erros.add(new Erro(msgUsuario, msgDesenvolvedor));
        });
        return erros;
    }

    private String tratarMensagemDeErroParaUsuario(FieldError fieldError) {
        if (fieldError.getCode().equals(CONSTANT_VALIDATION_NOT_BLANK)) {
            return fieldError.getDefaultMessage().concat(" é obrigatório.");
        }
        if (fieldError.getCode().equals(CONSTANT_VALIDATION_NOT_NULL)) {
            return fieldError.getDefaultMessage().concat(" é obrigatório.");
        }
        if (fieldError.getCode().equals(CONSTANT_VALIDATION_LENGTH)) {
            return fieldError.getDefaultMessage().concat(String.format(" deve ter entre %s e %s caracteres.",
                    fieldError.getArguments()[2], fieldError.getArguments()[1]));
        }
        if (fieldError.getCode().equals(CONSTANT_VALIDATION_PATTERN)) {
            return fieldError.getDefaultMessage().concat(" formato inválido.");
        }
        if (fieldError.getCode().equals(CONSTANT_VALIDATION_MIN)) {
            return fieldError.getDefaultMessage().concat(String.format(" deve ser maior ou igual a %s.",
                    fieldError.getArguments()[1]));
        }
        return fieldError.toString();
    }
}

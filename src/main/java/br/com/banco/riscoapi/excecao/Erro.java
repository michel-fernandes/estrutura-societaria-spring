package br.com.banco.riscoapi.excecao;

public class Erro {
    
    private final String msgUsuario;
    private final String msgDesenvolvedor;
    
    public Erro(String msgUsuario, String msgDesenvolvedor) {
        this.msgUsuario = msgUsuario;
        this.msgDesenvolvedor = msgDesenvolvedor;
    }

    public String getMsgUsuario() {
        return msgUsuario;
    }

    public String getMsgDesenvolvedor() {
        return msgDesenvolvedor;
    }

    
    
}

package br.com.banco.riscoapi.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel("Socio(s) DTO")
public class PessoaDTO {
    @ApiModelProperty(value = "CPF ou CNPJ")
    @NotNull(message = "CPF ou CNPJ")
    @Pattern( regexp = "(^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$)|(^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{2}$)",
            message = "CPF fora do padrao 000.000.000-00 " +
                      "ou CNPJ  fora do padrao 00.000.000/0000-00")
    private String cpfCnpj;
    @Column(name = "Bens em imovel")
    @NotNull(message = "CPF")
    private Double bemImovel;

}

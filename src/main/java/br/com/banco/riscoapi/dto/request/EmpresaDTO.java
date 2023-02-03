package br.com.banco.riscoapi.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@ApiModel("Empresa DTO")
public class EmpresaDTO {

    @ApiModelProperty(value = "Lista de Socios")
    private List<@NotNull(message = "Lista de Socios")
                @Pattern(regexp = "(^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$)|(^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{2}$)",
            message = "CPF fora do padrao 000.000.000-00 ou CNPJ  fora do padrao 00.000.000/0000-00") String> sociosCpfCnpj;
}

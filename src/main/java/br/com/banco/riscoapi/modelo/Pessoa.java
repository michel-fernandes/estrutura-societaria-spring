package br.com.banco.riscoapi.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_PESSOA")
public class Pessoa implements Serializable {
        private static final  long serialVersionUID= 1L;
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        private Long id;
        @Column(name = "cpfCnpj", unique = true, updatable = false, nullable = false, length = 20)
        private String cpfCnpj;
        @Column(name = "bem_imovel", nullable = false)
        private Double bemImovel;

}

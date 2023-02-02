package br.com.banco.riscoapi.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TB_PESSOA_JURIDICA")
public class Empresa implements Serializable {
    private static final  long serialVersionUID= 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @OneToOne
    private Pessoa pessoaJuridica;
    @Column(name = "comprometimentoFinanceiro")
    private  Double comprometimentoFinanceiro;
    @OneToMany
    private List<Pessoa> quadroSocietario;
}

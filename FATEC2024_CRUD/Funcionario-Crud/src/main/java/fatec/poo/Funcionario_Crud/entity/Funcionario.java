package fatec.poo.Funcionario_Crud.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Entity
@Table(name="funcionario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Funcionario {

    // Getters e Setters

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "funcionario_seq")
    @SequenceGenerator(name = "funcionario_seq", sequenceName = "funcionario_seq", allocationSize = 1)
    private Integer id;

    @Setter
    @Getter
    @Column(name="nome")
    private String nome;

    @Setter
    @Getter
    @Column(name="cpf")
    private String cpf;

    @Getter
    @Setter
    @Column(name="email")
    private String email;

    @Setter
    @Getter
    @Column(name="telefone")
    private String telefone;

    @Getter
    @Setter
    @Column(name="cargo")
    private String cargo;

    @Getter
    @Setter
    @Column(name="salario")
    private Double salario;

    @Column(name="data_admissao")
    @JsonFormat(pattern = "yyyy-MM-dd")  // Garante que o formato da data seja 'yyyy-MM-dd'
    private Date data_admissao;

    @Column(name = "departamento")
    private String departamento;

    @Column(name="status")
    private String status;  // ou outro tipo dependendo da sua necessidade, como 'Boolean' ou 'Integer'

}



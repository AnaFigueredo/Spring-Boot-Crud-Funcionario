package fatec.poo.Funcionario_Crud.repository;


import fatec.poo.Funcionario_Crud.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

// A interface FuncionarioRepository estende JpaRepository, que já fornece os métodos de CRUD
public interface FuncionarioRepository extends JpaRepository<Funcionario,Integer> {
    // Aqui podemos adicionar métodos personalizados, caso necessário
}

package fatec.poo.Funcionario_Crud.service;

import fatec.poo.Funcionario_Crud.entity.Funcionario;
import fatec.poo.Funcionario_Crud.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public List<Funcionario> listarFuncionarios() {
        return funcionarioRepository.findAll();  // Usando o método findAll do JpaRepository
    }

    public Optional<Funcionario> buscarFuncionarioPorId(Integer id) {
        return funcionarioRepository.findById(id);  // Usando o método findById do JpaRepository
    }

    public Funcionario salvarFuncionario(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);  // Usando o método save do JpaRepository
    }

    public void deletarFuncionario(Integer id) {
        funcionarioRepository.deleteById(id);  // Usando o método deleteById do JpaRepository
    }

    public Funcionario updateFuncionarioService(Funcionario funcionario) {
        // Verifica se o funcionário existe no banco de dados
        Optional<Funcionario> funcionarioExistente = funcionarioRepository.findById(Math.toIntExact(funcionario.getId()));
        // Caso o funcionário não exista, exibe uma mensagem
        if (funcionarioExistente.isPresent()) {
            Funcionario atualizado = funcionarioExistente.get();

            // Atualiza os campos do funcionário existente
            atualizado.setNome(funcionario.getNome());
            atualizado.setCpf(funcionario.getCpf());
            atualizado.setEmail(funcionario.getEmail());
            atualizado.setTelefone(funcionario.getTelefone());
            atualizado.setCargo(funcionario.getCargo());
            atualizado.setSalario(funcionario.getSalario());
            atualizado.setData_admissao(funcionario.getData_admissao());
            atualizado.setStatus(funcionario.getStatus());

            // Salva as alterações e retorna
            return funcionarioRepository.save(atualizado);
        } else {
            throw new RuntimeException("Funcionário com ID " + funcionario.getId() + " não encontrado.");
        }
    }
}



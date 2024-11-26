package fatec.poo.Funcionario_Crud.controller;

import fatec.poo.Funcionario_Crud.entity.Funcionario;
import fatec.poo.Funcionario_Crud.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/funcionarios")

public class FuncionarioController {


    @Autowired
    private FuncionarioService funcionarioService;

    //Apresenta todos os funcionarios cadastrados
    @GetMapping
    public List<Funcionario> listarFuncionarios() {
        return funcionarioService.listarFuncionarios();
    }


    // Busca Funcionario por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarFuncionario(@PathVariable Integer id) {
        Optional<Funcionario> funcionarioOptional = funcionarioService.buscarFuncionarioPorId(id);

        if (funcionarioOptional.isPresent()) {
            // Se o funcionário existir, retorna status 200 OK e o funcionário
            return ResponseEntity.ok(funcionarioOptional.get());
        } else {
            // Se não existir, retorna 404 NOT FOUND com a mensagem personalizada
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário com ID " + id + " não encontrado.");
        }
    }

    // Atualiza um funcionário existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFuncionario(@PathVariable Integer id, @RequestBody Funcionario funcionario) {
        try {
            // Define o ID do funcionário no objeto recebido
            funcionario.setId(id);

            // Chama o serviço para atualizar o funcionário
            Funcionario funcionarioAtualizado = funcionarioService.updateFuncionarioService(funcionario);

            // Retorna o funcionário atualizado
            return ResponseEntity.ok(funcionarioAtualizado);
        } catch (RuntimeException e) {
            // Retorna uma mensagem de erro caso o funcionário não seja encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


   //Cria novo registro
    @PostMapping
    public Funcionario criarFuncionario(@RequestBody Funcionario funcionario) {
        return funcionarioService.salvarFuncionario(funcionario);
    }

    //Deleta dado por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFuncionario(@PathVariable Integer id) {
        funcionarioService.deletarFuncionario(id);
        return ResponseEntity.noContent().build();
    }
}

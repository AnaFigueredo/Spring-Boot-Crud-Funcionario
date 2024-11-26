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

    @GetMapping
    public List<Funcionario> listarFuncionarios() {
        return funcionarioService.listarFuncionarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarFuncionario(@PathVariable Integer id) {
        Optional<Funcionario> funcionarioOptional = funcionarioService.buscarFuncionarioPorId(id);

        if (funcionarioOptional.isPresent()) {
            return ResponseEntity.ok(funcionarioOptional.get());
        } else {
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário com ID " + id + " não encontrado.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFuncionario(@PathVariable Integer id, @RequestBody Funcionario funcionario) {
        try {
            funcionario.setId(id);

            Funcionario funcionarioAtualizado = funcionarioService.updateFuncionarioService(funcionario);

            return ResponseEntity.ok(funcionarioAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public Funcionario criarFuncionario(@RequestBody Funcionario funcionario) {
        return funcionarioService.salvarFuncionario(funcionario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFuncionario(@PathVariable Integer id) {
        funcionarioService.deletarFuncionario(id);
        return ResponseEntity.noContent().build();
    }
}

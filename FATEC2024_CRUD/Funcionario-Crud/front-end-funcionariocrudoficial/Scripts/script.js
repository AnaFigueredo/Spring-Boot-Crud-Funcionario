const apiUrl = "http://localhost:8080/api/funcionarios";

// Função para buscar funcionários por ID ou Nome
async function searchEmployee() {
    const query = document.getElementById("search-id").value.trim();
    const resultDiv = document.getElementById("search-result");

    resultDiv.innerHTML = ""; // Limpar resultados anteriores

    if (!query) {
        resultDiv.innerHTML = `<p class="error-message">Por favor, insira um ID ou Nome.</p>`;
        return;
    }

    try {
        const url = isNaN(query)
            ? `${apiUrl}?nome=${encodeURIComponent(query)}`
            : `${apiUrl}/${query}`;
        const response = await fetch(url);

        if (response.ok) {
            const funcionario = await response.json();
            const funcionarios = Array.isArray(funcionario) ? funcionario : [funcionario];

            if (funcionarios.length > 0) {
                resultDiv.innerHTML = funcionarios
                    .map(
                        (f) => `
                        <div class="employee-card">
                            <p><strong>ID:</strong> ${f.id}</p>
                            <p><strong>Nome:</strong> ${f.nome}</p>
                            <p><strong>Cargo:</strong> ${f.cargo}</p>
                        </div>
                        `
                    )
                    .join("");
            } else {
                resultDiv.innerHTML = `<p class="error-message">Funcionário não encontrado.</p>`;
            }
        } else {
            resultDiv.innerHTML = `<p class="error-message">Erro na consulta ou funcionário não encontrado.</p>`;
        }
    } catch (error) {
        console.error("Erro ao conectar ao servidor:", error);
        resultDiv.innerHTML = `<p class="error-message">Erro ao conectar ao servidor.</p>`;
    }
}

// Limpar pesquisa
document.getElementById("clear-search-btn").addEventListener("click", () => {
    document.getElementById("search-id").value = "";
    document.getElementById("search-result").innerHTML = "";
});

// Mostrar todos os funcionários
document.getElementById("show-all-employees").addEventListener("change", (e) => {
    if (e.target.checked) {
        loadEmployees();
    } else {
        document.getElementById("employee-list-body").innerHTML = "";
    }
});

// Função para carregar todos os funcionários
async function loadEmployees() {
    const tbody = document.getElementById("employee-list-body");
    tbody.innerHTML = "";

    try {
        const response = await fetch(apiUrl);
        if (response.ok) {
            const funcionarios = await response.json();
            funcionarios.forEach(funcionario => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${funcionario.id}</td>
                    <td>${funcionario.nome}</td>
                    <td>${funcionario.cpf}</td>
                    <td>${funcionario.email}</td>
                    <td>${funcionario.telefone}</td>
                    <td>${funcionario.cargo}</td>
                    <td>${funcionario.salario}</td>
                    <td>${funcionario.data_admissao}</td>
                    <td>${funcionario.departamento}</td>
                    <td>${funcionario.status}</td>
                    <td>
                        <button onclick="deleteEmployee(${funcionario.id})">Excluir</button>
                    </td>
                `;
                tbody.appendChild(row);
            });
        }
    } catch {
        alert("Erro ao carregar funcionários.");
    }
}

// Função para excluir um funcionário
async function deleteEmployee(id) {
    if (!confirm("Tem certeza que deseja excluir este funcionário?")) return;

    try {
        const response = await fetch(`${apiUrl}/${id}`, { method: "DELETE" });
        if (response.ok) {
            alert("Funcionário excluído com sucesso!");
            loadEmployees();
        } else {
            alert("Erro ao excluir funcionário.");
        }
    } catch {
        alert("Erro ao conectar ao servidor.");
    }
}

// Abrir modal de novo funcionário
document.getElementById("new-employee-btn").addEventListener("click", () => {
    document.getElementById("new-employee-modal").style.display = "flex";
});

// Fechar modal
document.querySelector(".close-btn").addEventListener("click", () => {
    document.getElementById("new-employee-modal").style.display = "none";
});

// Enviar formulário de novo funcionário
document.getElementById("new-employee-form").addEventListener("submit", async (e) => {
    e.preventDefault();

    // Obter os valores dos campos do formulário
    const novoFuncionario = {
        nome: document.getElementById("nome").value.trim(),
        cpf: document.getElementById("cpf").value.trim(),
        email: document.getElementById("email").value.trim(),
        telefone: document.getElementById("telefone").value.trim(),
        cargo: document.getElementById("cargo").value.trim(),
        salario: parseFloat(document.getElementById("salario").value),
        data_admissao: document.getElementById("data_admissao").value.trim(),
        departamento: document.getElementById("departamento").value.trim(),
        status: document.getElementById("status").value.trim(),
    };

    try {
        const response = await fetch(apiUrl, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(novoFuncionario),
        });

        if (response.ok) {
            alert("Funcionário cadastrado com sucesso!");
            document.getElementById("new-employee-modal").style.display = "none";
            loadEmployees();
        } else {
            alert("Erro ao cadastrar funcionário.");
        }
    } catch {
        alert("Erro ao conectar ao servidor.");
    }
});


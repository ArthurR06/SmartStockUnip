document.addEventListener("DOMContentLoaded", function () {
    fetchSuppliers(); // Chama a função automaticamente ao carregar a página
});

// Função para listar os fornecedores
function fetchSuppliers() {
    const token = localStorage.getItem('authToken'); // Recupera o token

    if (!token) {
        console.error("Token não encontrado. Faça login novamente.");
        alert("Sessão expirada! Faça login novamente.");
        window.location.href = "TelaLogin.html";
        return;
    }

    fetch("http://localhost:8080/supplier/list", {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`, // Adiciona o token no cabeçalho
            "Content-Type": "application/json"
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        atualizarTabela(data); // Chama a função para atualizar a tabela com fornecedores
    })
    .catch(error => {
        console.error("Erro ao buscar fornecedores:", error);
        alert("Erro ao carregar fornecedores. Tente novamente.");
    });
}

// Função para atualizar a tabela com os fornecedores
function atualizarTabela(fornecedores) {
    const tabela = document.getElementById("tabela-fornecedores");
    tabela.innerHTML = ""; // Limpa a tabela antes de atualizar

    fornecedores.forEach(fornecedor => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${fornecedor.id}</td>
            <td>${fornecedor.name}</td>
            <td>${fornecedor.cnpj}</td>
            <td>${fornecedor.phone}</td>
            <td>${fornecedor.email}</td>
        `;
        tabela.appendChild(row);
    });
}

// Função para buscar um fornecedor por ID
function fetchSuppplier() {
    const searchQuery = document.getElementById('input-busca').value.trim(); // Obtém o valor inserido

    // Recupera o token de autenticação do localStorage
    const token = localStorage.getItem('authToken');
    if (!token) {
        console.error("Token não encontrado. Faça login novamente.");
        alert("Sessão expirada! Faça login novamente.");
        window.location.href = "TelaLogin.html"; // Redireciona para login se o token não existir
        return;
    }

    let url;
    // Se o campo estiver vazio, busca todos os produtos
    if (!searchQuery) {
        url = `http://localhost:8080/supplier/list`;
    } 
    // Se for um número, busca por ID
    else if (!isNaN(searchQuery)) {
        url = `http://localhost:8080/supplier/${searchQuery}`;
    } 
    // Caso contrário, busca por nome
    else {
        url = `http://localhost:8080/supplier/searchName?name=${encodeURIComponent(searchQuery)}`;
    }

    // Fazendo a requisição GET para buscar os produtos
    fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Erro ao buscar o supplier: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        // Exibe os produtos encontrados
        if (data) {
            atualizarTabela(Array.isArray(data) ? data : [data]); // Garante que a função recebe um array
        } else {
            alert("Nenhum supplier encontrado!");
        }
    })
    .catch(error => {
        console.error("Erro ao buscar supplier:", error);
        alert("Erro ao tentar buscar os supplier. Tente novamente.");
    });
}

//Criar fornecedor
// Função para cadastrar o endereço
async function cadastrarEndereco() {
    const token = localStorage.getItem('authToken'); // Obtém o token de validação
    const enderecoData = {
        cep: document.getElementById("supplier-cep").value,
        public_place: document.getElementById("supplier-public-place").value,
        number: document.getElementById("supplier-number").value,
        neighborhood: document.getElementById("supplier-neighborhood").value,
        city: document.getElementById("supplier-city").value,
        state: document.getElementById("supplier-state").value,
    };

    try {
        const response = await fetch("http://localhost:8080/adress", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`, // Inclui o token no cabeçalho
            },
            body: JSON.stringify(enderecoData),
        });

        if (!response.ok) throw new Error("Erro ao cadastrar o endereço");

        const result = await response.json();
        enderecoId = result.id; // Armazena o ID do endereço retornado pela API

        alert("Endereço cadastrado com sucesso!");
        document.getElementById("saveSupplierButton").disabled = false; // Habilita o botão de fornecedor
    } catch (error) {
        alert(error.message);
    }
}

// Função para cadastrar o fornecedor
async function cadastrarFornecedor() {
    if (!enderecoId) {
        alert("Cadastre o endereço antes de criar o fornecedor.");
        return;
    }

    const token = localStorage.getItem('authToken'); // Obtém o token de validação
    const fornecedorData = {
        name: document.getElementById("supplier-name").value,
        phone: document.getElementById("supplier-phone").value,
        email: document.getElementById("supplier-email").value,
        cnpj: document.getElementById("supplier-cnpj").value,
        adressId: enderecoId, // Vincula o ID do endereço cadastrado
    };

    try {
        const response = await fetch("http://localhost:8080/supplier", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`, // Inclui o token no cabeçalho
            },
            body: JSON.stringify(fornecedorData),
        });

        if (!response.ok) throw new Error("Erro ao cadastrar o fornecedor");

        alert("Fornecedor cadastrado com sucesso!");
        location.reload(); // Recarrega a página após o cadastro
    } catch (error) {
        alert(error.message);
    }
}

// Função para configurar os eventos onclick
function configurarEventos() {
    document.getElementById("saveSupplierButton").disabled = true; // Inicialmente desativa o botão
}

// Chama a função para configurar os eventos assim que o DOM estiver pronto
document.addEventListener("DOMContentLoaded", function () {
    configurarEventos();
});
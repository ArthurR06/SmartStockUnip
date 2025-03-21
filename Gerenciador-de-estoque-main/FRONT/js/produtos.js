document.addEventListener("DOMContentLoaded", function () {
    fetchProducts();// Carrega os produtos ao iniciar a página
});

// Recupera o token armazenado no localStorage
function getToken() {
    return localStorage.getItem('authToken');
}

// Função para buscar a lista de produtos
function fetchProducts() {
    const token = getToken();
    if (!token) {
        alert("Sessão expirada! Faça login novamente.");
        window.location.href = "TelaLogin.html";
        return;
    }

    fetch("http://localhost:8080/products/list", {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`,
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
        atualizarTabela(data);
    })
    .catch(error => {
        console.error("Erro ao buscar produtos:", error);
        alert("Erro ao carregar produtos. Tente novamente.");
    });
}

// Atualiza a tabela com os produtos
function atualizarTabela(produtos) {
    const tabela = document.getElementById("tableListProducts-body");
    tabela.innerHTML = ""; // Limpa a tabela antes de atualizar

    if (produtos.length === 0) {
        tabela.innerHTML = '<tr><td colspan="6">Nenhum produto encontrado.</td></tr>';
        return;
    }

    produtos.forEach(produto => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${produto.id}</td>
            <td>${produto.name}</td>
            <td>${produto.nameCategory}</td>
            <td>${produto.description}</td>
            <td>${produto.quantity_min}</td>
            <td>${produto.quantity_stock}</td>
            <td>${produto.unit_of_measure}</td>
           
        `;
        tabela.appendChild(row);
    });
}

// Função para cadastrar um novo produto
function createProduct(event) {
    event.preventDefault(); // Evita que o formulário seja enviado normalmente

    // Captura os valores do formulário
    const name = document.getElementById('productName').value;
    const description = document.getElementById('description').value;
    const quantity_min = parseInt(document.getElementById('quantityMin').value);
    const unit_of_measure = document.getElementById('unityMeasure').value;
    const categoryId = document.getElementById('productCategory').value;

    // Monta o objeto JSON do produto
    const productData = {
        name: name,
        description: description,
        location: "string",
        quantity_min: quantity_min,
        unit_of_measure: unit_of_measure,
        status: "ativo",
        categoryId: categoryId
    };

    const token = getToken();
    if (!token) {
        alert("Sessão expirada! Faça login novamente.");
        window.location.href = "TelaLogin.html";
        return;
    };

    fetch("http://localhost:8080/products", {
        method: "POST",
        headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
        },
        body: JSON.stringify(productData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.status} `);
        }
        return response.json();
    })
    .then(() => {
        alert("Produto cadastrado com sucesso!");
        document.getElementById('addProductForm').reset(); // Limpa o formulário
        fetchProducts(); // Atualiza a lista de produtos
    })
    .catch(error => {
        console.error("Erro ao criar produto:", error);
        alert("Erro ao tentar cadastrar o produto. Tente novamente.");
    });
}

// Função para buscar categoria pelo ID
function fetchProduct() {
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
        url = `http://localhost:8080/products/list`;
    } 
    // Se for um número, busca por ID
    else if (!isNaN(searchQuery)) {
        url = `http://localhost:8080/products/${searchQuery}`;
    } 
    // Caso contrário, busca por nome
    else {
        url = `http://localhost:8080/products/searchName?name=${encodeURIComponent(searchQuery)}`;
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
            throw new Error(`Erro ao buscar o produto: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        // Exibe os produtos encontrados
        if (data) {
            atualizarTabela(Array.isArray(data) ? data : [data]); // Garante que a função recebe um array
        } else {
            alert("Nenhum produto encontrado!");
        }
    })
    .catch(error => {
        console.error("Erro ao buscar produto:", error);
        alert("Erro ao tentar buscar os produtos. Tente novamente.");
    });
}

//Formulário
async function carregarCategorias() {
    const token = localStorage.getItem('authToken'); // Substitua pelo token real

    try {
        const response = await fetch("http://localhost:8080/categories/list", {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`, // Adiciona o token no cabeçalho
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {
            throw new Error(`Erro na API: ${response.status}`);
        }

        const categorias = await response.json();
        console.log("Categorias recebidas:", categorias); // Debug no console

        const select = document.getElementById("productCategory");
        select.innerHTML = '<option value="">Selecione uma categoria</option>'; // Reseta o select

        if (!Array.isArray(categorias)) {
            throw new Error("Os dados da API não são um array.");
        }

        categorias.forEach(categoria => {
            if (!categoria.id || !categoria.name) {
                console.warn("Categoria inválida:", categoria);
                return;
            }

            const option = document.createElement("option");
            option.value = categoria.id; // ID que será enviado ao backend
            option.textContent = categoria.name; // Nome visível no select
            select.appendChild(option);
        });

    } catch (error) {
        console.error("Erro ao carregar categorias:", error);
    }
}

document.getElementById("btnAdicionar-Produto").addEventListener("click", carregarCategorias);

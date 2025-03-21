document.addEventListener("DOMContentLoaded", function () {
    fetchStock(); // Carrega os produtos ao iniciar a página
});

// Recupera o token armazenado no localStorage
function getToken() {
    return localStorage.getItem('authToken');
}

// Função para buscar a lista das notas de entrada e saída
function fetchStock() {
    const token = getToken();
    if (!token) {
        alert("Sessão expirada! Faça login novamente.");
        window.location.href = "TelaLogin.html";
        return;
    }

    // Requisição para as entradas
    fetch("http://localhost:8080/invoices", {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Erro na requisição de entradas: ${response.status}`);
        }
        return response.json();
    })
    .then(entradas => {
        // Requisição para as saídas
        fetch("http://localhost:8080/stock-output", {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Erro na requisição de saídas: ${response.status}`);
            }
            return response.json();
        })
        .then(saidas => {
            // Combina entradas e saídas e atualiza a tabela
            const lancamentos = [...entradas, ...saidas]; // Junta as entradas e saídas
            atualizarTabelaPage(lancamentos);
        })
        .catch(error => {
            console.error("Erro ao buscar saídas:", error);
            alert("Erro ao carregar saídas. Tente novamente.");
        });
    })
    .catch(error => {
        console.error("Erro ao buscar entradas:", error);
        alert("Erro ao carregar entradas. Tente novamente.");
    });
}

// Atualiza a tabela com as notas de entrada e saída
function atualizarTabelaPage(lancamentos) {
    const tabela = document.getElementById("tabela-lancamento");
    tabela.innerHTML = ""; // Limpa a tabela antes de atualizar

    if (lancamentos.length === 0) {
        tabela.innerHTML = '<tr><td colspan="6">Nenhum produto encontrado.</td></tr>';
        return;
    }

    lancamentos.forEach(lancamento => {
        // Verificar se a data existe e é válida (para entradas ou saídas)
        const data = new Date(lancamento.outputDate || lancamento.issueDate); // Pode ser de entrada ou saída
        
        // Verifique se a data é válida
        const dataFormatada = isNaN(data.getTime()) ? "Data inválida" : new Intl.DateTimeFormat('pt-BR', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit'
        }).format(data);

        // Verifique se é uma entrada ou saída e atribua os valores corretamente
        let tipoMovimento = "Entrada"; // Default para "Entrada"
        let numeroMovimento = lancamento.invoiceNumber; // Para entradas
        let nomeFornecedor = lancamento.supplierName; // Para entradas
        let valorMovimento = lancamento.totalAmount; // Para entradas

        // Se for saída, ajuste as variáveis
        if (lancamento.outputDate) {
            tipoMovimento = "Saída";
            numeroMovimento = lancamento.id;; // Para saídas não temos um número de nota
            nomeFornecedor = "N/A"; // Para saídas, não tem fornecedor
            valorMovimento = "N/A"; // Para saídas, podemos não ter um valor total diretamente
        }

        const row = document.createElement("tr");
        row.innerHTML = `

            <td>${tipoMovimento}</td> <!-- Mostra "Entrada" ou "Saída" -->
            <td>${numeroMovimento || "N/A"}</td> <!-- Número da nota ou saída -->
            <td>${nomeFornecedor}</td> <!-- Nome do fornecedor (ou N/A para saídas) -->
            <td>${dataFormatada}</td>
            <td>${valorMovimento === "N/A" ? "N/A" : `R$ ${valorMovimento.toFixed(2)}`}</td> <!-- Valor total (ou N/A para saídas) -->
        `;
        tabela.appendChild(row);
    });
}

// Função para cadastrar um novo lançamento (entrada ou saída)

// Função para buscar pelo ID
function fetchVById() {
    const categoryId = document.getElementById('input-busca').value; // Obtém o ID inserido
    if (!categoryId) {
        alert("Por favor, insira um ID.");
        return;
    }

    // Recupera o token de autenticação do localStorage
    const token = localStorage.getItem('authToken');
    if (!token) {
        console.error("Token não encontrado. Faça login novamente.");
        alert("Sessão expirada! Faça login novamente.");
        window.location.href = "TelaLogin.html"; // Redireciona para login se o token não existir
        return;
    }

    // Fazendo a requisição GET para buscar a categoria pelo ID
    fetch(`http://localhost:8080/products/${categoryId}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`, // Adiciona o token de autenticação
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Erro ao buscar a categoria: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        // Exibe os dados da categoria se encontrados
        if (data) {
           //Atualiza a tabelo com os dados recebidos
           atualizarTabela([data])
        } else {
            alert("Categoria não encontrada!");
        }
    })
    .catch(error => {
        console.error("Erro ao buscar categoria:", error);
        alert("Erro ao tentar buscar a categoria. Tente novamente.");
    });
}



let itensNotaFiscal = [];

function addProdutoNota() {
    const productSelect = document.getElementById("productId");
    const priceInput = document.getElementById("price");
    const quantityInput = document.getElementById("inputQuatityMoviment");

    const productId = productSelect.value;
    const productName = productSelect.options[productSelect.selectedIndex].text;
    const price = parseFloat(priceInput.value);
    const quantity = parseInt(quantityInput.value);

    if (!productId || isNaN(price) || isNaN(quantity) || quantity <= 0) {
        alert("Preencha todos os campos corretamente!");
        return;
    }

    const item = {
        productId: parseInt(productId),
        nome: productName, // Adiciona o nome do produto
        quantity: quantity,
        price: price
    };

    itensNotaFiscal.push(item); // Adiciona o item na lista global

    atualizarTabela(); // Atualiza a tabela de itens no modal

    console.log("Itens adicionados:", itensNotaFiscal); // Verifica os itens no console
}


let tipoMovimento = '';  // Variável global para armazenar o tipo de movimento, pode ser 'entrada' ou 'saida'

// Exemplo de como você pode configurar o tipo de movimento dependendo de uma seleção
document.getElementById('tipoMovimento').addEventListener('change', function(event) {
    tipoMovimento = event.target.value;  // 'entrada' ou 'saida'
    atualizarTabela();
});

function atualizarTabela() {
    const tableBody = document.getElementById("productList");
    tableBody.innerHTML = ""; // Limpa a tabela antes de atualizar

    // Verifica se há itens na lista e atualiza a tabela
    itensNotaFiscal.forEach((item, index) => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${item.nome}</td>
            <td>R$ ${item.price.toFixed(2)}</td>
            <td>${item.quantidade}</td>
            <td>${tipoMovimento === 'entrada' ? 'Entrada' : 'Saída'}</td> <!-- Coluna de tipo de movimento -->
            <td><button type="button" class="btn btn-danger btn-sm" onclick="removerProduto(${index})">X</button></td>
        `;
        tableBody.appendChild(row);
    });
}

    function removerProduto(index) {
        itensNotaFiscal.splice(index, 1);
        atualizarTabela();
    }

    async function criarNotaFiscal() {
        const token = getToken(); // Recupera o token do localStorage

        if (!token) {
            console.error("Erro: Token de autenticação não encontrado.");
            return;
        }

        if (itensNotaFiscal.length === 0) {
            alert("Adicione pelo menos um item antes de criar a nota fiscal!");
            return;
        }

        const invoiceNumber = document.getElementById("NumeroNota").value;
        const supplierId = document.getElementById("selectSupplier").value;

        if (!invoiceNumber || !supplierId) {
            alert("Preencha o número da nota e selecione um fornecedor!");
            return;
        }

        try {
            let invoiceItemIds = [];
            console.log(itensNotaFiscal)

            // Criar os itens da nota fiscal e obter os IDs retornados
            for (const item of itensNotaFiscal) {
                const response = await fetch("http://localhost:8080/invoice-items", {
                    method: "POST",
                    headers: {
                        "Authorization": `Bearer ${token}`, // Enviando token corretamente
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        productId: item.productId,
                        quantity: item.quantity,
                        unitPrice: item.price
                    }),
                });

                if (!response.ok) {
                    throw new Error(`Erro ao criar item: ${item.nome}`);
                }

                const data = await response.json();
                invoiceItemIds.push(data.id); // Captura o ID retornado do backend
            }

            // Criar a nota fiscal com os IDs dos itens
            const invoiceData = {
                invoiceNumber: invoiceNumber,
                supplierId: parseInt(supplierId),
                invoiceItemIds: invoiceItemIds
            };

            const invoiceResponse = await fetch("http://localhost:8080/invoices", {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`, // Enviando token corretamente
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(invoiceData),
            });

            if (!invoiceResponse.ok) {
                throw new Error("Erro ao criar a nota fiscal.");
            }

            alert("Nota Fiscal criada com sucesso!");

            // Limpar tudo após o envio
            itensNotaFiscal = [];
            document.getElementById("NumeroNota").value = "";
            document.getElementById("selectSupplier").value = "";
            atualizarTabela();
        } catch (error) {
            console.error(error);
            alert("Erro ao criar a nota fiscal.");
        }
}




async function carregarFornecedor() {
    const token = localStorage.getItem('authToken');

    try {
        const response = await fetch("http://localhost:8080/supplier/list", {
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

        const select = document.getElementById("selectSupplier");
        select.innerHTML = '<option value="">Selecione um fornecedor</option>'; // Reseta o select

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


async function carregarProdutos() {
    const token = localStorage.getItem('authToken');

    try {
        const response = await fetch("http://localhost:8080/products/list", {
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

        const select = document.getElementById("productId");
        select.innerHTML = '<option value="">Selecione um produto</option>'; // Reseta o select

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

async function carregarProdutosSaida() {
    const token = localStorage.getItem('authToken');

    try {
        const response = await fetch("http://localhost:8080/products/list", {
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

        const select = document.getElementById("productIdSaida");
        select.innerHTML = '<option value="">Selecione um produto</option>'; // Reseta o select

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

document.getElementById("btnAdicionar-Produto").addEventListener("click", carregarProdutos);
document.getElementById("btnAdicionar-Produto").addEventListener("click", carregarFornecedor);

// Usar Object.defineProperty para observar alterações no array
Object.defineProperty(window, 'itensNotaFiscal', {
    get: function() {
      return itensNotaFiscal;
    },
    set: function(value) {
      itensNotaFiscal = value;
      console.log('itensNotaFiscal alterado:', itensNotaFiscal); // Exibe no console sempre que o array for alterado
    }
  });

  // Alterna os campos de acordo com o tipo de movimentação (Entrada ou Saída)
function toggleTipoMovimentacao() {
    const tipo = document.getElementById("tipoMovimentacao").value;
    const camposEntrada = document.getElementById("camposEntrada");

    if (tipo === "entrada") {
        camposEntrada.style.display = "block"; // Mostra os campos
    } else {
        camposEntrada.style.display = "none"; // Esconde os campos
    }
}

function atualizarModal() {
    const tipoMovimentacao = document.getElementById("tipoMovimentacao").value;
    const numeroNota = document.getElementById("NumeroNota").closest(".mb-3");
    const fornecedor = document.getElementById("selectSupplier").closest(".mb-3");
    const botaoEnviar = document.getElementById("btnEntrada");

    if (tipoMovimentacao === "entrada") {
        numeroNota.style.display = "block"; // Exibe campo de nota
        fornecedor.style.display = "block"; // Exibe campo de fornecedor
        botaoEnviar.textContent = "Registrar Entrada";
        botaoEnviar.setAttribute("onclick", "criarNotaFiscal()");
    } else {
        numeroNota.style.display = "none"; // Oculta campo de nota
        fornecedor.style.display = "none"; // Oculta campo de fornecedor
        botaoEnviar.textContent = "Registrar Saída";
        botaoEnviar.setAttribute("onclick", "processarSaidaEstoque()");
    }
}

// Função de saída de estoque (a ser implementada)
async function processarSaidaEstoque() {
    const token = getToken(); // Obtém o token de autenticação
    const tableRows = document.querySelectorAll("#productList tr");

    if (tableRows.length === 0) {
        alert("Adicione pelo menos um produto para registrar a saída.");
        return;
    }

    for (let row of tableRows) {
        const cells = row.getElementsByTagName("td");
        const productName = cells[0].innerText;
        const price = cells[1].innerText.replace("R$ ", "").trim();
        const quantity = parseInt(cells[2].innerText, 10);
        const productId = row.dataset.productId; // ID salvo na linha

        if (!productId || isNaN(quantity) || quantity <= 0) {
            console.error("Erro: Produto ou quantidade inválida.");
            continue;
        }

        // Buscar quantidade atual no estoque
        try {
            const estoqueResponse = await fetch(`http://localhost:8080/products/${productId}`, {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-Type": "application/json"
                }
            });

            if (!estoqueResponse.ok) {
                throw new Error(`Erro ao buscar estoque para o produto ${productName}: ${estoqueResponse.status}`);
            }

            const produto = await estoqueResponse.json();
            const estoqueDisponivel = produto.quantidadeEstoque; // Ajuste conforme a resposta da API

            if (quantity > estoqueDisponivel) {
                alert(`Estoque insuficiente para o produto ${productName}! Disponível: ${estoqueDisponivel}`);
                continue; // Pula para o próximo item
            }

            // Monta a requisição de saída
            const payload = {
                productId: parseInt(productId),
                quantity: quantity,
                observation: `Saída de estoque do produto: ${productName}`
            };

            console.log("Enviando saída de estoque:", payload);

            const response = await fetch("http://localhost:8080/stock-output", {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            });

            if (!response.ok) {
                throw new Error(`Erro na API ao registrar saída do produto ${productName}: ${response.status}`);
            }

            console.log(`✅ Saída registrada com sucesso para ${productName}`);

        } catch (error) {
            console.error("Erro ao processar saída de estoque:", error);
        }
    }

    alert("Saída de estoque registrada com sucesso!");
}

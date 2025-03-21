document.addEventListener("DOMContentLoaded", () => {
    let produtosCarregados = false;
    const url = `https://dummyjson.com/products?limit=100&skip=0`;

    async function carregarProdutos() {
        if (produtosCarregados) return;

        try {
            const response = await fetch(url);
            const data = await response.json();
            atualizarSelect("#modalEntrada #selectProduto", data.products);
            produtosCarregados = true;
        } catch (error) {
            console.error("Erro ao carregar produtos:", error);
        }
    }

    function atualizarSelect(selectId, produtos) {
        const selectElement = document.querySelector(selectId);
        if (!selectElement) return;

        selectElement.innerHTML = `<option value="">Selecione um produto</option>`;

        produtos.forEach(produto => {
            const option = document.createElement("option");
            option.value = produto.id;
            option.textContent = produto.title;
            selectElement.appendChild(option);
        });

        // Ativar Select2 para a modal de entrada
        $(selectId).select2({
            dropdownParent: $(selectId).closest(".modal"),
            width: '100%',
            placeholder: "Selecione um produto",
            allowClear: true
        });
    }

    document.getElementById("modalEntrada").addEventListener("shown.bs.modal", carregarProdutos);
});

document.addEventListener("DOMContentLoaded", () => {
    let produtosCarregados = false;
    const url = `https://dummyjson.com/products?limit=100&skip=0`;

    async function carregarProdutos() {
        if (produtosCarregados) return;

        try {
            const response = await fetch(url);
            const data = await response.json();
            atualizarSelect("#modalSaida #selectProduto", data.products);
            produtosCarregados = true;
        } catch (error) {
            console.error("Erro ao carregar produtos:", error);
        }
    }

    function atualizarSelect(selectId, produtos) {
        const selectElement = document.querySelector(selectId);
        if (!selectElement) return;

        selectElement.innerHTML = `<option value="">Selecione um produto</option>`;

        produtos.forEach(produto => {
            const option = document.createElement("option");
            option.value = produto.id;
            option.textContent = produto.title;
            selectElement.appendChild(option);
        });

        // Ativar Select2 para a modal de saída
        $(selectId).select2({
            dropdownParent: $(selectId).closest(".modal"),
            width: '100%',
            placeholder: "Selecione um produto",
            allowClear: true
        });
    }

    document.getElementById("modalSaida").addEventListener("shown.bs.modal", carregarProdutos);
});

// Chama a função para carregar os produtos automaticamente ao abrir a página
document.addEventListener('DOMContentLoaded', () => {
    buscarProdutos(0).then(produtos => {
        atualizarTabela(produtos);  // Atualiza a tabela com os produtos
        atualizarPagina(0);         // Exibe a página inicial
    });
});

document.getElementById('btn-busca').addEventListener('click', () => {
    const termoBusca = document.getElementById('input-busca').value.trim();
    buscarProdutos(0, termoBusca).then(produtos => {
        atualizarTabela(produtos);
        paginaAtual = 0;
        atualizarPagina(0);
    });
});

document.getElementById('btn-anterior').addEventListener('click', () => {
    if (paginaAtual > 0) {
        const termoBusca = document.getElementById('input-busca').value.trim();
        buscarProdutos(paginaAtual - 1, termoBusca).then(produtos => {
            atualizarTabela(produtos);
            paginaAtual--;
            atualizarPagina(paginaAtual);
        });
    }
});

document.getElementById('btn-proximo').addEventListener('click', () => {
    const termoBusca = document.getElementById('input-busca').value.trim();
    buscarProdutos(paginaAtual + 1, termoBusca).then(produtos => {
        if (produtos.length > 0) {
            atualizarTabela(produtos);
            paginaAtual++;
            atualizarPagina(paginaAtual);
        }
    });
});

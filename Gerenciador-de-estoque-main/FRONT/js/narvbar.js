document.addEventListener("DOMContentLoaded", function () {
    // Carregar a navbar
    fetch("./navbar.html")
        .then(response => response.text())
        .then(data => {
            document.getElementById("navbar-container").innerHTML = data;
            
            // Agora que a navbar foi carregada, podemos atualizar o nome do usuário
            const userName = localStorage.getItem('userName');
            
            // Se o nome do usuário existir no localStorage, atualize o conteúdo do span
            if (userName) {
                document.getElementById('nomeUsuario').innerHTML = userName;
            } else {
                document.getElementById('nomeUsuario').innerHTML = "Usuário não autenticado";
            }
        })
        .catch(error => console.error("Erro ao carregar a navbar:", error));
});

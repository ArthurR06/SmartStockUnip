// Função para fazer login e redirecionar para a página home
function loginUser(event) {
    event.preventDefault(); // Evita o envio do formulário e recarregamento da página
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const data = {
        email: email,
        password: password
    };

    fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(data => {
        if (data.token) {
            localStorage.setItem('authToken', data.token); 
            localStorage.setItem('userName', data.name); // Armazenando token
            alert('Login realizado com sucesso!');
            window.location.href = 'home.html';  // Redireciona para a página de home
        } else {
            alert('Erro no login. Verifique suas credenciais.');
        }
    })
    .catch(error => {
        console.error('Erro ao tentar login', error);
        alert('Erro ao tentar login');
    });
}

// Função para fazer o cadastro
function registerUser(event) {
    event.preventDefault(); // Evita o envio do formulário e recarregamento da página
    const name = document.getElementById('name-register').value;
    const email = document.getElementById('email-register').value;
    const password = document.getElementById('password-register').value;

    const data = {
        name: name,
        email: email,
        password: password
    };

    fetch('http://localhost:8080/auth/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro na requisição: ' + response.statusText);
        }
        return response.json();
    })
    .then(data => {
        if (data.name) {
            alert('Cadastro realizado com sucesso! eee');
            window.location.href = 'TelaLogin.html';  // Redireciona para a página de login
        } else {
            alert('Erro ao registrar usuário');
        }
    })
    .catch(error => {
        console.error('Erro ao registrar usuário 2', error);
        alert('Erro ao tentar registrar 3');
    });
}
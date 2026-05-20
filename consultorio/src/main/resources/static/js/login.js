const API_URL = 'http://localhost:8080';

function mostrarMensagem(texto, tipo) {
    const el = document.getElementById('mensagem');
    el.textContent = texto;
    el.className = 'mensagem ' + tipo;
    el.style.display = 'block';
}

document.getElementById('formLogin').addEventListener('submit', async function (e) {
    e.preventDefault();

    const dados = {
        email: document.getElementById('email').value,
        password: document.getElementById('password').value
    };

    try {
        const resposta = await fetch(`${API_URL}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dados)
        });

        if (resposta.ok) {
            const token = await resposta.text();
            localStorage.setItem('token', token);
            localStorage.setItem('email', dados.email);
            window.location.href = 'agendamentos.html';
        } else {
            mostrarMensagem('Email ou senha incorretos.', 'erro');
        }
    } catch (err) {
        mostrarMensagem('Não foi possível conectar ao servidor.', 'erro');
    }
});
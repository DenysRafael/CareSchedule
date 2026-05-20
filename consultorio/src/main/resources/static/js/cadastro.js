const API_URL = 'http://localhost:8080';

function formatarCPF(valor) {
    valor = valor.replace(/\D/g, '');
    valor = valor.replace(/(\d{3})(\d)/, '$1.$2');
    valor = valor.replace(/(\d{3})(\d)/, '$1.$2');
    valor = valor.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
    return valor;
}

function formatarTelefone(valor) {
    valor = valor.replace(/\D/g, '');
    valor = valor.replace(/(\d{2})(\d)/, '($1) $2');
    valor = valor.replace(/(\d{5})(\d)/, '$1-$2');
    return valor;
}

function mostrarMensagem(texto, tipo) {
    const el = document.getElementById('mensagem');
    el.textContent = texto;
    el.className = 'mensagem ' + tipo;
    el.style.display = 'block';
}

document.getElementById('cpf').addEventListener('input', function () {
    this.value = formatarCPF(this.value);
});

document.getElementById('phone').addEventListener('input', function () {
    this.value = formatarTelefone(this.value);
});

document.getElementById('formCadastro').addEventListener('submit', async function (e) {
    e.preventDefault();

    const cpfLimpo = document.getElementById('cpf').value.replace(/\D/g, '');
    const phoneLimpo = document.getElementById('phone').value.replace(/\D/g, '');

    const dados = {
        fullName: document.getElementById('fullName').value,
        cpf: cpfLimpo,
        phone: phoneLimpo,
        dataNascimento: document.getElementById('dataNascimento').value,
        email: document.getElementById('email').value,
        password: document.getElementById('password').value
    };

    try {
        const resposta = await fetch(`${API_URL}/pacientes`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dados)
        });

        if (resposta.ok) {
            mostrarMensagem('Cadastro realizado com sucesso! Redirecionando...', 'sucesso');
            setTimeout(() => { window.location.href = 'login.html'; }, 2000);
        } else {
            const erro = await resposta.text();
            mostrarMensagem('Erro: ' + erro, 'erro');
        }
    } catch (err) {
        mostrarMensagem('Não foi possível conectar ao servidor.', 'erro');
    }
});
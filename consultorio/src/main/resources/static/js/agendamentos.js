const API_URL = 'http://localhost:8080';

const token = localStorage.getItem('token');
const email = localStorage.getItem('email');

if (!token) {
    window.location.href = 'login.html';
}

document.getElementById('emailUsuario').textContent = email;

function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('email');
    window.location.href = 'login.html';
}

function mostrarMensagem(texto, tipo) {
    const el = document.getElementById('mensagem');
    el.textContent = texto;
    el.className = 'mensagem ' + tipo;
    el.style.display = 'block';
}

function formatarData(data) {
    const [ano, mes, dia] = data.split('-');
    return `${dia}/${mes}/${ano}`;
}

function formatarHorario(horario) {
    return horario.substring(0, 5);
}

function traduzirStatus(status) {
    const map = { AGENDADO: 'Agendado', CANCELADO: 'Cancelado', REALIZADO: 'Realizado' };
    return map[status] || status;
}

function classeStatus(status) {
    const map = { AGENDADO: 'status-agendado', CANCELADO: 'status-cancelado', REALIZADO: 'status-realizado' };
    return map[status] || '';
}

async function cancelarAgendamento(id) {
    if (!confirm('Deseja cancelar este agendamento?')) return;

    try {
        const resposta = await fetch(`${API_URL}/agendamento/${id}`, {
            method: 'PATCH',
            headers: { 'Authorization': 'Bearer ' + token }
        });

        if (resposta.ok) {
            mostrarMensagem('Agendamento cancelado com sucesso!', 'sucesso');
            carregarAgendamentos();
        } else {
            const erro = await resposta.text();
            mostrarMensagem(erro, 'erro');
        }
    } catch (err) {
        mostrarMensagem('Erro ao cancelar agendamento.', 'erro');
    }
}

async function carregarAgendamentos() {
    try {
        const resposta = await fetch(`${API_URL}/agendamento`, {
            headers: { 'Authorization': 'Bearer ' + token }
        });

        if (resposta.status === 401 || resposta.status === 403) {
            window.location.href = 'login.html';
            return;
        }

        const agendamentos = await resposta.json();
        const lista = document.getElementById('listaAgendamentos');

        if (agendamentos.length === 0) {
            lista.innerHTML = '<p class="sem-dados">Você não possui agendamentos.</p>';
            return;
        }

        lista.innerHTML = agendamentos.map(ag => `
            <div class="card-agendamento">
                <div class="ag-info">
                    <div class="ag-data">${formatarData(ag.data)} às ${formatarHorario(ag.horario)}</div>
                    <div class="ag-tipo">${ag.tipoConsulta === 'PRIMEIRA_CONSULTA' ? 'Primeira consulta' : 'Retorno'}</div>
                    ${ag.discricao ? `<div class="ag-desc">${ag.discricao}</div>` : ''}
                </div>
                <div class="ag-direita">
                    <span class="badge ${classeStatus(ag.status)}">${traduzirStatus(ag.status)}</span>
                    ${ag.status === 'AGENDADO' ? `<button class="btn-cancelar" onclick="cancelarAgendamento(${ag.id})">Cancelar</button>` : ''}
                </div>
            </div>
        `).join('');

    } catch (err) {
        document.getElementById('listaAgendamentos').innerHTML = '<p class="sem-dados">Erro ao carregar agendamentos.</p>';
    }
}

carregarAgendamentos();
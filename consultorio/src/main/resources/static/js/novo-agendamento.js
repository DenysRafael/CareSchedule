const API_URL = 'http://localhost:8080';
const token = localStorage.getItem('token');
const email = localStorage.getItem('email');

if (!token) window.location.href = 'login.html';

document.getElementById('emailUsuario').textContent = email;

const MEDICO_ID = 1;

// Define data mínima como hoje + 4 dias
const dataInput = document.getElementById('data');
const hoje = new Date();
hoje.setDate(hoje.getDate() + 4);
const minDate = hoje.toISOString().split('T')[0];
dataInput.min = minDate;

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

// Gera slots de 30 em 30 minutos entre 09:00 e 11:30, e 13:00 e 17:30
function gerarSlots() {
    const slots = [];
    const periodos = [
        { inicio: 9, fimH: 11, fimM: 30 },
        { inicio: 13, fimH: 17, fimM: 30 }
    ];

    for (const p of periodos) {
        let h = p.inicio, m = 0;
        while (h < p.fimH || (h === p.fimH && m <= p.fimM)) {
            slots.push(`${String(h).padStart(2, '0')}:${String(m).padStart(2, '0')}`);
            m += 30;
            if (m >= 60) { m = 0; h++; }
        }
    }
    return slots;
}

// Quando o usuário escolhe uma data, carrega os horários disponíveis
dataInput.addEventListener('change', async function () {
    const data = this.value;
    const selectHorario = document.getElementById('horario');
    selectHorario.innerHTML = '<option value="">Carregando...</option>';

    try {
        // Busca agendamentos existentes nessa data
        const resposta = await fetch(`${API_URL}/agendamento`, {
            headers: { 'Authorization': 'Bearer ' + token }
        });
        const agendamentos = await resposta.json();

        // Filtra os horários já ocupados nessa data
        const ocupados = agendamentos
            .filter(ag => ag.data === data && ag.status === 'AGENDADO')
            .map(ag => ag.horario.substring(0, 5));

        const todos = gerarSlots();
        const disponiveis = todos.filter(h => !ocupados.includes(h));

        if (disponiveis.length === 0) {
            selectHorario.innerHTML = '<option value="">Nenhum horário disponível</option>';
            return;
        }

        selectHorario.innerHTML = '<option value="">Selecione um horário</option>' +
            disponiveis.map(h => `<option value="${h}:00">${h}</option>`).join('');

    } catch (err) {
        selectHorario.innerHTML = '<option value="">Erro ao carregar horários</option>';
    }
});

async function agendar() {
    const data = document.getElementById('data').value;
    const horario = document.getElementById('horario').value;
    const tipoConsulta = document.getElementById('tipoConsulta').value;
    const discricao = document.getElementById('discricao').value;

    if (!data || !horario) {
        mostrarMensagem('Selecione a data e o horário.', 'erro');
        return;
    }

    // Busca o ID do paciente logado
    let pacienteId;
    try {
        const resposta = await fetch(`${API_URL}/pacientes`, {
            headers: { 'Authorization': 'Bearer ' + token }
        });
        const pacientes = await resposta.json();
        const paciente = pacientes.find(p => p.email === email);
        if (!paciente) {
            mostrarMensagem('Paciente não encontrado.', 'erro');
            return;
        }
        pacienteId = paciente.id;
    } catch (err) {
        mostrarMensagem('Erro ao buscar dados do paciente.', 'erro');
        return;
    }

    const dados = {
        paciente: { id: pacienteId },
        medico: { id: MEDICO_ID },
        data,
        horario,
        tipoConsulta,
        discricao,
        status: 'AGENDADO'
    };

    try {
        const resposta = await fetch(`${API_URL}/agendamento`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            },
            body: JSON.stringify(dados)
        });

        if (resposta.ok) {
            mostrarMensagem('Agendamento realizado com sucesso!', 'sucesso');
            setTimeout(() => { window.location.href = 'agendamentos.html'; }, 2000);
        } else {
            const erro = await resposta.text();
            mostrarMensagem(erro, 'erro');
        }
    } catch (err) {
        mostrarMensagem('Erro ao realizar agendamento.', 'erro');
    }
}
// SCVP — interações simples em JS puro (sem dependências)
(function () {
    // Sidebar mobile toggle
    const toggle = document.querySelector('[data-menu-toggle]');
    const sidebar = document.querySelector('.app-sidebar');
    const backdrop = document.querySelector('.sidebar-backdrop');
    if (toggle && sidebar) {
        toggle.addEventListener('click', () => {
            sidebar.classList.toggle('open');
            if (backdrop) backdrop.classList.toggle('show');
        });
    }
    if (backdrop) {
        backdrop.addEventListener('click', () => {
            sidebar.classList.remove('open');
            backdrop.classList.remove('show');
        });
    }

    // Wizard (Nova Reserva)
    const wizard = document.querySelector('[data-wizard]');
    if (wizard) {
        const steps = wizard.querySelectorAll('.wizard-step');
        const panes = wizard.querySelectorAll('.wizard-pane');
        let current = 0;

        function go(to) {
            current = Math.max(0, Math.min(panes.length - 1, to));
            steps.forEach((s, i) => {
                s.classList.toggle('active', i === current);
                s.classList.toggle('completed', i < current);
            });
            panes.forEach((p, i) => p.classList.toggle('active', i === current));

            // Atualiza o resumo ao entrar na última etapa
            const resumoPane = document.querySelector('[aria-label="Resumo"]');
            if (resumoPane && resumoPane.classList.contains('active')) {
                preencherResumo();
            }
        }

        wizard.addEventListener('click', (e) => {
            const t = e.target.closest('[data-wizard-next]');
            const p = e.target.closest('[data-wizard-prev]');
            if (t) { e.preventDefault(); go(current + 1); }
            if (p) { e.preventDefault(); go(current - 1); }
        });

        steps.forEach((s, i) => s.addEventListener('click', () => go(i)));
    }

    // Mostrar/ocultar campos condicionais (data-toggle-target -> seletor)
    document.querySelectorAll('[data-toggle-target]').forEach((el) => {
        el.addEventListener('change', () => {
            const target = document.querySelector(el.dataset.toggleTarget);
            if (!target) return;
            const showOn = (el.dataset.toggleValue || '').split('|');
            const value = el.type === 'checkbox' ? (el.checked ? 'true' : 'false') : el.value;
            target.classList.toggle('hidden', !showOn.includes(value));
        });
    });

    // Adicionar cidades intermediárias dinamicamente
    const addStopBtn = document.querySelector('[data-add-stop]');
    const stopsList = document.querySelector('[data-stops-list]');
    if (addStopBtn && stopsList) {
        addStopBtn.addEventListener('click', () => {
            const index = stopsList.children.length;
            const row = document.createElement('div');
            row.className = 'flex gap-2';
            row.style.marginBottom = 'var(--space-3)';

            const select = document.createElement('select');
            select.name = `escalas[${index}]`;
            select.className = 'form-control';
            select.required = true;

            const defaultOpt = document.createElement('option');
            defaultOpt.value = '';
            defaultOpt.textContent = 'Selecione...';
            select.appendChild(defaultOpt);

            (window.CIDADES || []).forEach(cidade => {
                const opt = document.createElement('option');
                opt.value = cidade.id;
                opt.textContent = `${cidade.cidade} — ${cidade.estado} (${cidade.codigoIata})`;
                select.appendChild(opt);
            });

            const removeBtn = document.createElement('button');
            removeBtn.type = 'button';
            removeBtn.className = 'btn btn-ghost btn-sm';
            removeBtn.setAttribute('aria-label', 'Remover');
            removeBtn.textContent = '✕';
            removeBtn.addEventListener('click', () => row.remove());

            row.appendChild(select);
            row.appendChild(removeBtn);
            stopsList.appendChild(row);
        });
    }

    // Busca de clientes na etapa 2
    const btnBuscar = document.getElementById('btn-buscar');
    const inputBusca = document.getElementById('busca');
    const tabelaClientes = document.querySelector('[aria-label="Cliente"] tbody');

    function calcularIdade(dataNascimento) {
        const hoje = new Date();
        const nasc = new Date(dataNascimento);
        let idade = hoje.getFullYear() - nasc.getFullYear();
        const m = hoje.getMonth() - nasc.getMonth();
        if (m < 0 || (m === 0 && hoje.getDate() < nasc.getDate())) idade--;
        return idade;
    }

    function renderClientes(clientes) {
        tabelaClientes.innerHTML = '';
        if (!clientes.length) {
            tabelaClientes.innerHTML = '<tr><td colspan="5" class="text-center">Nenhum cliente encontrado.</td></tr>';
            return;
        }
        clientes.forEach(c => {
            const idade = c.dataNascimento ? calcularIdade(c.dataNascimento) : '—';
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td><input type="radio" name="clienteId" value="${c.id}" /></td>
                <td>${c.nome}</td>
                <td>${c.cpf}</td>
                <td>${idade}</td>
                <td>${c.telefone ?? '—'}</td>
            `;
            tabelaClientes.appendChild(tr);
        });
    }

    if (btnBuscar && inputBusca && tabelaClientes) {
        btnBuscar.addEventListener('click', async () => {
            const q = inputBusca.value.trim();
            const res = await fetch(`/reservas/nova/clientes?q=${encodeURIComponent(q)}`);
            const clientes = await res.json();
            renderClientes(clientes);
        });

        inputBusca.addEventListener('keydown', (e) => {
            if (e.key === 'Enter') { e.preventDefault(); btnBuscar.click(); }
        });
    }

    // Seleção visual das opções de modal (etapa 2)
    document.querySelectorAll('.modal-option input[type="radio"]').forEach(radio => {
        radio.addEventListener('change', () => {
            document.querySelectorAll('.modal-option').forEach(opt => opt.classList.remove('selected'));
            radio.closest('.modal-option').classList.add('selected');
        });
    });

    const primeiroModal = document.querySelector('.modal-option input[type="radio"]:checked');
    if (primeiroModal) primeiroModal.closest('.modal-option').classList.add('selected');

    // Preencher resumo dinamicamente (etapa 3)
    function getSelectText(id) {
        const el = document.getElementById(id);
        if (!el) return '—';
        return el.options[el.selectedIndex]?.text || '—';
    }

    function preencherResumo() {
        // Trajeto
        const origem = getSelectText('origem');
        const destino = getSelectText('destino');
        const trajeto = (origem !== '—' && destino !== '—') ? `${origem} → ${destino}` : '—';
        setText('summary-trajeto', trajeto);

        // Tipo de viagem
        const tipoViagem = document.querySelector('input[name="tipoViagem"]:checked');
        setText('summary-tipo-viagem', tipoViagem ? (tipoViagem.value === 'direta' ? 'Direta' : 'Com escalas') : '—');

        // Escalas
        const escalasSelects = document.querySelectorAll('[data-stops-list] select');
        const escalasTexto = escalasSelects.length > 0
            ? Array.from(escalasSelects).map(s => s.options[s.selectedIndex]?.text || '—').join(', ')
            : 'Nenhuma';
        setText('summary-escalas', escalasTexto);

        // Data
        const data = document.getElementById('data');
        if (data && data.value) {
            const [ano, mes, dia] = data.value.split('-');
            setText('summary-data', `${dia}/${mes}/${ano}`);
        } else {
            setText('summary-data', '—');
        }

        // Modal selecionado
        const modalSelecionado = document.querySelector('.modal-option.selected');
        if (modalSelecionado) {
            const nome = modalSelecionado.querySelector('.name')?.textContent || '—';
            const meta = modalSelecionado.querySelector('.meta')?.textContent || '';
            setText('summary-modal', nome);
            setText('summary-modal-meta', meta);
        } else {
            setText('summary-modal', '—');
            setText('summary-modal-meta', '');
        }

        // Tipo de passagem
        const tipoPassagem = getSelectText('tipoPassagem');
        setText('summary-tipo-passagem', tipoPassagem);
    }

    function setText(id, value) {
        const el = document.getElementById(id);
        if (el) el.textContent = value;
    }

})();

// Dialog modal (abrir/fechar via data-attrs)
(function () {
    function open(d) { d.classList.add('open'); document.body.classList.add('dialog-open'); }
    function close(d) { d.classList.remove('open'); document.body.classList.remove('dialog-open'); }

    document.querySelectorAll('[data-dialog-open]').forEach((btn) => {
        btn.addEventListener('click', (e) => {
            e.preventDefault();
            const d = document.querySelector(btn.dataset.dialogOpen);
            if (d) open(d);
        });
    });
    document.querySelectorAll('.dialog-backdrop').forEach((d) => {
        d.addEventListener('click', (e) => { if (e.target === d) close(d); });
        d.querySelectorAll('[data-dialog-close]').forEach((b) => {
            b.addEventListener('click', (e) => { e.preventDefault(); close(d); });
        });
    });
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape') document.querySelectorAll('.dialog-backdrop.open').forEach(close);
    });
})();
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
            const row = document.createElement('div');
            row.className = 'flex gap-2';
            row.style.marginBottom = 'var(--space-3)';
            row.innerHTML = `
        <input type="text" class="form-control" placeholder="Cidade de escala" name="escala" />
        <button type="button" class="btn btn-ghost btn-sm" aria-label="Remover">✕</button>
      `;
            row.querySelector('button').addEventListener('click', () => row.remove());
            stopsList.appendChild(row);
        });
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

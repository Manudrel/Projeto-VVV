/**
 * SCVP — Checkout & Emissão de Ticket
 * checkout.js
 *
 * Depende de: app.js (menu-toggle / sidebar já inicializados lá)
 */

/* ==========================================================================
   Dados das reservas (substituir por chamada à API / Thymeleaf inline)
   ========================================================================== */
const RESERVAS = {
  'res-1': {
    cliente:       'João Pereira',
    cpf:           '012.345.678-90',
    trajeto:       'GRU → SSA',
    origemIata:    'GRU',
    destinoIata:   'SSA',
    origemCidade:  'São Paulo',
    destinoCidade: 'Salvador',
    modal:         'LATAM 3024 (Avião)',
    modalBadge:    '✈️ Avião',
    modalLabel:    'LATAM AIRLINES · VVV',
    tipo:          'Econômica',
    data:          '22/06/2026 · 08:30',
    dataTicket:    '22 Jun 2026',
    saida:         '08:30',
    assento:       '14C',
    tarifa:        489.00,
    taxa:          24.00,
  },
  'res-2': {
    cliente:       'Ana Lima',
    cpf:           '987.654.321-00',
    trajeto:       'CPQ → GIG',
    origemIata:    'CPQ',
    destinoIata:   'GIG',
    origemCidade:  'Campinas',
    destinoCidade: 'Rio de Janeiro',
    modal:         'Viação Cometa (Ônibus)',
    modalBadge:    '🚌 Ônibus',
    modalLabel:    'VIAÇÃO COMETA · VVV',
    tipo:          'Semi-Leito',
    data:          '23/06/2026 · 22:00',
    dataTicket:    '23 Jun 2026',
    saida:         '22:00',
    assento:       '08',
    tarifa:        189.00,
    taxa:          10.00,
  },
  'res-3': {
    cliente:       'Pedro Alves',
    cpf:           '111.222.333-44',
    trajeto:       'CWB → GRU',
    origemIata:    'CWB',
    destinoIata:   'GRU',
    origemCidade:  'Curitiba',
    destinoCidade: 'São Paulo',
    modal:         'Vale Expresso (Trem)',
    modalBadge:    '🚆 Trem',
    modalLabel:    'VALE EXPRESSO · VVV',
    tipo:          'Leito',
    data:          '25/06/2026 · 06:15',
    dataTicket:    '25 Jun 2026',
    saida:         '06:15',
    assento:       '22A',
    tarifa:        259.00,
    taxa:          15.00,
  },
};

/* ==========================================================================
   Estado local
   ========================================================================== */
let reservaSelecionada = null;  // id da reserva escolhida na etapa 1
let metodoPagamento    = 'dinheiro';

/* ==========================================================================
   Helpers
   ========================================================================== */

/** Formata um número como moeda BRL (ex.: 1234.5 → "R$ 1.234,50") */
function formatBRL(valor) {
  return valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
}

/** Exibe/oculta um elemento pelo id, baseado em um booleano */
function toggleHidden(id, hidden) {
  document.getElementById(id)?.classList.toggle('hidden', hidden);
}

/** Define o texto de um elemento pelo id */
function setText(id, text) {
  const el = document.getElementById(id);
  if (el) el.textContent = text;
}

/* ==========================================================================
   Wizard — navegação entre etapas
   ========================================================================== */

/**
 * Ativa o pane e o tab correspondentes ao número de etapa informado.
 * @param {number} num  1 | 2 | 3
 */
function irParaEtapa(num) {
  document.querySelectorAll('.wizard-pane').forEach((pane, i) => {
    pane.classList.toggle('active', i + 1 === num);
  });

  document.querySelectorAll('.wizard-step').forEach((tab, i) => {
    tab.classList.remove('active', 'completed');
    if (i + 1 < num)  tab.classList.add('completed');
    if (i + 1 === num) tab.classList.add('active');
  });

  // Faz scroll para o topo do card sem pular a navegação
  document.querySelector('.card')?.scrollIntoView({ behavior: 'smooth', block: 'start' });
}

/* ==========================================================================
   Etapa 1 — Seleção de reserva
   ========================================================================== */

/** Inicializa os cards de reserva (clique em qualquer parte do label). */
function initReservaCards() {
  document.querySelectorAll('.reserva-card').forEach(card => {
    // O radio está embutido no label; o browser cuida do check.
    // Apenas rastreamos o id selecionado.
    card.addEventListener('change', () => {
      reservaSelecionada = card.dataset.id;
      toggleHidden('aviso-selecao', true);
    });
  });
}

/** Avança para a etapa 2 após validar que uma reserva foi escolhida. */
function irParaPagamento() {
  if (!reservaSelecionada) {
    toggleHidden('aviso-selecao', false);
    document.getElementById('aviso-selecao')?.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
    return;
  }

  const r     = RESERVAS[reservaSelecionada];
  const total = r.tarifa + r.taxa;

  // Preenche o resumo da etapa 2
  setText('resumo-cliente',      r.cliente);
  setText('resumo-cpf',          r.cpf);
  setText('resumo-trajeto',      r.trajeto);
  setText('resumo-modal',        r.modal);
  setText('resumo-modal-badge',  r.modalBadge);
  setText('resumo-tipo',         r.tipo);
  setText('resumo-data',         r.data);
  setText('resumo-tarifa',       formatBRL(r.tarifa));
  setText('resumo-taxa',         formatBRL(r.taxa));
  setText('resumo-total',        formatBRL(total));
  setText('payment-total',       formatBRL(total));

  atualizarParcelas(total);
  irParaEtapa(2);
}

/* ==========================================================================
   Etapa 1 — Filtro de reservas
   ========================================================================== */

function filtrarReservas() {
  const termo = document.getElementById('busca-reserva').value.trim().toLowerCase();
  const modal = document.getElementById('filtro-modal').value;

  document.querySelectorAll('.reserva-card').forEach(card => {
    const textoCard    = card.textContent.toLowerCase();
    const modalCard    = card.dataset.modal ?? '';
    const matchTexto   = !termo || textoCard.includes(termo);
    const matchModal   = !modal || modalCard === modal;
    card.style.display = (matchTexto && matchModal) ? '' : 'none';
  });
}

/* ==========================================================================
   Etapa 2 — Método de pagamento
   ========================================================================== */

const METODOS = ['dinheiro', 'pix', 'debito', 'credito'];

/**
 * Ativa o painel do método escolhido e atualiza os botões.
 * @param {string} metodo
 */
function setMetodo(metodo) {
  metodoPagamento = metodo;

  document.querySelectorAll('.method-btn').forEach(btn => {
    btn.classList.toggle('active', btn.dataset.metodo === metodo);
    btn.setAttribute('aria-pressed', btn.dataset.metodo === metodo);
  });

  METODOS.forEach(m => toggleHidden(`metodo-${m}`, m !== metodo));
}

/* ==========================================================================
   Etapa 2 — Cálculo de troco (método Dinheiro)
   ========================================================================== */

function calcTroco() {
  if (!reservaSelecionada) return;

  const r        = RESERVAS[reservaSelecionada];
  const total    = r.tarifa + r.taxa;
  const recebido = parseFloat(document.getElementById('troco-de').value) || 0;
  const troco    = Math.max(0, recebido - total);

  setText('troco-valor', formatBRL(troco));
}

/* ==========================================================================
   Etapa 2 — Máscaras de entrada
   ========================================================================== */

/** Formata número de cartão em grupos de 4 dígitos. */
function mascaraCartao(input) {
  let v = input.value.replace(/\D/g, '').slice(0, 16);
  v = v.replace(/(.{4})/g, '$1 ').trim();
  input.value = v;
}

/** Formata validade no padrão MM/AA. */
function mascaraValidade(input) {
  let v = input.value.replace(/\D/g, '').slice(0, 4);
  if (v.length > 2) v = `${v.slice(0, 2)}/${v.slice(2)}`;
  input.value = v;
}

/* ==========================================================================
   Etapa 2 — Parcelamento (crédito)
   ========================================================================== */

/**
 * Gera as opções do select de parcelas com base no total da reserva.
 * @param {number} total
 */
function atualizarParcelas(total) {
  const sel = document.getElementById('parcelas');
  if (!sel) return;

  const opcoes = [
    { n: 1,  juros: false },
    { n: 2,  juros: false },
    { n: 3,  juros: false },
    { n: 4,  juros: false },
    { n: 6,  juros: true  },
    { n: 12, juros: true  },
  ];

  sel.innerHTML = opcoes.map(({ n, juros }) => {
    const fator  = juros ? Math.pow(1.05, n) : 1;
    const parc   = (total * fator / n).toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
    const sufixo = juros ? 'com juros (5% a.m.)' : 'sem juros';
    return `<option value="${n}">${n}x de R$ ${parc} ${sufixo}</option>`;
  }).join('');
}

/* ==========================================================================
   Etapa 2 — Confirmação do pagamento
   ========================================================================== */

function confirmarPagamento() {
  const r          = RESERVAS[reservaSelecionada];
  const total      = r.tarifa + r.taxa;
  const localizador = 'VVV' + String(Math.floor(Math.random() * 999)).padStart(3, '0');

  // Preenche o ticket
  setText('ticket-modal-label',    r.modalLabel);
  setText('ticket-origem-iata',    r.origemIata);
  setText('ticket-destino-iata',   r.destinoIata);
  setText('ticket-origem-cidade',  r.origemCidade);
  setText('ticket-destino-cidade', r.destinoCidade);
  setText('ticket-modal-badge',    r.modalBadge);
  setText('ticket-passageiro',     r.cliente);
  setText('ticket-data',           r.dataTicket);
  setText('ticket-saida',          r.saida);
  setText('ticket-tipo',           r.tipo);
  setText('ticket-assento',        r.assento);
  setText('ticket-valor',          formatBRL(total));
  setText('ticket-localizador',    localizador);

  gerarCodigoBarras();

  // Número aleatório do código de barras
  const codNum = Array.from({ length: 5 }, () =>
    String(Math.floor(Math.random() * 10000)).padStart(4, '0')
  ).join(' ');
  setText('barcode-num', codNum);

  irParaEtapa(3);
}

/* ==========================================================================
   Etapa 3 — Geração visual do código de barras fictício
   ========================================================================== */

function gerarCodigoBarras() {
  const container = document.getElementById('barcode-img');
  if (!container) return;

  container.innerHTML = '';

  for (let i = 0; i < 60; i++) {
    const span  = document.createElement('span');
    const largo = Math.random() > 0.7;
    const espaco = Math.random() > 0.55;

    if (!espaco) {
      span.style.width      = (largo ? 3 : 1) + 'px';
      span.style.height     = Math.random() > 0.3 ? '100%' : '70%';
      span.style.alignSelf  = 'center';
      span.style.background = 'var(--color-text)';
    } else {
      span.style.width = '2px';
    }

    container.appendChild(span);
  }
}

/* ==========================================================================
   Etapa 1 — Nova reserva (volta ao início e reseta estado)
   ========================================================================== */

function novaReserva() {
  reservaSelecionada = null;

  // Desmarca todos os radios e remove a classe visual
  document.querySelectorAll('.reserva-card input[type="radio"]').forEach(r => {
    r.checked = false;
  });

  // Força o browser a refletir o estado "sem seleção" nos cards
  // (o :has() cuida do estilo, basta disparar um change no DOM)
  toggleHidden('aviso-selecao', true);

  // Limpa campo de busca e filtro
  const busca = document.getElementById('busca-reserva');
  const filtro = document.getElementById('filtro-modal');
  if (busca)  busca.value  = '';
  if (filtro) filtro.value = '';

  // Garante que todos os cards estejam visíveis
  document.querySelectorAll('.reserva-card').forEach(c => (c.style.display = ''));

  irParaEtapa(1);
}

/* ==========================================================================
   Boot — eventos
   ========================================================================== */

document.addEventListener('DOMContentLoaded', () => {

  // Etapa 1
  initReservaCards();
  document.getElementById('btn-ir-pagamento')?.addEventListener('click', irParaPagamento);
  document.getElementById('btn-filtrar')?.addEventListener('click', filtrarReservas);
  document.getElementById('busca-reserva')?.addEventListener('keyup', e => {
    if (e.key === 'Enter') filtrarReservas();
  });

  // Etapa 2 — navegação
  document.getElementById('btn-voltar-reserva')?.addEventListener('click', () => irParaEtapa(1));
  document.getElementById('btn-confirmar-pagamento')?.addEventListener('click', confirmarPagamento);

  // Etapa 2 — métodos de pagamento
  document.querySelectorAll('.method-btn').forEach(btn => {
    btn.addEventListener('click', () => setMetodo(btn.dataset.metodo));
  });

  // Etapa 2 — troco
  document.getElementById('troco-de')?.addEventListener('input', calcTroco);

  // Etapa 2 — máscaras de cartão
  ['deb-numero', 'cred-numero'].forEach(id => {
    document.getElementById(id)?.addEventListener('input', e => mascaraCartao(e.target));
  });

  ['deb-validade', 'cred-validade'].forEach(id => {
    document.getElementById(id)?.addEventListener('input', e => mascaraValidade(e.target));
  });

  // Etapa 3 — nova reserva
  document.getElementById('btn-nova-reserva')?.addEventListener('click', novaReserva);
});
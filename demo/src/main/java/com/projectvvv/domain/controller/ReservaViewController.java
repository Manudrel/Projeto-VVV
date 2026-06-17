package com.projectvvv.domain.controller;

import com.projectvvv.domain.dto.ReservaDTO;
import com.projectvvv.domain.dto.RotaDaReservaDTO;
import com.projectvvv.domain.model.Cidade;
import com.projectvvv.domain.model.Cliente;
import com.projectvvv.domain.model.Reserva;
import com.projectvvv.domain.model.Rota;
import com.projectvvv.domain.model.TipoModal;
import com.projectvvv.domain.model.TipoPassagem;
import com.projectvvv.domain.model.TipoViagem;
import com.projectvvv.domain.repository.RotaRepository;
import com.projectvvv.domain.service.CidadeService;
import com.projectvvv.domain.service.ClienteService;
import com.projectvvv.domain.service.ReservaService;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reservas")
public class ReservaViewController {

    private static final String SESSION_DADOS = "novaReservaDados";
    private static final String SESSION_CIDADES_ROTA = "novaReservaCidades";

    private final CidadeService cidadeService;
    private final ClienteService clienteService;
    private final ReservaService reservaService;
    private final RotaRepository rotaRepository;

    public ReservaViewController(CidadeService cidadeService,
                                 ClienteService clienteService,
                                 ReservaService reservaService,
                                 RotaRepository rotaRepository) {
        this.cidadeService = cidadeService;
        this.clienteService = clienteService;
        this.reservaService = reservaService;
        this.rotaRepository = rotaRepository;
    }

    @GetMapping("/consultar")
    public String consultarReservas() {
        return "reservas/consultar";
    }

    // ---------- ETAPA 1: Dados base ----------

    @GetMapping("/nova")
    public String novaReservaEtapa1(Model model, HttpSession session) {
        ReservaDTO dados = (ReservaDTO) session.getAttribute(SESSION_DADOS);
        model.addAttribute("dados", dados != null ? dados : new ReservaDTO());
        return "reservas/nova";
    }

    @PostMapping("/nova")
    public String salvarEtapa1(
            @RequestParam LocalDate data,
            @RequestParam LocalTime horaPartida,
            @RequestParam String localizador,
            @RequestParam TipoViagem tipoViagem,
            @RequestParam TipoModal tipoModal,
            @RequestParam TipoPassagem tipoPassagem,
            @RequestParam Float valor,
            HttpSession session) {

        ReservaDTO dto = new ReservaDTO();
        dto.setData(data);
        dto.setHoraPartida(horaPartida);
        dto.setLocalizador(localizador);
        dto.setTipoViagem(tipoViagem);
        dto.setTipoModal(tipoModal);
        dto.setTipoPassagem(tipoPassagem);
        dto.setValor(valor);

        session.setAttribute(SESSION_DADOS, dto);

        return "redirect:/reservas/nova/rotas";
    }

    // ---------- ETAPA 2: Rotas ----------

    @GetMapping("/nova/rotas")
    public String novaReservaEtapa2(Model model, HttpSession session) {

        if (session.getAttribute(SESSION_DADOS) == null) {
            return "redirect:/reservas/nova";
        }

        model.addAttribute("cidades", cidadeService.listarTodas());
        return "reservas/nova-rotas.html";
    }

    @PostMapping("/nova/rotas")
    public String salvarEtapa2(
            @RequestParam Long origemId,
            @RequestParam Long destinoId,
            @RequestParam(required = false) List<Long> escalaId,
            HttpSession session) {

        if (session.getAttribute(SESSION_DADOS) == null) {
            return "redirect:/reservas/nova";
        }

        List<Long> sequenciaCidades = new ArrayList<>();
        sequenciaCidades.add(origemId);
        if (escalaId != null) {
            sequenciaCidades.addAll(escalaId);
        }
        sequenciaCidades.add(destinoId);

        session.setAttribute(SESSION_CIDADES_ROTA, sequenciaCidades);

        return "redirect:/reservas/nova/confirmar";
    }

    // ---------- ETAPA 3: Confirmação ----------

    @GetMapping("/nova/confirmar")
    public String novaReservaEtapa3(Model model, HttpSession session) {

        ReservaDTO dados = (ReservaDTO) session.getAttribute(SESSION_DADOS);
        @SuppressWarnings("unchecked")
        List<Long> cidadesRota = (List<Long>) session.getAttribute(SESSION_CIDADES_ROTA);

        if (dados == null || cidadesRota == null) {
            return "redirect:/reservas/nova";
        }

        List<Cidade> todasCidades = cidadeService.listarTodas();
        List<String[]> trechos = new ArrayList<>();

        for (int i = 0; i < cidadesRota.size() - 1; i++) {
            String nomeOrigem = nomeCidade(todasCidades, cidadesRota.get(i));
            String nomeDestino = nomeCidade(todasCidades, cidadesRota.get(i + 1));
            trechos.add(new String[]{nomeOrigem, nomeDestino});
        }

        model.addAttribute("dados", dados);
        model.addAttribute("trechos", trechos);

        return "reservas/nova-confirmar";
    }

    @PostMapping("/nova/confirmar")
    public String confirmarReserva(HttpSession session) {

        ReservaDTO dados = (ReservaDTO) session.getAttribute(SESSION_DADOS);
        @SuppressWarnings("unchecked")
        List<Long> cidadesRota = (List<Long>) session.getAttribute(SESSION_CIDADES_ROTA);

        if (dados == null || cidadesRota == null) {
            return "redirect:/reservas/nova";
        }

        List<RotaDaReservaDTO> rotas = new ArrayList<>();

        for (int i = 0; i < cidadesRota.size() - 1; i++) {

            Long origemId = cidadesRota.get(i);
            Long destinoId = cidadesRota.get(i + 1);

            Rota rota = rotaRepository
                    .findByOrigem_IdAndDestino_Id(origemId, destinoId)
                    .orElseGet(() -> criarNovaRota(origemId, destinoId));

            RotaDaReservaDTO rotaDTO = new RotaDaReservaDTO();
            rotaDTO.setRotaId(rota.getId());
            rotaDTO.setOrdem(i + 1);

            rotas.add(rotaDTO);
        }

        dados.setRotas(rotas);

        Reserva reservaSalva = reservaService.criar(dados);

        session.removeAttribute(SESSION_DADOS);
        session.removeAttribute(SESSION_CIDADES_ROTA);

        return "redirect:/reservas/detalhe?id=" + reservaSalva.getCodigoReserva();
    }

    private Rota criarNovaRota(Long origemId, Long destinoId) {

        List<Cidade> cidades = cidadeService.listarTodas();

        Cidade origem = cidades.stream()
                .filter(c -> c.getId().equals(origemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cidade de origem não encontrada: " + origemId));

        Cidade destino = cidades.stream()
                .filter(c -> c.getId().equals(destinoId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cidade de destino não encontrada: " + destinoId));

        Rota nova = new Rota();
        nova.setOrigem(origem);
        nova.setDestino(destino);

        return rotaRepository.save(nova);
    }

    private String nomeCidade(List<Cidade> cidades, Long id) {
        return cidades.stream()
                .filter(c -> c.getId().equals(id))
                .map(Cidade::getCidade)
                .findFirst()
                .orElse("—");
    }

    @GetMapping("/nova/clientes")
    @ResponseBody
    public List<Cliente> buscarClientes(@RequestParam(required = false) String q) {
        if (q == null || q.isBlank()) return clienteService.listarTodos();
        return clienteService.buscarPorNomeOuCpf(q);
    }

    @GetMapping("/detalhe")
    public String detalheReserva() {
        return "reservas/detalhe";
    }
}
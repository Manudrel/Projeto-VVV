package com.projectvvv.demo.service;

import com.projectvvv.domain.model.Cliente;
import com.projectvvv.domain.model.Pagamento;
import com.projectvvv.domain.model.Reserva;
import com.projectvvv.domain.model.Ticket;
import com.projectvvv.domain.model.TipoPassagem;
import com.projectvvv.domain.repository.TicketRepository;
import com.projectvvv.domain.service.TicketService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    @DisplayName("buscarPorId - Deve retornar o ticket quando o ID existir")
    void deveBuscarTicketPorIdComSucesso() {
        Long id = 1L;
        Ticket ticketEsperado = new Ticket();
        Mockito.when(ticketRepository.findById(id)).thenReturn(Optional.of(ticketEsperado));

        Ticket resultado = ticketService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(ticketEsperado, resultado);
    }

    @Test
    @DisplayName("buscarPorId - Deve lançar RuntimeException quando o ID não existir")
    void deveLancarErroAoBuscarIdInexistente() {
        Long id = 99L;
        Mockito.when(ticketRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ticketService.buscarPorId(id);
        });

        assertEquals("Ticket não encontrado.", exception.getMessage());
    }

    @Test
    @DisplayName("criar - Deve gerar código TK-XXXX, mapear os IDs de cliente/reserva e salvar")
    void deveCriarTicketComSucesso() {
        Reserva reserva = new Reserva();
        reserva.setCodigoReserva(12345L);
        reserva.setTipoPassagem(TipoPassagem.LEITO);

        Cliente cliente = new Cliente();
        cliente.setId(10L);

        Pagamento pagamento = new Pagamento(); 

        Mockito.when(ticketRepository.save(any(Ticket.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        
        Ticket resultado = ticketService.criar(reserva, pagamento, cliente);
        
        assertNotNull(resultado.getCodigoTicket());
        assertTrue(resultado.getCodigoTicket().startsWith("TK-"));
        assertEquals(TipoPassagem.LEITO, resultado.getTipoPassagem());
        assertEquals(10L, resultado.getIdCliente());
        assertEquals(12345L, resultado.getIdReserva());
    }

    @Test
    @DisplayName("atualizar - Deve atualizar com sucesso quando os IDs forem correspondentes")
    void deveAtualizarTicketComSucesso() {
        
        Long id = 1L;
        Ticket ticketExistente = new Ticket();
        ticketExistente.setId(id);
        ticketExistente.setTipoPassagem(TipoPassagem.ECONOMICA);

        Ticket ticketDadosNovos = new Ticket();
        ticketDadosNovos.setId(id); 
        ticketDadosNovos.setTipoPassagem(TipoPassagem.SEMI_LEITO);

        Mockito.when(ticketRepository.findById(id)).thenReturn(Optional.of(ticketExistente));
        Mockito.when(ticketRepository.save(any(Ticket.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        
        Ticket resultado = ticketService.atualizar(id, ticketDadosNovos);
        
        assertEquals(TipoPassagem.SEMI_LEITO, resultado.getTipoPassagem());
    }

    @Test
    @DisplayName("atualizar - Deve lançar exceção quando o ID do objeto enviado for diferente do ID existente")
    void deveLancarErroAoAtualizarComIdsDiferentes() {
        
        Long id = 1L;
        Ticket ticketExistente = new Ticket();
        ticketExistente.setId(id);

        Ticket ticketDadosNovos = new Ticket();
        ticketDadosNovos.setId(2L); 

        Mockito.when(ticketRepository.findById(id)).thenReturn(Optional.of(ticketExistente));
        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ticketService.atualizar(id, ticketDadosNovos);
        });

        assertEquals("ID do ticket atualizado não corresponde ao ID do ticket existente.", exception.getMessage());
        Mockito.verify(ticketRepository, Mockito.never()).save(any());
    }

    @Test
    @DisplayName("deletar - Deve excluir o ticket quando o ID existir")
    void deveDeletarTicketComSucesso() {
        Long id = 1L;
        Mockito.when(ticketRepository.existsById(id)).thenReturn(true);

        ticketService.deletar(id);

        Mockito.verify(ticketRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    @DisplayName("deletar - Deve lançar exceção ao tentar excluir um ID inexistente")
    void deveLancarErroAoDeletarInexistente() {
        Long id = 99L;
        Mockito.when(ticketRepository.existsById(id)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ticketService.deletar(id);
        });

        assertEquals("Ticket não encontrado para exclusão.", exception.getMessage());
        Mockito.verify(ticketRepository, Mockito.never()).deleteById(any());
    }

    @Test
    @DisplayName("listarTodos - Deve retornar a lista de todos os tickets")
    void deveListarTodosOsTickets() {
        List<Ticket> listaEsperada = List.of(new Ticket(), new Ticket());
        Mockito.when(ticketRepository.findAll()).thenReturn(listaEsperada);

        List<Ticket> resultado = ticketService.listarTodos();

        assertEquals(2, resultado.size());
    }
}
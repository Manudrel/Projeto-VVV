package com.projectvvv.demo.service;

import com.projectvvv.domain.model.*;
import com.projectvvv.domain.service.*;
import com.projectvvv.domain.repository.PagamentoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private ReservaService reservaService;

    @Mock
    private ModalService modalService;

    @Mock
    private TicketService ticketService;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private PagamentoService pagamentoService;

    @Test
    @DisplayName("realizarPagamento - Deve processar com sucesso sem juros (Até 4 parcelas)")
    void deveRealizarPagamentoSemJuros() {
        Long reservaId = 1L;
        Long clienteId = 2L;

        Reserva reserva = new Reserva();
        reserva.setCodigoReserva(2123L);
        reserva.setIdModal(10L);

        Cliente cliente = new Cliente();
        cliente.setId(clienteId);

        Modal modal = new Modal();
        modal.setId(10L);
        modal.setCapacidade(30);

        Pagamento pagamentoInput = new Pagamento();
        pagamentoInput.setTipoPagamento(TipoPagamento.CARTAO_CREDITO);
        pagamentoInput.setParcelas(3);
        pagamentoInput.setValorPago(100.0f);

        Mockito.when(reservaService.buscarPorId(reservaId)).thenReturn(reserva);
        Mockito.when(clienteService.buscarPorId(clienteId)).thenReturn(cliente);
        Mockito.when(modalService.buscarPorId(10L)).thenReturn(modal);
        Mockito.when(pagamentoRepository.save(any(Pagamento.class))).thenAnswer(i -> i.getArgument(0));

        
        Pagamento resultado = pagamentoService.realizarPagamento(reservaId, clienteId, pagamentoInput);

        
        assertNotNull(resultado);
        assertEquals(100.0f, resultado.getValorPago()); 
        assertEquals(StatusPagamento.FINALIZADO, resultado.getStatusPagamento());
        assertEquals(StatusReserva.CONCLUIDO, reserva.getStatusReserva());
        assertEquals(29, modal.getCapacidade());


        Mockito.verify(reservaService)
                .atualizar(
                        eq(reserva.getCodigoReserva()),
                        any()
                );
        Mockito.verify(modalService).atualizar(eq(10L), eq(modal));
        Mockito.verify(ticketService).criar(eq(reserva), any(Pagamento.class), eq(cliente));
    }

    @Test
    @DisplayName("realizarPagamento - Deve aplicar 5% de juros quando parcelado em mais de 4 vezes no crédito")
    void deveAplicarJurosNoCartaoCredito() {
        
        Long reservaId = 1L;
        Long clienteId = 2L;

        Reserva reserva = new Reserva();
        reserva.setIdModal(10L);

        Modal modal = new Modal();
        modal.setCapacidade(10);

        Pagamento pagamentoInput = new Pagamento();
        pagamentoInput.setTipoPagamento(TipoPagamento.CARTAO_CREDITO);
        pagamentoInput.setParcelas(5); 
        pagamentoInput.setValorPago(100.0f);

        Mockito.when(reservaService.buscarPorId(reservaId)).thenReturn(reserva);
        Mockito.when(clienteService.buscarPorId(clienteId)).thenReturn(new Cliente());
        Mockito.when(modalService.buscarPorId(10L)).thenReturn(modal);
        Mockito.when(pagamentoRepository.save(any(Pagamento.class))).thenAnswer(i -> i.getArgument(0));

        
        Pagamento resultado = pagamentoService.realizarPagamento(reservaId, clienteId, pagamentoInput);

        
        assertNotNull(resultado);
        assertEquals(104.99999f, resultado.getValorPago()); 
    }

    @Test
    @DisplayName("cancelarPagamento - Deve alterar o status para CANCELADO se o pagamento estiver FINALIZADO")
    void deveCancelarPagamentoComSucesso() {
        
        Long pagamentoId = 1L;
        Pagamento pagamentoExistente = new Pagamento();
        pagamentoExistente.setStatusPagamento(StatusPagamento.FINALIZADO);

        Mockito.when(pagamentoRepository.findById(pagamentoId)).thenReturn(Optional.of(pagamentoExistente));
        Mockito.when(pagamentoRepository.save(any(Pagamento.class))).thenAnswer(i -> i.getArgument(0));

        
        Pagamento resultado = pagamentoService.cancelarPagamento(pagamentoId);

        
        assertEquals(StatusPagamento.CANCELADO, resultado.getStatusPagamento());
    }

    @Test
    @DisplayName("cancelarPagamento - Deve lançar exceção se tentar cancelar um pagamento que não esteja FINALIZADO")
    void deveLancarErroAoCancelarPagamentoNaoFinalizado() {
        
        Long pagamentoId = 1L;
        Pagamento pagamentoPendente = new Pagamento();
        pagamentoPendente.setStatusPagamento(StatusPagamento.PENDENTE); 

        Mockito.when(pagamentoRepository.findById(pagamentoId)).thenReturn(Optional.of(pagamentoPendente));

        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pagamentoService.cancelarPagamento(pagamentoId);
        });

        assertEquals("Apenas pagamentos finalizados podem ser cancelados.", exception.getMessage());
        Mockito.verify(pagamentoRepository, Mockito.never()).save(any());
    }

    @Test
    @DisplayName("buscarPorId - Deve lançar exceção se o pagamento não existir")
    void deveLancarErroAoBuscarIdInexistente() {
        Long id = 99L;
        Mockito.when(pagamentoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> pagamentoService.buscarPorId(id));
    }
}
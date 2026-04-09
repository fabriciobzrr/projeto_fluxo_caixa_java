package services;

import entities.Movimentacao;
import entities.enums.Categorias;
import exception.FluxoDeCaixaException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FluxoDeCaixaService {
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private List<Movimentacao> movimentacoes;

    public FluxoDeCaixaService() {
        this.movimentacoes = new ArrayList<>();
    }

    public void adicionarMovimentacao(Movimentacao movimentacao){
        if (movimentacao.getValor() <= 0) {
            throw new FluxoDeCaixaException("Valor não pode ser menor que zero!");
        }

        if (movimentacao.getData().isAfter(LocalDate.now())) {
            throw new FluxoDeCaixaException("A data não pode ser futura! Você digitou a data: " + dtf.format(movimentacao.getData()));
        }

        movimentacoes.add(movimentacao);
    }

    public List<Movimentacao> listarMovimentacoes() {
        return new ArrayList<>(movimentacoes);
    }

    public double despesasTotal() {
        return movimentacoes.stream()
                .filter(m -> m.getTipo().equals("DESPESA"))
                .mapToDouble(m -> m.getValor())
                .sum();
    }

    public double receitaTotal() {
        return movimentacoes.stream()
                .filter(r -> r.getTipo().equals("RECEITA"))
                .mapToDouble(m -> m.getValor())
                .sum();
    }

    public double saldoTotal() {
        return receitaTotal() - despesasTotal();
    }

    public List<Movimentacao> filtrarPorCategoria(Categorias categoria) {
        return movimentacoes.stream()
                .filter(m -> m.getCategoria().equals(categoria))
                .collect(Collectors.toList());
    }

    public List<Movimentacao> filtrarPorTipo(String tipo) {
        return movimentacoes.stream()
                .filter(m -> m.getTipo().equals(tipo))
                .collect(Collectors.toList());
    }
}
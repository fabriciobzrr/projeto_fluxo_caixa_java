package entities;

import entities.enums.Categorias;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Movimentacao {
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String descricao;
    private double valor;
    private Categorias categoria;
    private LocalDate data;
    private String tipo;

    public Movimentacao(String descricao, Double valor, Categorias categoria, LocalDate data, String tipo) {
        this.descricao = descricao;
        this.valor = valor;
        this.categoria = categoria;
        this.data = data;
        this.tipo = tipo;
    }

    public String getNome() {
        return descricao;
    }

    public void setNome(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Categorias getCategoria() {
        return categoria;
    }

    public void setCategoria(Categorias categoria) {
        this.categoria = categoria;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | R$ %.2f | %s | %s", data.format(dtf), tipo, valor, categoria.getDescricao(), descricao);
    }
}


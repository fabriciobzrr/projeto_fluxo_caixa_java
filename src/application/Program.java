package application;

import entities.Movimentacao;
import entities.enums.Categorias;
import exception.FluxoDeCaixaException;
import services.FluxoDeCaixaService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Program {
    private static Scanner sc =  new Scanner(System.in);
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final FluxoDeCaixaService fluxoDeCaixaService = new FluxoDeCaixaService();
    public static void main (String[] args) {

        IO.println("========== FLUXO DE CAIXA ==========");

        int opcao;
        do {
            mostrarMenuPrincipal();
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarDespesa();
                    break;
                case 2:
                    cadastrarReceita();
                    break;
                case 3:
                    listarTodas();
                    break;
                case 4:
                    mostrarResumo();
                    break;
                case 5:
                    filtrarPorCategoria();
                    break;
                case 6:
                    filtrarPorTipo();
                    break;
                case 0:
                    IO.println("Encerrando aplicação...");
                    break;
                default:
                    IO.println("\nOpção inválida!");
                    break;
            }
        } while (opcao != 0);


        sc.close();
    }

    public static void mostrarMenuPrincipal() {
        IO.println("\n---- Menu Principal ----");
        IO.println("1 - Cadastrar Despesa");
        IO.println("2 - Cadastrar Receita");
        IO.println("3 - Listar Todas");
        IO.println("4 - Mostrar Resumo");
        IO.println("5 - Filtrar Por Categoria");
        IO.println("6 - Filtrar Por Tipo");
        IO.println("0 - Encerrar");
        IO.print("\nSelecione: ");
    }

    public static void cadastrarDespesa() {

        IO.println("\n---- Cadastrar Despesa ----");

        try {
            IO.print("Descrição: ");
            String descricao = sc.nextLine();

            IO.print("Valor: R$ ");
            double valor = sc.nextDouble();
            sc.nextLine();

            if (valor <= 0) {
                throw new FluxoDeCaixaException("Valor não pode ser menor ou igual a zero!");
            }

            IO.println("\nCategorias disponíveis: ");
            for (int i = 0; i < Categorias.values().length; i++) {
                IO.println((i + 1) + " - " + Categorias.values()[i].getDescricao());
            }

            IO.print("\nSelecione uma categoria (1 a " + Categorias.values().length + "): ");
            int opcaoCategoria = sc.nextInt();
            sc.nextLine();

            if (opcaoCategoria <= 0 || opcaoCategoria > Categorias.values().length) {
                throw new FluxoDeCaixaException("Opção inválida! Digite um valor de 1 a " + Categorias.values().length + "!");
            }

            Categorias categoria = Categorias.values()[opcaoCategoria - 1];
            IO.println("Selecionada: " + categoria.getDescricao());

            IO.print("\nData (DD/MM/YYYY) ou Enter para data atual: ");
            String dataTexto = sc.nextLine();
            LocalDate data;

            if (dataTexto.isEmpty()) {
                data = LocalDate.now();
                IO.println(data.format(dtf));
            } else {
                data = LocalDate.parse(dataTexto, dtf);
            }

            if (data.isAfter(LocalDate.now())) {
                throw new FluxoDeCaixaException("A data não pode ser futura!");
            }

            String tipoMovimentacao = "DESPESA";
            Movimentacao movimentacao = new Movimentacao(descricao, valor, categoria, data, tipoMovimentacao);

            fluxoDeCaixaService.adicionarMovimentacao(movimentacao);

            IO.println("\n✔ Despesa adicionada com sucesso!");
        } catch (FluxoDeCaixaException err) {
            IO.println("❌ Erro: " + err.getMessage());
        } catch (DateTimeParseException err) {
            IO.println("❌ Erro: Data inválida! Use o formato DD/MM/YYYY!");
        } catch (InputMismatchException err) {
            IO.println("❌ Erro: você não digitou um número válido!");
            sc.nextLine();
        }
    }

    public static void cadastrarReceita() {

        IO.println("\n---- Cadastrar Receita ----");

        try {
            IO.print("Descrição: ");
            String descricao = sc.nextLine();

            IO.print("Valor: R$ ");
            double valor = sc.nextDouble();

            if (valor <= 0) {
                throw new FluxoDeCaixaException("Valor não pode ser menor ou igual a zero!");
            }

            IO.println("\nCategorias disponíveis: ");
            for(int i = 0; i < Categorias.values().length; i++) {
                IO.println((i + 1) + " - " + Categorias.values()[i].getDescricao());
            }
            IO.print("\nSelecione uma categoria (1 a " + Categorias.values().length + "): ");
            int opcaoReceita = sc.nextInt();
            sc.nextLine();

            if (opcaoReceita <= 0 || opcaoReceita > Categorias.values().length) {
                throw new FluxoDeCaixaException("Opção inválida! Digite um valor de 1 a " + Categorias.values().length + "!");
            }

            Categorias categoria = Categorias.values()[opcaoReceita - 1];
            IO.println("Selecionada: " + categoria.getDescricao());

            IO.print("\nData (DD/MM/YYYY) ou ENTER para data atual: ");
            String dataTexto = sc.nextLine();
            LocalDate data;
            if (dataTexto.isEmpty()) {
                data = LocalDate.now();
            } else {
                data = LocalDate.parse(dataTexto, dtf);
            }

            if (data.isAfter(LocalDate.now())) {
                throw new FluxoDeCaixaException("A data não pode ser futura!");
            }

            String tipoMovimentacao = "RECEITA";
            Movimentacao movimentacao = new Movimentacao(descricao, valor, categoria, data, tipoMovimentacao);

            fluxoDeCaixaService.adicionarMovimentacao(movimentacao);

            IO.println("\n✔ Receita adicionada com sucesso!");
        } catch (FluxoDeCaixaException err) {
            IO.println("❌ Erro: " + err.getMessage());
        } catch (DateTimeParseException err) {
            IO.println("❌ Erro: data inválida! Use o formato DD/MM/YYYY!");
        } catch (InputMismatchException err) {
            IO.println("❌ Erro: Você não digitou um número válido!");
            sc.nextLine();
        }
    }

    public static void listarTodas() {
        IO.println("\n---- LISTAGEM DE MOVIMENTAÇÕES ----");

        if (!fluxoDeCaixaService.listarMovimentacoes().isEmpty()) {
            for (Movimentacao item : fluxoDeCaixaService.listarMovimentacoes()) {
                IO.println(item.toString());
            }
        } else {
            IO.println("Nenhum registro encontrado!");
        }


        IO.println("\n---- RESULTADO GERAL: ----");
        IO.println("Total de Despesas: R$ " + String.format("%.2f", fluxoDeCaixaService.despesasTotal()));
        IO.println("Total de Receitas: R$ " + String.format("%.2f", fluxoDeCaixaService.receitaTotal()));
        IO.println("TOTAL: R$ " + String.format("%.2f", fluxoDeCaixaService.saldoTotal()));
    }

    public static void mostrarResumo() {
        IO.println("\n---- Resumo Geral ----");
        IO.println("Total de Receitas: +R$ " + String.format("%.2f", fluxoDeCaixaService.receitaTotal()));
        IO.println("Total de Despesas: -R$ " + String.format("%.2f", fluxoDeCaixaService.despesasTotal()));

        Double saldo = fluxoDeCaixaService.saldoTotal();
        if (saldo > 0) {
            IO.println("Saldo Positivo: R$ " + String.format("%.2f", saldo));
        } else {
            IO.println("Saldo Negativo: R$ " + String.format("%.2f", saldo));
        }
        IO.println("\n----------------------");
    }

    public static void filtrarPorCategoria() {
        IO.println("\n---- Filtrar por Categoria ----");

        try {
            int count = 0;
            for (Categorias item : Categorias.values()) {
                IO.println((count + 1) + " - " + item.getDescricao());
                count++;
            }

            IO.print("\nSelecione uma categoria (1 a " + Categorias.values().length + "): ");
            int opcaoCategoria = sc.nextInt();
            sc.nextLine();

            if (opcaoCategoria < 1 || opcaoCategoria > Categorias.values().length) {
                throw new FluxoDeCaixaException("Opção inválida! Digite um valor de 1 a " + Categorias.values().length + "!");
            }

            Categorias categoria = Categorias.values()[opcaoCategoria - 1];
            IO.println("Selecionada: " + categoria.getDescricao());

            List<Movimentacao> filtradas = fluxoDeCaixaService.filtrarPorCategoria(categoria);

            if (!filtradas.isEmpty()) {
                IO.println("\n---- MOVIMENTAÇÕES POR CATEGORIA: " + categoria.getDescricao().toUpperCase() + " ----");

                for(Movimentacao item : filtradas) {
                    IO.println(item.toString());
                }

            } else {
                IO.println("\n⚠️ Nenhuma movimentação encontrada para a categoria " + categoria.getDescricao() + ".");
            }

            double totalDespesas, totalReceitas;

            totalDespesas = filtradas.stream()
                    .filter(m -> m.getTipo().equals("DESPESA"))
                    .mapToDouble(m -> m.getValor())
                    .sum();

            totalReceitas = filtradas.stream()
                    .filter(m -> m.getTipo().equals("RECEITA"))
                    .mapToDouble(m -> m.getValor())
                    .sum();
            IO.println("\n---- TOTAIS: ----");
            IO.println("Total de Despesas: R$ " + String.format("%.2f", totalDespesas));
            IO.println("Total de Receitas: R$ " + String.format("%.2f", totalReceitas));
            IO.println("Total: R$ " + String.format("%.2f", totalReceitas - totalDespesas));
        }catch (FluxoDeCaixaException err) {
            IO.println("❌ Erro: " + err.getMessage());
        } catch (InputMismatchException err) {
            IO.println("❌ Erro: Você não digitou um número válido!");
            sc.nextLine();
        }

    }

    public static void filtrarPorTipo() {
        IO.println("---- Filtrar por Tipo ----");
        try {
            String[] tipoMovimentacao = {"DESPESA", "RECEITA"};

            int count = 0;
            for (String tipo : tipoMovimentacao) {
                IO.println((count + 1) + " - " + tipo);
                count++;
            }

            IO.print("\nEscolha um tipo (1 a " + tipoMovimentacao.length + "): ");
            int opcaoTipo = sc.nextInt();
            sc.nextLine();

            if (opcaoTipo < 1 || opcaoTipo > tipoMovimentacao.length) {
                throw new FluxoDeCaixaException("Opção inválida! Digite um valor de 1 a " + tipoMovimentacao.length + "!");
            }

            String tipo = tipoMovimentacao[opcaoTipo - 1];
            IO.println("Selecionada: " + tipo);

            List<Movimentacao> filtrados = fluxoDeCaixaService.filtrarPorTipo(tipo);

            if (!filtrados.isEmpty()) {
                IO.println("\n---- MOVIMENTAÇÕES POR TIPO: " + tipo + " ----");
                for (Movimentacao item : filtrados) {
                    IO.println(item);
                }
                IO.println();
            } else {
                IO.println("\n⚠️ Nenhuma movimentação encontrada para o tipo " + tipo + ".");
            }

            double total = filtrados.stream()
                    .mapToDouble(m -> m.getValor())
                    .sum();

            IO.println("\n---- TOTAIS: ----");
            if(tipo.equals("DESPESA")) {
                IO.println("Total de Despesas: " + String.format("%.2f", total));
            } else {
                IO.println("Total de Receitas: " + String.format("%.2f", total));
            }
        } catch (FluxoDeCaixaException err) {
            IO.println("❌ Erro: " + err.getMessage());
        } catch (InputMismatchException err) {
            IO.println("❌ Erro: Você não digitou um número válido!");
            sc.nextLine();
        }

    }
}
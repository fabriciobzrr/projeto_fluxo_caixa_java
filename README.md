# Fluxo de Caixa - Sistema Java

## Sobre o Projeto

Sistema de gerenciamento de fluxo de caixa desenvolvido em Java, permitindo o cadastro de receitas e despesas, listagem de movimentações, resumo financeiro e filtros por categoria e tipo.

## Funcionalidades

- Cadastrar receitas e despesas
- Listar todas as movimentações
- Mostrar resumo com totais e saldo
- Filtrar movimentações por categoria
- Filtrar movimentações por tipo (receita/despesa)
- Validar dados (valores negativos, datas futuras, categorias inválidas)

## Estrutura do Projeto

- src/application/Program.java
- src/entities/Movimentacao.java
- src/entities/enums/Categorias.java
- src/services/FluxoDeCaixaService.java
- src/exception/FluxoDeCaixaException.java
- .gitignore
- README.md

## Tecnologias Utilizadas

- Java 25 LTS
- IntelliJ IDEA

## Conceitos Aplicados

- Programação Orientada a Objetos (POO)
- Enum para categorias fixas
- Stream API (filter, mapToDouble, sum, collect)
- Tratamento de Exceções com exceção personalizada
- Validação em duas camadas (Program + Service)
- DateTimeFormatter para datas no padrão brasileiro
- Scanner com limpeza de buffer

## Como Executar

1. Clone o repositório
2. Abra no IntelliJ IDEA
3. Execute a classe Program.java
4. Menu principal será exibido no console

## Autor

Fabrício Bezerra

- LinkedIn: https://linkedin.com/in/seu-usuario
- GitHub: https://github.com/seu-usuario

## Licença

MIT
# ProdutoDAO - Projeto de Gerenciamento de Produtos - EBAC - MD30

Este projeto é uma implementação de um DAO (Data Access Object) para gerenciar operações de banco de dados relacionadas a produtos, utilizando JDBC para a interação com o banco de dados.

## Descrição

A classe `ProdutoDAO` fornece métodos para realizar operações CRUD (Criar, Ler, Atualizar e Excluir) em produtos. A classe é uma extensão de `GenericDAO`, uma classe genérica que facilita o acesso a dados no banco de dados. O `ProdutoDAO` é responsável por realizar operações específicas na tabela `TB_PRODUTO`.

## Funcionalidades

- **Inserir Produto**: Insere um novo produto na tabela `TB_PRODUTO`.
- **Consultar Produto**: Realiza a busca de um produto específico pelo código.
- **Buscar Todos os Produtos**: Retorna todos os produtos cadastrados na tabela.
- **Excluir Produto**: Exclui um produto com base no código fornecido.

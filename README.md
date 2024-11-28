# Gestor de Coleções de Times ⚽

O **Gestor de Coleções de Times** é um sistema desenvolvido para gerenciar coleções de times e camisas utilizando uma arquitetura baseada em interfaces. O objetivo é oferecer uma solução modular e flexível para CRUDs relacionados a camisas de times de futebol.

## 🚀 Funcionalidades

- **Gerenciamento de Camisas**:
  - Adicionar novas camisas com detalhes como nome, marca, tamanho, temporada e time associado.
  - Editar informações de camisas.
  - Excluir camisas da coleção.
  - Listar camisas por time ou de forma geral.

- **Gerenciamento de Times**:
  - Adicionar novos times.
  - Editar informações de times.
  - Excluir times da coleção.
  - Listar todos os times cadastrados.

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: Java
- **Persistência de Dados**: JPA/Hibernate
- **Banco de Dados**: MySQL
- **IDE**: NetBeans

## 📂 Estrutura do Projeto

### Entidades Principais
- **Camisa**:
  - Atributos: `id`, `timeId`, `nome`, `marca`, `tamanho`, `temporada`.
- **Time**:
  - Atributos: `id`, `nome`, `ligaTime`, `sigla`.

### Interfaces
- **InterfaceDaoUsr**:
  - Métodos para gerenciar usuários, times e camisas.
  - Permite operações de adição, edição, exclusão e listagem.
  
- **InterfaceDao**:
  - Operações genéricas de CRUD para as entidades.

### Implementações
- **CamisaDaoJpa**:
  - Métodos como `incluir`, `editar`, `excluir`, `pesquisarPorId`, `listar`, `listarPorTime`.
- **TimeDaoJpa**:
  - Métodos para gerenciar a entidade Time.

## 🔧 Configuração e Execução

### Pré-requisitos
- JDK 11+
- MySQL
- IDE como NetBeans ou IntelliJ IDEA

### Passos para execução
1. Clone este repositório:
   ```bash
   git clone https://github.com/seu-usuario/gestor-colecoes-times.git

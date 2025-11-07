# 📦 Estoque de Placas — JavaFX + JSON

Aplicação desktop desenvolvida em **Java 21** com interface em **JavaFX**, criada para gerenciar o estoque de placas utilizando um arquivo **JSON** como armazenamento.  
O sistema implementa um CRUD completo e oferece uma interface simples e intuitiva para visualização, busca e manipulação dos dados.

## ✅ Funcionalidades

- **Cadastro de placas**
- **Edição** de informações já existentes
- **Exclusão** de registros
- **Listagem completa** em uma tabela dinâmica
- **Pesquisa em tempo real**, filtrando os dados exibidos
- **Persistência local** em um arquivo `placas.json`
- Interface construída com **JavaFX**
- Operações rápidas e sem necessidade de banco de dados

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **JavaFX**
- **Maven**
- **JSON** para armazenamento dos dados
- Biblioteca de leitura/escrita JSON (Jackson, Gson ou a usada no projeto)

## ▶️ Como Executar o Projeto

### **Pré-requisitos**
- Java **21**
- Maven (ou usar o Maven Wrapper incluso no projeto)
- JavaFX compatível com a versão do Java declarada no `pom.xml`

### **Rodando com Maven**
No diretório raiz:

```bash
./mvnw clean javafx:run

Aqui está o texto revisado e formatado para o **README** do GitHub:

---

# Documentação para Rodar o Aplicativo Web

## Instalando o MySQL

1. **MySQL Installer**:
   - Execute o arquivo `.exe`.
   - Na aba **Setup Type**, selecione a opção **Server only**.
   - Clique em **Next** até a parte de configuração.
   - Em **Configuration**, selecione **Development Computer** e clique em **Next**.
   - Em **Authentication Method**, selecione **Use Strong Password Encryption** e clique em **Next**.
   - Crie uma senha para utilizar posteriormente na conexão e clique em **Next**.
   - Em **Apply Configuration**, clique em **Execute**. Quando terminar, clique em **Finish**.

2. **MySQL Workbench Installer**:
   - Execute o instalador do MySQL Workbench.
   - Siga as opções padrão até o final.

## Iniciando o MySQL

1. Pesquise no computador por **MySQL 8.0 Command Line Client**.
2. Execute o **MySQL 8.0 Command Line Client**.
3. Digite a senha registrada na instalação do MySQL.
4. Após a autenticação, pode fechar o CLI.

## Configurando o MySQL Workbench

1. Abra o **MySQL Workbench**. Você verá a conexão **Local instance MySQL80**.
2. Clique nessa conexão para estabelecer a conexão, e insira a senha que você registrou durante a instalação.
3. No painel de comandos, digite o seguinte comando:
   ```sql
   CREATE DATABASE controle_estoque;
   ```
4. No painel à esquerda, em **Administration** e **Schemas**, clique em **Schemas**.
5. No painel da direita, clique nas setas de atualização para verificar se o banco de dados **controle_estoque** foi criado corretamente.
6. Se o banco de dados aparecer, você pode fechar o MySQL Workbench.

## Clonando o Repositório

1. Navegue até o diretório onde deseja clonar o projeto. Por exemplo:
   ```bash
   cd ~/Documents/IA 5º SEM
   ```

2. Clone o repositório da API (branch `SmartStock`):
   ```bash
   git clone -b SmartStock https://github.com/VictorSantilli/API-controle-de-estoque.git
   ```

3. Para clonar o repositório que contém o front-end, execute:
   ```bash
   git clone https://github.com/VictorSantilli/Gerenciador-de-estoque.git
   ```

## Rodando a API

1. Abra o código em uma IDE e navegue até o diretório:
   ```
   C:\Users\victo\Documents\IA 5º SEM\API-controle-de-estoque\api_controle_estoque\api_controle_estoque\src\main\resources
   ```

2. Abra o arquivo de configuração e adicione a senha do banco de dados no campo:
   ```properties
   spring.datasource.password=<SUA_SENHA>
   ```

   **Nota**: Este é apenas um ajuste para testes iniciais. A senha será alterada posteriormente.

3. Tente rodar o código. Se tudo estiver correto, a página de login do front-end será carregada.

---

Espero que isso ajude! Se precisar de mais alguma coisa, é só avisar.

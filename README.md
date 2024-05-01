# Controle Financeiro - Aplicação Java com Spring Boot

Esta aplicação de controle financeiro foi desenvolvida utilizando Java com Spring Boot. Com ela os usuários podem adicionar e remover receitas e despesas de forma fácil e intuitiva.

## Requisitos

Antes de iniciar, certifique-se de ter os seguintes requisitos instalados em sua máquina:

- Java Development Kit (JDK) 11 ou superior
- Apache Maven
- MySQL (ou outro banco de dados compatível)

## Configuração do Banco de Dados

1. **Crie um banco de dados MySQL**: Execute o seguinte comando SQL para criar o banco de dados:

```sql
CREATE DATABASE financasspring
```

2. **Configure as propriedades do banco de dados**: No arquivo `application.properties` localizado em `src/main/resources`, altere as configurações do banco de dados conforme necessário:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/financasspring
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

## Executando a Aplicação

1. **Clone o repositório**: Use o seguinte comando para clonar o repositório para a sua máquina local:

```bash
git clone https://github.com/kauannlima/financas-spring.git
```

2. **Acesse o diretório do projeto**: Navegue até o diretório do projeto clonado:

```bash
cd financas-spring
```

3. **Compile o projeto**: Utilize o Maven para compilar o projeto:

```bash
mvn clean install
```

4. **Execute a aplicação**: Após a compilação, execute o JAR gerado:

```bash
java -jar target/financas-spring.jar
```

## Utilização

Após iniciar a aplicação, você pode acessá-la em seu navegador usando o seguinte URL: `http://localhost:8080`. A partir daí, você poderá adicionar, visualizar e remover suas receitas e despesas de forma intuitiva.

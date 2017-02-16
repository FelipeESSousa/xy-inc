# -xy-inc
Projeto Teste Zup


 
1.	Pré-requisitos
-	Sistema Operacional Ubuntu/Windows.
-	Oracle Java JDK 7u67 instalado.
-	Jboss AS 7 instalado e configurado com módulo do oracle. 
2.	Plano de Implantação
Este plano contém as instruções para a implantação do sistema Zup. 
As instruções a seguir devem ser realizadas nos servidores que oferecem cada serviço abaixo relacionado.
3.	Informações Gerais
3.1.	Check list de execução
3.1.1.	Configurar Jboss as 7.1.1 Final

Caso não tenha o servidor JBoss AS 7.1.1.Final segue link para download do mesmo.
http://jbossas.jboss.org/downloads
Na pasta de instalação do JBoss 7.1 AS configurar os seguintes arquivos:
-	Antes das alterações, pare o servidor de aplicação Jboss.
-	Acessar a pasta conf contida no zup-parent, a qual contém os arquivos properties de configuração de variáveis utilizadas no zup project e diretórios de armazenamento de relatórios. Realize o seguinte procedimento:
o	Copiar do zup-parent de instalação: 
O diretório conf em config/jboss/conf.
o	Colar no servidor de aplicação JBoss:
Dentro do diretório $JBOSS_HOME/.

-	Acessar a pasta modules contida no zup-parent, a qual contém os módulos necessários do projeto. Realize o seguinte procedimento:
	Módulo gson:
o	Copiar da pasta o módulo gson.
o	Colar no servidor de aplicação Jboss e descompactar dentro do diretório JBOSS_HOME/modules/com/google/.
Módulo oracle:
o	Copiar da pasta o módulo oracle.
o	Colar no servidor de aplicação Jboss e descompactar dentro do diretório JBOSS_HOME/modules/com/.
Módulo json:
o	Copiar da pasta o módulo json.
o	Colar no servidor de aplicação Jboss e descompactar dentro do diretório JBOSS_HOME/modules/org/.

	
Obs.: Caso o caminho de algum diretório não exista no servidor Jboss, o mesmo deverá ser criado. Todas as pastas criadas precisam de permissão de leitura e escrita.
-	Acesse o arquivo standalone.xml dentro do diretório standalone/configuration/ e adicione as seguintes linhas dentro da tag <datasources></datasources> para configuração do datasource.

<datasource jndi-name="java:jboss/aplicacao-zup" pool-name="DeadPoolOracleZUP" enabled="true" use-java-context="true">
    <connection-url>jdbc:oracle:thin:@ SERVIDOR_ORACLE_ZUP: PORTA:SID </connection-url>
    <driver>oracleDriver</driver>
    <new-connection-sql>
        Begin
            execute immediate('ALTER SESSION SET NLS_COMP=LINGUISTIC');
            execute immediate('ALTER SESSION SET NLS_SORT=BINARY_AI');
        end;
    </new-connection-sql>
    <pool>
        <min-pool-size>0</min-pool-size>
        <max-pool-size>20</max-pool-size>
        <prefill>true</prefill>
    </pool>
    <security>
        <user-name>USUARIO_ ZUP</user-name>
        <password>SENHA_ ZUP</password>
    </security>
</datasource>

Obs.: 
Alterar os campos: SERVIDOR_ORACLE_ZUP:PORTA:SID, USUARIO_ ZUP e SENHA_ ZUP de acordo com os dados de conexão da base de dados do projeto zup. 


3.1.2.	Executar scripts da base de dados
Para a configuração do sistema é necessária a criação de uma base de dados e a inserção de dados. 
Os scripts a serem executados estão no diretório scripts/oracle/ do pacote de zup-parent.
Segue abaixo a finalidade e os passos para a execução dos scripts:

3.1.2.1.	Executar scripts da base de dados versão 1.0
1.	Executar o script 1.0.ZUP/1.0.1_zup_criar_tabelas.sql para a criação das tablespaces, usuários, papel, sequências, tabelas e índices do sistema. Se necessário, atualizar o caminho das tablespaces e a senha dos usuários.

2.	Executar o script 1.0.ZUP/1.0.2_zup_inserir_dados.sql para a inserção de dados iniciais no sistema.

1.2.3	Configurar projeto no eclipse
	No zup-parent, encontra-se os arquivos fontes do sistema, que devem ser importados no eclipse.
-	Configure o servidor jboss 7.1.1 em um eclipse com suporte para o mesmo. Caso seja necessário, baixo o plugin jboss tools dentro do eclipse market;
-	Importe os projetos como maven Project e os adicione no servidor:
•	zup-ejbDao.jar
•	zup-ejbNegocio.jar
•	zup-restClient.war
•	zup.war


-	Inicie o servidor de aplicação;

1.2.4	Configurar properties da aplicação
	Algumas variáveis de ambiente são configuradas externamente na aplicação pois dependem da localização da implantação do servidor de aplicação.
	No diretório de instalação do Jboss edite o arquivo zup-config.properties localizado em conf/aplicacao/zup/, as seguintes variáveis precisam ser alteradas caso necessário.
	Configurações de URLs:
•	PROJECT_NAME= Nome do projeto web para a url de navegação. (Ex.: zup)
•	PROJECT_REST= Nome do projeto rest para a url de serviços. (Ex.: zup-restClient)
•	PROTOCOL_REST= Protocolo de endereço web do servidor de aplicação onde se encontra o pacote zup-restClient.war (Ex.: http)
•	PORT_REST= Porta de endereço web do servidor de aplicação onde se encontra o pacote zup-restClient.war (Ex.: 8080)
•	IP_REST= IP de endereço web do servidor de aplicação onde se encontra o pacote zup-restClient.war (Ex.: localhost)

1.2.5	Configurar logs aplicação
Abra o arquivo $JBOSS_HOME/jboss/standalone/configuration/standalone.xml para edição e adicione a seguinte configuração dentro da tag <subsystem xmlns="urn:jboss:domain:logging:1.1">:
<size-rotating-file-handler name="FILE-ZUP">
    <formatter>
        <pattern-formatter pattern="%d{yyyy-MM-dd HH:mm:ss} [%-5p] %C.%M(%F:%L) - %m%n"/>
    </formatter>
    <file relative-to="jboss.server.log.dir" path="zup.log"/>
    <rotate-size value="20480k"/>
    <max-backup-index value="10"/>
    <append value="true"/>
</size-rotating-file-handler>
<logger category="br.com.zup">
    <level name="INFO"/>
    <handlers>
        <handler name="CONSOLE"/>
        <handler name="FILE-ZUP"/>
    </handlers>
</logger>

1.3	Acessar o sistema

Após as configurações, reinicie o Jboss. Caso tudo ocorra corretamente, acesse o endereço através de um navegador web:
•	http://ENDERECO_SERVIDOR_APLICACAO:PORTA/zup

2.	Plano de Rollback
Caso o servidor de aplicação não execute corretamente ou ocorram erros na execução dos scripts SQL, realize o rollback da implantação através dos passos a seguir.

2.2	Desinstalar a aplicação
-	Parar o servidor de aplicação JBoss.
-	Remover os seguintes arquivos do diretório standalone/deployments/ do JBoss:
•	zup-ejbDao.jar
•	zup-ejbNegocio.jar
•	zup-restClient.war
•	zup.war
-	Verificar se existe os seguintes diretórios em standalone/deployments/, remove-los caso exista:
•	zup-ejbDao.jar
•	zup-ejbNegocio.jar
•	zup-restClient.war
•	zup.war

-	Iniciar o Jboss novamente.

2.3	Rollback da Base de Dados
Na ocorrência de erros nos scripts da base de dados versão 1.0, execute o script de rollback 1.0.zup_rollback.sql, disponível no diretório scripts/Oracle/1.0.ZUP do zup-parent.

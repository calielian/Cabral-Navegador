# Cabral Navegador
Aqui está o Cabral Navegador, um projeto pessoal pensado a anos.

Se quiser construir o aplicativo a partir do código fonte, o ***makefile*** está disponível para isso, simplesmente digitando ```make``` no seu terminal na pasta onde está o ***makefile***.

Escrito totalmente em Java, usando a biblioteca [Equo Chromium Community Edition](https://github.com/equodev/chromium) para a integração do navegador junto com a biblioteca Swing e [FlatLaf](https://github.com/JFormDesigner/FlatLaf) para GUI.

Todos os ícones, com exceção da logo do aplicativo, foram retirados do [Boxicons](https://github.com/atisawd/boxicons), um set de ícones open source.

Como ferramenta de build do Java, foi utilizado o Apache Maven.

O navegador possui um sistema de favoritos, configurações de tema claro ou escuro e também definição da tela inicial do navegador, que também já vem uma simples de fábrica.

O navegador NÃO está muito polido, e dificilmente estará algum dia: a cada vez que é alterado um link, a barra de URL não altera automaticamente, as abas podem apresentar bugs ou mal-funcionamento, etc. Não sei como resolver.

**Caso der erro, verifique se o Java, o Maven e o Make estão instalados no sistema e no seu PATH executando esses comandos:**

```java -version```<br>
```mvn -v``` <br>
```make -v```

Aos serem executados, você deve ver a versão do Java, do Maven e do Make, respectivamente. Se algum não aparecer, instale o que falta.
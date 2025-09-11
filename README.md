
# [![Typing SVG](https://readme-typing-svg.herokuapp.com?font=Fira+Code&weight=500&size=32&pause=1000&color=228B22&width=435&lines=LabelLens)](https://github.com/andregarcia0412/LabelLens)

![Python](https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=python&logoColor=white)

![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white) 

![Android Studio](https://img.shields.io/badge/Android%20Studio-3DDC84?style=for-the-badge&logo=androidstudio&logoColor=white) 

Desenvolvido para o processo seletivo da Apple Developer Academy, o LabelLens é um aplicativo que usa a câmera do celular e a tecnologia da Google Vision AI para reconhecer os caracteres e interpretar as informações nutricionais dos alimentos.


## 📱 Instalação (Mobile)
O APK para celular pode ser baixado em: https://github.com/andregarcia0412/LabelLens/releases/tag/v1.0

### Pré-requisitos:
- Java Development Kit (JDK 11 ou superior)
- Android Studio: A IDE para desenvolver e rodar o aplicativo.

## 💻 Instalação e Execução (PC)
1. Clonar ou Baixar o Repositório:
Abra o terminal e clone o repositório do projeto do GitHub.

```Bash
git clone https://github.com/andregarcia0412/LabelLens
```
2. Abrir o Projeto no Android Studio:

Abra o Android Studio.

Vá em File > Open (Arquivo > Abrir) e selecione a pasta do projeto Android que você acabou de clonar.

Aguarde o Android Studio sincronizar o projeto. Isso pode levar alguns minutos na primeira vez.

3. Verificar a URL do Servidor:

Dentro do projeto no Android Studio, abra o arquivo ServiceApi.java.

Confirme que a variável apiUrl está armazenando o endereço correto do servidor já hospedado no Render. O valor deve ser algo como:

`private String apiUrl = "https://leitor-rotulos-api.onrender.com/analyse_label"`

(Não é necessário usar ngrok ou rodar um servidor local, pois a API já está online).

4. Rodar o Aplicativo:

Conecte um celular Android (com o modo de desenvolvedor ativado) ao seu computador ou inicie um Emulador a partir do Android Studio.

Clique no botão de "Play" (▶️) na barra de ferramentas superior para compilar e instalar o aplicativo no seu dispositivo.


    
## ⚙ Futuras Atualizações

O projeto ainda está em desenvolvimento, futuras atualizações incluirão:

- Contador de calorias baseado nos produtos escaneados
- Possibilidade de leitura dos ingredientes
- Alertas de alergênicos
- Comparador de produtos
- Histórico e favoritos


## 📚 Stack utilizada

**Front-end:** Java

**Back-end:** Python 3, Flask, Gunicorn, Unidecode, Google Cloud Vision AI

## 📃 Licença

Este projeto está licenciado sob a **MIT License**.  
Para mais detalhes, consulte o arquivo [LICENSE](LICENSE) do repositório.

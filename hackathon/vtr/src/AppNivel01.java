import java.util.Scanner;
import java.io.IOException;
import java.util.Random;

public class AppNivel01 {

    // Constante com os caracteres aceitos
    final static String CARACTERES_IDENTIFICADORES_ACEITOS = "XO0UC";

    // Tamanho do tabuleiro 3x3.
    final static int TAMANHO_TABULEIRO = 3;

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        // Inicializa o tabuleiro
        char[][] tabuleiro = new char[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
        inicializarTabuleiro(tabuleiro);

        // Obtém os caracteres para o usuário e o computador
        char caractereUsuario = obterCaractereUsuario(teclado);
        char caractereComputador = obterCaractereComputador(teclado, caractereUsuario);

        // Define quem começa jogando
        boolean vezUsuarioJogar = sortearValorBooleano();

        boolean jogoContinua;
        do {
            jogoContinua = true;
            exibirTabuleiro(tabuleiro);

            if (vezUsuarioJogar) {
                // Processa a vez do usuário
                tabuleiro = processarVezUsuario(teclado, tabuleiro, caractereUsuario);

                // Verifica se o usuário venceu
                if (teveGanhador(tabuleiro, caractereUsuario)) {
                    exibirVitoriaUsuario();
                    jogoContinua = false;
                }

                vezUsuarioJogar = false; // Passa a vez para o computador
            } else {
                // Processa a vez do computador
                tabuleiro = processarVezComputador(tabuleiro, caractereComputador);

                // Verifica se o computador venceu
                if (teveGanhador(tabuleiro, caractereComputador)) {
                    exibirVitoriaComputador();
                    jogoContinua = false;
                }

                vezUsuarioJogar = true; // Passa a vez para o usuário
            }

            // Verifica se houve empate
            if (jogoContinua && teveEmpate(tabuleiro)) {
                exibirEmpate();
                jogoContinua = false;
            }
        } while (jogoContinua);

        teclado.close();
    }
//INICIO******************************************************

    //Inicializa o tabuleiro 3x3 com o caractere ' ' (espaço).
    static char[][] inicializarTabuleiro(char[][] tabuleiro) {

        // Percorre todas as linhas e colunas do tabuleiro
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[i].length; j++) {

                // Define cada posição com o caractere ' ' (espaço)
                tabuleiro[i][j] = ' ';
            }
        }
        // Retorna o tabuleiro inicializado
        return tabuleiro;
    }
    //Solicita ao usuário um caractere para representá-lo no jogo.
    //Solicita ao usuário um caractere para representá-lo no jogo.
    static char obterCaractereUsuario(Scanner teclado) {
        char caractereEscolhido;
        boolean caractereValido = false;

        do {
            // Solicita ao usuário para escolher um caractere
            System.out.print("Escolha um caractere para representar você (X, O, 0, U, C): ");
            String entrada = teclado.nextLine().toUpperCase();

            // Valida se o usuário digitou apenas um caractere
            if (entrada.length() == 1) {
                caractereEscolhido = entrada.charAt(0);

                // Verifica se o caractere está na lista de permitidos
                for (int i = 0; i < CARACTERES_IDENTIFICADORES_ACEITOS.length(); i++) {
                    if (caractereEscolhido == CARACTERES_IDENTIFICADORES_ACEITOS.charAt(i)) {
                        caractereValido = true;
                        break;
                    }
                }
                if (!caractereValido) {
                    System.out.println("Caractere inválido! Escolha um dos caracteres permitidos.");
                }
            } else {
                System.out.println("Entrada inválida! Por favor, digite apenas um caractere.");
                
                // Apenas inicializa para evitar erro de compilação
                caractereEscolhido = ' ';
            }
            
        // Continua até que o caractere seja válido
        } while (!caractereValido);

        return caractereEscolhido;
    }
    //Solicita ao usuário um caractere para representar o computador.
    static char obterCaractereComputador(Scanner teclado, char caractereUsuario) {
        char caractereEscolhido;
        boolean caractereValido = false;

        do {
            // Solicita ao usuário para escolher um caractere para o computador
            System.out.print("Escolha um caractere para representar o computador (X, O, 0, U, C): ");
            String entrada = teclado.nextLine().toUpperCase();

            // Verifica se o usuário digitou apenas um caractere
            if (entrada.length() == 1) {
                caractereEscolhido = entrada.charAt(0);

                // Verifica se o caractere está na lista de permitidos e não é igual ao do usuário
                if (caractereEscolhido != caractereUsuario) {
                    for (int i = 0; i < CARACTERES_IDENTIFICADORES_ACEITOS.length(); i++) {
                        if (caractereEscolhido == CARACTERES_IDENTIFICADORES_ACEITOS.charAt(i)) {
                            caractereValido = true;
                            break;
                        }
                    }
                    if (!caractereValido) {
                        System.out.println("Caractere inválido! Escolha um dos caracteres permitidos.");
                    }
                } else {
                    System.out.println("Caractere inválido! O computador não pode usar o mesmo caractere que você.");
                }
            } else {
                System.out.println("Entrada inválida! Por favor, digite apenas um caractere.");
                
                // Apenas inicializa para evitar erro de compilação
                caractereEscolhido = ' ';
            }
            
        // Continua até que o caractere seja válido
        } while (!caractereValido);

        return caractereEscolhido;
    }
    static boolean jogadaValida(String posicoesLivres, int linha, int coluna) {

        // Formatar a jogada no formato "linha+coluna" (e.g., "01" para linha 0, coluna 1)
        String jogada = linha + "" + coluna;
    
        // Verificar se a jogada está presente na lista de posições livres
        return posicoesLivres.contains(jogada);
    }
    static int[] obterJogadaUsuario(String posicoesLivres, Scanner teclado) {

        int[] jogada = new int[2]; // Vetor para armazenar linha e coluna
        boolean jogadaValida = false;
    
        while (!jogadaValida) {
            
            try {
                System.out.println("Digite a linha e a coluna que deseja jogar, separados por um espaço (Exemplo: 1 2):");
                String entrada = teclado.nextLine().trim();
    
                // Dividir a entrada em dois valores separados por espaço
                String[] valores = entrada.split(" ");
    
                // Verificar se foram digitados exatamente dois valores
                if (valores.length != 2) {
                    System.out.println("Por favor, digite dois números separados por espaço!");
                    continue;
                }
                // Converter os valores para números inteiros
                int linha = Integer.parseInt(valores[0]);
                int coluna = Integer.parseInt(valores[1]);
    
                // Ajustar os valores para índices (de 1-3 para 0-2)
                linha -= 1;
                coluna -= 1;
    
                // Verificar se a jogada está dentro dos limites do tabuleiro (3x3)
                if (linha < 0 || linha > 2 || coluna < 0 || coluna > 2) {
                    System.out.println("Linha ou coluna fora dos limites! Digite valores entre 1 e 3.");
                    continue;
                }
    
                // Verificar se a jogada está presente na lista de posições livres
                String jogadaFormatada = linha + "" + coluna;
                if (!posicoesLivres.contains(jogadaFormatada)) {
                    System.out.println("Esta jogada não é permitida, escolha outra posição!");
                    continue;
                }
    
                // A jogada é válida
                jogada[0] = linha;
                jogada[1] = coluna;
                jogadaValida = true;
    
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Certifique-se de digitar dois números inteiros.");
            }
        }
        return jogada;
    }
    //Obtém a jogada do computador sorteando uma posição livre.
    static int[] obterJogadaComputador(String posicoesLivres) {

        // Divide as posições livres em um vetor de strings
        String[] vetorPosicoesLivres = posicoesLivres.split(";");

        // Gera um número aleatório dentro do intervalo de posições livres
        Random random = new Random();
        int indiceSorteado = random.nextInt(vetorPosicoesLivres.length);

        // Obtém a posição sorteada no formato "xy"
        String jogadaSorteada = vetorPosicoesLivres[indiceSorteado];

        // Converte a jogada no formato "xy" para um vetor de inteiros [x, y]
        return converterJogadaStringParaVetorInt(jogadaSorteada);
    }
    //Converte uma jogada no formato "xy" para um vetor de inteiros [x, y].
    static int[] converterJogadaStringParaVetorInt(String jogada) {

        // Extrai o caractere referente à linha (x) e converte para inteiro
        int linha = Character.getNumericValue(jogada.charAt(0));

        // Extrai o caractere referente à coluna (y) e converte para inteiro
        int coluna = Character.getNumericValue(jogada.charAt(1));

        // Retorna o vetor de inteiros [linha, coluna]
        return new int[] { linha, coluna };
    }
    static char[][] processarVezUsuario(Scanner teclado, char[][] tabuleiro, char caractereUsuario) {

        // Exibe mensagem indicando que é a vez do usuário
        System.out.println("É a sua vez, usuário! Faça sua jogada.");

        // Obtém a jogada do usuário
        int[] jogada = obterJogadaUsuario(retornarPosicoesLivres(tabuleiro), teclado);

        // Atualiza o tabuleiro com a jogada do usuário
        tabuleiro = retornarTabuleiroAtualizado(tabuleiro, jogada, caractereUsuario);

        // Retorna o tabuleiro atualizado
        return tabuleiro;
    }
    static char[][] processarVezComputador(char[][] tabuleiro, char caractereComputador) {
        System.out.println("É a vez do computador!");
        
        // Obter as posições livres no tabuleiro
        String posicoesLivres = retornarPosicoesLivres(tabuleiro);

        // Obter a jogada do computador a partir das posições livres
        int[] jogadaComputador = obterJogadaComputador(posicoesLivres);

        // Atualizar o tabuleiro com a jogada do computador
        return retornarTabuleiroAtualizado(tabuleiro, jogadaComputador, caractereComputador);
    }
    static String retornarPosicoesLivres(char[][] tabuleiro) {
        
        // Usando StringBuilder para construir a string
        StringBuilder posicoesLivres = new StringBuilder();
        
        // Percorrer o tabuleiro para encontrar as posições livres
        for (int i = 0; i < tabuleiro.length; i++) {  // Percorre as linhas
            for (int j = 0; j < tabuleiro[i].length; j++) {  // Percorre as colunas
        
                // Se a posição estiver livre (caractere ' '), adiciona à lista de posições livres
                if (tabuleiro[i][j] == ' ') {
                    posicoesLivres.append(i).append(j).append(";");  // Adiciona a posição no formato xy
                }
            }
        }
        // Se a lista de posições livres não estiver vazia, remove o último ponto e vírgula
        if (posicoesLivres.length() > 0) {
            posicoesLivres.deleteCharAt(posicoesLivres.length() - 1);
        }
        // Retorna a string com as posições livres
        return posicoesLivres.toString();
    }
    static boolean teveGanhador(char[][] tabuleiro, char caractereJogador) {

        // Verifica se o jogador ganhou em alguma linha
        if (teveGanhadorLinha(tabuleiro, caractereJogador)) {
            return true;
        }
        // Verifica se o jogador ganhou em alguma coluna
        if (teveGanhadorColuna(tabuleiro, caractereJogador)) {
            return true;
        }
        // Verifica se o jogador ganhou na diagonal principal
        if (teveGanhadorDiagonalPrincipal(tabuleiro, caractereJogador)) {
            return true;
        }
        // Verifica se o jogador ganhou na diagonal secundária
        if (teveGanhadorDiagonalSecundaria(tabuleiro, caractereJogador)) {
            return true;
        }
        // Se não encontrou ganhador, retorna false
            return false;
    }
    // Verifica se o jogador ganhou em alguma linha
    static boolean teveGanhadorLinha(char[][] tabuleiro, char caractereJogador) {

        // Verifica se todas as células de uma linha estão preenchidas com o caractere do jogador
        for (int i = 0; i < 3; i++) {
            if (tabuleiro[i][0] == caractereJogador && 
                tabuleiro[i][1] == caractereJogador && 
                tabuleiro[i][2] == caractereJogador) {
                return true;  // Ganhou na linha
            }
        }
        return false;  // Não ganhou em nenhuma linha
    }
    // Verifica se o jogador ganhou em alguma coluna
    static boolean teveGanhadorColuna(char[][] tabuleiro, char caractereJogador) {

        // Verifica se todas as células de uma coluna estão preenchidas com o caractere do jogador
        for (int i = 0; i < 3; i++) {
            if (tabuleiro[0][i] == caractereJogador && 
                tabuleiro[1][i] == caractereJogador && 
                tabuleiro[2][i] == caractereJogador) {
                return true;  // Ganhou na coluna
            }
        }
        return false;  // Não ganhou em nenhuma coluna
    }
    // Verifica se o jogador ganhou na diagonal principal
    static boolean teveGanhadorDiagonalPrincipal(char[][] tabuleiro, char caractereJogador) {

        // Verifica se a diagonal principal está toda preenchida com o caractere do jogador
        return tabuleiro[0][0] == caractereJogador && 
            tabuleiro[1][1] == caractereJogador && 
            tabuleiro[2][2] == caractereJogador;
    }
    // Verifica se o jogador ganhou na diagonal secundária
    static boolean teveGanhadorDiagonalSecundaria(char[][] tabuleiro, char caractereJogador) {

        // Verifica se a diagonal secundária está toda preenchida com o caractere do jogador
        return tabuleiro[0][2] == caractereJogador && 
            tabuleiro[1][1] == caractereJogador && 
            tabuleiro[2][0] == caractereJogador;
    }
    static void limparTela() {

    // Verifica o sistema operacional para decidir qual comando executar
    String sistemaOperacional = System.getProperty("os.name").toLowerCase();
    
    try {
        // Se o sistema operacional for Windows
        if (sistemaOperacional.contains("win")) {
            // Executa o comando 'cls' para limpar a tela no Windows
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "cls");
            processBuilder.inheritIO().start().waitFor();
        } 
        // Se o sistema operacional for baseado em Unix (Linux, macOS)
        else {
            // Executa o comando 'clear' para limpar a tela no Linux/macOS
            ProcessBuilder processBuilder = new ProcessBuilder("clear");
            processBuilder.inheritIO().start().waitFor();
        }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(); // Caso ocorra algum erro, imprime a exceção
        }
    }
    public static void exibirTabuleiro(char[][] tabuleiro) {
        Scanner teclado = new Scanner(System.in);

        // Inicializa o tabuleiro com o tamanho fixo 3x3
        inicializarTabuleiro(tabuleiro);

        // Obtém os caracteres para o jogador e computador
        char caractereUsuario = obterCaractereUsuario(teclado);
        char caractereComputador = obterCaractereComputador(teclado, caractereUsuario);

        System.out.println("Caractere do usuário: " + caractereUsuario);
        System.out.println("Caractere do computador: " + caractereComputador);
    }
   static char[][] retornarTabuleiroAtualizado(char[][] tabuleiro, int[] jogada, char caractereJogador) {

        // Atualiza o tabuleiro na posição indicada pela jogada
        tabuleiro[jogada[0]][jogada[1]] = caractereJogador;
    
        // Retorna o tabuleiro atualizado
        return tabuleiro;
    }
    static void exibirVitoriaComputador() {

        System.out.println("O computador venceu!");
        System.out.println("""
        \t\t\t    +---------------+
        \t\t\t    | +-----------+ |
        \t\t\t    | | __    __  | |
        \t\t\t    | |  0    0   | |
        \t\t\t    | |    --     | |
        \t\t\t    | |   \\__/    | |
        \t\t\t    | |           | |
        \t\t\t    | +-----------+ |
        \t\t\t    +-----+---+-----+
        \t\t\t    _____|    |_____
        \t\t\t   /                \\
        \t\t\t  /  --------------- \\
        \t\t\t /   ---------------  \\
        \t\t\t +---------------------+
        \n\n""");
    }
    static void exibirVitoriaUsuario() {

        System.out.println("O usuário venceu!");
        System.out.println("""
        \t\t\t          /////////
        \t\t\t    \\\\|/////////
        \t\t\t     \\/////////
        \t\t\t     |         |
        \t\t\t    (| --- --- |)
        \t\t\t     |  0   0  |
        \t\t\t     |    ..   |
        \t\t\t     |  \\___/  |
        \t\t\t     |_________|
        \t\t\t        |   |
        \t\t\t        |   |
        \t\t\t       /     \\
        \t\t\t      /       \\
        \n\n\n""");
    }
    static void exibirEmpate() {

        // Exibe a mensagem de empate
        System.out.println("Ocorreu empate!");
    
        // Exibe a arte ASCII do placar "0 X 0"
        System.out.println("    _____   _____ ");
        System.out.println("   |     | |     |");
        System.out.println("   |  0  | |  X  |");
        System.out.println("   |_____| |_____|");
    }
    static boolean teveEmpate(char[][] tabuleiro) {

        // Percorrer todo o tabuleiro e verificar se há algum espaço vazio
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[i].length; j++) {
                if (tabuleiro[i][j] == ' ') {

                    // Se encontrar algum espaço vazio, significa que o jogo não terminou em empate
                    return false;
                }
            }
        }
        // Se não encontrar espaços vazios, então o jogo terminou em empate
        return true;
    }
    //Realiza as ações necessárias para processar a vez do usuário jogar.
    static boolean sortearValorBooleano() {

        // Criar um objeto Random
        Random random = new Random();
    
        // Retornar o valor sorteado (true ou false)
        return random.nextBoolean();
    }
}
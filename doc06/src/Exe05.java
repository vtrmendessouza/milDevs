/*
Depois que implementar o item anterior, 
tente criar um contador para contar quantas vezes 
o usuário ganhou e quantas vezes o computador ganhou. 
Quando o usuário responder que não deseja mais jogar, 
apresente a quantidade de vitórias de cada, 
com a quantidade de vitórias e o % de vitórias. 
*/
import java.util.Scanner;
import java.util.Random;
public class Exe05 {
    public static void main(String[] args) throws Exception {
        Scanner teclado = new Scanner(System.in);
        Random random = new Random();
        
        int numeroSorteado;
        int escolhaComputador;
        int escolhaJogador;
        String resultado;
        int repetir = 0;
        do{
            numeroSorteado = random.nextInt(1,90);

            if (numeroSorteado >= 61)
                escolhaComputador = 3;
            else if (numeroSorteado >= 31) 
                escolhaComputador = 2;
            else 
                escolhaComputador = 1;
            
            //papel = 1
            //pedra = 2
            //tesoura = 3

            //papel ganha de pedra
            //pedra ganha de tesoura
            //tesoura ganha de papel
            //empates

            System.out.println("Jogo Pedra/Papel/Tesoura: ");
            System.out.println("\n1 - papel ");
            System.out.println("2 - pedra ");
            System.out.println("3 - tesoura ");
            System.out.print("\nDigite uma das opções: ");

            escolhaJogador = teclado.nextInt();

            if (escolhaComputador == 1 && escolhaJogador == 2) 
                resultado = "Computador ganhou";
            else if (escolhaComputador == 2 && escolhaJogador == 1)
                resultado = "Você ganhou";
                else if (escolhaComputador == 3 && escolhaJogador == 2) 
                resultado = "Você ganhou";
            else if (escolhaComputador == 2 && escolhaJogador == 3)
                resultado = "Computador ganhou";
            else if (escolhaComputador == 1 && escolhaJogador == 3) 
                resultado = "Você ganhou";
            else if (escolhaComputador == 3 && escolhaJogador == 1)
                resultado = "Computador ganhou";
            else 
                resultado = "Empate";

            System.out.println("\nComputador escolheu: " + devolveEscolha(escolhaComputador));
            System.out.println("Usuário escolheu: " + devolveEscolha(escolhaJogador));

            System.out.println("\nResultado: " + resultado);

            System.out.println("\nDigite 1 para repetir ou 0 para encerrar: ");
            repetir = teclado.nextInt();
        }
        while(repetir == 1);
        teclado.close();
    }
    static String devolveEscolha(int escolha){
        switch (escolha) {
            case 1:
                return "papel";
            case 2:
                return "pedra";
            default:
            return "tesoura";
        }
    }
}
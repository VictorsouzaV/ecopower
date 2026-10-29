package br.com.ecopower.view;

import java.util.Scanner;

public final class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static String lerTexto(String mensagem) {
        System.out.print(mensagem);
            return SCANNER.nextLine().trim();
    }

    public static int lerInteiro(String mensagem) {
        while (true) {
            try { 
                return Integer.parseInt(lerTexto(mensagem)); 

            } catch (NumberFormatException e) { 
                System.out.println("Digite um número inteiro válido."); 
            }
        }
    }

    public static double lerDecimal(String mensagem) {
        while (true) {
            try { 
                return Double.parseDouble(lerTexto(mensagem).replace(',', '.')); 
                
            } catch (NumberFormatException e) { 
                System.out.println("Digite um número válido."); 
            }
        }
    }

    public static double lerPercentual(String mensagem) {
        return lerDecimal(mensagem) / 100.0;
    }
}

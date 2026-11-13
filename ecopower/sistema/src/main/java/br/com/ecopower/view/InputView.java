package br.com.ecopower.view;

import java.util.Scanner;

public final class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return SCANNER.nextLine().trim();
    }

    public static void aguardarEnter() {
        System.out.println("Pressione Enter para voltar ao menu...");
            SCANNER.nextLine();
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

    public static int lerInteiroEntre(String mensagem, int minimo, int maximo) {
        while (true) {
            int valor = lerInteiro(mensagem);
            if (valor >= minimo && valor <= maximo) return valor;
            System.out.printf("Informe um valor inteiro entre %d e %d.%n", minimo, maximo);
        }
    }

    public static double lerDecimal(String mensagem) {
        while (true) {
            try {
                double valor = Double.parseDouble(lerTexto(mensagem).replace(',', '.'));
                if (Double.isFinite(valor)) return valor;
                System.out.println("Digite um número finito válido.");
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }

    public static double lerDecimalEntre(String mensagem, double minimo, double maximo) {
        while (true) {
            double valor = lerDecimal(mensagem);
            if (valor >= minimo && valor <= maximo) return valor;
            System.out.printf(java.util.Locale.US, "Informe um valor entre %.2f e %.2f.%n", minimo, maximo);
        }
    }

    public static double lerPercentualEntre(String mensagem, double maximoPercentual) {
        return lerDecimalEntre(mensagem, 0, maximoPercentual) / 100.0;
    }
}

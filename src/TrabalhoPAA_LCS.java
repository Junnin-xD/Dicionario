import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TrabalhoPAA_LCS {

    static ArrayList<String> tokens;

    public static void main(String[] args) {
        tokens = lerPalavrasDicionario();
        Scanner in = new Scanner(System.in);

        System.out.println("Digite a palavra chave: ");
        String palavraChave = in.nextLine();

        palavraChave = palavraChave.toUpperCase();

        encontraPalavraChave(tokens, palavraChave);

    }

    // palavra chave deve possuir pelo menos três letras em maiúsculo
    public static boolean verificaPalavraChave(String palavra) {
        int cont = 0;
        for (int i = 0; i < palavra.length(); i++) {
            if (Character.isUpperCase(palavra.charAt(i)))
                cont++;
            else
                break;
        }

        return cont >= 3;
    }

    // Implementar código para encontrar a palavra mais próxima no dicionário
    // e escrever o seu significado
    public static void encontraPalavraChave(ArrayList<String> tokens, String busca) {
        int maior = 0;
        int indice = 0;
        int lcs = 0;

        for (int i = 0; i < tokens.size(); i++) {
            if (verificaPalavraChave(tokens.get(i))) {
                lcs = lcsProgamacaoDinamica(removeAcentos(busca), removeAcentos(tokens.get(i)));
                if (lcs > maior) {
                    maior = lcs;
                    indice = i;
                }
            }
        }
        System.out.println("Voce quis dizer: " + tokens.get(indice));

        for (int n = lcs + 1; n < tokens.size(); n++) {

            if (!verificaPalavraChave(tokens.get(n))) {
                System.out.print(tokens.get(n) + " ");

            } else {
                break;
            }

        }

    }

    public static String removeAcentos(String value) {
        String normalizer = Normalizer.normalize(value, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("[^\\p{ASCII}]");
        return pattern.matcher(normalizer).replaceAll("");
    }

    // https://github.com/PonjoDEV
    public static int lcsProgamacaoDinamica(String y, String x) {

        int[][] matriz = new int[x.length() + 1][y.length() + 1];

        for (int i = 0; i < matriz.length; i++) {
            matriz[i][0] = 0;
        }

        for (int i = 0; i < matriz[0].length; i++) {
            matriz[0][i] = 0;
        }

        for (int i = 1; i <= x.length(); i++) {
            for (int j = 1; j <= y.length(); j++) {
                if (x.charAt(i - 1) == y.charAt(j - 1)) {
                    matriz[i][j] = matriz[i - 1][j - 1] + 1;
                } else {
                    matriz[i][j] = Math.max(matriz[i - 1][j], matriz[i][j - 1]);
                }
            }
        }

        return matriz[x.length()][y.length()];

    }

    public static ArrayList<String> lerPalavrasDicionario() {
        try {
            Scanner in;
            in = new Scanner(new FileReader("dicionario.txt"));
            ArrayList<String> tokens = new ArrayList<String>();
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] words = line.split(" ");
                for (String w : words)
                    tokens.add(w);
            }
            return tokens;
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

}
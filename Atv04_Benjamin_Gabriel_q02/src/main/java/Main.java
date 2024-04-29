import estruturas.*;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        TabelaHash<String, String> tabelaHash = new TabelaHash<>();

        // Caminho do arquivo de entrada
        String inputFilePath = "src/main/java/estruturas/entrada.txt";
        // Caminho do arquivo de saída
        String outputFilePath = "src/main/java/estruturas/saida_hash.txt";

        try {
            // Cria um FileWriter para escrever no arquivo de saída
            FileWriter writer = new FileWriter(outputFilePath);

            // Usa Scanner para ler o arquivo de entrada
            Scanner scanner = new Scanner(new File(inputFilePath));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(", ");
                if (parts.length == 2) {
                    String chave = parts[0];
                    String elemento = parts[1];
                    String hash = Ripemd128.ripemd128(elemento);
                    writer.write("Criptografia do elemento de (" + chave + "), (" + elemento + "): " + hash + "\n");
                }
            }
            scanner.close();
            writer.close();

            System.out.println("Processo concluído. Verifique o arquivo de saída em '" + outputFilePath + "'.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
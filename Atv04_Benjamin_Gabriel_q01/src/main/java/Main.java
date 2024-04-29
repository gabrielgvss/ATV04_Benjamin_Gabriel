import estruturas.TabelaHash;

public class Main {
    public static void main(String[] args){
        TabelaHash<String, String> nomes = new TabelaHash<>();

        System.out.println("\nBuscar o elemento com chave id9:");
        System.out.println("Valor encontrado: " + nomes.buscar("id9"));

        // Tentar buscar um elemento inexistente na tabela
        System.out.println("\nBuscar o elemento com chave id99:");
        System.out.println("Valor encontrado: " + nomes.buscar("id99"));

        System.out.println("Elemento removido: " + nomes.remover("id300"));
        System.out.println("\nBuscar o elemento com chave id300:");
        System.out.println("Valor encontrado: " + nomes.buscar("id300"));

        //nomes.imprimir();






    }
}

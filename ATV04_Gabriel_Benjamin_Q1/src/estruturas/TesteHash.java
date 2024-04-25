package estruturas;

public class TesteHash {
    public static void main(String[] args){
        TabelaHash<Integer, Character> letras = new TabelaHash<>(25);
        char[] alfabeto = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        for (int i = 0; i < alfabeto.length; i++){
            letras.inserir(i, alfabeto[i]);

        }

        letras.imprimirElementos();



        /*
        nomes.inserir(1, "Ricardo");
        nomes.inserir(2, "Pedro");
        nomes.inserir(3, "Jorge");
        nomes.inserir(2, "Nego ney");
        String buscar = nomes.buscar(3);
        System.out.println(buscar);
        nomes.imprimirElementos();

         */


    }
}

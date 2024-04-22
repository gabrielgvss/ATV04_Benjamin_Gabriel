package estruturas;

public class TesteArvore {
    public static void main(String[] args){
        Arvore<Integer> arvore = new Arvore<>();
        arvore.inserir(1);
        arvore.inserir(5);
        arvore.inserir(-1);
        arvore.inserir(-3);
        arvore.inserir(20);
        arvore.inserir(3);

        //arvore.imprimir();
        System.out.println(arvore.buscar(1));
        System.out.println(arvore.buscar(5));
        System.out.println(arvore.buscar(0));

        arvore.remover(1);
        arvore.remover(0);

        arvore.imprimir();
        System.out.println(arvore.buscar(1));

    }
}

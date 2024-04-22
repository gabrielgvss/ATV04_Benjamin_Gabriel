package estruturas;
public class TabelaHash<T extends Comparable<T>> {
    private static final int TAMANHO_INICIAL = 15;
    private Arvore<T>[] tabela;
    private int tamanho;

    public TabelaHash() {
        this.tabela = new Arvore[TAMANHO_INICIAL];
        for (int i = 0; i < TAMANHO_INICIAL; i++) {
            this.tabela[i] = new Arvore<>();
        }
        this.tamanho = 0;
    }

    private int calcularHash(T chave) {
        return Math.abs(chave.hashCode() % this.tabela.length);
    }

    public void inserir(T elemento) {
        int indice = calcularHash(elemento);
        Arvore<T> bucket = this.tabela[indice];
        bucket.inserir(elemento);
        this.tamanho++;

        // Rehashing se necessário
        if (this.tamanho >= this.tabela.length) {
            rehash();
        }
    }

    private void rehash() {
        // Dobrar o tamanho da tabela
        int novoTamanho = this.tabela.length * 2;
        Arvore<T>[] novaTabela = new Arvore[novoTamanho];
        for (int i = 0; i < novoTamanho; i++) {
            novaTabela[i] = new Arvore<>();
        }

        // Recalcular os hashes dos elementos e inseri-los na nova tabela
        for (Arvore<T> bucket : this.tabela) {
            for (NoArvore<T> no : bucket.;) {
                int novoIndice = Math.abs(no.getElemento().hashCode() % novoTamanho);
                novaTabela[novoIndice].inserir(no.getElemento());
            }
        }

        this.tabela = novaTabela;
    }

    public boolean buscar(T elemento) {
        int indice = calcularHash(elemento);
        Arvore<T> bucket = this.tabela[indice];
        return bucket.buscar(bucket.getRaiz(), elemento) != null;
    }

    public boolean remover(T elemento) {
        int indice = calcularHash(elemento);
        Arvore<T> bucket = this.tabela[indice];
        boolean removido = bucket.remover(elemento);
        if (removido) {
            this.tamanho--;
        }
        return removido;
    }

    public void imprimir() {
        for (int i = 0; i < this.tabela.length; i++) {
            System.out.println("Bucket " + i + ":");
            this.tabela[i].preOrdem(this.tabela[i].getRaiz());
        }
    }
}


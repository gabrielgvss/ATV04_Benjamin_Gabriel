package estruturas;

public class TabelaHash<K extends Comparable<K>, V> {
    private Arvore<Entry<K, V>>[] tabela;
    private int qtdTotalElementos;
    private int tamanhoMaximo;
    private int tamanhoOcupado;

    public TabelaHash(int tamanhoMaximo) {
        this.tamanhoMaximo = tamanhoMaximo;
        this.qtdTotalElementos = 0;
        this.tamanhoOcupado = 0;
        this.tabela = new Arvore[tamanhoMaximo];

        for (int i = 0; i < tamanhoMaximo; i++) {
            this.tabela[i] = new Arvore<>();
        }
    }

    private int calcularHash(K chave) {
        // Converter a chave para uma sequência de bytes
        byte[] bytesChave = converterParaBytes(chave);

        // Aplicar uma função de hash sobre os bytes da chave
        int hash = aplicarFuncaoHash(bytesChave);

        // Reduzir o valor do hash para o intervalo [0, tamanhoMaximo)
        return Math.abs(hash % tamanhoMaximo);
    }

    // Método para converter a chave para uma sequência de bytes
    private byte[] converterParaBytes(K chave) {
        // Implemente a conversão da chave para uma sequência de bytes
        // Esta implementação pode variar dependendo do tipo de chave que você está usando

        // Por exemplo, se a chave for uma String, você pode converter para bytes assim:
        return chave.toString().getBytes();
    }

    // Função de hash simples para bytes
    private int aplicarFuncaoHash(byte[] bytes) {
        int a = 31; // Escolha de um primo aleatório
        int b = 17; // Escolha de outro primo aleatório
        int hash = 0;

        for (byte by : bytes) {
            hash = (a * hash + by) % tamanhoMaximo;
        }

        return hash;
    }

    public void inserir(K chave, V valor) {
        int indice = calcularHash(chave);

        // Verifica se a chave já existe na tabela
        if (buscar(chave) != null) {
            System.out.println("A chave já está associada a um elemento na tabela.");
            return;
        }

        // Insere o novo elemento na árvore do bucket correspondente
        Entry<K, V> entrada = new Entry<>(chave, valor);
        tabela[indice].inserir(entrada);
        qtdTotalElementos++;

        // Verifica se todos os buckets estão ocupados e realiza rehashing, se necessário
        if (tamanhoOcupado >= tamanhoMaximo) {
            rehashing();
        }
    }

    public V buscar(K chave) {
        int indice = calcularHash(chave);

        for (Entry<K, V> entrada : tabela[indice].obterTodos()) {
            if (entrada.getChave().equals(chave)) {
                return entrada.getValor();
            }
        }

        return null;
    }

    public boolean excluir(K chave) {
        int indice = calcularHash(chave);

        for (Entry<K, V> entrada : tabela[indice].obterTodos()) {
            if (entrada.getChave().equals(chave)) {
                tabela[indice].remover(entrada);
                qtdTotalElementos--;

                // Verifica se o bucket está vazio após a remoção e atualiza o tamanho ocupado
                if (tabela[indice].getTamanho() == 0) {
                    tamanhoOcupado--;
                }

                return true;
            }
        }

        return false;
    }

    public void imprimirElementos() {
        for (int i = 0; i < tamanhoMaximo; i++) {
            System.out.println("Bucket " + i + ":");

            for (Entry<K, V> entrada : tabela[i].obterTodos()) {
                System.out.println("Chave: " + entrada.getChave() + ", Valor: " + entrada.getValor());
            }

            System.out.println();
        }
    }


    // Método para rehashing
    private void rehashing() {
        // Dobrar o tamanho da tabela
        int novoTamanho = tamanhoMaximo * 2;
        Arvore<Entry<K, V>>[] novaTabela = new Arvore[novoTamanho];

        // Inicializar a nova tabela com árvores vazias
        for (int i = 0; i < novoTamanho; i++) {
            novaTabela[i] = new Arvore<>();
        }

        // Reinsere todos os elementos na nova tabela
        for (int i = 0; i < tamanhoMaximo; i++) {
            for (Entry<K, V> entrada : tabela[i].obterTodos()) {
                int indice = calcularHash(entrada.getChave());
                novaTabela[indice].inserir(entrada);
            }
        }

        // Atualiza a tabela e o tamanho máximo
        this.tabela = novaTabela;
        this.tamanhoMaximo = novoTamanho;
    }

    public int getQtdTotalElementos() {
        return qtdTotalElementos;
    }

    public int getTamanhoOcupado() {
        return tamanhoOcupado;
    }

    private static class Entry<K, V> implements Comparable<Entry<K, V>> {
        private K chave;
        private V valor;

        public Entry(K chave, V valor) {
            this.chave = chave;
            this.valor = valor;
        }

        public K getChave() {
            return chave;
        }

        public V getValor() {
            return valor;
        }

        @Override
        public int compareTo(Entry<K, V> o) {
            return 0;
        }
    }
}

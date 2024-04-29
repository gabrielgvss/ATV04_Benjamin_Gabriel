package estruturas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TabelaHash<Chave, Valor> {
    private int tamanho;
    private int elementosInseridos;
    private ListaEncadeada<Entrada<Chave, Valor>>[] tabela;

    public TabelaHash() {
        this(20); // Tamanho Padrão
    }

    public TabelaHash(int tamanho) {
        this.tamanho = tamanho;
        this.elementosInseridos = 0;
        this.tabela = new ListaEncadeada[tamanho];
        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new ListaEncadeada<>();
        }
        inserirDeArquivo();
    }

    public void inserirDeArquivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/estruturas/entrada.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 2) {
                    Chave chave = (Chave) partes[0].trim();
                    Valor valor = (Valor) partes[1].trim();
                    inserir(chave, valor);
                } else {
                    System.err.println("Formato de linha inválido: " + linha);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public void inserir(Chave chave, Valor valor) {
        int indice = funcaoHash(chave, tamanho);

        // Verifica se a chave já existe na lista encadeada
        ListaEncadeada<Entrada<Chave, Valor>> lista = tabela[indice];
        for (Entrada<Chave, Valor> entrada : lista) {
            if (entrada.getChave().equals(chave)) {
                System.out.println("Chave duplicada. Não é possível inserir.");
                return;
            }
        }

        Entrada<Chave, Valor> novaEntrada = new Entrada<>(chave, valor);
        tabela[indice].inserir(novaEntrada);
        elementosInseridos++;
        if ((double) elementosInseridos / tamanho >= 0.7) {
            redimensionarTabela();
        }
    }

    public int funcaoHash(Chave chave, int tamanho) {
        double A = 0.6180339887;

        if (chave instanceof Integer) {
            int chaveInt = (int) chave;
            return (int) (tamanho * ((chaveInt * A) % 1));


        } else if (chave instanceof String) {
            String chaveString = (String) chave;
            int somaCaracteres = 0;

            for (int i = 0; i < chaveString.length(); i++) {
                somaCaracteres += (int) chaveString.charAt(i);
            }

            return (int) (tamanho * ((somaCaracteres * A) % 1));
        } else {
            throw new IllegalArgumentException("Chave inválida");
        }
    }


    private void redimensionarTabela() {
        int novoTamanho = tamanho * 2;
        ListaEncadeada<Entrada<Chave, Valor>>[] novaTabela = new ListaEncadeada[novoTamanho];
        for (int i = 0; i < novoTamanho; i++) {
            novaTabela[i] = new ListaEncadeada<>();
        }
        for (int i = 0; i < tamanho; i++) {
            ListaEncadeada<Entrada<Chave, Valor>> lista = tabela[i];
            for (Entrada<Chave, Valor> entrada : lista) {
                int novoIndice = funcaoHash(entrada.getChave(), novoTamanho);
                novaTabela[novoIndice].inserir(entrada);
            }
        }
        tamanho = novoTamanho;
        tabela = novaTabela;
    }

    public Valor remover(Chave chave) {
        int indice = funcaoHash(chave, tamanho);
        ListaEncadeada<Entrada<Chave, Valor>> lista = tabela[indice];

        // Busca pela entrada correspondente à chave na lista encadeada
        for (Entrada<Chave, Valor> entrada : lista) {
            if (entrada.getChave().equals(chave)) {
                Valor valorRemovido = entrada.getValor();
                lista.remover(entrada); // Remove a entrada da lista
                elementosInseridos--; // Decrementa o número de elementos inseridos
                return valorRemovido;
            }
        }
        return null; // Retorna null se a chave não for encontrada
    }


    public Valor buscar(Chave chave) {
        int indice = funcaoHash(chave, tamanho);
        ListaEncadeada<Entrada<Chave, Valor>> lista = tabela[indice];
        for (Entrada<Chave, Valor> entrada : lista) {
            if (entrada.getChave().equals(chave)) {
                return entrada.getValor();
            }
        }
        return null;
    }

    public void imprimir() {
        System.out.println("Tabela Hash");
        for (int i = 0; i < tamanho; i++) {
            ListaEncadeada<Entrada<Chave, Valor>> lista = tabela[i];
            System.out.print("Bucket " + i + ": ");
            if (lista.getTamanho() == 0) {
                System.out.println("null");
            } else {
                for (Entrada<Chave, Valor> entrada : lista) {
                    System.out.print("(" + entrada.getChave() + ", " + entrada.getValor() + ") ");
                }
                System.out.println();
            }
        }
    }

    public boolean contemChave(Chave chave) {
        int indice = funcaoHash(chave, tamanho);
        ListaEncadeada<Entrada<Chave, Valor>> lista = tabela[indice];
        for (Entrada<Chave, Valor> entrada : lista) {
            if (entrada.getChave().equals(chave)) {
                return true;
            }
        }
        return false;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public int getElementosInseridos() {
        return elementosInseridos;
    }

    public void setElementosInseridos(int elementosInseridos) {
        this.elementosInseridos = elementosInseridos;
    }

    private static class Entrada<Chave, Valor> {
        private final Chave chave;
        private final Valor valor;
        public Entrada(Chave chave, Valor valor) {
            this.chave = chave;
            this.valor = valor;
        }
        public Chave getChave() {
            return chave;
        }
        public Valor getValor() {
            return valor;
        }
    }
}
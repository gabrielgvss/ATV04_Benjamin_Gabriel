package estruturas;

import java.io.*;

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

        registraHashGeradoArquivo(chave, indice);
    }

    private void registraHashGeradoArquivo(Chave chave, int hash) {
        // Nome do arquivo de saída
        String nomeArquivo = "src/main/java/estruturas/saida_hash.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo, true))) {
            // Escreve no arquivo o hash gerado para a chave
            writer.write("Hash inteiro RIPEMD-128 gerado para a chave: " + chave.toString() + ": " + hash);
            writer.newLine(); // Adiciona uma nova linha para o próximo registro
        } catch (IOException e) {
            // Em caso de erro de IO, imprime a exceção
            e.printStackTrace();
        }
    }

    public int funcaoHash(Chave chave, int tamanho) {
        if (chave instanceof Integer) {
            int chaveInt = (int) chave;

            return ripemd128(String.valueOf(chaveInt), tamanho);
        } else if (chave instanceof String) {
            return ripemd128((String) chave, tamanho);
        } else {
            throw new IllegalArgumentException("Chave inválida");
        }
    }

    private int ripemd128(String input, int tamanho) {
        int[] buffer = new int[4];
        int a = 0x67452301;
        int b = 0xefcdab89;
        int c = 0x98badcfe;
        int d = 0x10325476;

        byte[] bytes = input.getBytes();
        int[] x = new int[16];

        for (int i = 0; i < bytes.length; i += 64) {
            for (int j = 0; j < 16; j++) {
                int index = i + j * 4;
                if (index + 3 < bytes.length) {
                    x[j] = (bytes[index] & 0xFF) | ((bytes[index + 1] & 0xFF) << 8) | ((bytes[index + 2] & 0xFF) << 16) | ((bytes[index + 3] & 0xFF) << 24);
                } else {
                    // Se não houver bytes suficientes para formar uma palavra de 32 bits, preenchemos com zeros
                    int value = 0;
                    for (int k = 0; k < 4; k++) {
                        if (index + k < bytes.length) {
                            value |= (bytes[index + k] & 0xFF) << (k * 8);
                        }
                    }
                    x[j] = value;
                }
            }

            int aa = a;
            int bb = b;
            int cc = c;
            int dd = d;

            // Round 1
            a = ff(a, b, c, d, x[0], 11);
            d = ff(d, a, b, c, x[1], 14);
            c = ff(c, d, a, b, x[2], 15);
            b = ff(b, c, d, a, x[3], 12);
            a = ff(a, b, c, d, x[4], 5);
            d = ff(d, a, b, c, x[5], 8);
            c = ff(c, d, a, b, x[6], 7);
            b = ff(b, c, d, a, x[7], 9);
            a = ff(a, b, c, d, x[8], 11);
            d = ff(d, a, b, c, x[9], 13);
            c = ff(c, d, a, b, x[10], 14);
            b = ff(b, c, d, a, x[11], 15);
            a = ff(a, b, c, d, x[12], 6);
            d = ff(d, a, b, c, x[13], 7);
            c = ff(c, d, a, b, x[14], 9);
            b = ff(b, c, d, a, x[15], 8);

            // Round 2
            a = gg(a, b, c, d, x[7], 7);
            d = gg(d, a, b, c, x[4], 6);
            c = gg(c, d, a, b, x[13], 8);
            b = gg(b, c, d, a, x[1], 13);
            a = gg(a, b, c, d, x[10], 11);
            d = gg(d, a, b, c, x[6], 9);
            c = gg(c, d, a, b, x[15], 7);
            b = gg(b, c, d, a, x[3], 15);
            a = gg(a, b, c, d, x[12], 7);
            d = gg(d, a, b, c, x[0], 12);
            c = gg(c, d, a, b, x[9], 15);
            b = gg(b, c, d, a, x[5], 9);
            a = gg(a, b, c, d, x[2], 11);
            d = gg(d, a, b, c, x[14], 7);
            c = gg(c, d, a, b, x[11], 13);
            b = gg(b, c, d, a, x[8], 12);

            // Round 3
            a = hh(a, b, c, d, x[3], 11);
            d = hh(d, a, b, c, x[10], 13);
            c = hh(c, d, a, b, x[14], 6);
            b = hh(b, c, d, a, x[4], 7);
            a = hh(a, b, c, d, x[9], 14);
            d = hh(d, a, b, c, x[15], 9);
            c = hh(c, d, a, b, x[8], 13);
            b = hh(b, c, d, a, x[1], 15);
            a = hh(a, b, c, d, x[2], 14);
            d = hh(d, a, b, c, x[7], 8);
            c = hh(c, d, a, b, x[0], 13);
            b = hh(b, c, d, a, x[6], 6);
            a = hh(a, b, c, d, x[13], 5);
            d = hh(d, a, b, c, x[11], 12);
            c = hh(c, d, a, b, x[5], 7);
            b = hh(b, c, d, a, x[12], 5);

            // Round 4
            a = ii(a, b, c, d, x[1], 11);
            d = ii(d, a, b, c, x[9], 12);
            c = ii(c, d, a, b, x[11], 14);
            b = ii(b, c, d, a, x[10], 15);
            a = ii(a, b, c, d, x[0], 14);
            d = ii(d, a, b, c, x[8], 15);
            c = ii(c, d, a, b, x[12], 9);
            b = ii(b, c, d, a, x[4], 8);
            a = ii(a, b, c, d, x[13], 9);
            d = ii(d, a, b, c, x[3], 14);
            c = ii(c, d, a, b, x[7], 5);
            b = ii(b, c, d, a, x[15], 6);
            a = ii(a, b, c, d, x[14], 8);
            d = ii(d, a, b, c, x[5], 6);
            c = ii(c, d, a, b, x[6], 5);
            b = ii(b, c, d, a, x[2], 12);

            a += aa;
            b += bb;
            c += cc;
            d += dd;
        }

        int hash = (a + b + c + d) % tamanho;
        hash = hash < 0 ? hash + tamanho : hash; // Garantindo que o índice seja não negativo
        return hash;

    }

    private int ff(int a, int b, int c, int d, int x, int s) {
        return rotateLeft(a + f(b, c, d) + x, s);
    }

    private int gg(int a, int b, int c, int d, int x, int s) {
        return rotateLeft(a + g(b, c, d) + x + 0x5a827999, s);
    }

    private int hh(int a, int b, int c, int d, int x, int s) {
        return rotateLeft(a + h(b, c, d) + x + 0x6ed9eba1, s);
    }

    private int ii(int a, int b, int c, int d, int x, int s) {
        return rotateLeft(a + i(b, c, d) + x + 0x8f1bbcdc, s);
    }

    private int f(int x, int y, int z) {
        return (x & y) | (~x & z);
    }

    private int g(int x, int y, int z) {
        return (x & z) | (y & ~z);
    }

    private int h(int x, int y, int z) {
        return x ^ y ^ z;
    }

    private int i(int x, int y, int z) {
        return y ^ (x | ~z);
    }

    private int rotateLeft(int x, int n) {
        return (x << n) | (x >>> (32 - n));
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

package estruturas;

public class Arvore<T extends Comparable<T>> {
    public NoArvore<T> raiz;
    public int tamanho;

    public Arvore() {
        this.raiz = null;
        this.tamanho = 0;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void inserir(T elemento) {
        if (buscar(elemento)) {
            // O elemento já está presente na árvore, não é necessário adicionar novamente
            return;
        }

        NoArvore<T> aux = new NoArvore<>(elemento);
        if (this.raiz == null) {
            this.raiz = aux;
            this.tamanho++;
        } else {
            NoArvore<T> atual = this.raiz;
            while (true) {
                if (aux.getElemento().compareTo(atual.getElemento()) < 0) {
                    if (atual.getEsquerdo() != null) {
                        atual = atual.getEsquerdo();
                    } else {
                        atual.setEsquerdo(aux);
                        this.tamanho++;
                        break;
                    }
                } else {
                    if (atual.getDireito() != null) {
                        atual = atual.getDireito();
                    } else {
                        atual.setDireito(aux);
                        this.tamanho++;
                        break;
                    }
                }
            }
        }
    }

    public boolean buscar(T elemento) {
        return buscarRecursivo(raiz, elemento);
    }

    private boolean buscarRecursivo(NoArvore<T> aux, T elemento) {
        if (aux == null) {
            return false; // Elemento não encontrado na árvore
        }

        if (aux.getElemento().equals(elemento)) {
            return true; // Elemento encontrado na árvore
        } else {
            // Recursivamente buscar nas subárvores esquerda e direita
            return buscarRecursivo(aux.getEsquerdo(), elemento) || buscarRecursivo(aux.getDireito(), elemento);
        }
    }

    public NoArvore<T> getRaiz() {
        return raiz;
    }

    public void imprimir() {
        preOrdem(this.raiz);
    }

    public void emOrdem(NoArvore<T> aux) {
        if (aux != null) {
            emOrdem(aux.getEsquerdo());
            System.out.println(aux.getElemento());
            emOrdem(aux.getDireito());
        }
    }

    public void preOrdem(NoArvore<T> aux) {
        if (aux != null) {
            System.out.println(aux.getElemento());
            preOrdem(aux.getEsquerdo());
            preOrdem(aux.getDireito());
        }
    }

    public void posOrdem(NoArvore<T> aux) {
        if (aux != null) {
            posOrdem(aux.getEsquerdo());
            posOrdem(aux.getDireito());
            System.out.println(aux.getElemento());
        }
    }

    public boolean remover(T elemento) {
        int tamanhoAtual = this.tamanho;
        raiz = removerRecursivo(raiz, elemento);
        return tamanhoAtual != this.tamanho;
    }

    private NoArvore<T> removerRecursivo(NoArvore<T> atual, T elemento) {
        if (atual == null) {
            return null;
        }

        int comparacao = elemento.compareTo(atual.getElemento());

        if (comparacao < 0) {
            atual.setEsquerdo(removerRecursivo(atual.getEsquerdo(), elemento));
        } else if (comparacao > 0) {
            atual.setDireito(removerRecursivo(atual.getDireito(), elemento));
        } else {
            // Nó a ser removido encontrado
            if (atual.getEsquerdo() == null) {
                this.tamanho--;
                return atual.getDireito();
            } else if (atual.getDireito() == null) {
                this.tamanho--;
                return atual.getEsquerdo();
            }

            NoArvore<T> sucessor = encontrarSucessor(atual.getDireito());
            atual.setElemento(sucessor.getElemento());
            atual.setDireito(removerRecursivo(atual.getDireito(), sucessor.getElemento()));

        }

        return atual;
    }

    private NoArvore<T> encontrarSucessor(NoArvore<T> atual) {
        while (atual.getEsquerdo() != null) {
            atual = atual.getEsquerdo();
        }
        return atual;
    }
}

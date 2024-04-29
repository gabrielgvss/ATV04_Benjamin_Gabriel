package estruturas;

import java.util.Iterator;

public class ListaEncadeada<Elem> implements Iterable<Elem> {

    public class No {
        private Elem elem;
        private No proximo;
        private No anterior;

        public No() {
            this.elem = null;
        }

        public No(Elem novoElem) {
            this.elem = novoElem;
        }

        public Elem getElem() {
            return elem;
        }

        public void setElem(Elem elem) {
            this.elem = elem;
        }

        public No getProximo() {
            return proximo;
        }

        public void setProximo(No proximo) {
            this.proximo = proximo;
        }

        public No getAnterior() {
            return anterior;
        }

        public void setAnterior(No anterior) {
            this.anterior = anterior;
        }

        @Override
        public String toString() {
            return "No{" + "Elem = " + elem + ", Próximo = " + proximo + ", Anterior = " + anterior + '}';
        }
    }

    private No primeiro;
    private No ultimo;
    private int tamanho;

    public ListaEncadeada() {
        this.tamanho = 0;
    }

    public No getPrimeiro() {
        return primeiro;
    }

    public void setPrimeiro(No primeiro) {
        this.primeiro = primeiro;
    }

    public No getUltimo() {
        return ultimo;
    }

    public void setUltimo(No ultimo) {
        this.ultimo = ultimo;
    }

    public int getTamanho() {
        return tamanho;
    }

    public boolean inserir(Elem novoElem) {
        // Verifica se a chave já existe na lista
        if (buscar(novoElem) != null) {
            System.out.println("Chave duplicada. Não é possível inserir.");
            return false;
        }

        No aux = new No(novoElem);
        if (this.primeiro == null) {
            this.primeiro = aux;
            this.ultimo = aux;
            this.tamanho++;
            return true;
        } else {
            aux.setAnterior(this.ultimo);
            this.ultimo.setProximo(aux);
            this.ultimo = aux;
            this.tamanho++;
            return true;
        }
    }

    public boolean remover(Elem elem) {
        No aux = this.primeiro;

        // Caso especial: a lista está vazia
        if (aux == null) {
            return false;
        }

        // Caso especial: o elemento a ser removido é o primeiro da lista
        if (aux.getElem().equals(elem)) {
            this.primeiro = aux.getProximo();
            if (this.primeiro != null) {
                this.primeiro.setAnterior(null);
            } else {
                this.ultimo = null;
            }
            this.tamanho--;
            return true;
        }

        // Percorre a lista em busca do elemento a ser removido
        while (aux != null) {
            if (aux.getElem().equals(elem)) {
                // Remove o elemento atual da lista
                No anterior = aux.getAnterior();
                No proximo = aux.getProximo();
                anterior.setProximo(proximo);
                if (proximo != null) {
                    proximo.setAnterior(anterior);
                } else {
                    this.ultimo = anterior;
                }
                this.tamanho--;
                return true;
            }
            aux = aux.getProximo();
        }

        // Caso o elemento não seja encontrado na lista
        return false;
    }

    public boolean isEmpty() {
        return tamanho == 0;
    }

    public void imprimir() {
        No atual = primeiro;
        while (atual != null) {
            System.out.println(atual.getElem());
            atual = atual.getProximo();
        }
    }

    public No buscar(Elem elem) {
        No aux;
        if (this.tamanho > 0) {
            aux = this.primeiro;
            while (aux != null) {
                if (elem.equals(aux.getElem()))
                    return aux;
                aux = aux.getProximo();
            }
        }
        return null;
    }

    public No get(int posicao) {
        No aux = this.primeiro;
        for (int i = 0; i < posicao; i++) {
            if (aux.getProximo() != null) {
                aux = aux.getProximo();
            }
        }
        return aux;
    }

    @Override
    public Iterator<Elem> iterator() {
        return new Iterator<Elem>() {
            private No current = primeiro;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Elem next() {
                No temp = current;
                current = current.getProximo();
                return temp.getElem();
            }
        };
    }
}

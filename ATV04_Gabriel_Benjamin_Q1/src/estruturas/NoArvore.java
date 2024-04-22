package estruturas;

class NoArvore<T> {
    public T elemento;
    public NoArvore<T> direito;
    public NoArvore<T> esquerdo;

    public NoArvore() {
        this.elemento = null;
        this.direito = null;
        this.esquerdo = null;
    }

    public NoArvore(T novoElem) {
        this.elemento = novoElem;
        this.direito = null;
        this.esquerdo = null;
    }

    public T getElemento() {
        return this.elemento;
    }

    public void setElemento(T elemento) {
        this.elemento = elemento;
    }

    public NoArvore<T> getDireito() {
        return direito;
    }

    public void setDireito(NoArvore<T> direito) {
        this.direito = direito;
    }

    public NoArvore<T> getEsquerdo() {
        return esquerdo;
    }

    public void setEsquerdo(NoArvore<T> esquerdo) {
        this.esquerdo = esquerdo;
    }
}


package estruturas;

public class TesteLista {
    public static void main(String[] args){
        TabelaHash<String, String> nomes = new TabelaHash<>();
        nomes.inserir("aa111", "Roberto");
        nomes.inserir("bb222", "Maria");
        nomes.inserir("cc333", "João");
        nomes.inserir("dd444", "Ana");
        nomes.inserir("ee555", "Carlos");
        // Mais 25 exemplos
        nomes.inserir("ff666", "Mariana");
        nomes.inserir("gg777", "Pedro");
        nomes.inserir("hh888", "Sandra");
        nomes.inserir("ii999", "Fernando");
        nomes.inserir("jj1010", "Juliana");
        nomes.inserir("kk1111", "Rafael");
        nomes.inserir("ll1212", "Luciana");
        nomes.inserir("mm1313", "Gustavo");
        nomes.inserir("nn1414", "Carolina");
        nomes.inserir("oo1515", "Diego");
        nomes.inserir("pp1616", "Tatiane");
        nomes.inserir("qq1717", "Gabriela");
        nomes.inserir("rr1818", "Marcelo");
        nomes.inserir("ss1919", "Renata");
        nomes.inserir("tt2020", "Felipe");
        nomes.inserir("uu2121", "Patrícia");
        nomes.inserir("vv2222", "Victor");
        nomes.inserir("ww2323", "Vanessa");
        nomes.inserir("xx2424", "Maurício");
        nomes.inserir("yy2525", "Isabela");
        nomes.inserir("zz2626", "Lucas");
        nomes.inserir("aa2727", "Aline");
        nomes.inserir("bb2828", "Ricardo");
        nomes.inserir("cc2929", "Laura");
        nomes.inserir("dd3030", "Eduardo");
        nomes.inserir("ee3131", "Camila");
        nomes.inserir("ff3232", "Hugo");

        System.out.println(nomes.getElementosInseridos());

        nomes.imprimir();


    }
}

package pt.ulusofona.lp2.greatprogrammingjourney;

public class Player {
    private int id;
    private String nome;
    private String cor;
    private int posicao;

    public Player(int id, String nome, String cor) {
        this.id = id;
        this.nome = nome;
        this.cor = cor;
        this.posicao = 1; //começa na posição inicial
    }

    //
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCor() { return cor; }
    public int getPosicao() { return posicao; }

    //
    public void setPosicao(int novaPosicao) {
        this.posicao = novaPosicao;
    }

    //atualiza posição (mover n casas)
    public void mover(int casas) {
        this.posicao += casas;
    }
}

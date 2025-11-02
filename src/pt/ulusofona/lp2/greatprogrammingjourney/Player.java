package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.ArrayList;

public class Player {
    private int id;
    private String nome;
    private String cor;
    private int posicao;
    private ArrayList<String> linguagensFavoritas = new ArrayList<>();
    private boolean derrotado = false;

    public Player(int id, String nome, String cor) {
        this.id = id;
        this.nome = nome.trim();
        this.cor = cor.trim();
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

    public ArrayList<String> getLinguagensFavoritas() {
        return linguagensFavoritas;
    }

    public void adicionarLinguagem(String linguagem) {
        if (linguagem != null && !linguagem.trim().isEmpty()) {
            linguagensFavoritas.add(linguagem.trim());
        }
    }

    //linguagens em formato "Java;Python;C"
    public String linguagensComoString() {
        return String.join(";", linguagensFavoritas);
    }

    public boolean isDerrotado() {
        return derrotado;
    }

    public void setDerrotado(boolean derrotado) {
        this.derrotado = derrotado;
    }
}
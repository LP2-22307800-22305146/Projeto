package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.ArrayList;

public class Player {
    private int id;
    private String nome;
    private String cor;
    private int posicao;
    private ArrayList<String> linguagensFavoritas = new ArrayList<>();
    private ArrayList<Ferramenta> ferramentas = new ArrayList<>();
    private int posicaoAnterior = 1;
    private int posicaoHaDoisTurnos = 1;
    private boolean preso = false;
    private boolean derrotado = false;

    public Player(int id, String nome, String cor) {
        this.id = id;
        this.nome = nome.trim();
        this.cor = cor.trim();
        this.posicao = 1; //começa na posição inicial
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCor() { return cor; }
    public int getPosicao() { return posicao; }
    public int getPosicaoAnterior() { return posicaoAnterior; }
    public int getPosicaoHaDoisTurnos() { return posicaoHaDoisTurnos; }
    public ArrayList<Ferramenta> getFerramentas () {
        return ferramentas;
    }

    // GETTERS
    public ArrayList<String> getLinguagensFavoritas() {
        return linguagensFavoritas;
    }

    public void atualizarHistorico() {
        posicaoHaDoisTurnos = posicaoAnterior;
        posicaoAnterior = posicao;
    }

    // Setters
    public void setPosicao(int novaPosicao) {
        this.posicao = novaPosicao;
    }

    public void setDerrotado(boolean derrotado) {
        this.derrotado = derrotado;
    }

    public void setPreso(boolean preso) {
        this.preso = preso;
    }

    public void setPosicaoAnterior(int posicaoAnterior) {
        this.posicaoAnterior = posicaoAnterior;
    }

    public void setPosicaoHaDoisTurnos(int posicaoHaDoisTurnos) {
        this.posicaoHaDoisTurnos = posicaoHaDoisTurnos;
    }

    // MÉTODOS
    //atualiza posição (mover n casas)
    public void mover(int casas) {
        this.posicao += casas;
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

    //verifica se o jogador já possui uma ferramenta com o mesmo nome
    public boolean temFerramenta(Ferramenta f) {
        for (Ferramenta existente : ferramentas) {
            if (existente.getId() == f.getId()) {
                return true;
            }
        }
        return false;
    }

    //adiciona uma ferramenta ao jogador (se ainda não a tiver)
    public void adicionarFerramenta(Ferramenta f) {
        if (!temFerramenta(f)) {
            ferramentas.add(f);
        }
    }

    //verifica se o jogador tem uma ferramenta que anula o abismo
    // (por agora simplificamos: qualquer ferramenta pode anular um abismo)
    public boolean temFerramentaQueAnula(Abismo a) {
        int aid = a.getId();
        for (Ferramenta f : ferramentas) {
            int fid = f.getId();
            if ((aid == 0 && (fid == 4 || fid == 5))  // Erro de Sintaxe → IDE ou Ajuda do Professor
                    || (aid == 1 && fid == 1)             // Erro de Lógica → Programação Funcional
                    || (aid == 2 && fid == 2)             // Exception → Testes Unitários
                    || (aid == 3 && fid == 3)             // FileNotFoundException → Tratamento de Excepções
                    || (aid == 4 && fid == 4)             // Crash → IDE
                    || (aid == 5 && fid == 5)) {          // Código Duplicado → Ajuda do Professor
                return true;
            }
        }
        return false;
    }

    //(remove) uma ferramenta para anular um abismo
    public void usarFerramentaContra(Abismo a) {
        ferramentas.removeIf(f -> f.getId() == a.getId());
    }

    public boolean isPreso() {
        return preso;
    }

    // LINGUAGENS
    public String primeiraLinguagem () {

        return linguagensFavoritas.get(0);

    }

}

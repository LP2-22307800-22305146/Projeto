package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.ArrayList;
import java.util.Comparator;

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
    private String causaDerrota = null;


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
        //ordenar alfabeticamente
        ferramentas.sort(Comparator.comparing(Ferramenta::getNome));
    }

    //verifica se o jogador tem uma ferramenta que anula o abismo
    // (por agora simplificamos: qualquer ferramenta pode anular um abismo)
    public boolean temFerramentaQueAnula(Abismo a) {
        for (Ferramenta f : ferramentas) {
            switch (a.getId()) {
                case 0 -> { // Erro de Sintaxe ← IDE
                    if (f.getId() == 4){
                        return true;
                    }
                }
                case 1 -> { // Erro de Lógica ← Testes Unitários
                    if (f.getId() == 2){
                        return true;
                    }
                }
                case 2 -> { // Exception ← Tratamento de Exceções
                    if (f.getId() == 3 || f.getId() == 100) {
                        return true;
                    }
                }
                case 3 -> { // FileNotFoundException ← Ajuda do Professor e Tratamento de Exceções
                    if (f.getId() == 5 || f.getId() == 3) {
                        return true;
                    }
                }
                case 4 -> { // Crash ← Ajuda do Professor
                    if (f.getId() == 5) {
                        return true;
                    }
                }
                case 5 -> { // Código Duplicado ← Herança
                    if (f.getId() == 0) {
                        return true;
                    }
                }
                case 6 -> { // Efeitos Secundários ← Programação Funcional
                    if (f.getId() == 1) {
                        return true;
                    }
                }
                case 8 -> { // Ciclo Infinito ← Ajuda do Professor
                    if (f.getId() == 5) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void usarFerramentaContra(Abismo a) {
        int ferramentaParaRemover = -1;

        switch (a.getId()) {
            case 0 -> ferramentaParaRemover = 4; // Erro de Sintaxe ← IDE
            case 1 -> ferramentaParaRemover = 2; // Erro de Lógica ← Testes Unitários
            case 2 -> ferramentaParaRemover = 3; // Exception ← Tratamento de Exceções
            case 3 -> ferramentaParaRemover = 5; // FileNotFoundException ← Ajuda do Professor
            case 4 -> ferramentaParaRemover = 5; // Crash ← Ajuda do Professor
            case 5 -> ferramentaParaRemover = 0; // Código Duplicado ← Herança
            case 6 -> ferramentaParaRemover = 1; // Efeitos Secundários ← Programação Funcional
            case 8 -> ferramentaParaRemover = 5; // Ciclo Infinito ← Ajuda do Professor
        }

        int finalFerramentaParaRemover = ferramentaParaRemover;
        ferramentas.removeIf(f -> f.getId() == finalFerramentaParaRemover);
    }

    public boolean isPreso() {
        return preso;
    }

    // LINGUAGENS
    public String primeiraLinguagem () {

        return linguagensFavoritas.get(0);

    }

    public String getCausaDerrota() {
        return causaDerrota;
    }

    public void setCausaDerrota(String causa) {
        this.causaDerrota = causa;
    }


}
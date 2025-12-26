package pt.ulusofona.lp2.greatprogrammingjourney;

public class Abismo {

    private int id;
    private int posicao;
    private String nome;

    public Abismo(int id, int posicao) {
        this.id = id;
        this.posicao = posicao;
        this.nome = nomeAbismoPorId(id);
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getPosicao() {
        return posicao;
    }

    public String getNome() {
        return nome;
    }

    // Nome do abismo conforme o ID
    private String nomeAbismoPorId(int id) {
        switch (id) {
            case 0: return "Erro de Sintaxe";
            case 1: return "Erro de Lógica";
            case 2: return "Exception";
            case 3: return "FileNotFoundException";
            case 4: return "Crash";
            case 5: return "Código Duplicado";
            case 6: return "Efeitos Secundários";
            case 7: return "Blue Screen of Death";
            case 8: return "Ciclo Infinito";
            case 9: return "Segmentation Fault";
            case 20: return "LLM";
            default: return "Abismo Desconhecido";
        }
    }

    @Override
    public String toString() {
        return nome + " (posição " + posicao + ")";
    }
}
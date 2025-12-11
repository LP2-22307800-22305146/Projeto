package pt.ulusofona.lp2.greatprogrammingjourney;

public class Ferramenta {

    private int id;
    private String nome;

    public Ferramenta(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    // Setter opcional (caso precises de alterar o nome mais tarde)
    public void setNome(String nome) {
        this.nome = nome;
    }

    // toString() — muito importante para getProgrammersInfo()
    @Override
    public String toString() {
        return nome;
    }
}
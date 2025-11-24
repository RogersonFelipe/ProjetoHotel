
package enums;

public enum Avaliacao {
    
    UE("Uma Estrela"),
    DE("Duas Estrelas"),
    TE("Três Estrelas"),
    QE("Quatro Estrelas"),
    CE("Cinco Estrelas");
    
    //
    private String descricao;
    
    //

    private Avaliacao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
    
    
    
}

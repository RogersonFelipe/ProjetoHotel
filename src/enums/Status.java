
package enums;

public enum Status {
    L("Livre"),
    R("Reservado"),
    A("Alugado"),
    F("Em Reforma");
    
    private String descricao;

    private Status(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
    
    
}

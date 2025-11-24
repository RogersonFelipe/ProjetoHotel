
package enums;

public enum Cargos {
    GG("Gerente Geral"),
    RC("Recepcionista"),
    CC("Chefe de Cozinha"),
    CM("Camareira"),
    CG("Concierge"),
    MN("Manobrista"),
    SM("Supervisor de Marketing");
    
    private String descricao;

    private Cargos(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
    
    
}

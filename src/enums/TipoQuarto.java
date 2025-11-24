
package enums;

public enum TipoQuarto {
    ST("Standard"),
    SU("Superior"),
    DE("Deluxe"),
    SI("Suite"),
    SP("Suíte Presidencial");
    
    //
    private String descricao;
    
    //

    private TipoQuarto(String descricao) {
        this.descricao = descricao;
    }
    
    //

    @Override
    public String toString() {
        return descricao;
    }
    
}

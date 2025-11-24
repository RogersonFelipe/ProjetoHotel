
package enums;

public enum MetodoPagamento {
    
    Di("Dinheiro em Espécie"),
    CC("Cartão de Crédito"),
    CD("Cartão de Debito"),
    PI("Pix"),
    BB("Boleto Bancário");
       
    //
    private String descricao;
       
    //
    private MetodoPagamento(String descricao) {
        this.descricao = descricao;
    }
    
    //

    @Override
    public String toString() {
        return descricao;
    }
    
       
}

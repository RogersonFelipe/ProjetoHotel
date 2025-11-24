
package model;

import enums.Status;
import enums.TipoQuarto;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_QUARTOS")
public class Quartos implements Serializable {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @SwingColumn(description = "Codigo")
    private int id;
    
    @Column(name = "numero_quarto", length = 4, nullable = false)
    @SwingColumn(description = "Numero do Quarto")
    private String numero_quarto;
    
    @Column(name = "tipo_quarto", length = 2, nullable = false)
    @Enumerated(EnumType.STRING)
    @SwingColumn(description = "Tipo de Quarto")
    private TipoQuarto tipo_quarto;
    
    @Column(name = "preco_diaria", precision = 10, scale = 2, nullable = false)
    @SwingColumn(description = "Preço da Diária")
    private BigDecimal preco_diaria; 
    
    @Column(name = "status", length = 1, nullable = false)
    @Enumerated(EnumType.STRING)
    @SwingColumn(description = "Status do Quarto")
    private Status status;
    

    public Quartos() {
        this.setId(0);
        this.setNumero_quarto("Sem Numero");
        this.setPreco_diaria(BigDecimal.ZERO);
        this.setStatus(status.L);
        this.setTipo_quarto(tipo_quarto.ST);
    }

    public Quartos(int id, String numero_quarto, TipoQuarto tipo_quarto, BigDecimal preco_diaria, Status status) {
        this.setId(id);
        this.setNumero_quarto(numero_quarto);
        this.setPreco_diaria(preco_diaria);
        this.setStatus(status);
        this.setTipo_quarto(tipo_quarto);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumero_quarto(String numero_quarto) {
        this.numero_quarto = numero_quarto.trim().isEmpty() ? "Sem Numero" : numero_quarto.toUpperCase();
    }

    public void setTipo_quarto(TipoQuarto tipo_quarto) {
        this.tipo_quarto = tipo_quarto;
    }

    public void setPreco_diaria(BigDecimal preco_diaria) {
        if (preco_diaria == null || preco_diaria.compareTo(BigDecimal.ZERO) < 0) {
            this.preco_diaria = BigDecimal.ZERO;
        } else {
            this.preco_diaria = preco_diaria.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return this.id;
    }

    public String getNumero_quarto() {
        return this.numero_quarto;
    }

    public TipoQuarto getTipo_quarto() {
        return this.tipo_quarto;
    }

    public BigDecimal getPreco_diaria() {
        return this.preco_diaria;
    }

    public Status getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return numero_quarto;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Quartos other = (Quartos) obj;
        return this.id == other.id;
    }
    
    
   
    
    
    
    
}

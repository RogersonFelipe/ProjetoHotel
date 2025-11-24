
package model;

import enums.Avaliacao;
import javax.annotation.processing.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_AVALIACAO")

public class AvaliacaoQuarto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @SwingColumn(description = "Codigo")
    private int id;
    
    @Column(name = "nota", length = 2, nullable = false)
    @Enumerated(EnumType.STRING)
    @SwingColumn(description = "Nota")
    private Avaliacao avaliacao;
    
    @Column(name = "observacao", length = 250, nullable = false)
    @SwingColumn(description = "Observação")
    private String observacao;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "ID_RESERVAS", nullable = false, referencedColumnName = "id")
    @SwingColumn(description = "Reserva")
    private Reservas reservas;

    public AvaliacaoQuarto() {
        this.setId(0);
        this.setAvaliacao(Avaliacao.CE);
        this.setObservacao("");
        this.setReservas(null);
    }

    public AvaliacaoQuarto(int id, Avaliacao avaliacao, String observacao, Reservas reservas) {
        this.setId(id);
        this.setAvaliacao(avaliacao);
        this.setObservacao(observacao);
        this.setReservas(null);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao.trim().isEmpty() ? "Sem Observação" :observacao;
    }

    public void setReservas(Reservas reservas) {
        this.reservas = reservas;
    }

    public int getId() {
        return this.id;
    }

    public Avaliacao getAvaliacao() {
        return this.avaliacao;
    }

    public String getObservacao() {
        return this.observacao;
    }

    public Reservas getReservas() {
        return this.reservas;
    }

    @Override
    public String toString() {
        return observacao;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.id;
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
        final AvaliacaoQuarto other = (AvaliacaoQuarto) obj;
        return this.id == other.id;
    }
    
    
    
}

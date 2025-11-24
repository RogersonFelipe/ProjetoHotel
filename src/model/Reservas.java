
package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "TB_RESERVAS")

public class Reservas implements java.io.Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    @SwingColumn(description = "Codigo")
    private int id;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "ID_QUARTO", nullable = false, referencedColumnName = "id")
    @SwingColumn(description = "Quarto Reservado")
    private Quartos quartos;
    
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "ID_HOSPEDES", nullable = false, referencedColumnName = "id")
    @SwingColumn(description = "Hospede")
    private Hospedes hospedes;
    
    @Column (name = "nome_reserva", length = 100, nullable = false)
    @SwingColumn(description = "Nome da Reserva")
    private String nome_reserva;
    
    @Column (name = "data_checkin", length = 10, nullable = false)
    @SwingColumn(description = "Dia de Entrada")
    private LocalDate data_checkin;
    
    @Column (name = "data_checkout", length = 10, nullable = false)
    @SwingColumn(description = "Dia de Saida")
    private LocalDate data_checkout;
    
    @Column(name = "total", precision = 10, scale = 2, nullable = false)
    @SwingColumn(description = "Valor Total")
    private BigDecimal total;

    public Reservas() {
        this.setId(0);
        this.setQuartos(null);
        this.setHospedes(null);
        this.setNome_reserva("Sem Nome na Reserva");
        this.setData_checkin(LocalDate.now());
        this.setData_checkout(LocalDate.now());
        this.setTotal(BigDecimal.ZERO);
    }

    public Reservas(int id, Quartos quartos, Hospedes hospedes, String nome_reserva, LocalDate data_checkin, LocalDate data_checkout, BigDecimal total) {
        this.setId(id);
        this.setQuartos(null);
        this.setHospedes(null);
        this.setNome_reserva(nome_reserva);
        this.setData_checkin(data_checkin);
        this.setData_checkout(data_checkout);
        this.setTotal(total);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuartos(Quartos quartos) {
        this.quartos = quartos;
    }

    public void setHospedes(Hospedes hospedes) {
        this.hospedes = hospedes;
    }

    public void setNome_reserva(String nome_reserva) {
        this.nome_reserva = nome_reserva.trim().isEmpty() ? "SEM NOME" : nome_reserva.toUpperCase();
    }

    public void setData_checkin(LocalDate data_checkin) {
        this.data_checkin = data_checkin;
    }

    public void setData_checkout(LocalDate data_checkout) {
        this.data_checkout = data_checkout;
    }

    public void setTotal(BigDecimal total) {
        if (total == null || total.compareTo(BigDecimal.ZERO) < 0) {
            this.total = BigDecimal.ZERO;
        } else {
            this.total = total.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

    public int getId() {
        return this.id;
    }

    public Quartos getQuartos() {
        return this.quartos;
    }

    public Hospedes getHospedes() {
        return this.hospedes;
    }

    public String getNome_reserva() {
        return this.nome_reserva;
    }

    public LocalDate getData_checkin() {
        return this.data_checkin;
    }

    public LocalDate getData_checkout() {
        return this.data_checkout;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    @Override
    public String toString() {
        return hospedes.getNome() + " - Quarto " + quartos.getNumero_quarto();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.id;
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
        final Reservas other = (Reservas) obj;
        return this.id == other.id;
    }

    
}

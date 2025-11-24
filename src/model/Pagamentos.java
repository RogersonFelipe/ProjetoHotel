
package model;

import enums.MetodoPagamento;
import enums.TipoQuarto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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
@Table(name = "TB_PAGAMENTO")

public class Pagamentos implements Serializable {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id", nullable = false)
    @SwingColumn(description = "Codigo")
    private int id;
    
    @Column (name = "descricao", length = 250, nullable = false)
    @SwingColumn(description = "Descricao")
    private String descricao;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn (name = "ID_RESERVA",nullable = false, referencedColumnName = "id")
    @SwingColumn(description = "Hospede")
    private Reservas reservas;
    
    @Column (name = "valor", precision = 10, scale = 2, nullable = false)
    @SwingColumn(description = "Valor Total")
    private BigDecimal valor;
    
    
    @Column(name = "metodo_pagamento", length = 2, nullable = false)
    @Enumerated(EnumType.STRING)
    @SwingColumn(description = "Metodo de Pagamento")
    private MetodoPagamento metodo_pagamento;
    
    @Column (name = "data_pagamento", length = 10, nullable = false)
    @SwingColumn(description = "Dia do Pagamento")
    private LocalDate data_pagamento;

    public Pagamentos() {
        this.setId(0);
        this.setDescricao("");
        this.setReservas(null);
        this.setValor(BigDecimal.ZERO);
        this.setMetodo_pagamento(metodo_pagamento.PI);
        this.setData_pagamento(LocalDate.now());
    }

    public Pagamentos(int id, Reservas reservas, BigDecimal valor, MetodoPagamento metodo_pagamento, LocalDate data_pagamento) {
        this.setId(id);
        this.setDescricao(descricao);
        this.setReservas(null);
        this.setValor(valor);
        this.setMetodo_pagamento(metodo_pagamento);
        this.setData_pagamento(data_pagamento);
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setDescricao(String descricao){
        this.descricao = descricao.trim().isEmpty() ? "Sem Descricao" : descricao.toUpperCase();
    }

    public void setReservas(Reservas reservas) {
        this.reservas = reservas;
    }

    public void setValor(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            this.valor = BigDecimal.ZERO;
        } else {
            this.valor = valor.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

    public void setMetodo_pagamento(MetodoPagamento metodo_pagamento) {
        this.metodo_pagamento = metodo_pagamento;
    }

    public void setData_pagamento(LocalDate data_pagamento) {
        this.data_pagamento = data_pagamento;
    }

    public int getId() {
        return this.id;
    }
    
    public String getDescricao(){
        return this.descricao;
    }

    public Reservas getReservas() {
        return this.reservas;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public MetodoPagamento getMetodo_pagamento() {
        return this.metodo_pagamento;
    }

    public LocalDate getData_pagamento() {
        return this.data_pagamento;
    }

    @Override
    public String toString() {
        return descricao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.id;
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
        final Pagamentos other = (Pagamentos) obj;
        return this.id == other.id;
    }
    
    
    
}

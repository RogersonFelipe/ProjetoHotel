
package model;

import enums.Cargos;
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
@Table(name = "TB_FUNCIONARIOS")

public class Funcionarios implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id", nullable = false)
    @SwingColumn(description = "Codigo")
    private int id;
    
    @Column (name = "nome", length = 100, nullable = false)
    @SwingColumn(description = "Nome")
    private String nome;
    
    @Column (name = "cargo", length = 2, nullable = false)
    @Enumerated(EnumType.STRING)
    @SwingColumn(description = "Cargo")
    private Cargos cargos;
    
    @Column(name = "salario", precision = 10, scale = 2, nullable = false)
    @SwingColumn(description = "Salario")
    private BigDecimal salario;

    public Funcionarios() {
        this.setId(0);
        this.setNome("Sem nome");
        this.setCargos(cargos.RC);
        this.setSalario(BigDecimal.ZERO);
    }

    public Funcionarios(int id, String nome, Cargos cargos, BigDecimal salario) {
        this.setId(id);
        this.setNome(nome);
        this.setCargos(cargos);
        this.setSalario(salario);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome.trim().isEmpty() ? "Sem Nome" : nome.toUpperCase();
    }

    public void setCargos(Cargos cargos) {
        this.cargos = cargos;
    }

    public void setSalario(BigDecimal salario) {
        if (salario == null || salario.compareTo(BigDecimal.ZERO) < 0) {
            this.salario = BigDecimal.ZERO;
        } else {
            this.salario = salario.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public Cargos getCargos() {
        return this.cargos;
    }

    public BigDecimal getSalario() {
        return this.salario;
    }

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.id;
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
        final Funcionarios other = (Funcionarios) obj;
        return this.id == other.id;
    }
    
    
}

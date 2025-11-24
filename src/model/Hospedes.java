
package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "TB_HOSPEDES")

public class Hospedes implements java.io.Serializable {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id", nullable = false)
    @SwingColumn(description = "Codigo")
    private int id;
    
    @Column (name = "nome", length = 100, nullable = false)
    @SwingColumn(description = "Nome")
    private String nome;
    
    @Column (name = "email", length = 100, nullable = false)
    @SwingColumn(description = "Email")
    private String email;
    
    @Column (name = "telefone", length = 14, nullable = false, unique = true)
    @SwingColumn(description = "Telefone")
    private String telefone;

    public Hospedes() {
        this.setId(0);
        this.setNome("Sem Nome");
        this.setEmail("Sem Email");
        this.setTelefone("Sem Telefone");
    }

    public Hospedes(int id, String nome, String email, String telefone) {
        this.setId(id);
        this.setNome(nome);
        this.setEmail(email);
        this.setTelefone(telefone);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome.trim().isEmpty() ? "SEM NOME" : nome.toUpperCase();
    }

    public void setEmail(String email) {
        this.email = email.trim().isEmpty() ? "Sem Email" : email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone.trim().isEmpty() ? "(00)00000-0000" : telefone;
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getEmail() {
        return this.email;
    }

    public String getTelefone() {
        return this.telefone;
    }

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Hospedes other = (Hospedes) obj;
        return this.id == other.id;
    }
    
    
    
    
    
    
}

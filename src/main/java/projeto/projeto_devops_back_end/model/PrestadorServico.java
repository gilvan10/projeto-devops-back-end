package projeto.projeto_devops_back_end.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import projeto.projeto_devops_back_end.utils.PrestadorServicoRole;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "prestador_servicos")
@EqualsAndHashCode(of = "id")
public class PrestadorServico {

    //UUID é um string aleatória que é gerada automáticamente
    @Id
    @Column(name = "id_prestador_servicos")
    @GeneratedValue(strategy=GenerationType.UUID)
    private String idPrestadorServico;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "profissao")
    private String profissao;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "role")
    private PrestadorServicoRole role;

    @OneToMany(mappedBy = "prestadorServico")
    private List<Fotos> fotos = new ArrayList<>();

    @OneToMany(mappedBy = "prestadorServico")
    private List<Videos> videos = new ArrayList<>();

    public PrestadorServico() {

    }
    public PrestadorServico(String nome, String email, String password, String profissao, String telefone, PrestadorServicoRole role) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.profissao = profissao;
        this.telefone = telefone;
        this.role = role;
    }

    /*tinha  o set e get dos atributos mas foi substituido por o @Lombok.getter e setter acredito por ter construtor
    public String getEmail() {
        return email;
    }


    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
   */


}

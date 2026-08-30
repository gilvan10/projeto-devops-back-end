package projeto.projeto_devops_back_end.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "fotos")
public class Fotos {

    @Id
    @Column(name = "id_foto")
    @GeneratedValue(strategy=GenerationType.UUID)
    private String idFoto;

    @Column(name = "caminho_foto")
    private String caminhoFoto;

    @ManyToOne
    @JoinColumn(name = "prestador_servicos_id_prestador_servicos")
    private PrestadorServico prestadorServico;

}

package projeto.projeto_devops_back_end.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "videos")
public class Videos {

    @Id
    @Column(name = "id_video")
    @GeneratedValue(strategy=GenerationType.UUID)
    private String idVideo;

    @Column(name = "caminho_video")
    private String caminhoVideo;

    @ManyToOne
    @JoinColumn(name = "prestador_servicos_id_prestador_servicos")
    private PrestadorServico prestadorServico;

}

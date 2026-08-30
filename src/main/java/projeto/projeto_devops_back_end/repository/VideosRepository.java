package projeto.projeto_devops_back_end.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projeto.projeto_devops_back_end.model.Videos;

import java.util.List;

@Repository
public interface VideosRepository extends JpaRepository<Videos, String> {

    ////Se precisar fazer query
    //@Query("SELECT v FROM Videos v WHERE v.idVideo = (:idVideo)")
    //Videos findByIdVideo(@Param("idVideo") String idVideo);

    Videos findByIdVideo(String idVideo);

    //Vem todas as videos, complementar para vim videos agrupadas
    //Não estar sendo utilizado esse método
    @Query("SELECT v FROM Videos v INNER JOIN v.prestadorServico ps")
    List<Videos> carregarTodosVideosPrestadorServico();

}

package projeto.projeto_devops_back_end.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projeto.projeto_devops_back_end.model.Fotos;
import java.util.List;

@Repository
public interface FotosRepository extends JpaRepository<Fotos, String> {

    //Se precisar fazer query
    //@Query("SELECT f FROM Fotos f WHERE f.idFoto = (:idFoto)")
    //Fotos findByIdFoto(@Param("idFoto")String idFoto);

    Fotos findByIdFoto(String idFoto);

    //Vem todas as fotos, complementar para vim fotos agrupadas
    //Não estar sendo utilizado esse método
    @Query("SELECT f FROM Fotos f INNER JOIN f.prestadorServico ps")
    List<Fotos> carregarTodasFotosPrestadorServico();

}

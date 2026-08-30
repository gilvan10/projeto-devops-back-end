package projeto.projeto_devops_back_end.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import projeto.projeto_devops_back_end.model.Fotos;
import projeto.projeto_devops_back_end.model.PrestadorServico;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projeto.projeto_devops_back_end.model.Videos;
//import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Repository
public interface PrestadorServicoRepository extends JpaRepository<PrestadorServico, String> {

    //UserDetails findByEmail(String email);

    @Query("SELECT ps FROM PrestadorServico ps WHERE ps.email = (:email)")
    PrestadorServico carregarInfoPrestadorServico(@Param("email") String email);

    PrestadorServico findByIdPrestadorServico(String id);

    //Vem somente as fotos do id selecionado
    @Query("SELECT f FROM Fotos f INNER JOIN f.prestadorServico ps WHERE ps.idPrestadorServico = (:id)")
    List<Fotos> carregarFotosPrestadorServico(@Param("id") String id);

    @Query("SELECT v FROM Videos v INNER JOIN v.prestadorServico ps WHERE ps.idPrestadorServico = (:id)")
    List<Videos> carregarVideosPrestadorServico(@Param("id") String id);
}

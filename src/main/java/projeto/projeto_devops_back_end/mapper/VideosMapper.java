package projeto.projeto_devops_back_end.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import projeto.projeto_devops_back_end.model.Videos;
import projeto.projeto_devops_back_end.model.dto.VideosRequestDTO;
import projeto.projeto_devops_back_end.model.dto.VideosResponseDTO;

@Mapper(componentModel = "spring")
public interface VideosMapper {

    //Para atualizar Videos
    @Mappings({
            @Mapping(source="dto.idVideo", target="idVideo"),
            @Mapping(source="dto.caminhoVideo", target="caminhoVideo"),
            @Mapping(source="dto.prestadorServicoResponseDTO", target="prestadorServico")
    })
    Videos toEntity(VideosResponseDTO dto);

    //Para salvar Videos
    @Mappings({
            @Mapping(target="dto.idVideo", ignore=true),
            @Mapping(source="videoUrl", target="caminhoVideo"),
            @Mapping(source="dto.prestadorServicoResponseDTO", target="prestadorServico")
    })
    Videos toEntity(VideosRequestDTO dto, String videoUrl);

    @Mappings({
            @Mapping(source="entidade.idVideo", target="idVideo"),
            @Mapping(source="entidade.caminhoVideo", target="caminhoVideo"),
            @Mapping(source="entidade.prestadorServico", target="prestadorServicoResponseDTO")
    })
    VideosResponseDTO toDto(Videos entidade);

}

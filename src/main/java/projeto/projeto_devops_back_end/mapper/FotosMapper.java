package projeto.projeto_devops_back_end.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import projeto.projeto_devops_back_end.model.Fotos;
import projeto.projeto_devops_back_end.model.dto.FotosRequestDTO;
import projeto.projeto_devops_back_end.model.dto.FotosResponseDTO;

@Mapper(componentModel = "spring")
public interface FotosMapper {

    //Para atualizar Fotos
    @Mappings({
            @Mapping(source="dto.idFoto", target="idFoto"),
            @Mapping(source="dto.caminhoFoto", target="caminhoFoto"),
            @Mapping(source="dto.prestadorServicoResponseDTO", target="prestadorServico")
    })
    Fotos toEntity(FotosResponseDTO dto);

    //Para salvar Fotos
    @Mappings({
            @Mapping(target="dto.idFoto", ignore=true),
            @Mapping(source="imgUrl", target="caminhoFoto"),
            @Mapping(source="dto.prestadorServicoResponseDTO", target="prestadorServico")
    })
    Fotos toEntity(FotosRequestDTO dto, String imgUrl);

    @Mappings({
            @Mapping(source="entidade.idFoto",target="idFoto"),
            @Mapping(source="entidade.caminhoFoto", target="caminhoFoto"),
            @Mapping(source="entidade.prestadorServico", target="prestadorServicoResponseDTO")
    })
    FotosResponseDTO toDto(Fotos entidade);

}

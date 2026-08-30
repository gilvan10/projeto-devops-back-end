package projeto.projeto_devops_back_end.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;
import projeto.projeto_devops_back_end.model.PrestadorServico;
import projeto.projeto_devops_back_end.model.dto.PrestadorServicoRequestDTO;
import projeto.projeto_devops_back_end.model.dto.PrestadorServicoResponseDTO;

@Mapper(componentModel = "spring")
public interface PrestadorServicoMapper {

    //para atualizar PrestadorServico
    @Mappings({
            @Mapping(source = "dto.idPrestadorServico", target = "idPrestadorServico"),
            @Mapping(source = "dto.nome", target = "nome"),
            @Mapping(source = "dto.email", target = "email"),
            @Mapping(source = "dto.password", target = "password"),
            @Mapping(source = "dto.profissao", target = "profissao"),
            @Mapping(source = "dto.telefone", target = "telefone"),
            @Mapping(source = "dto.role", target = "role"),
    })
    PrestadorServico toEntity(PrestadorServicoResponseDTO dto);

    //para salvar PrestadorServico
    @Mappings({
            @Mapping(target = "idPrestadorServico", ignore = true),
            @Mapping(source = "dto.nome", target = "nome"),
            @Mapping(source = "dto.email", target = "email"),
            @Mapping(source = "dto.password", target = "password"),
            @Mapping(source = "dto.profissao", target = "profissao"),
            @Mapping(source = "dto.telefone", target = "telefone"),
            @Mapping(source = "dto.role", target = "role"),
    })
    PrestadorServico toEntity(PrestadorServicoRequestDTO dto);

    @Mappings({
            @Mapping(source = "entidade.idPrestadorServico", target = "idPrestadorServico"),
            @Mapping(source = "entidade.nome", target = "nome"),
            @Mapping(source = "entidade.email", target = "email"),
            @Mapping(source = "entidade.password", target = "password"),
            @Mapping(source = "entidade.profissao", target = "profissao"),
            @Mapping(source = "entidade.telefone", target = "telefone"),
            @Mapping(source = "entidade.role", target = "role"),
    })
    PrestadorServicoResponseDTO toDto(PrestadorServico entidade);
}

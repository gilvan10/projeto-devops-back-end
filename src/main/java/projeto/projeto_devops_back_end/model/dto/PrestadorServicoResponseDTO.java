package projeto.projeto_devops_back_end.model.dto;

import projeto.projeto_devops_back_end.utils.PrestadorServicoRole;

public record PrestadorServicoResponseDTO(String idPrestadorServico, String nome, String email, String password, String profissao, String telefone, PrestadorServicoRole role) {
}

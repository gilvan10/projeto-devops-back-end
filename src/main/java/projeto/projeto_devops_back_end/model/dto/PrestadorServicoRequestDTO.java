package projeto.projeto_devops_back_end.model.dto;

import projeto.projeto_devops_back_end.utils.PrestadorServicoRole;

public record PrestadorServicoRequestDTO(String nome, String email, String password, String profissao, String telefone, PrestadorServicoRole role) {
}

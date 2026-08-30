package projeto.projeto_devops_back_end.model.dto;

import org.springframework.web.multipart.MultipartFile;

public record VideosRequestDTO(MultipartFile video, PrestadorServicoResponseDTO prestadorServicoResponseDTO) {
}

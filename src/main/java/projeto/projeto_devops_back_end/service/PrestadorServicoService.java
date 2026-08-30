package projeto.projeto_devops_back_end.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto.projeto_devops_back_end.mapper.FotosMapper;
import projeto.projeto_devops_back_end.mapper.PrestadorServicoMapper;
import projeto.projeto_devops_back_end.model.Fotos;
import projeto.projeto_devops_back_end.model.PrestadorServico;
import projeto.projeto_devops_back_end.model.Videos;
import projeto.projeto_devops_back_end.model.dto.FotosResponseDTO;
import projeto.projeto_devops_back_end.model.dto.PrestadorServicoRequestDTO;
import projeto.projeto_devops_back_end.model.dto.PrestadorServicoResponseDTO;
import projeto.projeto_devops_back_end.model.dto.VideosResponseDTO;
import projeto.projeto_devops_back_end.repository.PrestadorServicoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrestadorServicoService {

    @Autowired
    private PrestadorServicoMapper mapper;

    @Autowired
    private FotosMapper fotosMapper;

    final PrestadorServicoRepository repository;

    public PrestadorServicoService(PrestadorServicoRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true )
    public List<PrestadorServicoResponseDTO> findAll() {
        List<PrestadorServico> prestadoresServicos = this.repository.findAll();
        return prestadoresServicos.stream().map(prestadorServico -> new PrestadorServicoResponseDTO(
                        prestadorServico.getIdPrestadorServico(),
                        prestadorServico.getNome(),
                        prestadorServico.getEmail(),
                        prestadorServico.getPassword(),
                        prestadorServico.getProfissao(),
                        prestadorServico.getTelefone(),
                        prestadorServico.getRole())
                )
                .toList();
    }

    @Transactional
    public PrestadorServicoResponseDTO save(PrestadorServicoRequestDTO dto) {
        PrestadorServico prestadorServico = this.mapper.toEntity(dto);
        this.repository.save(prestadorServico);
        return this.carregarInfoPrestadorServico(dto.email());
    }

    @Transactional(readOnly =  true )
    public PrestadorServicoResponseDTO carregarInfoPrestadorServico(String email) {
        PrestadorServico prestadorServico = this.repository.carregarInfoPrestadorServico(email);
        return this.mapper.toDto(prestadorServico);
    }

    @Transactional(readOnly =  true )
    public List<FotosResponseDTO> carregarFotosPrestadorServico(String id) {
        List<Fotos> fotos = this.repository.carregarFotosPrestadorServico(id);
        ArrayList<FotosResponseDTO> fotosDTO = new ArrayList<>();
        String idFoto;
        String caminhoFoto;
        PrestadorServicoResponseDTO prestadorServico;
        for(int i = 0; i < fotos.size(); i++) {
            idFoto = fotos.get(i).getIdFoto();
            caminhoFoto = fotos.get(i).getCaminhoFoto();
            prestadorServico = this.mapper.toDto(fotos.get(i).getPrestadorServico());
            FotosResponseDTO foto = new FotosResponseDTO(idFoto,caminhoFoto,prestadorServico);
            fotosDTO.add(foto);
        }
        return fotosDTO;
    }

    @Transactional(readOnly =  true )
    public List<VideosResponseDTO> carregarVideosPrestadorServico(String id) {
        List<Videos> videos = this.repository.carregarVideosPrestadorServico(id);
        ArrayList<VideosResponseDTO> videosDTO = new ArrayList<>();
        String idVideo;
        String caminhoVideo;
        PrestadorServicoResponseDTO prestadorServico;
        for(int i = 0; i < videos.size(); i++) {
            idVideo = videos.get(i).getIdVideo();
            caminhoVideo = videos.get(i).getCaminhoVideo();
            prestadorServico = this.mapper.toDto(videos.get(i).getPrestadorServico());
            VideosResponseDTO video = new VideosResponseDTO(idVideo,caminhoVideo,prestadorServico);
            videosDTO.add(video);
        }
        return videosDTO;
    }

    @Transactional
    public PrestadorServicoResponseDTO update(PrestadorServicoResponseDTO dto) {
        PrestadorServico prestadorServico = this.mapper.toEntity(dto);
        this.repository.save(prestadorServico);
        return this.mapper.toDto(prestadorServico);
    }

    public PrestadorServicoResponseDTO delete(String id) {
        // Esse método retorna um Optional this.repository.findById(id); por isso foi feito uma @query no repository retonando PrestadorServico
        PrestadorServico prestadorServico = this.repository.findByIdPrestadorServico(id);
        this.repository.deleteById(prestadorServico.getIdPrestadorServico());
        return this.mapper.toDto(prestadorServico);
    }
}

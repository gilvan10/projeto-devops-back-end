package projeto.projeto_devops_back_end.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import projeto.projeto_devops_back_end.mapper.PrestadorServicoMapper;
import projeto.projeto_devops_back_end.mapper.VideosMapper;
import projeto.projeto_devops_back_end.model.Videos;
import projeto.projeto_devops_back_end.model.dto.PrestadorServicoResponseDTO;
import projeto.projeto_devops_back_end.model.dto.VideosRequestDTO;
import projeto.projeto_devops_back_end.model.dto.VideosResponseDTO;
import projeto.projeto_devops_back_end.model.dto.VideosResponseDTOBasico;
import projeto.projeto_devops_back_end.repository.PrestadorServicoRepository;
import projeto.projeto_devops_back_end.repository.VideosRepository;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.nio.ByteBuffer;
import java.io.InputStream;

@Service
public class VideosService {

    @Value("${aws.bucket.name}")
    private String bucketName;

    //Foi retirado do applications.properties
    //@Value("${admin.key}")
    //private String adminKey;

    @Autowired
    VideosMapper mapper;

    @Autowired
    PrestadorServicoMapper mapperPrestadorServico;

    private final S3Client s3Client;
    final VideosRepository repository;
    final PrestadorServicoRepository prestadorRepository;

    public VideosService(VideosRepository repository, PrestadorServicoRepository prestadorRepository) {
        this.s3Client = S3Client.create();
        this.repository = repository;
        this.prestadorRepository = prestadorRepository;
    }

    /*@Transactional(readOnly = true)
    public List<VideosResponseDTO> findAll() {
    	List<Videos> videos = this.repository.findAll();
    	ArrayList<VideosResponseDTO> videosDTO = new ArrayList<>();
    	String idVideo;
    	String caminhoVideo;
    	PrestadorServicoResponseDTO prestadorServico;
    	for(int i = 0; i < videos.size(); i++) {
    		idVideo = videos.get(i).getIdVideo();
    		caminhoVideo = videos.get(i).getCaminhoVideo();
    		prestadorServico = this.mapperPrestadorServico.toDto(videos.get(i).getPrestadorServico());
    		VideosResponseDTO video = new VideosResponseDTO(idVideo, caminhoVideo, prestadorServico);
    		videosDTO.add(video);
    	}
    	return videosDTO;
    }*/

    public List<VideosResponseDTOBasico> findAll() {
        List<Videos> videos = this.repository.findAll();
        ArrayList<VideosResponseDTOBasico> videosDTO = new ArrayList<>();
        String idVideo;
        String caminhoVideo;
        PrestadorServicoResponseDTO prestadorServico;
        for(int i = 0; i < videos.size(); i++) {
            idVideo = videos.get(i).getIdVideo();
            caminhoVideo = videos.get(i).getCaminhoVideo();
            prestadorServico = this.mapperPrestadorServico.toDto(videos.get(i).getPrestadorServico());
            VideosResponseDTOBasico video = new VideosResponseDTOBasico(idVideo, caminhoVideo, prestadorServico.nome(), prestadorServico.profissao(), prestadorServico.telefone());
            videosDTO.add(video);
        }
        return videosDTO;
    }

    @Transactional
    public VideosResponseDTO save(VideosRequestDTO dto) {

        String videoUrl = null;
        if(dto.video() != null) {
            videoUrl = this.uploadArquivo(dto.video());
        }

        Videos video = this.mapper.toEntity(dto, videoUrl);
        Videos retorno = this.repository.save(video);
        VideosResponseDTO videoDTO = this.mapper.toDto(retorno);
        return videoDTO;

    }


    //principal:  fotos e videos pequenos 15MB  funciona, mas para um vídeo de 112MB não funcionou
	/*private String uploadArquivo(MultipartFile multipartFile) {

		String filename = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
		   try {
	            PutObjectRequest putOb = PutObjectRequest.builder()
	                    .bucket(bucketName)
	                    .key(filename)
	                    .build();
	            s3Client.putObject(putOb, RequestBody.fromByteBuffer(ByteBuffer.wrap(multipartFile.getBytes())));
	            GetUrlRequest request = GetUrlRequest.builder()
	                    .bucket(bucketName)
	                    .key(filename)
	                    .build();
	            return s3Client.utilities().getUrl(request).toString();
	        } catch (Exception e) {
	            //log.error("erro ao subir arquivo: {}", e.getMessage());
	            return "";
	        }
	}*/

    //secundario para fotos videos pequenos e videos grandes
    private String uploadArquivo(MultipartFile multipartFile) {
        try {
            String filename = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
            InputStream imputStream = multipartFile.getInputStream();
            long contentLength = multipartFile.getSize();
            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filename)
                    .contentLength(contentLength)
                    .build();
            s3Client.putObject(putOb, RequestBody.fromInputStream(imputStream, contentLength));
            GetUrlRequest request = GetUrlRequest.builder()
                    .bucket(bucketName)
                    .key(filename)
                    .build();
            return s3Client.utilities().getUrl(request).toString();
        } catch (Exception e) {
            //log.error("erro ao subir arquivo: {}", e.getMessage());
            return "";
        }
    }

    @Transactional(readOnly = true)
    public VideosResponseDTO findById(String Id) {
        Videos video = this.repository.findByIdVideo(Id);
        return this.mapper.toDto(video);
    }

    @Transactional
    public VideosResponseDTO update(VideosResponseDTO dto) {
        Videos video = this.mapper.toEntity(dto);
        this.repository.save(video);
        return this.mapper.toDto(video);
    }

    public VideosResponseDTO delete(String Id) {
        Videos video = this.repository.findByIdVideo(Id);
        this.repository.deleteById(video.getIdVideo());
        return this.mapper.toDto(video);
    }

}

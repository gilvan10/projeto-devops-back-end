package projeto.projeto_devops_back_end.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import projeto.projeto_devops_back_end.mapper.FotosMapper;
import projeto.projeto_devops_back_end.mapper.PrestadorServicoMapper;
import projeto.projeto_devops_back_end.model.Fotos;
import projeto.projeto_devops_back_end.model.dto.FotosRequestDTO;
import projeto.projeto_devops_back_end.model.dto.FotosResponseDTO;
import projeto.projeto_devops_back_end.model.dto.FotosResponseDTOBasico;
import projeto.projeto_devops_back_end.model.dto.PrestadorServicoResponseDTO;
import projeto.projeto_devops_back_end.repository.FotosRepository;
import projeto.projeto_devops_back_end.repository.PrestadorServicoRepository;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.nio.ByteBuffer;

@Service
public class FotosService {

    @Value("${aws.bucket.name}")
    private String bucketName;

    //Foi retirado do applications.properties
    //@Value("${admin.key}")
    //private String adminKey;

    @Autowired
    FotosMapper mapper;

    @Autowired
    PrestadorServicoMapper mapperPrestadorServico;

    private final S3Client s3Client;
    final FotosRepository repository;
    final PrestadorServicoRepository prestadorRepository;

    public FotosService(FotosRepository repository, PrestadorServicoRepository prestadorRepository) {
        this.s3Client = S3Client.create();
        this.repository = repository;
        this.prestadorRepository = prestadorRepository;
    }

    /*@Transactional(readOnly = true )
    public List<FotosResponseDTO> findAll() {
    	List<Fotos> fotos = this.repository.findAll();
    	ArrayList<FotosResponseDTO> fotosDTO = new ArrayList<>();
    	String idFoto;
    	String caminhoFoto;
    	PrestadorServicoResponseDTO prestadorServico;
    	for(int i = 0; i < fotos.size(); i++) {
    		idFoto = fotos.get(i).getIdFoto();
    		caminhoFoto = fotos.get(i).getCaminhoFoto();
    		prestadorServico = this.mapperPrestadorServico.toDto(fotos.get(i).getPrestadorServico());
    		FotosResponseDTO foto = new FotosResponseDTO(idFoto,caminhoFoto,prestadorServico);
    		fotosDTO.add(foto);
    	}
    	return fotosDTO;
    }*/

    @Transactional(readOnly = true )
    public List<FotosResponseDTOBasico> findAll() {
        List<Fotos> fotos = this.repository.findAll();
        ArrayList<FotosResponseDTOBasico> fotosDTO = new ArrayList<>();
        String idFoto;
        String caminhoFoto;
        PrestadorServicoResponseDTO prestadorServico;
        for(int i = 0; i < fotos.size(); i++) {
            idFoto = fotos.get(i).getIdFoto();
            caminhoFoto = fotos.get(i).getCaminhoFoto();
            prestadorServico = this.mapperPrestadorServico.toDto(fotos.get(i).getPrestadorServico());
            FotosResponseDTOBasico foto = new FotosResponseDTOBasico(idFoto,caminhoFoto,prestadorServico.nome(),prestadorServico.profissao(),prestadorServico.telefone());
            fotosDTO.add(foto);
        }
        return fotosDTO;
    }

    @Transactional
    public FotosResponseDTO save(FotosRequestDTO dto) {

        String imgUrl = "";
        if(dto.imagem() != null) {
            imgUrl = this.uploadArquivo(dto.imagem());
        }

        Fotos foto = this.mapper.toEntity(dto, imgUrl);
        Fotos retorno = this.repository.save(foto);
        FotosResponseDTO fotoDTO = this.mapper.toDto(retorno);
        return fotoDTO;
    }

    private String uploadArquivo(MultipartFile multipartFile) {

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
    }

    @Transactional(readOnly = true )
    public FotosResponseDTO findById(String id) {
        Fotos foto = this.repository.findByIdFoto(id);
        return this.mapper.toDto(foto);
    }

    @Transactional
    public FotosResponseDTO update(FotosResponseDTO dto) {
        Fotos foto = this.mapper.toEntity(dto);
        this.repository.save(foto);
        return this.mapper.toDto(foto);
    }

    public FotosResponseDTO delete(String id) {
        Fotos foto = this.repository.findByIdFoto(id);
        this.repository.deleteById(foto.getIdFoto());
        return this.mapper.toDto(foto);
    }

}

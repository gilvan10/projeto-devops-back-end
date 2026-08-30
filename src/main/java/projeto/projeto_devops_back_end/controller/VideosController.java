package projeto.projeto_devops_back_end.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projeto.projeto_devops_back_end.model.dto.PrestadorServicoResponseDTO;
import projeto.projeto_devops_back_end.model.dto.VideosRequestDTO;
import projeto.projeto_devops_back_end.model.dto.VideosResponseDTO;
import projeto.projeto_devops_back_end.model.dto.VideosResponseDTOBasico;
import projeto.projeto_devops_back_end.service.VideosService;
import projeto.projeto_devops_back_end.utils.PrestadorServicoRole;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/videos")
public class VideosController {

    @Autowired
    final VideosService service;

    public VideosController(VideosService service) {
        this.service = service;
    }

	/*@GetMapping(produces =  MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<VideosResponseDTO>> findAll() {
		return ResponseEntity.ok(this.service.findAll());
	}*/

    @GetMapping(produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VideosResponseDTOBasico>> findAll() {
        return ResponseEntity.ok(this.service.findAll());
    }

    //@RequestParam(value = "video",required = false)  se colocar o valor false atributo não é obrigatório testar depois
    //@Valid
    @PostMapping(consumes = "multipart/form-data", produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VideosResponseDTO> save(@RequestParam("video") MultipartFile video,
                                                  @RequestParam("idPrestadorServico") String idPrestadorServico,
                                                  @RequestParam("nome") String nome,
                                                  @RequestParam("email") String email,
                                                  @RequestParam("password") String password,
                                                  @RequestParam("profissao") String profissao,
                                                  @RequestParam("telefone") String telefone,
                                                  @RequestParam("role") String role) {

        PrestadorServicoRole auxRole;
        if(role.equalsIgnoreCase("ADMIN"))
            auxRole = PrestadorServicoRole.ADMIN;
        else
            auxRole = PrestadorServicoRole.USER;
        PrestadorServicoResponseDTO prestadorServicoResponseDTO = new PrestadorServicoResponseDTO(idPrestadorServico, nome, email, password, profissao, telefone, auxRole);
        VideosRequestDTO arquivo = new VideosRequestDTO(video, prestadorServicoResponseDTO);
        return ResponseEntity.ok(this.service.save(arquivo));
    }

    @GetMapping(value = "/{id}", produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VideosResponseDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(this.service.findById(id));
    }

    @PutMapping(consumes = "multipart/form-data", produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VideosResponseDTO> update(@ModelAttribute VideosResponseDTO dto) {
        return ResponseEntity.ok(this.service.update(dto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VideosResponseDTO> delete(@PathVariable String id) {
        return ResponseEntity.ok(this.service.delete(id));
    }
}

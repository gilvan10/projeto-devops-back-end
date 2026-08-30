package projeto.projeto_devops_back_end.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projeto.projeto_devops_back_end.model.dto.FotosRequestDTO;
import projeto.projeto_devops_back_end.model.dto.FotosResponseDTO;
import projeto.projeto_devops_back_end.model.dto.FotosResponseDTOBasico;
import projeto.projeto_devops_back_end.model.dto.PrestadorServicoResponseDTO;
import projeto.projeto_devops_back_end.service.FotosService;
import projeto.projeto_devops_back_end.utils.PrestadorServicoRole;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/fotos")
public class FotosController {

    @Autowired
    final FotosService service;

    public FotosController(FotosService service) {
        this.service = service;
    }

	/*@GetMapping(produces =  MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FotosResponseDTO>> findAll() {
		return ResponseEntity.ok(this.service.findAll());
	}*/

    @GetMapping(produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FotosResponseDTOBasico>> findAll() {
        return ResponseEntity.ok(this.service.findAll());
    }

    //@RequestParam(value = "imagem",required = false)  se colocar o valor false atributo não é obrigatório testar depois
    //@Valid
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<FotosResponseDTO> save(@RequestParam("imagem") MultipartFile imagem,
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
        FotosRequestDTO foto = new FotosRequestDTO(imagem, prestadorServicoResponseDTO);
        return ResponseEntity.ok(this.service.save(foto));
    }

    @GetMapping(value = "/{id}", produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FotosResponseDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(this.service.findById(id));
    }

    @PutMapping(consumes = "multipart/form-data", produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FotosResponseDTO> update(@ModelAttribute FotosResponseDTO dto) {
        return ResponseEntity.ok(this.service.update(dto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FotosResponseDTO> delete(@PathVariable String id) {
        return ResponseEntity.ok(this.service.delete(id));
    }
}

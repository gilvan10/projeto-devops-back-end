package projeto.projeto_devops_back_end.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.projeto_devops_back_end.model.dto.FotosResponseDTO;
import projeto.projeto_devops_back_end.model.dto.PrestadorServicoRequestDTO;
import projeto.projeto_devops_back_end.model.dto.PrestadorServicoResponseDTO;
import projeto.projeto_devops_back_end.model.dto.VideosResponseDTO;
import projeto.projeto_devops_back_end.service.PrestadorServicoService;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/prestador-servico")
public class PrestadorServicoController {

    @Autowired
    final PrestadorServicoService service;

    public PrestadorServicoController(PrestadorServicoService service) {
        this.service = service;
    }

    //para a verificação de integridade do target group aws. aqui retorna o status code 200 + a string ok
    @GetMapping(value = "/check")
    public ResponseEntity<String> check() {
        return ResponseEntity.ok("ok teste pipeline teste20");
    }
    //ou bem simples
    //@GetMapping("/check")
    //public String check() {
      //  return "ok";
    //}


    @GetMapping(produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PrestadorServicoResponseDTO>> findAll(){
        return ResponseEntity.ok(this.service.findAll());
    }
    //@Valid
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PrestadorServicoResponseDTO> save(@RequestBody PrestadorServicoRequestDTO dto) {
        return ResponseEntity.ok(this.service.save(dto));
    }

    @GetMapping(value = "/{email}",  produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PrestadorServicoResponseDTO> carregarInfoPrestadorServico(@PathVariable String email) {
        return ResponseEntity.ok(this.service.carregarInfoPrestadorServico(email));
    }

    @GetMapping(value = "/fotos/{id}",  produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FotosResponseDTO>> carregarFotosPrestadorServico(@PathVariable String id) {
        return ResponseEntity.ok(this.service.carregarFotosPrestadorServico(id));
    }

    @GetMapping(value = "/videos/{id}",  produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VideosResponseDTO>> carregarVideosPrestadorServico(@PathVariable String id) {
        return ResponseEntity.ok(this.service.carregarVideosPrestadorServico(id));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PrestadorServicoResponseDTO> update(@RequestBody PrestadorServicoResponseDTO dto) {
        return ResponseEntity.ok(this.service.update(dto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PrestadorServicoResponseDTO> delete(@PathVariable String id) {
        return ResponseEntity.ok(this.service.delete(id));
    }
}

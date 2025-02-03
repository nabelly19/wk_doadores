package br.com.wk.donors.controllers;

import br.com.wk.donors.entities.Candidate;
import br.com.wk.donors.services.CandidateService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/candidates")
@Tag(name = "Candidates", description = "Candidatos e estatísticas de doadores")
public class CandidateController {

    private final CandidateService service;

    public CandidateController(CandidateService service) {
        this.service = service;
    }

    @Operation(summary = "Index das APIs Candidates", description = "Retorna o link para o swagger")
    @GetMapping
    public ResponseEntity<String> index() {
        String swaggerUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/swagger-ui/index.html")
                .toUriString();

        String responseHtml = "<html><head><style>"
                + "body { display: flex; justify-content: center; align-items: center; height: 100vh; font-family: sans-serif; text-align: center; }"
                + "</style></head><body>"
                + "<h2><a href='" + swaggerUrl + "' target='_blank'>API de Doadores de Sangue DOCs</a></h2>"
                + "</body></html>";

        return ResponseEntity.ok().body(responseHtml);
    }

    @Operation(summary = "Quantidade de candidatos por estado", description = "Retorna o número de candidatos registrados por estado")
    @GetMapping("/count-by-state")
    public Map<String, Long> countByState() {
        return service.countByState();
    }

    @Operation(summary = "IMC médio por faixa etária", description = "Calcula o IMC médio dos candidatos agrupados por faixa etária")
    @GetMapping("/average-bmi-by-age-group")
    public Map<String, Double> averageBmiByAgeGroup() {
        return service.averageBmiByAgeGroup();
    }

    @Operation(summary = "Percentual de obesidade", description = "Retorna o percentual de obesos entre os candidatos cadastrados")
    @GetMapping("/obesity-percentage")
    public Map<String, Double> calculateObesityPercentage() {
        return service.calculateObesityPercentage();
    }

    @Operation(summary = "Idade média por tipo sanguíneo", description = "Retorna a idade média dos candidatos agrupados por tipo sanguíneo")
    @GetMapping("/average-age-by-blood-type")
    public Map<String, Double> averageAgeByBloodType() {
        return service.averageAgeByBloodType();
    }

    @Operation(summary = "Doadores aptos", description = "Retorna a quantidade de candidatos aptos para doação de sangue")
    @GetMapping("/eligible-donors")
    public Map<String, Integer> eligibleDonors() {
        return service.eligibleDonors();
    }

    @Operation(summary = "Quantidade de doadores por receptor", description = "Retorna a quantidade de possíveis doadores para cada tipo sanguíneo receptor")
    @GetMapping("/donors-per-recipient")
    public Map<String, Integer> donorsPerRecipient() {
        return service.donorsPerRecipient();
    }

    @Operation(summary = "Carregar dados", description = "Recebe uma lista de candidatos e salva no banco de dados. Substitui os dados existentes.")
    @PostMapping("/load-data")
    public ResponseEntity<String> loadData(@RequestBody List<Candidate> candidates) {
        // TODO: falta tratar carga de dados em duplicidade,por enquanto só está apagando e carregando novamente.
        service.deleteAll();
        service.saveAll(candidates);
        return ResponseEntity.ok("Dados recebidos com sucesso!");
    }
}

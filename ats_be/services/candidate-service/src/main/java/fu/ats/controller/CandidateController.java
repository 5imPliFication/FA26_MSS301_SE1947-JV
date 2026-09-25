package fu.ats.controller;

import fu.ats.dto.CandidatePatchRequest;
import fu.ats.dto.CandidateRequest;
import fu.ats.dto.CandidateResponse;
import fu.ats.dto.SkillResponse;
import fu.ats.service.CandidateService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/candidates")
@RequiredArgsConstructor
@Validated
public class CandidateController {
    private final CandidateService candidateService;

    @PostMapping
    public ResponseEntity<CandidateResponse> createCandidate(
            @Valid @RequestBody CandidateRequest request
    ) {
        return ResponseEntity.ok(candidateService.save(request));
    }

    @GetMapping("/{candidateId}")
    public ResponseEntity<CandidateResponse> getCandidate(
            @PathVariable("candidateId") UUID candidateId
    ) {
        return ResponseEntity.ok(candidateService.getById(candidateId));
    }

    @GetMapping
    public ResponseEntity<List<CandidateResponse>> findCandidatesByEmail(
            @RequestParam("email")
            @NotBlank
            @Pattern(
                    regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
                    message = "Email is not valid format"
            )
            String email
    ) {
        return ResponseEntity.ok(candidateService.findByEmail(email));
    }

    @PatchMapping("/{candidateId}")
    public ResponseEntity<CandidateResponse> updateCandidate(
            @PathVariable("candidateId") UUID candidateId,
            @Valid @RequestBody CandidatePatchRequest request
    ) {
        return ResponseEntity.ok(candidateService.update(candidateId, request));
    }

    @GetMapping("/{candidateId}/skills")
    public ResponseEntity<List<SkillResponse>> getCandidateSkills(
            @PathVariable("candidateId") UUID candidateId
    ) {
        return ResponseEntity.ok(candidateService.getSkills(candidateId));
    }
}

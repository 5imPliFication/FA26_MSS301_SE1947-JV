package fu.ats.controller;

import fu.ats.dto.CandidateRequest;
import fu.ats.entity.Candidates;
import fu.ats.service.CandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @RestController = @Controller + @ResponseBody
@RequestMapping("/api/v1/candidates")
@RequiredArgsConstructor
public class CandidateController {
    private final CandidateService candidateService;

    @PostMapping
    public ResponseEntity<Candidates> createCandidate(
            @Valid @RequestBody CandidateRequest request
    ) {
        return ResponseEntity.ok(candidateService.save(request));
    }
}

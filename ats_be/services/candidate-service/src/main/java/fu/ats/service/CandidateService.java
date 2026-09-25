package fu.ats.service;

import fu.ats.dto.CandidatePatchRequest;
import fu.ats.dto.CandidateRequest;
import fu.ats.dto.CandidateResponse;
import fu.ats.dto.SkillResponse;

import java.util.List;
import java.util.UUID;

public interface CandidateService {
    CandidateResponse save(CandidateRequest request);

    CandidateResponse getById(UUID candidateId);

    List<CandidateResponse> findByEmail(String email);

    CandidateResponse update(UUID candidateId, CandidatePatchRequest request);

    List<SkillResponse> getSkills(UUID candidateId);
}

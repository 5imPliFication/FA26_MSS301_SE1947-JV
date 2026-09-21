package fu.ats.service;

import fu.ats.dto.CandidateRequest;
import fu.ats.entity.Candidates;

public interface CandidateService {
    Candidates save(CandidateRequest request);
}

package fu.ats.service;

import fu.ats.dto.CandidatePatchRequest;
import fu.ats.dto.CandidateRequest;
import fu.ats.dto.CandidateResponse;
import fu.ats.dto.SkillResponse;
import fu.ats.entity.CandidateSkillId;
import fu.ats.entity.CandidateSkills;
import fu.ats.entity.Candidates;
import fu.ats.entity.Skills;
import fu.ats.exception.CandidateNotFoundException;
import fu.ats.mapper.CandidateMapper;
import fu.ats.repository.CandidateRepository;
import fu.ats.repository.CandidateSkillsRepository;
import fu.ats.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final CandidateSkillsRepository candidateSkillsRepository;
    private final SkillRepository skillRepository;
    private final CandidateMapper candidateMapper;

    @Override
    @Transactional
    public CandidateResponse save(CandidateRequest request) {
        Candidates savedCandidate = candidateRepository.save(candidateMapper.toCandidate(request));
        addSkills(savedCandidate, request.getSkillIds());
        return candidateMapper.toResponse(savedCandidate);
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateResponse getById(UUID candidateId) {
        return candidateMapper.toResponse(getCandidate(candidateId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CandidateResponse> findByEmail(String email) {
        return candidateRepository.findAllByEmailIgnoreCaseOrderByIdAsc(email).stream()
                .map(candidateMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public CandidateResponse update(UUID candidateId, CandidatePatchRequest request) {
        if (request.hasNoValues()) {
            throw new IllegalArgumentException("At least one candidate field must be provided");
        }

        Candidates candidate = getCandidate(candidateId);
        candidateMapper.updateCandidate(request, candidate);
        return candidateMapper.toResponse(candidate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillResponse> getSkills(UUID candidateId) {
        Candidates candidate = getCandidate(candidateId);
        return candidateSkillsRepository.findAllForCandidate(candidate).stream()
                .map(candidateSkill -> candidateMapper.toResponse(
                        candidateSkill.getCandidateSkillId().getSkillId()
                ))
                .toList();
    }

    private Candidates getCandidate(UUID candidateId) {
        return candidateRepository.findById(candidateId)
                .orElseThrow(() -> new CandidateNotFoundException(candidateId));
    }

    private void addSkills(Candidates candidate, List<UUID> requestedSkillIds) {
        Set<UUID> uniqueSkillIds = requestedSkillIds == null
                ? Set.of()
                : new LinkedHashSet<>(requestedSkillIds);
        if (uniqueSkillIds.isEmpty()) {
            return;
        }

        List<Skills> skills = skillRepository.findAllById(uniqueSkillIds);
        if (skills.size() != uniqueSkillIds.size()) {
            throw new IllegalArgumentException("One or more skill IDs do not exist");
        }

        List<CandidateSkills> candidateSkills = skills.stream()
                .map(skill -> CandidateSkills.builder()
                        .candidateSkillId(new CandidateSkillId(candidate, skill))
                        .date(LocalDate.now())
                        .build())
                .toList();

        candidateSkillsRepository.saveAll(candidateSkills);
    }
}

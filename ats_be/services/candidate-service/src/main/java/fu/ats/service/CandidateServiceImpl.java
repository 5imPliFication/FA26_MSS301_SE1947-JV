package fu.ats.service;

import fu.ats.dto.CandidateRequest;
import fu.ats.entity.CandidateSkillId;
import fu.ats.entity.CandidateSkills;
import fu.ats.entity.Candidates;
import fu.ats.entity.Skills;
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

    @Override
    @Transactional
    public Candidates save(CandidateRequest request) {
        Candidates savedCandidate = candidateRepository.save(toCandidate(request));

        List<UUID> requestedSkillIds = request.getSkillIds() == null
                ? List.of()
                : request.getSkillIds();

        Set<UUID> uniqueSkillIds = new LinkedHashSet<>(requestedSkillIds);
        if (uniqueSkillIds.isEmpty()) {
            return savedCandidate;
        }

        List<Skills> skills = skillRepository.findAllById(uniqueSkillIds);
        if (skills.size() != uniqueSkillIds.size()) {
            throw new IllegalArgumentException("One or more skill IDs do not exist");
        }

        List<CandidateSkills> candidateSkills = skills.stream()
                .map(skill -> CandidateSkills.builder()
                        .candidateSkillId(new CandidateSkillId(savedCandidate, skill))
                        .date(LocalDate.now())
                        .build())
                .toList();

        candidateSkillsRepository.saveAll(candidateSkills);
        return savedCandidate;
    }

    private Candidates toCandidate(CandidateRequest request) {
        Candidates candidate = Candidates.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .source(request.getSource())
                .utmSource(request.getUtmSource())
                .utmCampaign(request.getUtmCampaign())
                .utmMedium(request.getUtmMedium())
                .status(fu.ats.entity.CandidateStatus.ACTIVE)
                .isDuplicate(false)
                .build();

        return candidate;
    }
}

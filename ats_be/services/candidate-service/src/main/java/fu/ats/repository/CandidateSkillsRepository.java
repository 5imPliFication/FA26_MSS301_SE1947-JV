package fu.ats.repository;

import fu.ats.entity.CandidateSkillId;
import fu.ats.entity.CandidateSkills;
import fu.ats.entity.Candidates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CandidateSkillsRepository extends JpaRepository<CandidateSkills, CandidateSkillId> {
    @Query("""
            select candidateSkill
            from CandidateSkills candidateSkill
            where candidateSkill.candidateSkillId.id = :candidate
            order by candidateSkill.candidateSkillId.skillId.skillName,
                     candidateSkill.candidateSkillId.skillId.id
            """)
    List<CandidateSkills> findAllForCandidate(@Param("candidate") Candidates candidate);
}

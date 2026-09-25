package fu.ats.mapper;

import fu.ats.dto.CandidatePatchRequest;
import fu.ats.dto.CandidateRequest;
import fu.ats.dto.CandidateResponse;
import fu.ats.dto.SkillResponse;
import fu.ats.entity.Candidates;
import fu.ats.entity.Skills;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CandidateMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "authProvider", ignore = true)
    @Mapping(target = "oauthProviderId", ignore = true)
    @Mapping(target = "status", expression = "java(fu.ats.entity.CandidateStatus.ACTIVE)")
    @Mapping(target = "isDuplicate", constant = "false")
    Candidates toCandidate(CandidateRequest request);

    CandidateResponse toResponse(Candidates candidate);

    @BeanMapping(
            ignoreByDefault = true,
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "source", source = "source")
    @Mapping(target = "utmSource", source = "utmSource")
    @Mapping(target = "utmMedium", source = "utmMedium")
    @Mapping(target = "utmCampaign", source = "utmCampaign")
    void updateCandidate(CandidatePatchRequest request, @MappingTarget Candidates candidate);

    SkillResponse toResponse(Skills skill);
}

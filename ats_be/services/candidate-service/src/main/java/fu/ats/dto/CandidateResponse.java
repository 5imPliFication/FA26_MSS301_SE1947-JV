package fu.ats.dto;

import fu.ats.entity.CandidateStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateResponse {
    private UUID id;
    private String fullName;
    private String email;
    private String phone;
    private String source;
    private String utmSource;
    private String utmMedium;
    private String utmCampaign;
    private CandidateStatus status;
    private Boolean isDuplicate;
}

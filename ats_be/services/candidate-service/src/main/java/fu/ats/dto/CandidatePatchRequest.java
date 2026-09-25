package fu.ats.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidatePatchRequest {
    @Pattern(regexp = ".*\\S.*", message = "Full name must not be blank")
    private String fullName;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Email is not valid format")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number is not valid format")
    private String phone;

    @Size(max = 150)
    private String source;

    @Size(max = 150)
    private String utmSource;

    @Size(max = 150)
    private String utmMedium;

    @Size(max = 255)
    private String utmCampaign;

    public boolean hasNoValues() {
        return fullName == null
                && email == null
                && phone == null
                && source == null
                && utmSource == null
                && utmMedium == null
                && utmCampaign == null;
    }
}

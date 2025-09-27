package com.example.onboarding.integration.model.ekyc;

import lombok.Data;

@Data
public class TopMatchesRetrivalResponse {
    private float match_score;
    private String person_id;
    private String full_name;
    private String dob;
    private String id_number;
    private String photo_url;
    private String match_reason;
}

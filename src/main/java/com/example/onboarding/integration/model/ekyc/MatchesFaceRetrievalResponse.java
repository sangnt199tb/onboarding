package com.example.onboarding.integration.model.ekyc;

import lombok.Data;

@Data
public class MatchesFaceRetrievalResponse {
    private Double match_score;
    private String person_id;
    private String full_name;
    private String dob;
    private IdCardResponse id_card;
    private String photo_url;
    private String similarity_method;
    private String matched_at;
    private MetaFaceRetrievalResponse meta;
}

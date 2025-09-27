package com.example.onboarding.integration.model.ekyc;

import lombok.Data;

@Data
public class FaceRetrievalResponse {
    private String vector_query_id;
    private QueryFaceResponse query_face;
    private RetrievalEngineResponse retrieval_engine;
    private ResultsFaceRetrievalResponse results;
    private SummaryFaceRetrievalResponse summary;
}

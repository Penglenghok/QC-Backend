package backend.quadcount.api;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ApiResponse {

    private Instant timestamp;
    private int     status;
    private String  message;
    private Object  data;     // keep it Object to stay flexible
}
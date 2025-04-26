package backend.quadcount.api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

public final class ResponseUtil {

    private ResponseUtil() {}

    /* ---------- success wrappers ---------- */

    public static ResponseEntity<ApiResponse> ok(Object body) {
        return of(body, HttpStatus.OK, "OK");
    }

    public static ResponseEntity<ApiResponse> created(Object body) {
        return of(body, HttpStatus.CREATED, "Created");
    }

    /* ---------- error wrappers ---------- */

    public static ResponseEntity<ApiResponse> error(HttpStatus status, String msg) {
        return ResponseEntity.status(status).body(
                ApiResponse.builder()
                        .timestamp(Instant.now())
                        .status(status.value())
                        .message(msg)
                        .data(null)
                        .build()
        );
    }

    /* ---------- internal helper ---------- */

    private static ResponseEntity<ApiResponse> of(Object body,
                                                  HttpStatus status,
                                                  String msg) {
        return ResponseEntity.status(status).body(
                ApiResponse.builder()
                        .timestamp(Instant.now())
                        .status(status.value())
                        .message(msg)
                        .data(body)
                        .build()
        );
    }
}

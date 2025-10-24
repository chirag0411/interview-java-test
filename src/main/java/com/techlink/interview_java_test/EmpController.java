package com.techlink.interview_java_test;

import com.techlink.interview_java_test.dto.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class EmpController {

    private final RemoteApiClient remoteApiClient;

    @GetMapping("/getEmpList")
    public ResponseEntity<List<Employee>> getEmpList() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/getEmpList";
        restTemplate.getForObject(url, String.class);

        String response = remoteApiClient.synchronousGet("/v1/hello", String.class);
        System.out.println("Response: " + response);

        remoteApiClient.asynchronousGet("/v1/hello", String.class)
                .doOnNext(body -> System.out.println("Response: " + body))
                .doOnError(err -> System.err.println("Error: " + err.getMessage()))
                .subscribe();   // triggers the call


        String strResponse = SimpleWebClientExample.get("/v1/hello", String.class);
        System.out.println("Response: " + strResponse);


        return ResponseEntity.ok(null);
    }
}

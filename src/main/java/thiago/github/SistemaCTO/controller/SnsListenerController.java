package thiago.github.SistemaCTO.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import thiago.github.SistemaCTO.dtos.sns.SensorEventDTO;
import thiago.github.SistemaCTO.service.SensorService;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/sns")
public class SnsListenerController {

    private final SensorService sensorService;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    public SnsListenerController(SensorService sensorService, ObjectMapper objectMapper) {
        this.sensorService = sensorService;
        this.objectMapper = objectMapper;
    }
    @Operation(
            summary = "Receiving sensor events via SNS.",
            description = "Internal endpoint used by AWS SNS (LocalStack) to notify sensor events. Should not be consumed directly."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event received successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid payload."),
            @ApiResponse(responseCode = "500", description = "Internal error server.")
    })

    @PostMapping("/events")
    public ResponseEntity<Void> receive(@RequestBody String payload) {

        System.out.println("===== SNS PAYLOAD =====");
        System.out.println(payload);

        JsonNode root = objectMapper.readTree(payload);
        String type = root.get("Type").asText();

        if ("SubscriptionConfirmation".equals(type)) {

            String subscribeUrl = root.get("SubscribeURL").asText();
            restTemplate.getForObject(subscribeUrl, String.class);

            System.out.println("SNS subscription confirmed automatically");
            return ResponseEntity.ok().build();
        }

        if ("Notification".equals(type)) {

            String message = root.get("Message").asText();

            System.out.println("===== SNS MESSAGE =====");
            System.out.println(message);

            SensorEventDTO dto =
                    objectMapper.readValue(message, SensorEventDTO.class);

            sensorService.processEvent(dto);
        }

        return ResponseEntity.ok().build();
    }
}

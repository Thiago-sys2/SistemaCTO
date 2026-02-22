package thiago.github.SistemaCTO.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thiago.github.SistemaCTO.dtos.sensor.SensorCreateDTO;
import thiago.github.SistemaCTO.dtos.sensor.SensorResponseDTO;
import thiago.github.SistemaCTO.service.SensorService;

import java.util.List;

@RestController
@RequestMapping("/sensors")
@AllArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @Operation(
            summary = "Create a sensor in the database.",
            description = "Endpoint responsible for calling the method to create a sensor in the database."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sensor created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid data in the request body."),
            @ApiResponse(responseCode = "404", description = "CTO not found."),
            @ApiResponse(responseCode = "500", description = "Internal error server.")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody SensorCreateDTO dto) {
        sensorService.create(dto);
    }

    @Operation(
            summary = "Search for the sensor with the CTO.",
            description = "It searches for a sensor that was created and the CTO that was created, and the relationship between them."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sensors listed successfully."),
            @ApiResponse(responseCode = "404", description = "CTO not found."),
            @ApiResponse(responseCode = "500", description = "Internal error server.")
    })
    @GetMapping("/cto/{ctoId}")
    public List<SensorResponseDTO> findAllByCto(@PathVariable Long ctoId) {
        return sensorService.findAll(ctoId);
    }
}

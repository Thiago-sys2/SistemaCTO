package thiago.github.SistemaCTO.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thiago.github.SistemaCTO.dtos.cto.CtoCreateDTO;
import thiago.github.SistemaCTO.dtos.cto.CtoResponseDTO;
import thiago.github.SistemaCTO.dtos.cto.CtoUpdateDTO;
import thiago.github.SistemaCTO.service.CtoService;

import java.util.List;

@RestController
@RequestMapping("/ctos")
@AllArgsConstructor
public class CtoController {

    private final CtoService ctoService;

    @Operation(
            summary = "Create a CTO",
            description = "This endpoint is responsible for calling the method to create a CTO in the database."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid data."),
            @ApiResponse(responseCode = "409", description = "The name CTO already exists."),
            @ApiResponse(responseCode = "500", description = "Internal error.")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CtoResponseDTO createCto(@RequestBody CtoCreateDTO dto) {
        return ctoService.createCto(dto);
    }

    @Operation(
            summary = "Update a CTO",
            description = "Endpoint responsible for calling the method to update a specific CTO by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid data."),
            @ApiResponse(responseCode = "404", description = "CTO Not found."),
            @ApiResponse(responseCode = "409", description = "No duplicate names allowed."),
            @ApiResponse(responseCode = "500", description = "Internal error.")
    })
    @PutMapping("/{ctoId}")
    public CtoResponseDTO updateCto(@PathVariable Long ctoId,
                                    @RequestBody CtoUpdateDTO dto) {

        return ctoService.updateCto(ctoId, dto);
    }

    @Operation(
            summary = "Search all CTOs in the system.",
            description = "Search all CTOs in the system."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search completed successfully"),
            @ApiResponse(responseCode = "500", description = "Internal error.")
    })
    @GetMapping
    public List<CtoResponseDTO> findAll() {
        return ctoService.findAll();
    }

    @Operation(
            summary = "Search for a CTO",
            description = "Endpoint responsible for retrieving a specific CTO from the database by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search completed successfully"),
            @ApiResponse(responseCode = "404", description = "CTO not found."),
            @ApiResponse(responseCode = "500", description = "Internal error.")
    })
    @GetMapping("/{ctoId}")
    public CtoResponseDTO findById(@PathVariable Long ctoId) {
        return ctoService.findById(ctoId);
    }

    @Operation(
            summary = "Delete a CTO",
            description = "Endpoint responsible for deleting a CTO from the database by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deleted successfully."),
            @ApiResponse(responseCode = "404", description = "CTO Not found."),
            @ApiResponse(responseCode = "409", description = "CTO possui sensor com status ALARMED."),
            @ApiResponse(responseCode = "500", description = "Internal error.")
    })
    @DeleteMapping("/{ctoId}")
    public ResponseEntity<Void> deleteCto(@PathVariable Long ctoId) {
        ctoService.delete(ctoId);
        return ResponseEntity.noContent().build();
    }
}

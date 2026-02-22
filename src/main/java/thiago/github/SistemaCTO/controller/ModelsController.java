package thiago.github.SistemaCTO.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thiago.github.SistemaCTO.dtos.model.ModelsCreateDTO;
import thiago.github.SistemaCTO.dtos.model.ModelsResponseDTO;
import thiago.github.SistemaCTO.dtos.model.ModelsUpdateDTO;
import thiago.github.SistemaCTO.service.ModelsService;

import java.util.List;

@RestController
@RequestMapping("/models")
@AllArgsConstructor
public class ModelsController {

    private final ModelsService modelsService;

    @Operation(
            summary = "Create a model",
            description = "Endpoint responsible for calling the method to create a Model in the database."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Model created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data in the request body."),
            @ApiResponse(responseCode = "409", description = "There is already a model with the same name and manufacturer."),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ModelsResponseDTO create(@RequestBody ModelsCreateDTO dto) {
        return modelsService.create(dto);
    }
    @Operation(
            summary = "Update a model in the database.",
            description = "Endpoint responsible for calling the method to update a model by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Model created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data in the request body."),
            @ApiResponse(responseCode = "404", description = "Model not found"),
            @ApiResponse(responseCode = "500", description = "Internal error server.")
    })
    @PutMapping("/{id}")
    public ModelsResponseDTO update(@PathVariable Long id,
                                    @RequestBody ModelsUpdateDTO dto) {

        return modelsService.update(id, dto);
    }
    @Operation(
            summary = "List all models",
            description = "Endpoint responsible for calling the method to list all models in the database."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of models returned successfully."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping
    public List<ModelsResponseDTO> findAll() {
        return modelsService.findAll();
    }

    @Operation(
            summary = "Search for a model by its ID.",
            description = "Endpoint for calling the method to search for a model by ID in the database."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Model found."),
            @ApiResponse(responseCode = "404", description = "Model not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/{id}")
    public ModelsResponseDTO findById(@PathVariable Long id) {
        return modelsService.findById(id);
    }

    @Operation(
            summary = "Delete a model from the database.",
            description = "Endpoint responsible for calling the method to delete a model by its id."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Model removed successfully"),
            @ApiResponse(responseCode = "404", description = "Model not found."),
            @ApiResponse(responseCode = "409", description = "A model associated with a CTO cannot be removed."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        modelsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package com.spring.hasdoctime.controller;

import com.spring.hasdoctime.entity.PostAppointmentData;
import com.spring.hasdoctime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdoctime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdoctime.interfaces.PostAppointmentDataInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * The PostAppointmentDataController class handles HTTP requests related to the PostAppointmentData entity.
 */
@RestController
@RequestMapping("postAppointmentData")
@CrossOrigin(origins = "${port.address}")
public class PostAppointmentDataController {

    private PostAppointmentDataInterface postAppointmentDataService;

    @Autowired
    public PostAppointmentDataController(@Qualifier("postAppointmentDataServiceImpl") PostAppointmentDataInterface postAppointmentDataService){
        this.postAppointmentDataService = postAppointmentDataService;
    }

    /**
     * Retrieves all post-appointment data.
     *
     * @return A ResponseEntity containing the list of all post-appointment data.
     */
    @GetMapping("")
    public ResponseEntity<Page<PostAppointmentData>> getAllPostAppointmentData(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "user.name") String sortBy,
            @RequestParam(required = false) String search
    ) {
            Page<PostAppointmentData> allPostAppointmentData = postAppointmentDataService.getAllPostAppointmentData(page, size, sortBy, search);
            if(allPostAppointmentData.isEmpty()){
                return new ResponseEntity<>(allPostAppointmentData, HttpStatus.NO_CONTENT);
            }
            return ResponseEntity.ok(allPostAppointmentData);
    }

    /**
     * Retrieves a post-appointment data by ID.
     *
     * @param id The ID of the post-appointment data to retrieve.
     * @return A ResponseEntity containing the retrieved post-appointment data.
     * @throws DoesNotExistException If the post-appointment data with the given ID does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostAppointmentData> getPostAppointmentDataById(@PathVariable int id) throws DoesNotExistException{
        PostAppointmentData postAppointmentData = postAppointmentDataService.getPostAppointmentDataById(id);
        if(postAppointmentData==null){
            return new ResponseEntity<>(postAppointmentData, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(postAppointmentData);
    }

    /**
     * Retrieves post-appointment data by user email.
     *
     * @return A ResponseEntity containing the list of post-appointment data associated with the user email.
     */
    @GetMapping("findByUserEmail")
    public ResponseEntity<Page<PostAppointmentData>> getPostAppointmentDataByEmail(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "user.name") String sortBy,
            @RequestParam(required = false) String search
    ) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<PostAppointmentData> allPostAppointmentData = postAppointmentDataService.getPostAppointmentDataByEmail(userEmail, page, size, sortBy, search);
        if(allPostAppointmentData.getContent().isEmpty()) {
            return new ResponseEntity<>(allPostAppointmentData, HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(allPostAppointmentData);
    }

    /**
     * Creates a new post-appointment data.
     *
     * @param postAppointmentData The post-appointment data object to create.
     * @return The created post-appointment data.
     * @throws MissingParameterException If required parameters are missing in the creation request.
     * @throws DoesNotExistException     If the post-appointment data with the given ID does not exist.
     */
    @PostMapping
    public PostAppointmentData createPostAppointmentData(@Valid @RequestBody PostAppointmentData postAppointmentData) throws MissingParameterException, DoesNotExistException {
        return postAppointmentDataService.createPostAppointmentData(postAppointmentData);
    }

    /**
     * Updates a post-appointment data.
     *
     * @param id                  The ID of the post-appointment data to update.
     * @param postAppointmentData The updated post-appointment data object.
     * @return A ResponseEntity containing the updated post-appointment data.
     * @throws DoesNotExistException     If the post-appointment data with the given ID does not exist.
     * @throws MissingParameterException If required parameters are missing in the update request.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PostAppointmentData> updatePostAppointmentData(@PathVariable int id, @Valid @RequestBody PostAppointmentData postAppointmentData) throws DoesNotExistException, MissingParameterException {
        try {
            PostAppointmentData updatedPostAppointmentData = postAppointmentDataService.updatePostAppointmentData(id, postAppointmentData);
            return ResponseEntity.ok(updatedPostAppointmentData);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a post-appointment data.
     *
     * @param id The ID of the post-appointment data to delete.
     * @return A ResponseEntity containing the result of the delete operation.
     * @throws DoesNotExistException If the post-appointment data with the given ID does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable int id) throws DoesNotExistException{
        String result = postAppointmentDataService.deletePostAppointmentData(id);
        if (result.equals("postAppointmentData with id: " + id + " is deleted")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves diseases grouped by symptom.
     *
     * @param symptom The symptom to filter the diseases.
     * @return A ResponseEntity containing the list of diseases grouped by symptom.
     * @throws DoesNotExistException If the symptom does not exist.
     */
    @GetMapping("diseaseList/{symptom}")
    public ResponseEntity<List<Map<String, Integer>>> getDiseasesListBySymptom(@PathVariable String symptom) throws DoesNotExistException{
        List<Map<String, Integer>> diseaseData = postAppointmentDataService.getDiseaseListGroupedBySymptom(symptom);
        if(diseaseData.isEmpty()){
            return new ResponseEntity<>(diseaseData, HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(diseaseData);
    }

    /**
     * Retrieves post-appointment data by symptom.
     *
     * @param symptom The symptom to filter the post-appointment data.
     * @return A ResponseEntity containing the list of post-appointment data matching the symptom.
     * @throws DoesNotExistException If the symptom does not exist.
     */
    @GetMapping("data/{symptom}")
    public ResponseEntity<Page<PostAppointmentData>> getPostAppointmentDataBySymptom(
            @PathVariable String symptom,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "user.name") String sortBy,
            @RequestParam(required = false) String search
    ) throws DoesNotExistException {
        Page<PostAppointmentData> appointmentData = postAppointmentDataService.getPostAppointmentDataBySymptom(symptom, page, size, sortBy, search);
        if(appointmentData.isEmpty()){
            return new ResponseEntity<>(appointmentData, HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(appointmentData);
    }

    /**
     * Retrieves post-appointment data of a doctor.
     *
     * @param id The ID of the doctor.
     * @return A ResponseEntity containing the list of post-appointment data of the doctor.
     * @throws DoesNotExistException If the doctor with the given ID does not exist.
     */
    @GetMapping("/doctor/{id}")
    public ResponseEntity<Page<PostAppointmentData>> getPostAppointmentDataOfDoctor(
            @PathVariable int id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "user.name") String sortBy,
            @RequestParam(required = false) String search
    ) throws DoesNotExistException{
        Page<PostAppointmentData> postAppointmentData = postAppointmentDataService.getPostAppointmentsDataOfDoctor(id, page, size, sortBy, search);
        return new ResponseEntity<>(postAppointmentData, HttpStatus.OK);
    }

    /**
     * Retrieves post-appointment data by user ID.
     *
     * @param id The ID of the user.
     * @return A ResponseEntity containing the list of post-appointment data associated with the user ID.
     * @throws DoesNotExistException If the user with the given ID does not exist.
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<Page<PostAppointmentData>> getPostAppointmentDataByUserId(
            @PathVariable int id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "doctor.user.name") String sortBy,
            @RequestParam(required = false) String search
    ) throws DoesNotExistException {
        Page<PostAppointmentData> postAppointmentDataList = postAppointmentDataService.getPostAppointmentDataByUserId(id, page, size, sortBy, search);
        return new ResponseEntity<>(postAppointmentDataList, HttpStatus.OK);
    }
}

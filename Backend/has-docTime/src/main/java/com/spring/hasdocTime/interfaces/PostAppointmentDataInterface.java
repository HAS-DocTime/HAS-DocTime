/**
 * Interface defining operations for post appointment data.
 */
package com.spring.hasdocTime.interfaces;

import com.spring.hasdocTime.entity.PostAppointmentData;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;

import java.util.List;
import java.util.Map;

public interface PostAppointmentDataInterface {

    /**
     * Retrieves a list of all post appointment data.
     *
     * @return a list of PostAppointmentData objects representing all post appointment data
     */
    List<PostAppointmentData> getAllPostAppointmentData();

    /**
     * Retrieves the post appointment data with the specified ID.
     *
     * @param id the ID of the post appointment data
     * @return the PostAppointmentData object representing the post appointment data
     * @throws DoesNotExistException if the post appointment data does not exist
     */
    PostAppointmentData getPostAppointmentDataById(int id) throws DoesNotExistException;

    /**
     * Creates a new post appointment data.
     *
     * @param postAppointmentData the PostAppointmentData object representing the post appointment data to be created
     * @return the created PostAppointmentData object
     * @throws MissingParameterException if required parameters are missing
     * @throws DoesNotExistException    if related entities do not exist
     */
    PostAppointmentData createPostAppointmentData(PostAppointmentData postAppointmentData) throws MissingParameterException, DoesNotExistException;

    /**
     * Retrieves a list of post appointment data based on the specified email.
     *
     * @param email the email address
     * @return a list of PostAppointmentData objects matching the email
     */
    List<PostAppointmentData> getPostAppointmentDataByEmail(String email);

    /**
     * Updates the post appointment data with the specified ID.
     *
     * @param id                  the ID of the post appointment data
     * @param postAppointmentData the updated PostAppointmentData object
     * @return the updated PostAppointmentData object
     * @throws DoesNotExistException    if the post appointment data does not exist
     * @throws MissingParameterException if required parameters are missing
     */
    PostAppointmentData updatePostAppointmentData(int id, PostAppointmentData postAppointmentData) throws DoesNotExistException, MissingParameterException;

    /**
     * Deletes the post appointment data with the specified ID.
     *
     * @param id the ID of the post appointment data
     * @return a success message indicating the deletion
     * @throws DoesNotExistException if the post appointment data does not exist
     */
    String deletePostAppointmentData(int id) throws DoesNotExistException;

    /**
     * Retrieves a list of diseases grouped by the specified symptom.
     *
     * @param symptom the symptom
     * @return a list of maps containing the disease names and their occurrence count
     * @throws DoesNotExistException if no data is found for the specified symptom
     */
    List<Map<String, Integer>> getDiseasesGroupedBySymptom(String symptom) throws DoesNotExistException;

    /**
     * Retrieves a list of post appointment data based on the specified symptom.
     *
     * @param symptom the symptom
     * @return a list of PostAppointmentData objects matching the symptom
     * @throws DoesNotExistException if no data is found for the specified symptom
     */
    List<PostAppointmentData> getPostAppointmentDataBySymptom(String symptom) throws DoesNotExistException;

    /**
     * Retrieves a list of post appointment data of a specific doctor.
     *
     * @param id the ID of the doctor
     * @return a list of PostAppointmentData objects associated with the doctor
     * @throws DoesNotExistException if the doctor does not exist
     */
    List<PostAppointmentData> getPostAppointmentsDataOfDoctor(int id) throws DoesNotExistException;

    /**
     * Retrieves a list of post appointment data based on the specified user ID.
     *
     * @param id the ID of the user
     * @return a list of PostAppointmentData objects associated with the user
     * @throws DoesNotExistException if the user does not exist
     */
    List<PostAppointmentData> getPostAppointmentDataByUserId(int id) throws DoesNotExistException;
}

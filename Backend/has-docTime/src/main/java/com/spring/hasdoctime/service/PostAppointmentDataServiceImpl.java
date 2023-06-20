package com.spring.hasdoctime.service;

import com.spring.hasdoctime.entity.PostAppointmentData;
import com.spring.hasdoctime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdoctime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdoctime.interfaces.PostAppointmentDataInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Implementation of the PostAppointmentDataInterface for handling post-appointment data functionality.
 */
@Service
public class PostAppointmentDataServiceImpl implements PostAppointmentDataInterface {

    private PostAppointmentDataInterface postAppointmentDataDao;

    @Autowired
    public PostAppointmentDataServiceImpl(@Qualifier("postAppointmentDataDaoImpl") PostAppointmentDataInterface postAppointmentDataDao){
        this.postAppointmentDataDao = postAppointmentDataDao;
    }

    /**
     * Retrieves all post-appointment data.
     *
     * @return The list of post-appointment data.
     */
    @Override
    public Page<PostAppointmentData> getAllPostAppointmentData(int page, int size, String sortBy, String search) {
        return postAppointmentDataDao.getAllPostAppointmentData(page, size, sortBy, search);
    }

    /**
     * Retrieves post-appointment data by ID.
     *
     * @param id The ID of the post-appointment data.
     * @return The post-appointment data.
     * @throws DoesNotExistException If the post-appointment data does not exist.
     */
    @Override
    public PostAppointmentData getPostAppointmentDataById(int id) throws DoesNotExistException {
        return postAppointmentDataDao.getPostAppointmentDataById(id);
    }

    /**
     * Retrieves post-appointment data by email.
     *
     * @param email The email to search for.
     * @return The list of post-appointment data.
     */
    @Override
    public Page<PostAppointmentData> getPostAppointmentDataByEmail(String email, int page, int size, String sortBy, String search) {
        return postAppointmentDataDao.getPostAppointmentDataByEmail(email, page, size, sortBy, search);
    }


    /**
     * Creates post-appointment data.
     *
     * @param postAppointmentData The post-appointment data to create.
     * @return The created post-appointment data.
     * @throws MissingParameterException If a required parameter is missing.
     * @throws DoesNotExistException     If the post-appointment data does not exist.
     */
    @Override
    public PostAppointmentData createPostAppointmentData(PostAppointmentData postAppointmentData) throws MissingParameterException, DoesNotExistException {
        return postAppointmentDataDao.createPostAppointmentData(postAppointmentData);
    }

    /**
     * Updates post-appointment data.
     *
     * @param id                  The ID of the post-appointment data to update.
     * @param postAppointmentData The updated post-appointment data.
     * @return The updated post-appointment data.
     * @throws DoesNotExistException     If the post-appointment data does not exist.
     * @throws MissingParameterException If a required parameter is missing.
     */
    @Override
    public PostAppointmentData updatePostAppointmentData(int id, PostAppointmentData postAppointmentData) throws DoesNotExistException, MissingParameterException {
        return postAppointmentDataDao.updatePostAppointmentData(id, postAppointmentData);
    }

    /**
     * Deletes post-appointment data.
     *
     * @param id The ID of the post-appointment data to delete.
     * @return The deletion status message.
     * @throws DoesNotExistException If the post-appointment data does not exist.
     */
    @Override
    public String deletePostAppointmentData(int id) throws DoesNotExistException {
        return postAppointmentDataDao.deletePostAppointmentData(id);
    }

    /**
     * Retrieves diseases grouped by symptom.
     *
     * @param symptom The symptom to search for.
     * @return The list of diseases grouped by symptom.
     * @throws DoesNotExistException If the data does not exist.
     */
    @Override
    public List<Map<String, Integer>> getDiseaseListGroupedBySymptom(String symptom) throws DoesNotExistException {
        return postAppointmentDataDao.getDiseaseListGroupedBySymptom(symptom);
    }

    /**
     * Retrieves post-appointment data by symptom.
     *
     * @param symptom The symptom to search for.
     * @return The list of post-appointment data.
     * @throws DoesNotExistException If the data does not exist.
     */
    @Override
    public Page<PostAppointmentData> getPostAppointmentDataBySymptom(String symptom, int page, int size, String sortBy, String search) throws DoesNotExistException {
        return postAppointmentDataDao.getPostAppointmentDataBySymptom(symptom, page, size, sortBy, search);
    }

    /**
     * Retrieves post-appointment data of a specific doctor.
     *
     * @param id The ID of the doctor.
     * @return The list of post-appointment data.
     * @throws DoesNotExistException If the data does not exist.
     */
    public Page<PostAppointmentData> getPostAppointmentsDataOfDoctor(int id, int page, int size, String sortBy, String search) throws DoesNotExistException{
        Page<PostAppointmentData> postAppointmentData = postAppointmentDataDao.getPostAppointmentsDataOfDoctor(id, page, size, sortBy, search);
        return postAppointmentData;
    }

    /**
     * Retrieves post-appointment data by user ID.
     *
     * @param id The ID of the user.
     * @return The list of post-appointment data.
     * @throws DoesNotExistException If the data does not exist.
     */
    @Override
    public Page<PostAppointmentData> getPostAppointmentDataByUserId(int id, int page, int size, String sortBy, String search) throws DoesNotExistException {
        Page<PostAppointmentData> postAppointmentData =  postAppointmentDataDao.getPostAppointmentDataByUserId(id, page, size, sortBy, search);
        return postAppointmentData;
    }
}

package com.spring.hasdocTime.service;

import com.spring.hasdocTime.entity.PostAppointmentData;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.PostAppointmentDataInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PostAppointmentDataServiceImpl implements PostAppointmentDataInterface {

    private PostAppointmentDataInterface postAppointmentDataDao;

    @Autowired
    public PostAppointmentDataServiceImpl(@Qualifier("postAppointmentDataDaoImpl") PostAppointmentDataInterface postAppointmentDataDao){
        this.postAppointmentDataDao = postAppointmentDataDao;
    }

    @Override
    public Page<PostAppointmentData> getAllPostAppointmentData(int page, int size, String sortBy, String search) {
        return postAppointmentDataDao.getAllPostAppointmentData(page, size, sortBy, search);
    }

    @Override
    public PostAppointmentData getPostAppointmentDataById(int id) throws DoesNotExistException {
        return postAppointmentDataDao.getPostAppointmentDataById(id);
    }

    @Override
    public Page<PostAppointmentData> getPostAppointmentDataByEmail(String email, int page, int size, String sortBy, String search) {
        return postAppointmentDataDao.getPostAppointmentDataByEmail(email, page, size, sortBy, search);
    }

    @Override
    public PostAppointmentData createPostAppointmentData(PostAppointmentData postAppointmentData) throws MissingParameterException, DoesNotExistException {
        return postAppointmentDataDao.createPostAppointmentData(postAppointmentData);
    }

    @Override
    public PostAppointmentData updatePostAppointmentData(int id, PostAppointmentData postAppointmentData) throws DoesNotExistException, MissingParameterException {
        return postAppointmentDataDao.updatePostAppointmentData(id, postAppointmentData);
    }

    @Override
    public String deletePostAppointmentData(int id) throws DoesNotExistException{
        return postAppointmentDataDao.deletePostAppointmentData(id);
    }

    @Override
    public List<Map<String, Integer>> getDiseaseListGroupedBySymptom(String symptom) throws DoesNotExistException {
        return postAppointmentDataDao.getDiseaseListGroupedBySymptom(symptom);
    }

    @Override
    public Page<PostAppointmentData> getPostAppointmentDataBySymptom(String symptom, int page, int size, String sortBy, String search) throws DoesNotExistException {
        return postAppointmentDataDao.getPostAppointmentDataBySymptom(symptom, page, size, sortBy, search);
    }
    public Page<PostAppointmentData> getPostAppointmentsDataOfDoctor(int id, int page, int size, String sortBy, String search) throws DoesNotExistException{
        Page<PostAppointmentData> postAppointmentData = postAppointmentDataDao.getPostAppointmentsDataOfDoctor(id, page, size, sortBy, search);
        return postAppointmentData;
    }

    @Override
    public Page<PostAppointmentData> getPostAppointmentDataByUserId(int id, int page, int size, String sortBy, String search) throws DoesNotExistException {
        Page<PostAppointmentData> postAppointmentData =  postAppointmentDataDao.getPostAppointmentDataByUserId(id, page, size, sortBy, search);
        return postAppointmentData;
    }
}

package com.spring.hasdocTime.service;

import com.spring.hasdocTime.entity.PostAppointmentData;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdocTime.interfaces.PostAppointmentDataInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public List<PostAppointmentData> getAllPostAppointmentData() {
        return postAppointmentDataDao.getAllPostAppointmentData();
    }

    @Override
    public PostAppointmentData getPostAppointmentDataById(int id) throws DoesNotExistException {
        return postAppointmentDataDao.getPostAppointmentDataById(id);
    }

    @Override
    public List<PostAppointmentData> getPostAppointmentDataByEmail(String email) {
        return postAppointmentDataDao.getPostAppointmentDataByEmail(email);
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
    public List<Map<String, Integer>> getDiseasesGroupedBySymptom(String symptom) throws DoesNotExistException {
        return postAppointmentDataDao.getDiseasesGroupedBySymptom(symptom);
    }

    @Override
    public List<PostAppointmentData> getPostAppointmentDataBySymptom(String symptom) throws DoesNotExistException {
        return postAppointmentDataDao.getPostAppointmentDataBySymptom(symptom
        );
    }
    public List<PostAppointmentData> getPostAppointmentsDataOfDoctor(int id) throws DoesNotExistException{
        return postAppointmentDataDao.getPostAppointmentsDataOfDoctor(id);
    }

    @Override
    public List<PostAppointmentData> getPostAppointmentDataByUserId(int id) throws DoesNotExistException {
        return postAppointmentDataDao.getPostAppointmentDataByUserId(id);
    }
}

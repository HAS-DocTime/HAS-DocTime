package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.PostAppointmentData;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface PostAppointmentDataInterface {

    public Page<PostAppointmentData> getAllPostAppointmentData(int page, int size, String sortBy, String search);

    public PostAppointmentData getPostAppointmentDataById(int id) throws DoesNotExistException;

    public PostAppointmentData createPostAppointmentData(PostAppointmentData postAppointmentData) throws MissingParameterException, DoesNotExistException;
    public Page<PostAppointmentData> getPostAppointmentDataByEmail(String email, int page, int size, String sortBy, String search);

    public PostAppointmentData updatePostAppointmentData(int id, PostAppointmentData postAppointmentData) throws DoesNotExistException, MissingParameterException;

    public String deletePostAppointmentData(int id) throws DoesNotExistException;

    public Page<Map<String, Integer>> getDiseasesGroupedBySymptom(String symptom, int page, int size, String sortBy, String search) throws DoesNotExistException;

    public Page<PostAppointmentData> getPostAppointmentDataBySymptom(String symptom, int page, int size, String sortBy, String search) throws DoesNotExistException;

    public Page<PostAppointmentData> getPostAppointmentsDataOfDoctor(int id, int page, int size, String sortBy, String search) throws DoesNotExistException;

    public Page<PostAppointmentData> getPostAppointmentDataByUserId(int id, int page, int size, String sortBy, String search) throws DoesNotExistException;
}

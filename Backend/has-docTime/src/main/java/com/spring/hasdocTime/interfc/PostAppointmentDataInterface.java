package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.PostAppointmentData;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;

import java.util.List;

public interface PostAppointmentDataInterface {

    public List<PostAppointmentData> getAllPostAppointmentData();

    public PostAppointmentData getPostAppointmentDataById(int id) throws DoesNotExistException;

    public PostAppointmentData createPostAppointmentData(PostAppointmentData postAppointmentData) throws MissingParameterException, DoesNotExistException;
    public List<PostAppointmentData> getPostAppointmentDataByEmail(String email);

    public PostAppointmentData updatePostAppointmentData(int id, PostAppointmentData postAppointmentData) throws DoesNotExistException, MissingParameterException;

    public String deletePostAppointmentData(int id) throws DoesNotExistException;

    public List<PostAppointmentData> getPostAppointmentsDataOfDoctor(int id) throws DoesNotExistException;

}

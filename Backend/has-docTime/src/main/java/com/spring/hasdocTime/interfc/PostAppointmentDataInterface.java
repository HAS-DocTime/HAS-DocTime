package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.PostAppointmentData;
import com.spring.hasdocTime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdocTime.exceptionHandling.exception.MissingParameterException;

import java.util.List;

public interface PostAppointmentDataInterface {

    public List<PostAppointmentData> getAllPostAppointmentData();

    public PostAppointmentData getPostAppointmentDataById(int id) throws DoesNotExistException;

    public PostAppointmentData createPostAppointmentData(PostAppointmentData postAppointmentData) throws MissingParameterException;

    public PostAppointmentData updatePostAppointmentData(int id, PostAppointmentData postAppointmentData) throws DoesNotExistException, MissingParameterException;

    public String deletePostAppointmentData(int id) throws DoesNotExistException;

}

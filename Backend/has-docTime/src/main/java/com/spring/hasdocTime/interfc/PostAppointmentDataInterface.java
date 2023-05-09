package com.spring.hasdocTime.interfc;

import com.spring.hasdocTime.entity.PostAppointmentData;

import java.util.List;

public interface PostAppointmentDataInterface {

    public List<PostAppointmentData> getAllPostAppointmentData();

    public PostAppointmentData getPostAppointmentDataById(int id);

    public List<PostAppointmentData> getPostAppointmentDataByEmail(String email);

    public PostAppointmentData createPostAppointmentData(PostAppointmentData postAppointmentData);

    public PostAppointmentData updatePostAppointmentData(int id, PostAppointmentData postAppointmentData);

    public String deletePostAppointmentData(int id);

}

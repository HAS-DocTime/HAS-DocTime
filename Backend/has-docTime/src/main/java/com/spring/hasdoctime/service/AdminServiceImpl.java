package com.spring.hasdoctime.service;

import com.spring.hasdoctime.exceptionHandling.exception.DoesNotExistException;
import com.spring.hasdoctime.exceptionHandling.exception.MissingParameterException;
import com.spring.hasdoctime.interfaces.AdminInterface;
import com.spring.hasdoctime.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link AdminInterface} interface that provides the business logic for managing admin entities.
 */
@Service
public class AdminServiceImpl implements AdminInterface{

    @Autowired
    @Qualifier("adminDaoImpl")
    private AdminInterface adminDao;

    /**
     * Retrieves all admin entities.
     *
     * @return List of admin entities.
     */
    @Override
    public List<Admin> getAllAdmin(){
        return adminDao.getAllAdmin();
    }

    /**
     * Retrieves an admin entity by its ID.
     *
     * @param id The ID of the admin entity.
     * @return The admin entity.
     * @throws DoesNotExistException If the admin entity does not exist.
     */
    @Override
    public Admin getAdmin(int id) throws DoesNotExistException {
        return adminDao.getAdmin(id);
    }

    /**
     * Updates an admin entity with the given ID.
     *
     * @param id    The ID of the admin entity to update.
     * @param admin The updated admin entity.
     * @return The updated admin entity.
     * @throws DoesNotExistException    If the admin entity does not exist.
     * @throws MissingParameterException If a required parameter is missing in the updated admin entity.
     */
    @Override
    public Admin updateAdmin(int id, Admin admin) throws DoesNotExistException, MissingParameterException{
        return adminDao.updateAdmin(id, admin);
    }

    /**
     * Deletes an admin entity by its ID.
     *
     * @param id The ID of the admin entity to delete.
     * @return {@code true} if the admin entity was successfully deleted, {@code false} otherwise.
     * @throws DoesNotExistException If the admin entity does not exist.
     */
    @Override
    public boolean deleteAdmin(int id) throws DoesNotExistException {
        return adminDao.deleteAdmin(id);
    }

    /**
     * Creates a new admin entity.
     *
     * @param admin The admin entity to create.
     * @return The created admin entity.
     * @throws MissingParameterException If a required parameter is missing in the new admin entity.
     * @throws DoesNotExistException    If the admin entity does not exist.
     */
    @Override
    public Admin createAdmin(Admin admin) throws MissingParameterException, DoesNotExistException{
        return adminDao.createAdmin(admin);
    }
}

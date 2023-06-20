/**
 * Interface defining operations for managing admin entities.
 */
package com.spring.hasdoctime.interfaces;

import com.spring.hasdoctime.entity.Admin;
import com.spring.hasdoctime.exceptionhandling.exception.DoesNotExistException;
import com.spring.hasdoctime.exceptionhandling.exception.MissingParameterException;

import java.util.List;

public interface AdminInterface {

    /**
     * Retrieves a list of all admin entities.
     *
     * @return List of Admin objects representing all admin entities.
     */
    public List<Admin> getAllAdmin();

    /**
     * Retrieves the admin entity with the specified ID.
     *
     * @param id the ID of the admin entity to retrieve
     * @return the Admin object representing the admin entity
     * @throws DoesNotExistException if the admin entity does not exist
     */
    public Admin getAdmin(int id) throws DoesNotExistException;

    /**
     * Updates the admin entity with the specified ID.
     *
     * @param id    the ID of the admin entity to update
     * @param admin the updated Admin object
     * @return the updated Admin object
     * @throws DoesNotExistException     if the admin entity does not exist
     * @throws MissingParameterException if there are missing parameters in the admin object
     */
    public Admin updateAdmin(int id, Admin admin) throws DoesNotExistException, MissingParameterException;

    /**
     * Deletes the admin entity with the specified ID.
     *
     * @param id the ID of the admin entity to delete
     * @return true if the admin entity is successfully deleted, false otherwise
     * @throws DoesNotExistException if the admin entity does not exist
     */
    public boolean deleteAdmin(int id) throws DoesNotExistException;

    /**
     * Creates a new admin entity.
     *
     * @param admin the Admin object representing the new admin entity
     * @return the created Admin object
     * @throws MissingParameterException if there are missing parameters in the admin object
     * @throws DoesNotExistException     if the admin entity does not exist
     */
    public Admin createAdmin(Admin admin) throws MissingParameterException, DoesNotExistException;
}

package com.w.ever.files.manager.repositories;

import com.w.ever.files.manager.models.FileModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends CrudRepository<FileModel, Integer> {

    /**
     * Finds a folder by its name and parent folder ID.
     *
     * @param name      the name of the folder.
     * @param parent_id the ID of the parent folder.
     * @return an {@code Optional} containing the matching {@code FileModel} if found, or empty if no match is found.
     */
    Optional<FileModel> findByNameAndParentIdAndPathIsNull(String name, Integer parent_id);

    /**
     * Finds a top-level folder by its name.
     *
     * @param name the name of the folder.
     * @return an {@code Optional} containing the matching {@code FileModel} if found, or empty if no match is found.
     */
    Optional<FileModel> findByNameAndParentIdIsNullAndPathIsNull(String name);

    /**
     * Retrieves all file requests created by a specific user and associated with a specific group.
     *
     * @param creator_id          the ID of the user who created the files.
     * @param groupFiles_group_id the ID of the group associated with the files.
     * @return a {@code List} of {@code FileModel} instances matching the criteria, or an empty list if none are found.
     */
    List<FileModel> findByAddedAtIsNullAndCreatorIdAndGroupFilesGroupId(Integer creator_id, Integer groupFiles_group_id);

    /**
     * Retrieves all file requests associated with a specific group.
     *
     * @param groupFiles_group_id the ID of the group associated with the files.
     * @return a {@code List} of {@code FileModel} instances matching the criteria, or an empty list if none are found.
     */
    List<FileModel> findByAddedAtIsNullAndGroupFilesGroupId(Integer groupFiles_group_id);

    /**
     * Retrieves all file requests created by a specific user.
     *
     * @param creator_id the ID of the user who created the files.
     * @return a {@code List} of {@code FileModel} instances matching the criteria, or an empty list if none are found.
     */
    List<FileModel> findByCreatorIdAndAddedAtIsNull(Integer creator_id);

    /**
     * Retrieves a file request by its ID.
     *
     * @param id the ID of the file request.
     * @return an {@code Optional} containing the matching {@code FileModel} if found, or empty if no match is found.
     */
    Optional<FileModel> findByIdAndAddedAtIsNull(Integer id);
}

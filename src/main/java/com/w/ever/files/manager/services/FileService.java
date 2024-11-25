package com.w.ever.files.manager.services;

import com.w.ever.files.manager.models.FileModel;
import com.w.ever.files.manager.models.GroupFileModel;
import com.w.ever.files.manager.models.GroupModel;
import com.w.ever.files.manager.models.UserModel;
import com.w.ever.files.manager.repositories.FileRepository;
import com.w.ever.files.manager.repositories.GroupFileRepository;
import com.w.ever.files.manager.repositories.GroupRepository;
import com.w.ever.files.manager.repositories.GroupUserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private final FileRepository fileRepository;
    private final GroupRepository groupRepository;
    private final GroupUserRepository groupUserRepository;
    private final GroupFileRepository groupFileRepository;

    public FileService(FileRepository fileRepository, GroupRepository groupRepository, GroupUserRepository groupUserRepository, GroupFileRepository groupFileRepository) {
        this.fileRepository = fileRepository;
        this.groupRepository = groupRepository;
        this.groupUserRepository = groupUserRepository;
        this.groupFileRepository = groupFileRepository;
    }

    public FileModel createFile(Integer groupId, String path, String newPath, String oldName) throws BadRequestException {
        // Separate path to use it easily
        List<String> folders = new ArrayList<>(List.of(path.split("/")));
        if (folders.get(0).equals("")) {
            folders.remove(0);
        }
        if (folders.get(folders.size() - 1).equals("")) {
            folders.remove(folders.size() - 1);
        }
        // Check if user in group
        /* TODO: Replace With Real User */
        UserModel user = new UserModel();
        user.setId(3);

        GroupModel group = groupRepository.findById(groupId).orElse(null);
        if (group == null) {
            throw new BadRequestException("Group not found");
        }
        if (!groupUserRepository.userJoined(user.getId(), group.getId())) {
            throw new BadRequestException("User not in group");
        }

        // Check if path exists
        Integer parentId = null;
        for (String name : folders) {
            FileModel folderExists;
            if (parentId == null) folderExists = fileRepository.findFolderByNameAndNullParentId(name);
            else folderExists = fileRepository.findFolderByNameAndParentId(name, parentId);
            if (folderExists == null) {
                throw new BadRequestException("Folder named " + name + " not found");
            }
            parentId = folderExists.getId();
        }

        // Add the new file and link it with the parent and the group
        FileModel parent = null;
        if (parentId != null) {
            parent = fileRepository.findById(parentId).orElse(null);
        }

        String extension = oldName.split("\\.")[oldName.split("\\.").length - 1];


        FileModel newFile = new FileModel();
        newFile.setCreator(user);
        newFile.setParent(parent);
        newFile.setPath(newPath);
        newFile.setExtension(extension);
        newFile.setName(oldName);
        newFile.setAddedAt(null);

        GroupFileModel groupFile = new GroupFileModel();
        groupFile.setFile(newFile);
        groupFile.setGroup(group);
        groupFile.setAddedAt(LocalDateTime.now());

        groupFileRepository.save(groupFile);

        fileRepository.save(newFile);

        return newFile;
    }

    public FileModel createFolder(Integer groupId, String path) throws BadRequestException {
        // Separate path to use it easily
        List<String> folders = new ArrayList<>(List.of(path.split("/")));
        if (folders.get(0).equals("")) {
            folders.remove(0);
        }
        if (folders.get(folders.size() - 1).equals("")) {
            folders.remove(folders.size() - 1);
        }

        if (folders.isEmpty()) throw new BadRequestException("Invalid folder tree.");

        // Check if user in group
        /* TODO: Replace With Real User */
        UserModel user = new UserModel();
        user.setId(3);

        GroupModel group = groupRepository.findById(groupId).orElse(null);
        if (group == null) {
            throw new BadRequestException("Group not found");
        }
        if (!groupUserRepository.userJoined(user.getId(), group.getId())) {
            throw new BadRequestException("User not in group");
        }

        // Check if path exists, if not then create it
        Integer parentId = null;
        FileModel folderExists = null;
        for (String name : folders) {
            if (parentId == null) folderExists = fileRepository.findFolderByNameAndNullParentId(name);
            else folderExists = fileRepository.findFolderByNameAndParentId(name, parentId);
            if (folderExists == null) {
                FileModel newFolder = new FileModel();
                newFolder.setName(name);
                newFolder.setAddedAt(LocalDateTime.now());
                newFolder.setCreator(user);
                newFolder.setParent(parentId == null ? null : fileRepository.findById(parentId).orElse(null));

                GroupFileModel groupFile = new GroupFileModel();
                groupFile.setFile(newFolder);
                groupFile.setGroup(group);
                groupFile.setAddedAt(LocalDateTime.now());

                groupFileRepository.save(groupFile);
                fileRepository.save(newFolder);
                folderExists = newFolder;
            }
            parentId = folderExists.getId();
        }

        return folderExists;
    }

    public List<FileModel> getFileRequests(Integer groupId, Integer userId) {
        return fileRepository.findFileRequestsByUserIdAndGroupId(userId, groupId);
    }
}

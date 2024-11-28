package com.w.ever.files.manager.services;

import com.w.ever.files.manager.models.*;
import com.w.ever.files.manager.repositories.*;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private final FileRepository fileRepository;
    private final GroupRepository groupRepository;
    private final GroupUserRepository groupUserRepository;
    private final GroupFileRepository groupFileRepository;
    private final FileHistoryRepository fileHistoryRepository;
    private final UserService userService;
    private final CheckInRepository checkInRepository;
    @Value("${file.storage.location:src/main/resources/static/uploads}")
    private String fileStorageLocation;

    public FileService(FileRepository fileRepository, GroupRepository groupRepository, GroupUserRepository groupUserRepository, GroupFileRepository groupFileRepository, FileHistoryRepository fileHistoryRepository, UserService userService, CheckInRepository checkInRepository) {
        this.fileRepository = fileRepository;
        this.groupRepository = groupRepository;
        this.groupUserRepository = groupUserRepository;
        this.groupFileRepository = groupFileRepository;
        this.fileHistoryRepository = fileHistoryRepository;
        this.userService = userService;
        this.checkInRepository = checkInRepository;
    }

    @Transactional
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
            if (parentId == null)
                folderExists = fileRepository.findByNameAndParentIdIsNullAndPathIsNull(name).orElse(null);
            else folderExists = fileRepository.findByNameAndParentIdAndPathIsNull(name, parentId).orElse(null);
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

    @Transactional
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
            if (parentId == null)
                folderExists = fileRepository.findByNameAndParentIdIsNullAndPathIsNull(name).orElse(null);
            else folderExists = fileRepository.findByNameAndParentIdAndPathIsNull(name, parentId).orElse(null);
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

    @Transactional
    public List<FileModel> getFileRequestsForGroupAndUser(Integer groupId, Integer userId) {
        return fileRepository.findByAddedAtIsNullAndCreatorIdAndGroupFilesGroupId(userId, groupId);
    }

    @Transactional
    public List<FileModel> getFileRequestsForGroup(Integer groupId) {
        return fileRepository.findByAddedAtIsNullAndGroupFilesGroupId(groupId);
    }

    @Transactional
    public List<FileModel> getFileRequestsForUser(Integer userId) {
        return fileRepository.findByCreatorIdAndAddedAtIsNull(userId);
    }

    @Transactional
    public FileModel getFileRequest(Integer fileRequestId) throws BadRequestException {
        FileModel fileRequest = fileRepository.findByIdAndAddedAtIsNull(fileRequestId).orElse(null);
        if (fileRequest == null) throw new BadRequestException("File request not found");
        return fileRequest;
    }

    @Transactional
    public FileModel acceptFileRequest(Integer fileRequestId) throws BadRequestException {
        // Set a value to addedAt field
        FileModel fileRequest = getFileRequest(fileRequestId);
        fileRequest.setAddedAt(LocalDateTime.now());
        fileRepository.save(fileRequest);

        // Create a history record
        FileHistoryModel history = new FileHistoryModel();
        history.setFile(fileRequest);
        history.setPath(fileRequest.getPath());
        history.setVersion("1");
        history.setCreatedAt(LocalDateTime.now());
        fileHistoryRepository.save(history);

        return fileRequest;
    }

    @Transactional
    public List<CheckInModel> reserveFiles(List<Integer> filesIds) throws BadRequestException {
        List<FileModel> files = fileRepository.findAllByIdInAndAddedAtNotNullAndPathNotNull(filesIds);
        if (files.size() != filesIds.size()) throw new BadRequestException("Some files doesn't exist");

        /* TODO: Get the real user */
        UserModel user = userService.getProfile(1);

        List<CheckInModel> checkIns = new ArrayList<>();
        for (FileModel file : files) {
            List<CheckInModel> checkInsForFile = file.getCheckIns();
            CheckInModel lastCheckIn = checkInsForFile.size() == 0 ? null : checkInsForFile.get(checkInsForFile.size() - 1);
            if (lastCheckIn != null && lastCheckIn.getCheckedOutAt() == null) {
                throw new BadRequestException("File " + file.getName() + " is reserved");
            }
            CheckInModel newCheckIn = new CheckInModel();
            newCheckIn.setCheckedInAt(LocalDateTime.now());
            newCheckIn.setFile(file);
            newCheckIn.setUser(user);
            checkInRepository.save(newCheckIn);
            checkIns.add(newCheckIn);
        }
        return checkIns;
    }

    @Transactional
    public Resource getFile(String pathName) throws MalformedURLException {
        /* TODO: Check if user can access th file */

        // Build file path
        Path filePath = Paths.get(fileStorageLocation).resolve(pathName).normalize();

        // Load the file as a Resource
        return new UrlResource(filePath.toUri());
    }
}

package com.w.ever.files.manager.services;

import com.w.ever.files.manager.dto.groups.CreateGroupDTO;
import com.w.ever.files.manager.dto.groups.GroupInvitationDTO;
import com.w.ever.files.manager.dto.groups.UpdateGroupDTO;
import com.w.ever.files.manager.models.GroupModel;
import com.w.ever.files.manager.models.GroupUserModel;
import com.w.ever.files.manager.models.UserModel;
import com.w.ever.files.manager.repositories.GroupRepository;
import com.w.ever.files.manager.repositories.GroupUserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupUserRepository groupUserRepository;
    private final UserService userService;

    public GroupService(GroupRepository groupRepository, UserService userService, GroupUserRepository groupUserRepository) {
        this.groupRepository = groupRepository;
        this.userService = userService;
        this.groupUserRepository = groupUserRepository;
    }

    @Transactional
    public GroupModel createGroup(CreateGroupDTO groupData) throws BadRequestException {
        UserModel userModel = userService.getProfile(groupData.getCreator_id());
        if (userModel == null) throw new BadRequestException("Creator Not Found");
        GroupModel groupModel = new GroupModel(groupData.getName(), userModel, groupData.getDescription(), groupData.getColor(), groupData.getLang());
        return groupRepository.save(groupModel);
    }

    @Transactional
    public GroupModel getGroup(Integer id) {
        return groupRepository.findById(id).orElse(null);
    }

    @Transactional
    public GroupModel updateGroup(Integer id, UpdateGroupDTO groupData) throws BadRequestException {
        GroupModel group = getGroup(id);
        if (group == null) throw new BadRequestException("Group Doesn't Exist");

        if (groupData.getName() != null) {
            String name = groupData.getName();
            if (isNameUnique(name, id)) {
                group.setName(groupData.getName());
            } else {
                throw new BadRequestException("Name is used");
            }
        }
        if (groupData.getDescription() != null) {
            group.setDescription(groupData.getDescription());
        }
        if (groupData.getColor() != null) {
            group.setColor(groupData.getColor());
        }
        if (groupData.getLang() != null) {
            group.setLang(groupData.getLang());
        }

        return groupRepository.save(group);
    }

    @Transactional
    public GroupUserModel invite(GroupInvitationDTO invitationData) throws BadRequestException {
        Integer groupId = invitationData.getGroupId();
        Integer userId = invitationData.getUserId();

        if (groupUserRepository.invitationExists(userId, groupId)) {
            throw new BadRequestException("User already invited.");
        }
        if (groupUserRepository.userJoined(userId, groupId)) {
            throw new BadRequestException("User already joined.");
        }

        GroupUserModel groupUserModel = new GroupUserModel();
        GroupModel group = getGroup(groupId);
        UserModel user = userService.getProfile(userId);

        /* TODO: Replace with real user from token */
        UserModel fakeUser = new UserModel();
        fakeUser.setId(1);

        groupUserModel.setGroup(group);
        groupUserModel.setUser(user);
        groupUserModel.setInviter(fakeUser);
        groupUserModel.setInvitationExpiresAt(LocalDateTime.now().plusDays(1));

        return groupUserRepository.save(groupUserModel);
    }

    @Transactional
    public void deleteGroup(Integer id) throws BadRequestException {
        if (!groupRepository.exists(id)) {
            throw new BadRequestException("Group doesn't exist.");
        }
        /* TODO: Implement Soft Delete */
        groupRepository.deleteById(id);
    }

    @Transactional
    public boolean isNameUnique(String name, Integer id) {
        if (name == null) return true;
        return !groupRepository.existsByNameAndNotGroupId(name, id);
    }
}

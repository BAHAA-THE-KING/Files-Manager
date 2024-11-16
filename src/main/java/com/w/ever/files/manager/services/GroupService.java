package com.w.ever.files.manager.services;

import com.w.ever.files.manager.dto.groups.CreateGroupDTO;
import com.w.ever.files.manager.dto.groups.UpdateGroupDTO;
import com.w.ever.files.manager.models.GroupModel;
import com.w.ever.files.manager.models.UserModel;
import com.w.ever.files.manager.repositories.GroupRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserService userService;

    public GroupService(GroupRepository groupRepository, UserService userService) {
        this.groupRepository = groupRepository;
        this.userService = userService;
    }

    public GroupModel createGroup(CreateGroupDTO groupData) throws BadRequestException {
        UserModel userModel = userService.getProfile(groupData.getCreator_id());
        if (userModel == null) throw new BadRequestException("Creator Not Found");
        GroupModel groupModel = new GroupModel(groupData.getName(), userModel, groupData.getDescription(), groupData.getColor(), groupData.getLang());
        return groupRepository.save(groupModel);
    }

    public GroupModel getGroup(Integer id) {
        return groupRepository.findById(id).orElse(null);
    }

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

    public void deleteGroup(Integer id) throws BadRequestException {
        if (!groupRepository.exists(id)) {
            throw new BadRequestException("Group doesn't exist.");
        }
        /* TODO: Implement Soft Delete */
        groupRepository.deleteById(id);
    }

    public boolean isNameUnique(String name, Integer id) {
        if (name == null) return true;
        return !groupRepository.existsByNameAndNotGroupId(name, id);
    }
}

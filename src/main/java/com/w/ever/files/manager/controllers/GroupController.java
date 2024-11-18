package com.w.ever.files.manager.controllers;

import com.w.ever.files.manager.dto.groups.CreateGroupDTO;
import com.w.ever.files.manager.dto.groups.GroupInvitationDTO;
import com.w.ever.files.manager.dto.groups.UpdateGroupDTO;
import com.w.ever.files.manager.models.GroupModel;
import com.w.ever.files.manager.models.GroupUserModel;
import com.w.ever.files.manager.responses.ErrorResponse;
import com.w.ever.files.manager.responses.SuccessResponse;
import com.w.ever.files.manager.services.GroupService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("group")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }


    @PostMapping("create")
    public ResponseEntity createGroup(@Valid @RequestBody CreateGroupDTO groupData) throws BadRequestException {
        GroupModel GroupModel = this.groupService.createGroup(groupData);
        return new SuccessResponse(GroupModel);
    }

    @GetMapping("{id}")
    public ResponseEntity getGroup(@PathVariable @NotNull(message = "Group ID cannot be null") Integer id) {
        GroupModel GroupModel = groupService.getGroup(id);
        if (GroupModel == null) {
            return new ErrorResponse(404, "Group not found");
        }
        return new SuccessResponse(GroupModel);
    }

    @PutMapping("{id}")
    public ResponseEntity updateGroup(@Valid @RequestBody UpdateGroupDTO groupData, @PathVariable @NotNull(message = "Group ID cannot be null") Integer id) throws BadRequestException {
        GroupModel GroupModel = groupService.updateGroup(id, groupData);
        return new SuccessResponse(GroupModel);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteGroup(@PathVariable @NotNull(message = "Group ID cannot be null") Integer id) throws BadRequestException {
        groupService.deleteGroup(id);
        return new SuccessResponse();
    }

    @PostMapping("invite")
    public ResponseEntity createInvitation(@Valid @RequestBody GroupInvitationDTO invitationData) throws BadRequestException {
        GroupUserModel groupUserModel = groupService.invite(invitationData);
        return new SuccessResponse(groupUserModel);
    }
}

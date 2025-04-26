package backend.quadcount.controller;

import backend.quadcount.api.ResponseUtil;
import backend.quadcount.dto.GroupRequestDto;
import backend.quadcount.model.Group;
import backend.quadcount.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<?> getAllGroups() {
        return ResponseUtil.ok(groupService.getAllGroups());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGroupById(@PathVariable String id) {
        return ResponseUtil.ok(groupService.getGroupById(id));
    }

    @PostMapping
    public ResponseEntity<?> createGroup(@Valid @RequestBody GroupRequestDto dto) {
        return ResponseUtil.created(groupService.createGroup(dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable String id) {
        groupService.deleteGroup(id);
        return ResponseUtil.ok("Deleted");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getGroupsByUserId(@PathVariable Long userId) {
        return ResponseUtil.ok(groupService.getGroupsByUserId(userId));
    }
}

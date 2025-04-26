package backend.quadcount.service;


import backend.quadcount.dto.GroupRequestDto;
import backend.quadcount.model.Group;
import backend.quadcount.model.User;
import backend.quadcount.repository.GroupRepository;
import backend.quadcount.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Optional<Group> getGroupById(String id) {
        return groupRepository.findById(id);
    }

    public Group createGroup(GroupRequestDto dto) {
        Group g = new Group();
        g.setName(dto.getName());
        g.setUsers((List<User>) userRepository.findAllById(dto.getUserIds()));
        return groupRepository.save(g);
    }
    public Group updateGroup(String id, GroupRequestDto dto) {
        return groupRepository.findById(id).map(g -> {
            g.setName(dto.getName());
            g.setUsers((List<User>) userRepository.findAllById(dto.getUserIds()));
            return groupRepository.save(g);
        }).orElseThrow(() -> new EntityNotFoundException("Group not found"));
    }

    public void deleteGroup(String id) {
        groupRepository.deleteById(id);
    }

    public List<Group> getGroupsByUserId(Long userId) {
        return groupRepository.findGroupsByUserId(userId);
    }
}
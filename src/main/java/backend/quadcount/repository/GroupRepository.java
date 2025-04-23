package backend.quadcount.repository;


import backend.quadcount.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {

    @Query(value = "SELECT g.* FROM groups g " +
            "JOIN groups_users gu ON g.id = gu.group_id " +
            "WHERE gu.user_id = :userId", nativeQuery = true)
    List<Group> findGroupsByUserId(@Param("userId") Long userId);

}
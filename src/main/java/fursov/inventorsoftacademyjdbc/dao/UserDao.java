package fursov.inventorsoftacademyjdbc.dao;

import fursov.inventorsoftacademyjdbc.domain.Role;
import java.util.Optional;

public interface RoleDao {
    Optional<Role> getRoleById(int id);
    Optional<Role> getRoleByName(String name);
}
package com.spdata.crm.Role.Service;

import com.spdata.crm.Role.Dao.RoleDao;
import com.spdata.crm.Role.input.RoleInput;
import com.spdata.crm.tool.SecurityTool;
import com.spdata.entity.Base.BaseService;
import com.spdata.entity.Role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService extends BaseService<RoleDao, Role> {
    @Autowired
    private RoleDao roleDao;

    /**
     * 查询当前用户的角色信息
     *
     * @return
     */
    public List<Role> findAccountRole() {
        return roleDao.findAccountRole(SecurityTool.getSecurityUserName());
    }

    /**
     * 插入角色与菜单的对应关系
     *
     * @param roleInput
     * @return
     */
    @Transactional
    public boolean InsertMenu(RoleInput roleInput) {
        return roleDao.InsertMenu(roleInput);
    }

    /**
     * 插入角色与权限的对应关系
     *
     * @param roleInput
     * @return
     */
    @Transactional
    public boolean InsertPermission(RoleInput roleInput) {
        return roleDao.InsertPermission(roleInput);
    }

    /**
     * 删除角色角色与菜单的对应关系
     *
     * @param roleInput
     * @return
     */
    @Transactional
    public boolean delectRoleMenu(RoleInput roleInput) {
        return roleDao.delectRoleMenu(roleInput);
    }

    /**
     * 删除角色与权限的对应关系
     *
     * @param roleInput
     * @return
     */
    @Transactional
    public boolean delectRolePermission(RoleInput roleInput) {
        return roleDao.delectRolePermission(roleInput);
    }
}

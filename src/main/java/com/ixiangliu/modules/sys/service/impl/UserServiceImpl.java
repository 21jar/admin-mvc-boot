package com.ixiangliu.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ixiangliu.common.constant.Const;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.common.utils.Query;
import com.ixiangliu.modules.sys.dao.UserDao;
import com.ixiangliu.modules.sys.entity.Dept;
import com.ixiangliu.modules.sys.entity.User;
import com.ixiangliu.modules.sys.service.IDeptService;
import com.ixiangliu.modules.sys.service.IUserRoleService;
import com.ixiangliu.modules.sys.service.IUserService;
import com.ixiangliu.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {


    @Autowired
    private IUserRoleService iUserRoleService;
    @Autowired
    private IDeptService iDeptService;

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    @Override
//    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String username = (String) params.get("username");
        IPage<User> page = this.page(
                new Query<User>().getPage(params),
                new QueryWrapper<User>()
                        .like(StringUtils.isNotBlank(username), "username", username)
                        .apply(params.get(Const.SQL_FILTER) != null, (String) params.get(Const.SQL_FILTER))
        );

        for (User User : page.getRecords()) {
            Dept sysDeptEntity = iDeptService.getById(User.getDeptId());
            User.setDeptName(sysDeptEntity != null ? sysDeptEntity.getName() : "");
        }

        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(User user) {
        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setSalt(salt);
        user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
        this.save(user);

        //保存用户与角色关系
        iUserRoleService.saveOrUpdate(user.getId(), user.getRoleIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(User user) {
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            User userEntity = this.getById(user.getId());
            user.setPassword(ShiroUtils.sha256(user.getPassword(), userEntity.getSalt()));
        }
        this.updateById(user);

        //保存用户与角色关系
        iUserRoleService.saveOrUpdate(user.getId(), user.getRoleIdList());
    }


    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        User user = new User();
        user.setPassword(newPassword);
        return this.update(user,
                new QueryWrapper<User>().eq("id", userId).eq("password", password));
    }

}

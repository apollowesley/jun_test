package cn.backflow.admin.service;

import cn.backflow.admin.common.pagination.Page;
import cn.backflow.admin.common.pagination.PageRequest;
import cn.backflow.admin.dao.PermissionDao;
import cn.backflow.admin.dao.ResetRecordDao;
import cn.backflow.admin.dao.RoleDao;
import cn.backflow.admin.dao.UserDao;
import cn.backflow.admin.entity.ResetRecord;
import cn.backflow.admin.entity.User;
import cn.backflow.admin.service.base.BaseService;
import cn.backflow.lib.util.DateUtil;
import cn.backflow.lib.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService extends BaseService<User, Integer> {

    private final ResetRecordDao resetRecordDao;
    private final PermissionDao permissionDao;
    private final UserDao userDao;
    private final RoleDao roleDao;

    @Autowired
    public UserService(ResetRecordDao resetRecordDao, PermissionDao permissionDao, UserDao userDao, RoleDao roleDao) {
        this.resetRecordDao = resetRecordDao;
        this.permissionDao = permissionDao;
        this.userDao = userDao;
        this.roleDao = roleDao;
    }


    public Page<User> query(PageRequest pr) {
        return userDao.query(pr);
    }

    public List<User> search(PageRequest pr) {
        return userDao.search(pr);
    }

    public List<String> findPermissionByUser(User user) {
        return user.isAdmin() ?
                permissionDao.findCode(null) :
                roleDao.findPermission(Collections.singletonMap("roleId", user.getRoleId()));
    }

    public User getByEmail(String email) {
        return userDao.getByIdentity(Collections.singletonMap("email", email));
    }

    public User getByPhone(String phone) {
        return userDao.getByIdentity(Collections.singletonMap("phone", phone));
    }

    public User getByName(String name) {
        return userDao.getByIdentity(Collections.singletonMap("name", name));
    }

    /**
     * 验证重置邮箱与密钥是否有效
     *
     * @param email    邮箱
     * @param resetKey 重置密钥
     * @return 是否有效
     */
    public boolean isResetRecordValid(String email, String resetKey) {
        ResetRecord record = resetRecordDao.getNewestByEmail(email);
        // String key = generateResetKey(email, record.getName(), record.getRequestTime());
        return record != null &&
                record.getValid() == 1 && // 有效状态值为1
                record.getResetKey().equals(resetKey) && //  重置密钥相同
                record.getExpirationTime().compareTo(new Date()) >= 0; // 在有效期内
    }

    /**
     * 生成密码重置密钥
     *
     * @param email       邮箱
     * @param name        用户名
     * @param requestTime 申请重置时间
     * @return 生成的密钥
     */
    private String generateResetKey(String email, String name, Date requestTime) {
        return StringUtil.md5(email, name + requestTime.toString().replaceAll("\\s", ""));
    }

    /**
     * 保存密码重置记录
     *
     * @param user 申请用户
     */
    @Transactional
    public ResetRecord saveResetResord(User user) {
        Date now = new Date();
        Date expire = DateUtil.shiftTime(now, Calendar.DAY_OF_YEAR, 1);
        ResetRecord record = new ResetRecord();
        record.setEmail(user.getEmail());
        record.setResetKey(generateResetKey(user.getEmail(), user.getName(), now));
        record.setRequestTime(now);
        record.setExpirationTime(expire);
        resetRecordDao.save(record);
        return record;
    }

    @Override
    @Transactional
    public int save(User user) throws DataAccessException {
        String password = StringUtil.md5(user.getPass(), user.getName());
        user.setPass(password);
        return userDao.save(user);
    }

    @Override
    @Transactional
    public int update(User user) throws DataAccessException {
        if (StringUtil.isNotBlank(user.getPass())) {
            String password = StringUtil.md5(user.getPass(), user.getName());
            user.setPass(password);
        }
        return userDao.updateSelective(user);
    }

    @Transactional
    public int updateProfile(User user) {
        user.setPass(StringUtil.isBlank(user.getPass()) ?
                null :
                StringUtil.md5(user.getPass(), user.getName())
        );
        return userDao.updateSelective(user);
    }

    @Transactional
    public void resetPass(String name, String email, String pass, String resetKey) {
        pass = StringUtil.md5(pass, name);
        userDao.updatePass(name, pass);
        resetRecordDao.updateValidState("email", email, 0);
    }

    /**
     * 批量局部更新
     *
     * @param users 用户集合
     * @return 受影响的行数
     */
    @Transactional
    public int updateSelectiveBatch(List<User> users) {
        int i = 0;
        for (User user : users) {
            i += userDao.updateSelective(user);
        }
        return i;
    }

    @Transactional
    public void updateVisited(Integer id) {
        Map<String, Object> parameter = new HashMap<>(2);
        parameter.put("visited", new Date());
        parameter.put("id", id);
        userDao.updateSelective(parameter);
    }

    @Transactional
    public int updateAvatar(Integer id, String avatar) {
        Map<String, Object> parameter = new HashMap<>(2);
        parameter.put("avatar", avatar);
        parameter.put("id", id);
        return userDao.updateSelective(parameter);
    }

    @Transactional
    public int updateRoleId(Integer id, Integer roleId) {
        Map<String, Object> parameter = new HashMap<>(2);
        parameter.put("roleId", roleId);
        parameter.put("id", id);
        return userDao.updateSelective(parameter);
    }
}
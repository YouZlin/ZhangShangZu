package com.zsz.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.zsz.dao.utils.JDBCUtils;
import com.zsz.dto.AdminUserDTO;
import com.zsz.tools.CommonUtils;

public class AdminUserDAO {
	
	public static void main(String[] args) {
		
		//JUnit：简化单元测试案例的编写、简化断言、简化单元测试案例运行的一个框架
		
		/*
		boolean b1 = new AdminUserDAO().hasPermission(1, "AdminUser.Query");
		System.out.println(b1);
		
		boolean b2 = new AdminUserDAO().hasPermission(1, "AdminUser.aaa");
		System.out.println(b2);*/
		
		//Test Case：测试案例：考虑所有具有典型性的可能的输入输出都考虑到，测试到，检查输出的合法新
		//单元测试 Unit Test
		//回归测试：检测是否由于修改A导致其他地方出问题
		
		boolean b1 = new AdminUserDAO().hasPermission(1, "AdminUser.Query");
		if(b1)//断言：Assert
		{
			System.out.println("ok");
		}
		else
		{
			System.err.println("error");
		}
		
		boolean b2 = new AdminUserDAO().hasPermission(1, "AdminUser.aaa");
		if(b2)
		{
			System.err.println("error");
		}
		else
		{
			System.out.println("ok");
		}
	}
	
	// 加入一个用户，name用户姓名，phoneNum手机号，password密码，email，cityId城市id（null表示总部）
	public long addAdminUser(String name, String phoneNum, String password, String email, Long cityId) {
	
		// 用户传进来的密码是明文，所以需要计算 散列值
		String passwordHash = CommonUtils.calcMD5(password);
		StringBuilder sb = new StringBuilder();
		sb.append(
				"insert into T_AdminUsers(Name,PhoneNum,PasswordHash,Email,CityId,LoginErrorTimes,LastLoginErrorDateTime,IsDeleted,CreateDateTime)\n");
		sb.append("values(?,?,?,?,?,0,null,0,now())");
		try {
			return JDBCUtils.executeInsert(sb.toString(), name, phoneNum, passwordHash, email, cityId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// 修改用户的信息
	public void updateAdminUser(long id, String name, String password, String email, Long cityId) {
		
		//如果密码为空，则表示用户不想修改密码，密码维持原值
		if(StringUtils.isEmpty(password))
		{
			StringBuilder sb = new StringBuilder();
			sb.append("update T_AdminUsers set Name=?,Email=?,CityId=?\n");
			sb.append("where id=?");
			try {
				JDBCUtils.executeNonQuery(sb.toString(), name, email, cityId, id);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		else
		{		
			// 用户传进来的密码是明文，所以需要计算 散列值
			String passwordHash = CommonUtils.calcMD5(password);
			StringBuilder sb = new StringBuilder();
			sb.append("update T_AdminUsers set Name=?,PasswordHash=?,Email=?,CityId=?\n");
			sb.append("where id=?");
			try {
				JDBCUtils.executeNonQuery(sb.toString(), name, passwordHash, email, cityId, id);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private AdminUserDTO toAdminUserDTO(ResultSet rs) throws SQLException {
		AdminUserDTO adminUser = new AdminUserDTO();
		adminUser.setId(rs.getLong("Id"));
		adminUser.setName(rs.getString("Name"));
		adminUser.setCityId((Long) rs.getObject("CityId"));
		adminUser.setCityName(rs.getString("cityName"));
		adminUser.setCreateDateTime(rs.getDate("CreateDateTime"));
		adminUser.setDeleted(rs.getBoolean("IsDeleted"));
		adminUser.setEmail(rs.getString("Email"));
		adminUser.setLastLoginErrorDateTime(rs.getDate("LastLoginErrorDateTime"));
		adminUser.setLoginErrorTimes(rs.getInt("LoginErrorTimes"));
		adminUser.setPasswordHash(rs.getString("PasswordHash"));
		adminUser.setPhoneNum(rs.getString("PhoneNum"));
		return adminUser;
	}

	// 获取cityId这个城市下的管理员
	public AdminUserDTO[] getAll(long cityId) {
		// SQL注入漏洞
		StringBuilder sb = new StringBuilder();
		sb.append("select u.*,c.Name cityName from T_AdminUsers u");
		sb.append("left join T_cities c on u.CityId=c.Id where u.IsDeleted=0 and u.CityId=?\n");

		List<AdminUserDTO> list = new ArrayList<>();
		ResultSet rs = null;
		try {
			rs = JDBCUtils.executeQuery(sb.toString(), cityId);
			while (rs.next()) {
				list.add(toAdminUserDTO(rs));
			}
			return list.toArray(new AdminUserDTO[list.size()]);
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			JDBCUtils.closeAll(rs);
		}
	}

	// 获取所有管理员
	public AdminUserDTO[] getAll() {
		StringBuilder sb = new StringBuilder();
		sb.append("select u.*,c.Name cityName from T_AdminUsers u\n");
		sb.append("left join T_cities c on u.CityId=c.Id where u.IsDeleted=0\n");

		List<AdminUserDTO> list = new ArrayList<>();
		ResultSet rs = null;
		try {
			rs = JDBCUtils.executeQuery(sb.toString());
			while (rs.next()) {
				list.add(toAdminUserDTO(rs));
			}
			return list.toArray(new AdminUserDTO[list.size()]);
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			JDBCUtils.closeAll(rs);
		}
	}

	// 根据id获取DTO
	public AdminUserDTO getById(long id) {
		ResultSet rs = null;
		try {
			rs = JDBCUtils.executeQuery(
					"select u.*,c.Name cityName from T_AdminUsers u left join T_Cities c on u.CityId=c.Id where u.Id=? and u.IsDeleted=0",
					id);
			if (rs.next()) {
				return toAdminUserDTO(rs);
			} else {
				return null;
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			JDBCUtils.closeAll(rs);
		}
	}

	// 根据手机号获取DTO
	public AdminUserDTO getByPhoneNum(String phoneNum) {
		ResultSet rs = null;
		try {
			rs = JDBCUtils.executeQuery(
					"select u.*,c.Name cityName from T_AdminUsers u left join T_Cities c on u.CityId=c.Id where u.PhoneNum=? and u.IsDeleted=0",
					phoneNum);
			if (rs.next()) {
				return toAdminUserDTO(rs);
			} else {
				return null;
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			JDBCUtils.closeAll(rs);
		}
	}

	// 检查用户名密码是否正确
	public boolean checkLogin(String phoneNum, String password) {
		AdminUserDTO user = getByPhoneNum(phoneNum);
		if (user == null)// 手机号错误
		{
			return false;
		}

		String passwordHash = CommonUtils.calcMD5(password);

		if (user.getPasswordHash().equals(passwordHash)) {
			return true;
		} else {
			return false;
		}
		// return user.getPasswordHash().equals(passwordHash);
	}

	// 软删除
	public void markDeleted(long adminUserId) {
		try {
			JDBCUtils.executeNonQuery("Update T_AdminUsers set IsDeleted=1 where Id=?", adminUserId);
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 判断用户是否有某个权限，比如hasPermission(3,"AdminUser.AddNew")
	 * 
	 * @param adminUserId
	 * @param permissionName
	 * @return
	 */
	public boolean hasPermission(long adminUserId, String permissionName) {
		// 想获得用户的拥有的角色，再获得这些角色拥有的权限，再判断permissionName这个权限是否在这些权限范围内
		//   select count(*) from t_permissions where Id in
		//   (
		//      select PermissionId from t_rolepermissions where RoleId in
		//      (
		//        select RoleId from t_adminuserroles where AdminUserId=1
		//      )
		//   )
		//   and Name ='AdminUser.Query'
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from t_permissions where Id in\n");
		sb.append("(\n");
		sb.append("   select PermissionId from t_rolepermissions where RoleId in\n");
		sb.append("   (\n");
		sb.append("      select RoleId from t_adminuserroles where AdminUserId=?\n");
		sb.append("   )\n");
		sb.append(")\n");
		sb.append("and Name =?\n");
		// 无论Integer还是Long、Double都继承自Number，这样写避免了返回值是Integer还是Long的问题
		try {
			Number number = (Number) JDBCUtils.querySingle(sb.toString(), adminUserId, permissionName);
			//如果查询的条数不是0，那么久说明有这个权限
			return number.intValue()>0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
}

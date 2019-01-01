package com.zsz.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.zsz.dao.utils.JDBCUtils;
import com.zsz.dto.UserDTO;
import com.zsz.tools.CommonUtils;
import com.zsz.tools.VerifyCodeUtils;

public class UserDAO {
	public long addnew(String phoneNum, String password) {
		
		
		
		// 生成一个盐
		String salt = VerifyCodeUtils.generateVerifyCode(6, "abcdefg1234567@!$%&*");
		String md5 = CommonUtils.calcMD5(password + salt);//用户输入的密码+盐 ，计算md5值
		try {
			return JDBCUtils.executeInsert(
					"insert into T_Users(PhoneNum,PasswordHash,PasswordSalt,CreateDateTime,LoginErrorTimes) values(?,?,?,now(),0)",
					phoneNum, md5, salt);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public UserDTO toDTO(ResultSet rs) throws SQLException {
		UserDTO user = new UserDTO();
		user.setCreateDateTime(rs.getDate("CreateDateTime"));
		user.setId(rs.getLong("Id"));
		user.setLastLoginErrorDateTime(rs.getDate("LastLoginErrorDateTime"));
		user.setLoginErrorTimes(rs.getInt("LoginErrorTimes"));
		user.setPasswordHash(rs.getString("PasswordHash"));
		user.setPasswordSalt(rs.getString("PasswordSalt"));
		user.setPhoneNum(rs.getString("PhoneNum"));
		//user.setCityId(rs.getLong("CityId"));
		user.setCityId((Long)rs.getObject("CityId"));
		return user;
	}

	public UserDTO getById(long id) {
		ResultSet rs = null;
		try {
			rs = JDBCUtils.executeQuery("select * from T_Users where Id=?", id);
			if (rs.next()) {
				return toDTO(rs);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.closeAll(rs);
		}
	}

	public UserDTO getByPhoneNum(String phoneNum) {
		ResultSet rs = null;
		try {
			rs = JDBCUtils.executeQuery("select * from T_Users where PhoneNum=?", phoneNum);
			if (rs.next()) {
				return toDTO(rs);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.closeAll(rs);
		}
	}

	public boolean checkLogin(String phoneNum, String password) {
		UserDTO user = getByPhoneNum(phoneNum);
		if(user==null)
		{
			return false;
		}
		String dbHash =  user.getPasswordHash();//数据库中保存的md5
		String salt = user.getPasswordSalt();
		String userHash = CommonUtils.calcMD5(password+salt);//用户密码+盐
		return dbHash.equals(userHash);
	}

	public void updatePwd(long userId, String newPassword) {
		UserDTO user = getById(userId);
		if(user==null)
		{
			throw new IllegalArgumentException("给定的用户id="+userId+"不存在！");
		}
		String salt = user.getPasswordSalt();
		String newHash = CommonUtils.calcMD5(newPassword+salt);
		try {
			JDBCUtils.executeNonQuery("Update T_Users set PasswordHash=? where Id=?", newHash,userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void setUserCityId(long userId, long cityId) {
		try {
			JDBCUtils.executeNonQuery("Update T_Users set CityId=? where Id=?", cityId, userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}

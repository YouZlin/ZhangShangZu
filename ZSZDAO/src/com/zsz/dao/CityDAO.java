package com.zsz.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zsz.dao.utils.JDBCUtils;
import com.zsz.dto.CityDTO;

public class CityDAO {
	static CityDTO toDTO(ResultSet rs) throws SQLException {
		CityDTO dto = new CityDTO();
		dto.setDeleted(rs.getBoolean("IsDeleted"));
		dto.setId(rs.getLong("Id"));
		dto.setName(rs.getString("Name"));
		return dto;
	}

	// 根据id获取城市DTO
	public CityDTO getById(long id) {
		ResultSet rs = null;
		try {
			rs = JDBCUtils.executeQuery("select * from T_cities where Id=? and IsDeleted=0", id);
			if (rs.next()) {
				return toDTO(rs);
			} else {
				return null;
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			JDBCUtils.closeAll(rs);
		}
	}

	// 获取所有城市
	public CityDTO[] getAll() {
		List<CityDTO> list = new ArrayList<CityDTO>();
		ResultSet rs = null;
		try {
			rs = JDBCUtils.executeQuery("select * from t_cities where  IsDeleted=0");
			while (rs.next()) {
				list.add(toDTO(rs));
			}
			return list.toArray(new CityDTO[list.size()]);
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			JDBCUtils.closeAll(rs);
		}
	}
}

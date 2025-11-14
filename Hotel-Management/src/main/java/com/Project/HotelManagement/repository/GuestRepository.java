package com.Project.HotelManagement.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.Project.HotelManagement.model.Guest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GuestRepository {
	private final JdbcTemplate jdbc;
	
	public GuestRepository(JdbcTemplate jdbc) {
		this.jdbc=jdbc;
	}

	private final RowMapper<Guest> guestMapper = new RowMapper<Guest>() {
        @Override
        public Guest mapRow(ResultSet rs, int rowNum) throws SQLException {
            Guest g = new Guest();
            g.setId(rs.getLong("id"));
            g.setName(rs.getString("name"));
            g.setEmail(rs.getString("email"));
            g.setPhone(rs.getString("phone"));
            g.setAadhar(rs.getString("aadhar"));
            g.setAge(rs.getInt("age"));
            g.setLengthOfStay(rs.getInt("lengthOfStay"));
            g.setMembers(rs.getInt("members"));
            g.setCheckInTime(rs.getString("checkInTime"));
            g.setCheckOutTime(rs.getString("checkOutTime"));
            return g;
        }
	};
	
	//Table name is "bookings"
	
	public int addGuest(Guest g) {
        String sql = "INSERT INTO bookings (name, email, phone, aadhar, age, lengthOfStay, members) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbc.update(sql, g.getName(), g.getEmail(), g.getPhone(), g.getAadhar(), g.getAge(), g.getLengthOfStay(), g.getMembers());
    }

    public List<Guest> findAll() {
        String sql = "SELECT * FROM bookings ORDER BY checkInTime DESC";
        return jdbc.query(sql, guestMapper);
    }

    public int checkOut(Long id, java.sql.Timestamp checkOutTime) {
    		String sql = "UPDATE bookings SET checkOutTime = NOW() WHERE id = ?";
        return jdbc.update(sql, id);
    }

    @SuppressWarnings("deprecation")
	public Guest findById(Long id) {
        String sql = "SELECT * FROM bookings WHERE id = ?";
        List<Guest> list = jdbc.query(sql, new Object[]{id}, guestMapper);
        return list.isEmpty() ? null : list.get(0);
    }
	
//	public List<Guest> findAll(){
//		String sql = "select * from reservation order by checkInTime";
//		return jdbc.query(sql, guestMapper);
//	}
//	
//	@SuppressWarnings("deprecation")
//	public Guest findByid(Long id) {
//		String sql = "select * from reservation where id = ?";
//		return jdbc.queryForObject(sql, new Object[] {id}, guestMapper);
//	}
//	
////	java.sql.Timestamp checkInTimestamp = java.sql.Timestamp.valueOf(g.getcheckInTime());
//	
//	public int save(Guest g) {
//		//java.sql.Timestamp checkInTimestamp = java.sql.Timestamp.valueOf(g.getCheckInTime());
//		String sql = "insert into reservation(name, email, phone, aadhar, age, lengthOfStay, members, checkInTim) values (?, ?, ?, ?, ?, ?, ?, ?)";
//		return jdbc.update(sql, g.getName(), g.getEmail(), g.getPhone(), g.getAadhar(), g.getAge(), g.getLengthOfStay(), g.getMembers(), java.sql.Date.valueOf(g.getCheckInTime()));
//	}
//	
//	public int checkOut(Guest g) {
//		String sql = "update reservation set checkOutTime = ? where id = ?";
//		return jdbc.update(sql, java.sql.Date.valueOf(g.getCheckOutTime()), g.getId());
//	}
//	 
//	public int available(Guest g) {
//		String sql = "select count(*) from reservation where checkOutTime is not null";
//		return jdbc.queryForObject(sql, Integer.class);
//	}
	
}


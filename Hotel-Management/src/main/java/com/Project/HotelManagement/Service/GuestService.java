package com.Project.HotelManagement.Service;
import org.springframework.stereotype.Service;

import com.Project.HotelManagement.model.Guest;
import com.Project.HotelManagement.repository.GuestRepository;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;

@Service
public class GuestService {

    private final GuestRepository repo;

    public GuestService(GuestRepository repo) {
        this.repo = repo;
    }

    public int addGuest(Guest g) {
        // Basic validation (extend as needed)
        if (!StringUtils.hasText(g.getName()) || !StringUtils.hasText(g.getPhone()) || !StringUtils.hasText(g.getAadhar()))
            throw new IllegalArgumentException("Name, phone and aadhar are required");
        if (g.getAge() <= 0 || g.getLengthOfStay() <= 0 || g.getMembers() <= 0)
            throw new IllegalArgumentException("Age, lengthOfStay and members must be positive numbers");

        return repo.addGuest(g);
    }

    public List<Guest> getAllGuests() {
        return repo.findAll();
    }

    public boolean checkOut(Long id) {
        Guest g = repo.findById(id);
        if (g == null) return false;
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return repo.checkOut(id, now) > 0;
    }
}
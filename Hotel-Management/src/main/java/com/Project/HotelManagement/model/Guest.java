package com.Project.HotelManagement.model;

public class Guest {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String aadhar;
    private int age;
    private int lengthOfStay;
    private int members;
    private String checkInTime;  // kept as String for simple display
    private String checkOutTime;

    public Guest() {}

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAadhar() { return aadhar; }
    public void setAadhar(String aadhar) { this.aadhar = aadhar; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public int getLengthOfStay() { return lengthOfStay; }
    public void setLengthOfStay(int lengthOfStay) { this.lengthOfStay = lengthOfStay; }

    public int getMembers() { return members; }
    public void setMembers(int members) { this.members = members; }

    public String getCheckInTime() { return checkInTime; }
    public void setCheckInTime(String checkInTime) { this.checkInTime = checkInTime; }

    public String getCheckOutTime() { return checkOutTime; }
    public void setCheckOutTime(String checkOutTime) { this.checkOutTime = checkOutTime; }
}

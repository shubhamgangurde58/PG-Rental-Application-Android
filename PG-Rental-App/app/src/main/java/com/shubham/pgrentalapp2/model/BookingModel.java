package com.shubham.pgrentalapp2.model;

public class BookingModel {

    // ================= PG DETAILS =================
    private String pgName;
    private String rent;
    private String pgAddress;   // ✅ NEW
    private String pgCity;      // ✅ NEW

    // ================= STUDENT DETAILS =================
    private String studentName;
    private String studentEmail;
    private String studentPhone;

    // ================= OWNER + STATUS =================
    private String ownerEmail;
    private String status;

    // ==================================================
    // ✅ FULL CONSTRUCTOR (USE THIS GOING FORWARD)
    // ==================================================
    public BookingModel(
            String pgName,
            String rent,
            String pgAddress,
            String pgCity,
            String studentName,
            String studentEmail,
            String studentPhone,
            String ownerEmail,
            String status
    ) {
        this.pgName = pgName;
        this.rent = rent;
        this.pgAddress = pgAddress;
        this.pgCity = pgCity;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentPhone = studentPhone;
        this.ownerEmail = ownerEmail;
        this.status = status;
    }

    // ==================================================
    // ⚠️ OLD CONSTRUCTOR (KEEP – FOR OLD BOOKINGS)
    // ==================================================
    public BookingModel(
            String pgName,
            String rent,
            String studentName,
            String studentEmail,
            String studentPhone,
            String ownerEmail,
            String status
    ) {
        this.pgName = pgName;
        this.rent = rent;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentPhone = studentPhone;
        this.ownerEmail = ownerEmail;
        this.status = status;

        // ✅ IMPORTANT DEFAULTS (PREVENT NULL UI)
        this.pgAddress = "Address not available";
        this.pgCity = "";
    }

    // ================= GETTERS =================
    public String getPgName() {
        return pgName;
    }

    public String getRent() {
        return rent;
    }

    public String getPgAddress() {
        return pgAddress;
    }

    public String getPgCity() {
        return pgCity;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public String getStatus() {
        return status;
    }

    // ================= SETTERS =================
    public void setStatus(String status) {
        this.status = status;
    }

    public void setPgAddress(String pgAddress) {
        this.pgAddress = pgAddress;
    }

    public void setPgCity(String pgCity) {
        this.pgCity = pgCity;
    }
}

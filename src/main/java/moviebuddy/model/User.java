package moviebuddy.model;

import java.time.LocalDate;

public class User {
    private int accountId;
    private String userName;
    private String email;
    private byte[] hashPassword;
    private byte[] salt;
    private String zip;
    private int buddyPoints;
    private LocalDate endDate;
    private boolean autoRenew;
    private int staffId;
    private String role;

    public int getAccountId() {
        return accountId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public byte[] getHashPassword() {
        return hashPassword;
    }

    public byte[] getSalt() {
        return salt;
    }

    public String getZip() {
        return zip;
    }

    public int getBuddyPoints() {
        return buddyPoints;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean getAutoRenew() {
        return autoRenew;
    }

    public int getStaffId() {
        return staffId;
    }

    public String getRole() {
        return role;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHashPassword(byte[] hashPassword) {
        this.hashPassword = hashPassword;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setBuddyPoints(int buddyPoints) {
        this.buddyPoints = buddyPoints;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setAutoRenew(boolean autoRenew) {
        this.autoRenew = autoRenew;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

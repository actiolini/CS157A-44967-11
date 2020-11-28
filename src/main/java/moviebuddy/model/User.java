package moviebuddy.model;

public class User {
    private int accountId;
    private String userName;
    private String email;
    private byte[] hashPassword;
    private byte[] salt;
    private String zip;

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
}

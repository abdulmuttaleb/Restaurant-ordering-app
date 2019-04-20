package com.isaiko.restaurantordering.Model;

public class User {
    private String mUserName, mPhoneNumber, mMail,mProfilePicture,userSelectedBranch,mUserState;
    private Address mUserAddress;
    public User() {
    }

    public User(String mUserName, String mMail, String mProfilePicture) {
        this.mUserName = mUserName;
        this.mMail = mMail;
        this.mProfilePicture = mProfilePicture;
    }

    //Singleton for user

    private static User currentUser = new User();
    public static User getInstance(){ return currentUser;}

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }

    public void clearUser(){
        currentUser = null;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getmMail() {
        return mMail;
    }

    public void setmMail(String mMail) {
        this.mMail = mMail;
    }

    public Address getmUserAddress() {
        return mUserAddress;
    }

    public void setmUserAddress(Address mUserAddress) {
        this.mUserAddress = mUserAddress;
    }

    public String getmProfilePicture() {
        return mProfilePicture;
    }

    public void setmProfilePicture(String mProfilePicture) {
        this.mProfilePicture = mProfilePicture;
    }

    public String getUserSelectedBranch() {
        return userSelectedBranch;
    }

    public void setUserSelectedBranch(String userSelectedBranch) {
        this.userSelectedBranch = userSelectedBranch;
    }
}

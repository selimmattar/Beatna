package com.mporject.interns.beatna;

public class ItemDiscuss {
    int id;
    int background;
    String itemTitle;
    int profilePhoto;
    String nbFollowers;
    String dateDiscuss;
    String contentDiscuss;


    public ItemDiscuss() {
    }

    public ItemDiscuss(String dateDiscuss, String contentDiscuss, int id, int background, String itemTitle, int profilePhoto, String nbFollowers) {
        this.dateDiscuss=dateDiscuss;
        this.contentDiscuss=contentDiscuss;
        this.id=id;
        this.background = background;
        this.itemTitle = itemTitle;
        this.profilePhoto = profilePhoto;
        this.nbFollowers = nbFollowers;
    }

    public String getDateDiscuss() {
        return dateDiscuss;
    }

    public void setDateDiscuss(String dateDiscuss) {
        this.dateDiscuss = dateDiscuss;
    }

    public String getContentDiscuss() {
        return contentDiscuss;
    }

    public void setContentDiscuss(String contentDiscuss) {
        this.contentDiscuss = contentDiscuss;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public int getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(int profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getNbFollowers() {
        return nbFollowers;
    }

    public void setNbFollowers(String nbFollowers) {
        this.nbFollowers = nbFollowers;
    }
}

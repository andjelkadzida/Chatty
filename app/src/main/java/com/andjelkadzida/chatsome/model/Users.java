package com.andjelkadzida.chatsome.model;

public class Users
{
    private String id;
    private String imageUrl;
    private String status;
    private String username;
    private String search;

    public Users(String id, String imageUrl, String username, String status, String search)
    {
        this.id = id;
        this.imageUrl = imageUrl;
        this.status = status;
        this.username = username;
        this.search = search;
    }

    public Users()
    {
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getSearch()
    {
        return search;
    }

    public void setSearch(String search)
    {
        this.search = search;
    }

    @Override
    public String toString()
    {
        return "Users{" +
                "id='" + id + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", status='" + status + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
package com.example.fsh_noticet;

import java.io.Serializable;

public class Upload implements Serializable {
    private transient String title;
    private transient String departmentName;
    private transient String publisherName;
    private transient String body;
    private transient String postImageUrl;
    private transient String postId;
    public Upload(){
        //Necessary
    }

    public Upload(String title, String department, String username, String uBody, String pIUrl,String postId) {
        if(title.trim().equals("")){
            title="no title";
        }
        if(department.trim().equals("")){
            department="department not mentioned";
        }
        if(username.trim().equals("")){
            username="anonymous";
        }
        if(uBody.trim().equals("")){
            uBody="no body";
        }
        if(pIUrl.trim().equals("")){
            pIUrl="No Image";
        }
        this.title = title;
        this.departmentName = department;
        this.publisherName = username;
        this.body=uBody;
        this.postImageUrl=pIUrl;
        this.postId=postId;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setDepartmentNames(String dept){
        this.departmentName=dept;
    }
    public String getDepartmentNames(){
        return this.departmentName;
    }
    public void setPublisherNames(String uName){
        this.publisherName=uName;
    }
    public String getPublisherNames(){
        return this.publisherName;
    }
    public void setBody(String uBody){
        this.body=uBody;
    }
    public String getBody(){
        return this.body;
    }
    public void setPostImageUrl(String pIUrl){
        this.postImageUrl=pIUrl;
    }
    public String getPostImageUrl(){
        return postImageUrl;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}

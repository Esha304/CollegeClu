package com.example.findfun;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseClassName;
import java.util.ArrayList;
import java.util.List;

@ParseClassName("Post")
public class Post extends ParseObject {
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_KEY = "createdAt";
    public static final String KEY_LIKED_BY = "likedBy";

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }
    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }
    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public String likeCountDisplayText() {
        String likesText = String.valueOf(getLikedBy().size());
        likesText += getLikedBy().size() == 1 ? " like" : " likes";
        return likesText;
    }

    public List<String> getLikedBy() {
        List<String> likedBy = getList(KEY_LIKED_BY);
        if(likedBy == null) {
            likedBy = new ArrayList<>();
        }
        return likedBy;
    }

    public void setLikedBy(List<String> likedBy) {
        put(KEY_LIKED_BY, likedBy);
    }

}
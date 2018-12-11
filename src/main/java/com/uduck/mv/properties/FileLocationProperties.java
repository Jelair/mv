package com.uduck.mv.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "uduck.mv.filelocation")
public class FileLocationProperties {

    public static final String WINDOW_VIDEO_LOCATION = "E:/upload/videos/";
    public static final String LINUX_VIDEO_LOCARION = "/srv/upload/videos/";
    public static final String WINDOW_COVER_LOCATION = "E:/upload/covers/";
    public static final String LINUX_COVER_LOCATION = "/srv/upload/covers/";
    public static final String WINDOW_AVATAR_LOCATION = "E:/upload/avatars/";
    public static final String LINUX_AVATAR_LOCATION = "/srv/upload/avatars/";

    private String videoLocation = LINUX_VIDEO_LOCARION;

    private String coverLocation = LINUX_COVER_LOCATION;

    private String avatarLocation = LINUX_AVATAR_LOCATION;

    public String getVideoLocation() {
        return videoLocation;
    }

    public void setVideoLocation(String videoLocation) {
        this.videoLocation = videoLocation;
    }

    public String getCoverLocation() {
        return coverLocation;
    }

    public void setCoverLocation(String coverLocation) {
        this.coverLocation = coverLocation;
    }

    public String getAvatarLocation() {
        return avatarLocation;
    }

    public void setAvatarLocation(String avatarLocation) {
        this.avatarLocation = avatarLocation;
    }
}

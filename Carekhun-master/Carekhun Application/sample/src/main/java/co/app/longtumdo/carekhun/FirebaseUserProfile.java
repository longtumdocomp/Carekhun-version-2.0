package co.app.longtumdo.carekhun;

public class FirebaseUserProfile {

    private String header;

    private String profileContent;

    public FirebaseUserProfile(String header, String profileContent) {
        this.header = header;
        this.profileContent = profileContent;
    }

    public String getHeader() {
        return header;
    }

    public String getProfileContent() {
        return profileContent;
    }
}

package joaorodrigues.mobileimgur.model;

public class Image {
    private String id;
    private String title;
    private String description;
    private long datetime;
    private String cover;
    private String coverWidth;
    private String coverHeight;
    private String accountUrl;
    private int accountId;
    private String privacy;
    private String layout;
    private int views;
    private String link;
    private int ups;
    private int downs;
    private int score;
    private boolean isAlbum;
    private boolean favorite;
    private boolean nsfw;
    private String section;
    private int commentCount;
    private String topic;
    private int topicId;
    private int imagesCount;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getDatetime() {
        return datetime;
    }

    public String getCover() {
        return cover;
    }

    public String getCoverWidth() {
        return coverWidth;
    }

    public String getCoverHeight() {
        return coverHeight;
    }

    public String getAccountUrl() {
        return accountUrl;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getPrivacy() {
        return privacy;
    }

    public String getLayout() {
        return layout;
    }

    public int getViews() {
        return views;
    }

    public String getLink() {
        return link;
    }

    public int getUps() {
        return ups;
    }

    public int getDowns() {
        return downs;
    }

    public int getScore() {
        return score;
    }

    public boolean isAlbum() {
        return isAlbum;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public boolean isNsfw() {
        return nsfw;
    }

    public String getSection() {
        return section;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public String getTopic() {
        return topic;
    }

    public int getTopicId() {
        return topicId;
    }

    public int getImagesCount() {
        return imagesCount;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setCoverWidth(String coverWidth) {
        this.coverWidth = coverWidth;
    }

    public void setCoverHeight(String coverHeight) {
        this.coverHeight = coverHeight;
    }

    public void setAccountUrl(String accountUrl) {
        this.accountUrl = accountUrl;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setUps(int ups) {
        this.ups = ups;
    }

    public void setDowns(int downs) {
        this.downs = downs;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setAlbum(boolean isAlbum) {
        this.isAlbum = isAlbum;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setNsfw(boolean nsfw) {
        this.nsfw = nsfw;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public void setImagesCount(int imagesCount) {
        this.imagesCount = imagesCount;
    }

}

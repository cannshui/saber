package dn.cs.saber.domain;

import java.util.Date;

public class Article {

	private int id;

	/** True article title. */
	private String title;

	/** Author of article. */
	private User author;

	/** Preview of this article. */
	private String preview;

	/** Article click count. */
	private int hitCount;

	/** Article read count. */
	private int readCount;

	/** Useful? */
	private int usefulCount;

	/** Average rating. */
	private int ratingCount;

	/** Average rating. */
	private float rating;

	/** Article type. */
	private int type;

	private String tag;

	/** Tags of this article. */
	private Tag[] tags;

	private int commentCount;

	/** Published time. */
	private Date publishTime;

	/** Republished after some fix. */
	private Date rePublishTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getHitCount() {
		return hitCount;
	}

	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public int getUsefulCount() {
		return usefulCount;
	}

	public void setUsefulCount(int usefulCount) {
		this.usefulCount = usefulCount;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Tag[] getTags() {
		return tags;
	}

	public void setTags(Tag[] tags) {
		this.tags = tags;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Date getRePublishTime() {
		return rePublishTime;
	}

	public void setRePublishTime(Date rePublishTime) {
		this.rePublishTime = rePublishTime;
	}

	public String getPreview() {
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(int ratingCount) {
		this.ratingCount = ratingCount;
	}

	@Override
	public String toString() {
		return "Article: { id: " + id + ", title: " + title + ", author: "
				+ author + ", preview: " + preview + ", hitCount: " + hitCount
				+ ", readCount: " + readCount + ", usefulCount: " + usefulCount
				+ ", ratingCount: " + ratingCount + ", rating: " + rating
				+ ", type: " + type + ", tags: " + tags + ", commentCount: "
				+ commentCount + ", publishTime: " + publishTime
				+ ", rePublishTime: " + rePublishTime + " }";
	}

}
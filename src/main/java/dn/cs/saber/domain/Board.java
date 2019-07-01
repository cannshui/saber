package dn.cs.saber.domain;

import java.util.Date;
import java.util.List;

/**
 * Board messages.
 *
 * @author Nen Den
 */
public class Board {

    private int id;

    private Integer userId;

    private User user;

    private String ip;

    private String content;

    private Integer reply;

    private List<Board> replies;

    private Date cts;

    private Date uts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Board> getReplies() {
        return replies;
    }

    public void setReplies(List<Board> replies) {
        this.replies = replies;
    }

    public Integer getReply() {
        return reply;
    }

    public void setReply(Integer reply) {
        this.reply = reply;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCts() {
        return cts;
    }

    public void setCts(Date cts) {
        this.cts = cts;
    }

    public Date getUts() {
        return uts;
    }

    public void setUts(Date uts) {
        this.uts = uts;
    }

    @Override
    public String toString() {
        return "Board: { id: " + id + ", user: " + user + ", content: "
                + content + ", reply: " + reply + ", replies: " + replies
                + ", cts: " + cts + ", uts: " + uts + " }";
    }

}

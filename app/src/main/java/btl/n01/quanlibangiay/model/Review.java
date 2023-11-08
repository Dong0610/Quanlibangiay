package btl.n01.quanlibangiay.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Review implements Serializable {
    private String reviewId = "";
    private String reviewUserId = "";
    private String reviewUserName = "";
    private String reviewUserImg = "";
    private float reviewStar = 0.0f;
    private String reviewComment = "";
    private String reviewTime = "";

    public Review() {
    }

    public Review(String reviewId, String reviewUserId, String reviewUserName, String reviewUserImg, float reviewStar, String reviewComment, String reviewTime) {
        this.reviewId = reviewId;
        this.reviewUserId = reviewUserId;

        this.reviewUserName = reviewUserName;
        this.reviewUserImg = reviewUserImg;
        this.reviewStar = reviewStar;
        this.reviewComment = reviewComment;
        this.reviewTime = reviewTime;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewUserId() {
        return reviewUserId;
    }

    public void setReviewUserId(String reviewUserId) {
        this.reviewUserId = reviewUserId;
    }
    public String getReviewUserName() {
        return reviewUserName;
    }

    public void setReviewUserName(String reviewUserName) {
        this.reviewUserName = reviewUserName;
    }

    public String getReviewUserImg() {
        return reviewUserImg;
    }

    public void setReviewUserImg(String reviewUserImg) {
        this.reviewUserImg = reviewUserImg;
    }

    public float getReviewStar() {
        return reviewStar;
    }

    public void setReviewStar(float reviewStar) {
        this.reviewStar = reviewStar;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }
}

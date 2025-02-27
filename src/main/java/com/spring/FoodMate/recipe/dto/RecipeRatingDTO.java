package com.spring.FoodMate.recipe.dto;

import java.sql.Date;

public class RecipeRatingDTO {
	private int cmt_rcp_rating_id;  // 댓글 ID
	private int rcp_id;  // 레시피 ID
    private String byr_id;  // 작성자 ID
    private int rating;  // 별점
    private String comments;  // 댓글
    private Date create_date;  // 작성일
    private Date update_date;  // 수정일
    
	private String nickname;     // JOIN을 통해 가져온 NICKNAME
    private String mainimg_path;      
    private String img_path; // JOIN을 통해 가져온 프로파일 IMG_PATH
    
    public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
	

    public String getMainimg_path() {
		return mainimg_path;
	}
	public void setMainimg_path(String mainimg_path) {
		this.mainimg_path = mainimg_path;
	}
	public int getCmt_rcp_rating_id() {
		return cmt_rcp_rating_id;
	}
	public void setCmt_rcp_rating_id(int cmt_rcp_rating_id) {
		this.cmt_rcp_rating_id = cmt_rcp_rating_id;
	}
	public int getRcp_id() {
		return rcp_id;
	}
	public void setRcp_id(int rcp_id) {
		this.rcp_id = rcp_id;
	}
	public String getByr_id() {
		return byr_id;
	}
	public void setByr_id(String byr_id) {
		this.byr_id = byr_id;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
   public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	public String getImg_path() {
		return img_path;
	}
	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}
	
	
}

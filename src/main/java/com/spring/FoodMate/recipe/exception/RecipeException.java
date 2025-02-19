package com.spring.FoodMate.recipe.exception;

public class RecipeException extends RuntimeException {
	private static final long serialVersionUID = 11L;
	
	private String errorMessage;
	private int errorCode;
	// 301 : 검색한 rcp_id가 DB에 존재하지 않음
	
	public String message() {
		return errorMessage;
	}
	public int getErrorCode() {
		return errorCode;
	}
	
    public RecipeException(int errorCode) {
        this.errorCode = errorCode;
    }

    public RecipeException(String message, int errorCode) {
        this.errorMessage = message;
        this.errorCode = errorCode;
    }
    
    public RecipeException(String message, Throwable cause) {
    	super(message, cause);
    }
}
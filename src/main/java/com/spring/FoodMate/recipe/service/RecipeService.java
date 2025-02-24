package com.spring.FoodMate.recipe.service;

import java.util.List;
import java.util.Map;

import com.spring.FoodMate.recipe.dto.RecipeCategoryDTO;
import com.spring.FoodMate.recipe.dto.RecipeDTO;
import com.spring.FoodMate.recipe.dto.RecipeIngredientDTO;
import com.spring.FoodMate.recipe.dto.RecipeStepDTO;

public interface RecipeService {

    // 레시피 등록 후 레시피 ID 반환
    int addRecipe(RecipeDTO recipe) throws Exception;

    // 재료 삽입
    void insertRecipeIngredients(List<RecipeIngredientDTO> ingredients) throws Exception;

    // 단계 삽입
    void insertRecipeSteps(List<RecipeStepDTO> steps) throws Exception;

    // 레시피 목록 조회
    List<RecipeDTO> selectRecipeList() throws Exception;
    
    // 레시피 목록 아이디 조회
    List<RecipeDTO> selectRecipeListByrID(String byr_id) throws Exception;
    
    // 레시피 하나 조회(순서랑 재료는 안들어있음)
    RecipeDTO recipe(int rcp_id) throws Exception;
    
    // 레시피 하나에 대한 재료만 조회
    List<RecipeIngredientDTO> recipeIngrds(int rcp_id) throws Exception;
    
    // 레시피 하나에 대한 순서만 조회
    List<RecipeStepDTO> recipeSteps(int rcp_id) throws Exception;
    
    //레시피 상세 조회에 필요한 모든 데이터 쌈싸주기
    Map<String, Object> selectRecipeDetail(int rcp_id) throws Exception;
    
    List<RecipeIngredientDTO> getRecipeIngrd(int rcp_id);
    
    List<RecipeCategoryDTO> getGrandCategoryList();
	
	List<RecipeCategoryDTO> getChildCategoryList(int category_id);
	
	List<RecipeCategoryDTO> categoryStep(int category_id);
}
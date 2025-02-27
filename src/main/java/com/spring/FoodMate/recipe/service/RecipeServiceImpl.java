package com.spring.FoodMate.recipe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.FoodMate.common.exception.DBException;
import com.spring.FoodMate.product.dto.CategoryDTO;
import com.spring.FoodMate.recipe.dao.RecipeDAO;
import com.spring.FoodMate.recipe.dto.RecipeCategoryDTO;
import com.spring.FoodMate.recipe.dto.RecipeDTO;
import com.spring.FoodMate.recipe.dto.RecipeIngredientDTO;
import com.spring.FoodMate.recipe.dto.RecipeRatingDTO;
import com.spring.FoodMate.recipe.dto.RecipeStepDTO;
import com.spring.FoodMate.recipe.exception.RecipeException;

@Service
@Transactional
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeDAO recipeDAO;
   

    // 레시피 등록 후 recipeId 반환
    @Override
    public int addRecipe(RecipeDTO recipe) throws Exception {
        // 레시피 등록
        recipeDAO.insertRecipe(recipe);

        // 레시피 ID 반환
        return recipeDAO.selectLastInsertedRecipeId();
    }

    // 재료 삽입
    @Override
    public void insertRecipeIngredients(List<RecipeIngredientDTO> ingredients) throws Exception {
        for (RecipeIngredientDTO ingredient : ingredients) {
            recipeDAO.insertRecipeIngredients(ingredient);  // 재료 삽입
        }
    }

    // 단계 삽입
    @Override
    public void insertRecipeSteps(List<RecipeStepDTO> steps) throws Exception {
        for (RecipeStepDTO step : steps) {
            recipeDAO.insertRecipeSteps(step);  // 단계 삽입
        }
    }

    // 레시피 목록 조회
    @Override
    public List<RecipeDTO> selectRecipeList() throws Exception {
        return recipeDAO.selectRecipeList();  // 레시피 목록 조회
    }
    
    @Override
    public List<RecipeDTO> selectRecipeListByrID(String byr_id) throws Exception {
        return recipeDAO.selectRecipeListByrID(byr_id);  // 레시피 목록 조회
    }
    // 레시피 하나 조회
    @Override
    public RecipeDTO recipe(int rcp_id) throws Exception {
    	return recipeDAO.selectRecipeDetail(rcp_id);
    }
    
    // 레시피 하나에 대한 재료만 조회
    @Override
    public List<RecipeIngredientDTO> recipeIngrds(int rcp_id) throws Exception {
    	return recipeDAO.selectIngredientDetail(rcp_id);
    }
    
 
    // 레시피 하나에 대한 순서도만 조회
    @Override
    public List<RecipeStepDTO> recipeSteps(int rcp_id) throws Exception {
    	return recipeDAO.selectStepDetail(rcp_id);
    }

    // 레시피 상세 조회에 필요한 모든 데이터 쌈싸주기
    @Override
    public Map<String, Object> selectRecipeDetail(int rcp_id) throws Exception {
        Map<String, Object> recipeDetail = new HashMap<>();
        recipeDetail.put("recipe", recipe(rcp_id));
        recipeDetail.put("ingredients", recipeIngrds(rcp_id));
        recipeDetail.put("steps", recipeSteps(rcp_id));
        RecipeDTO recipe = recipeDAO.selectRecipeDetail(rcp_id);
        recipeDetail.put("recipe", recipe);

        List<RecipeIngredientDTO> ingredientList = recipeDAO.selectIngredientDetail(rcp_id);
        recipeDetail.put("ingredients", ingredientList);

        List<RecipeStepDTO> stepList = recipeDAO.selectStepDetail(rcp_id);
        recipeDetail.put("steps", stepList);
        return recipeDetail;
    }
    
    // 레시피 id 입력후 재료정보들 뽑아옴
    @Override
    public List<RecipeIngredientDTO> getRecipeIngrd(int rcp_id) {
    	try {
    		return recipeDAO.selectIngredientDetail(rcp_id);
    	} catch (DBException e) {
    		throw new RecipeException("RecipeServiceImpl에서 DB예외 전달.", e);
    	} catch (Exception e) {
    		throw new RecipeException("RecipeServiceImpl.getRecipeIngrd 에러!", e);
    	}
    }
    // 레시피카테고리 최상위만 가져옴(처음에 보여줄 카테고리 처리용)
    @Override
    public List<RecipeCategoryDTO> getGrandCategoryList() {
		try {
			return recipeDAO.getGrandCategoryList();
		} catch (DBException e) {
			throw new RecipeException("RecipeServiceImpl에서 DB예외 전달.", e);
		} catch (Exception e) {
			throw new RecipeException("RecipeServiceImpl.getGrandCategoryList 에러!", e);
		}
	}
	
    // 레시피카테고리 자식만 가져옴(카테고리 선택시 ajax 로 자식카테고리 가져오는거임)
    @Override
	public List<RecipeCategoryDTO> getChildCategoryList(int category_id) {
		try {
			return recipeDAO.getChildCategoryList(category_id);
		} catch (DBException e) {
			throw new RecipeException("RecipeServiceImpl에서 DB예외 전달.", e);
		} catch (Exception e) {
			throw new RecipeException("RecipeServiceImpl.getChildCategoryList 에러! category_id = '" + category_id + "'", e);
		}
	}
	
    // 한 카테고리 아이디 입력하면 최상위 카테고리까지 쫙 출력.
    // 레시피의 카테고리아이디 받아와서 넣고 응답받아서 레시피상세정보에 한식 / 면 / 국수 이런식으로 표시해주기 위함임.
    @Override
	public List<RecipeCategoryDTO> categoryStep(int category_id) {
		try {
			return recipeDAO.getCategoryStep(category_id);
		} catch (DBException e) {
			throw new RecipeException("RecipeServiceImpl에서 DB예외 전달.", e);
		} catch (Exception e) {
			throw new RecipeException("RecipeServiceImpl.categoryStep 에러! category_id = '" + category_id + "'", e);
		}
	}   
    
 // 재료 카테고리 최상위만 가져옴(처음에 보여줄 카테고리 처리용)
    @Override
    public List<CategoryDTO> select_all_IngrdCategory() {
		try {
			return recipeDAO.select_all_IngrdCategory();
		} catch (DBException e) {
			throw new RecipeException("RecipeServiceImpl에서 DB예외 전달.", e);
		} catch (Exception e) {
			throw new RecipeException("RecipeServiceImpl.getGrandCategoryList 에러!", e);
		}
	}
    
 // 재료 카테고리 자식만 가져옴(카테고리 선택시 ajax 로 자식카테고리 가져오는거임)
    @Override
	public List<CategoryDTO> select_Child_IngrdCategory(int ingrd_category_id) {
		try {
			return recipeDAO.select_Child_IngrdCategory(ingrd_category_id);
		} catch (DBException e) {
			throw new RecipeException("RecipeServiceImpl에서 DB예외 전달.", e);
		} catch (Exception e) {
			throw new RecipeException("RecipeServiceImpl.select_Child_IngrdCategory 에러! category_id = '" + ingrd_category_id + "'", e);
		}
	}
    
	//후기 저장
    @Override
    public void addRecipeRating(RecipeRatingDTO ratingDTO) throws Exception {
        recipeDAO.insertRecipeRating(ratingDTO);  // 후기를 DB에 저장
    }
    
    @Override
    public List<RecipeRatingDTO> getRatingsByRecipeId(int rcp_id) throws Exception {
        return recipeDAO.getRatingsByRecipeId(rcp_id);
    }
    
    @Override
    public void updateRecipeRating(RecipeRatingDTO ratingDTO) {
        recipeDAO.updateRecipeRating(ratingDTO);
    }
}
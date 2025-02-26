package com.spring.FoodMate.recipe.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.FoodMate.common.UtilMethod;
import com.spring.FoodMate.member.dto.BuyerDTO;
import com.spring.FoodMate.product.dto.CategoryDTO;
import com.spring.FoodMate.recipe.dto.RecipeCategoryDTO;
import com.spring.FoodMate.recipe.dto.RecipeDTO;
import com.spring.FoodMate.recipe.dto.RecipeIngredientDTO;
import com.spring.FoodMate.recipe.dto.RecipeRatingDTO;
import com.spring.FoodMate.recipe.dto.RecipeStepDTO;
import com.spring.FoodMate.recipe.service.RecipeService;

@Controller
public class RecipeControllerImpl implements RecipeController {

    @Autowired
    private RecipeService recipeService;
    
    /*
    @RequestMapping("/recipe/selectRecipeByCategory")
    public ModelAndView selectRecipeByCategory(@RequestParam("categoryId") int categoryId, HttpServletRequest request) throws Exception {
        String viewName = (String) request.getAttribute("viewName");
        HttpSession session = request.getSession();

        // 카테고리 아이디에 맞는 레시피를 조회
        List<RecipeVO> recipeList = categoryService.getRecipesByCategory(categoryId);

        // 최근 본 레시피 리스트 갱신
        RecentRecipeView(categoryId, recipeList, session);

        // 업데이트된 recentRecipeList를 jsp에 전달
        List<RecipeVO> recentRecipeList = (List<RecipeVO>) session.getAttribute("recentRecipeList");
        ModelAndView mav = new ModelAndView(viewName);
        mav.setViewName("common/layout");
        mav.addObject("recipeList", recipeList);
        mav.addObject("recentRecipeList", recentRecipeList);
        mav.addObject("showNavbar", true);
        mav.addObject("title", "카테고리별 레시피");
        mav.addObject("body", "/WEB-INF/views/recipe/recipeList.jsp"); 
        return mav;
    }*/


    // 레시피 수정 폼
    @RequestMapping(value = "/recipe/recipe_Edit", method = RequestMethod.GET)
    public ModelAndView RecipeEditForm(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("common/layout");
        mav.addObject("showNavbar", true);
        mav.addObject("title","레시피 수정 페이지");
        mav.addObject("body", "/WEB-INF/views/recipe/recipe_Edit.jsp");
        return mav;
    }
    
    
    // 레시피 목록 조회
    @RequestMapping(value = "/recipe/recipe_list", method = RequestMethod.GET)
    public ModelAndView selectRecipeList(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("recipeList", recipeService.selectRecipeList()); //서비스에 selectRecipeList메소드있어야함
        mav.setViewName("common/layout");
        mav.addObject("showNavbar", true);
        mav.addObject("title","레시피 목록");
        mav.addObject("body", "/WEB-INF/views/recipe/recipe_list.jsp");
        return mav;
    }
    
    //레시피 상세조회
    @RequestMapping(value = "/recipe/recipe_Detail", method = RequestMethod.GET)
    public ModelAndView selectRecipeDetail(@RequestParam("rcp_id") int rcp_id, HttpServletRequest request) throws Exception {
    	ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        Map<String, Object> recipeMap = recipeService.selectRecipeDetail(rcp_id);
        RecipeDTO recipe = (RecipeDTO)recipeMap.get("recipe");
        Integer category_id = recipe.getCategory_id();
	        
	        if(category_id!=null) {
	        	List<RecipeCategoryDTO> categoryStep = recipeService.categoryStep(category_id);
	        	mav.addObject("category", categoryStep);
	        }
//      // 최근 본 레시피 리스트 갱신
//      // 지금 세션에 레시피DTO를 다 주고있는거같은데, 세션에는 레시피id만 주고 
	      RecipeDTO recipeDTO = (RecipeDTO) recipeMap.get("recipe");
	      RecentRecipeView(rcp_id, recipeDTO, session);
//
//      // 지금 레시피 상세조회 페이지에 그냥 최근 본 레시피 파트까지 넣은거같은데, 
//      // "최근 본 레시피" 를 jsp include 하고 거기서 알아서 처리하게 하면 어떨까요?
//      // 지금 이대로면 모든 메서드마다 리센트레시피리스트관련처리를 해야하는데.
        
        
        
        // 업데이트된 recentRecipeList를 jsp에 전달
        mav.addObject("recentRecipeList", (List<RecipeDTO>) session.getAttribute("recentRecipeList"));
        
        // 레시피맵에서 필요한거 뿌림
        mav.addObject("recipe", recipeMap.get("recipe"));
        mav.addObject("ingredients", recipeMap.get("ingredients"));
        mav.addObject("steps", recipeMap.get("steps"));
        
        // 댓글 및 후기 목록 조회 (레시피에 달린 댓글과 그에 대한 작성자 정보 포함)
        List<RecipeRatingDTO> ratingList = recipeService.getRatingsByRecipeId(rcp_id);
        mav.addObject("ratingList", ratingList);
        
        mav.setViewName("common/layout");
        mav.addObject("showNavbar", true);
        mav.addObject("title", "레시피 상세");
        mav.addObject("body", "/WEB-INF/views/" + UtilMethod.getViewName(request) + ".jsp"); 
        return mav;
    }

    private void RecentRecipeView(int rcp_Id, RecipeDTO recipeVO, HttpSession session) {
        // 세션에서 '최근 본 레시피' 리스트를 가져옴
        List<RecipeDTO> recentRecipeList = (List<RecipeDTO>) session.getAttribute("recentRecipeList");

        // recentRecipeList가 null인 경우 빈 리스트로 초기화
        if (recentRecipeList == null) {
            recentRecipeList = new ArrayList<>();
        }

        // ▼비상용 코드 최근본 레시피에 불량값이 들어올경우 주석을풀고 쓸것
//        if (recentRecipeList.isEmpty() || recentRecipeList.get(0) == null) {
//            recentRecipeList.clear();
//        }

        // 최근 본 레시피가 세션에 존재할 경우
        if (recentRecipeList != null) {
            boolean alreadyExisted = false;

            // 이미 해당 레시피가 최근 본 리스트에 있는지 확인
            for (int i = 0; i < recentRecipeList.size(); i++) {
                RecipeDTO _recipe = recentRecipeList.get(i);
                if (rcp_Id == _recipe.getRcp_id()) {
                    alreadyExisted = true;
                    // 이미 리스트에 있으면 해당 레시피를 가장 앞에 오도록 이동
                    recentRecipeList.remove(i);  // 기존 항목 삭제
                    recentRecipeList.add(0, _recipe);  // 최근 항목으로 추가
                    break;
                }
            }

            // 최근 본 레시피 리스트에 없다면
            if (!alreadyExisted) {
                // 리스트가 20개 이상일 경우, 가장 오래된 레시피를 제거
                if (recentRecipeList.size() >= 20) {
                    recentRecipeList.remove(recentRecipeList.size() - 1);  // 가장 오래된 레시피 제거
                }
                // 새로운 레시피를 리스트 앞에 추가
                recentRecipeList.add(0, recipeVO);
            }
        }       
        // 세션에 최근 본 레시피 리스트 저장
        session.setAttribute("recentRecipeList", recentRecipeList);
        session.setAttribute("recentRecipeListNum", recentRecipeList.size());
    }

    //검색 기능 수정 필요
    @RequestMapping(value = "/recipe/search_recipe_list", method = RequestMethod.GET)
    public ModelAndView searchList(HttpServletRequest request) throws Exception {
        
        String searchKeyword = request.getParameter("searchKeyword");

        ModelAndView mav = new ModelAndView();
        
       
        mav.addObject("recipeList", recipeService.selectRecipeList()); 
        mav.setViewName("common/layout");
        mav.addObject("showHeadermenu", true);
        mav.addObject("title", "레시피 목록");
        mav.addObject("body", "/WEB-INF/views/recipe/recipe_list.jsp");
        
        return mav;
    }
    
    // 레시피 작성 폼으로 이동
	@RequestMapping(value="/recipe/recipe_Add", method=RequestMethod.GET)
	public ModelAndView addRecipeForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("common/layout");
		mav.addObject("showNavbar", true);
		mav.addObject("title", "FoodMate - 레시피 작성");
		mav.addObject("body", "/WEB-INF/views" +  UtilMethod.getViewName(request) + ".jsp");
		mav.addObject("categories", recipeService.getGrandCategoryList());
		mav.addObject("ingrd_categories", recipeService.select_all_IngrdCategory());
		return mav;
	}
	
	// jsp에서 레시피 작성 눌렀을때 처리 메소드
	@Override
	@RequestMapping(value = "/recipe/addNewRecipe", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> addNewRecipe(
	    @RequestParam("title") String title,
	    @RequestParam("food_name") String foodName,
	    @RequestParam("category_id") Integer category_id,
	    @RequestParam("req_time") String reqTime,
	    @RequestParam("description") String description,
	    
	    @RequestParam(value = "mainimg_path", required = false) MultipartFile mainImg,
	    @RequestParam(value = "ingrd_name") List<String> ingredientsNames,
	    @RequestParam(value = "ingrd_qty") List<Integer> ingredientsQty,
	    @RequestParam(value = "unit") List<String> ingredientsUnits,
	    @RequestParam(value = "rcp_step") List<Integer> stepNumbers,
	    @RequestParam(value = "step_desc") List<String> stepDescriptions,
	    @RequestParam(value = "stepimg_path") List<MultipartFile> stepImages,
	    @RequestParam(value = "ingrd_category_id") List<Integer> ingredientsCategoryIds, 
	    HttpSession session
	) throws Exception {
	    System.out.println("가져온 레시피 카테고리 id 숫자는" + category_id);
	    System.out.println("가져온 재료 카테고리 id 숫자는" + ingredientsCategoryIds);
	    
	    
	    BuyerDTO buyerDTO = (BuyerDTO) session.getAttribute("buyerInfo");
	    String byr_id = buyerDTO.getByr_id();
	    
	    // 레시피 객체 생성
	    RecipeDTO recipe = new RecipeDTO();
	    recipe.setTitle(title);
	    recipe.setFood_name(foodName);
	    recipe.setCategory_id(category_id);
	    recipe.setReq_time(reqTime);
	    recipe.setDescription(description);
	    recipe.setByr_id(byr_id);
	    
	    if (!mainImg.isEmpty()) {
	        String mainImgPath = UtilMethod.saveRecipeImage(mainImg);
	        recipe.setMainimg_path(mainImgPath);
	    }
	    
	    int recipeId = recipeService.addRecipe(recipe);

	    // 재료 객체 생성 후 배치 삽입
	    List<RecipeIngredientDTO> ingredients = new ArrayList<>();
	    List<String> uniqueIngredients = new ArrayList<>();
	    
	    for (int i = 0; i < ingredientsNames.size(); i++) {
	        // 중복된 재료가 이미 리스트에 있으면 추가하지 않도록 함
	        if (!uniqueIngredients.contains(ingredientsNames.get(i))) {
	            RecipeIngredientDTO ingredient = new RecipeIngredientDTO();
	            ingredient.setRcp_id(recipeId);  // 생성된 레시피 ID
	            ingredient.setIngrd_name(ingredientsNames.get(i));
	            ingredient.setIngrd_qty(ingredientsQty.get(i));
	            ingredient.setUnit(ingredientsUnits.get(i));
	            ingredient.setCategory_id(ingredientsCategoryIds.get(i)); // 각 재료의 카테고리 ID 저장
	            ingredients.add(ingredient);
	            uniqueIngredients.add(ingredientsNames.get(i));
	        }
	    }
	    
	    recipeService.insertRecipeIngredients(ingredients);  // 재료 추가

	    // 단계 객체 생성 후 배치 삽입
	    List<RecipeStepDTO> steps = new ArrayList<>();
	    List<Integer> uniqueStepNumbers = new ArrayList<>();

	    for (int i = 0; i < stepNumbers.size(); i++) {
	        if (!uniqueStepNumbers.contains(stepNumbers.get(i))) {
	            RecipeStepDTO step = new RecipeStepDTO();
	            step.setRcp_id(recipeId);  // 생성된 레시피 ID
	            step.setRcp_step(stepNumbers.get(i));
	            step.setStep_desc(stepDescriptions.get(i));
	            if (!(stepImages == null || stepImages.isEmpty() || stepImages.get(0).getSize() == 0)) {
	                String stepImgPath = UtilMethod.saveRecipeImage(stepImages.get(i));
	                step.setStepimg_path(stepImgPath);
	            }
	            steps.add(step);
	            uniqueStepNumbers.add(stepNumbers.get(i));
	        }
	    }
	    
	    recipeService.insertRecipeSteps(steps);  // 단계 추가
	    
	    Map<String, Object> response = new HashMap<>();
	    response.put("message", "레시피 등록에 성공했습니다.");
	    response.put("status", "success");
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
	@RequestMapping(value="/recipe/getSubCategories/{category_id}", method=RequestMethod.GET)
	@ResponseBody
	public List<RecipeCategoryDTO> getSubCategories(@PathVariable("category_id") int category_id) throws Exception {
	    // 데이터베이스에서 category_id에 해당하는 자식 카테고리 가져오기
	    List<RecipeCategoryDTO> subCategories = recipeService.getChildCategoryList(category_id);
	    return subCategories;
	}
	
	@RequestMapping(value="/recipe/select_Sub_IngrdCategory/{ingrd_category_id}", method=RequestMethod.GET)
	@ResponseBody
	public List<CategoryDTO> select_Sub_IngrdCategory(@PathVariable("ingrd_category_id") int ingrd_category_id) throws Exception {
	    // 데이터베이스에서 category_id에 해당하는 자식 카테고리 가져오기
	    List<CategoryDTO> IngrdsubCategories = recipeService.select_Child_IngrdCategory(ingrd_category_id);
	    System.out.println(ingrd_category_id);
	    return IngrdsubCategories; 
	}
	
	//레시피후기작성
	

	@RequestMapping("/recipe/addRecipeRating")
	public String addRecipeRating(
	        @RequestParam("rcp_id") int rcp_id,  // 레시피 ID
	        @RequestParam("rating") int rating,  // 입력한 별점
	        @RequestParam("comments") String comments,  // 댓글
	        HttpSession session, 
	        RedirectAttributes redirectAttributes
	        ) {
	    
	    try {
	    	System.out.println("rcp_id: " + rcp_id);
	        System.out.println("rating: " + rating);
	        System.out.println("comments: " + comments);
	    	
	        // 세션에서 로그인한 사용자 정보 받기
	        BuyerDTO buyerDTO = (BuyerDTO) session.getAttribute("buyerInfo");
	        String byr_id = buyerDTO.getByr_id();  // 댓글 작성자의 ID
	        System.out.println("byr_id: " + byr_id);
	        // RecipeRatingDTO에 데이터 세팅
	        RecipeRatingDTO ratingDTO = new RecipeRatingDTO();
	        ratingDTO.setRcp_id(rcp_id);
	        ratingDTO.setByr_id(byr_id);
	        ratingDTO.setRating(rating);
	        ratingDTO.setComments(comments);

	        // 서비스 호출하여 DB에 후기 저장
	        recipeService.addRecipeRating(ratingDTO);

	        // 성공 메시지 추가
	        redirectAttributes.addFlashAttribute("message", "후기가 성공적으로 등록되었습니다.");
	    } catch (Exception e) {
	        // 오류 발생 시
	        redirectAttributes.addFlashAttribute("error", "후기 등록 중 오류가 발생했습니다.");
	    }

	    return "redirect:/recipe/recipe_Detail?rcp_id=" + rcp_id;
	}
	
	@RequestMapping("/recipe/updateRecipeRating")
	public String updateRecipeRating(
	        @RequestParam("cmt_rcp_rating_id") int cmt_rcp_rating_id,  // 댓글 ID
	        @RequestParam("rcp_id") int rcp_id,  // 레시피 ID (리다이렉트용)
	        @RequestParam("rating") int rating,  // 수정할 별점
	        @RequestParam("comments") String comments,  // 수정할 댓글 
	        RedirectAttributes redirectAttributes) {

	    try {

	        // RecipeRatingDTO에 데이터 세팅 (rcp_id는 안 넣음!)
	        RecipeRatingDTO ratingDTO = new RecipeRatingDTO();
	        ratingDTO.setCmt_rcp_rating_id(cmt_rcp_rating_id);
	        ratingDTO.setRating(rating);
	        ratingDTO.setComments(comments);

	        // 서비스 호출하여 후기 수정
	        recipeService.updateRecipeRating(ratingDTO);

	        // 성공 메시지 추가
	        redirectAttributes.addFlashAttribute("message", "후기가 성공적으로 수정되었습니다.");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "후기 수정 중 오류가 발생했습니다.");
	    }

	    // ✅ rcp_id는 리다이렉트할 때만 사용
	    return "redirect:/recipe/recipe_Detail?rcp_id=" + rcp_id;
	}


}

	

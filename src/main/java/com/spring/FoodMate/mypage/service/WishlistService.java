package com.spring.FoodMate.mypage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.FoodMate.cart.dto.CartDTO;
import com.spring.FoodMate.cart.exception.CartException;
import com.spring.FoodMate.common.exception.DBException;
import com.spring.FoodMate.mypage.dao.WishlistDAO;
import com.spring.FoodMate.mypage.dto.WishDTO;



@Service("wishlistService")
//@Transactional(propagation=Propagation.REQUIRED)
public class WishlistService {
	
	@Autowired
	private WishlistDAO wishlistDAO;
	
	public List<WishDTO> getwishList(String id) throws Exception {
		return wishlistDAO.getwishList(id);
	}
	
	public void addWishProduct(WishDTO wishDTO) throws Exception {
        try {
        	WishDTO duplResult = wishlistDAO.isInMyWish(wishDTO.getPdt_id(), wishDTO.getByr_id()); // 담으려는 상품이 이미 장바구니에 있냐?

            if (duplResult != null && duplResult.getWishlist_id() != 0) {
                updateWishQuantity(duplResult.getWishlist_id(), duplResult.getQty() + wishDTO.getQty());
                // 이미 장바구니에 있으면 수량만 더해
            }else {
            	// 없을경우 일반 찜 목록 추가
                wishlistDAO.addWishProduct(wishDTO); // 찜 목록에 아이템 추가
            }

        } catch (DBException e) {
        	throw new Exception("WishlistService에서 DB예외 전달.", e);
        } catch (Exception e) {
            throw new Exception("WishlistService.addToWish 에러! pdt_id=" , e);
        }
		
	}
	
    public boolean updateWishQuantity(int wishlist_id, int qty) throws Exception {
        try {
            return wishlistDAO.updateWishQuantity(wishlist_id, qty) > 0; // 업데이트된 행 수가 1 이상이면 성공
        } catch (DBException e) {
        	throw new CartException("WishlistService에서 DB예외 전달.", e);
        } catch (Exception e) {
            throw new Exception("WishlistService.updateWishQuantity 에러! wishlist_id=" + wishlist_id + ", qty=" + qty, e);
        }
    }

    public boolean deleteWishItem(int wishlist_id) throws Exception {
    	try {
    		return wishlistDAO.deleteWishItem(wishlist_id) > 0;  // 성공하면 true, 실패하면 false
        } catch (DBException e) {
        	throw new Exception("wishlistService에서 DB예외 전달.", e);
        } catch (Exception e) {
            throw new Exception("wishlistService.updateWishQuantity 에러! wishlist_id=" + wishlist_id, e);
        }
    }
//		// 주문목록이 없을경우 빈리스트를 반환해서 페이지 500에러 방지
//		if (list == null || list.isEmpty()) {
//		    return new ArrayList<>(); // 빈 리스트 반환하여 SQL 오류 방지
//		}
	}


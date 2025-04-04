package com.ezen.matzip.domain.bookmark.dto;

import com.ezen.matzip.domain.restaurant.dto.RestaurantDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDTO {
    private Long bookmarkCode;
    private RestaurantForBookmarkDTO restaurant;
}

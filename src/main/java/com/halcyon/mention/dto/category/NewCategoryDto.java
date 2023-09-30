package com.halcyon.mention.dto.category;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewCategoryDto {
    private Long listId;

    @Size(min = 1, max = 50, message = "Title must be more than 1 character and less than 50 characters.")
    private String title;
}
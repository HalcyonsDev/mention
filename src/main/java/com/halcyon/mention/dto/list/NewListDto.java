package com.halcyon.mention.dto.list;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewListDto {
    @Size(min = 2, max = 50, message = "Title must be more than 1 character and less than 50 characters.")
    private String title;
}
package com.ghassen.spring.web.dto;

import com.ghassen.spring.customAnnotations.ValidRoleName;
import lombok.Data;

/**
 * Created by Keno&Kemo on 08.12.2017..
 */
@Data
public class RoleDto {
    Long id;
    @ValidRoleName
    String name;
}

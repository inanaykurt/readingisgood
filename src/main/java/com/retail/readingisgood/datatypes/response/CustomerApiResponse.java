package com.retail.readingisgood.datatypes.response;

import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.web.bind.annotation.ResponseBody;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
@ResponseBody
public class CustomerApiResponse {

    private long id;

    private String name;

    private String surname;

    private String email;

}

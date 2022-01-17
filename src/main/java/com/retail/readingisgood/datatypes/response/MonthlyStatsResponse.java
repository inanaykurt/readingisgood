package com.retail.readingisgood.datatypes.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ResponseBody
public class MonthlyStatsResponse {

    ArrayList<OrderStats> orderStats;

}

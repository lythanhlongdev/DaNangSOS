package com.capstone2.dnsos.responses;

import com.capstone2.dnsos.models.User;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FamiliesResponses {

    private List<FamilyResponses> familyResponsesList;

    public static FamiliesResponses mapperListUser(List<User> users) {
        FamiliesResponses responses = FamiliesResponses.builder().build();
        for (User item : users) {
            responses.familyResponsesList.add(FamilyResponses.mapperUser(item));
        }
        return responses;
    }
}

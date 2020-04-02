package org.grammaticalframework.Repository;

import androidx.room.Embedded;
import androidx.room.Relation;

public class WNExplanationWithCheck {
    @Embedded
    WNExplanation wnExplanation;

    @Embedded
    CheckedFunction checkedFunction;
}

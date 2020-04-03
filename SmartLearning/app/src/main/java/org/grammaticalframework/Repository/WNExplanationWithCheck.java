package org.grammaticalframework.Repository;

import androidx.room.Embedded;
import androidx.room.Relation;

public class WNExplanationWithCheck {
    @Embedded
    WNExplanation wnExplanation;

    @Embedded
    CheckedFunction checkedFunction;

    public WNExplanation getWnExplanation() {
        return wnExplanation;
    }

    public CheckedFunction getCheckedFunction() {
        return checkedFunction;
    }

    public WNExplanationWithCheck(WNExplanation wnExplanation, CheckedFunction checkedFunction) {
        this.wnExplanation = wnExplanation;
        this.checkedFunction = checkedFunction;
    }
}

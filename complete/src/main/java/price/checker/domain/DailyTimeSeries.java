package price.checker.domain;

import javax.persistence.Embedded;
import javax.persistence.Embeddable;

/**
 * This class is embeddable by the Company entity
 */
@Embeddable
public class DailyTimeSeries {

    private String title;

    @Embedded
    private price.checker.domain.Date Date;

    public DailyTimeSeries(){}


}

package price.checker.domain.rpsy;

import org.springframework.stereotype.Repository;
import price.checker.domain.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * This is the interface that will be automatically implemented by Spring into a Bean
 */
@Repository
//JpaRepository<{Entity Class}, {Data Type of Primary Key Field}>
public interface StockPriceRepository extends JpaRepository<StockPrice,String>{


}

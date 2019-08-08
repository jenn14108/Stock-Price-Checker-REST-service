package price.checker.domain.rpsy;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import price.checker.domain.StockPrice;


import org.springframework.data.jpa.repository.JpaRepository;

@Repository
//JpaRepository<{Entity Class}, {Data Type of Primary Key Field}>
public interface StockPriceRepository extends JpaRepository<StockPrice,String>{


}

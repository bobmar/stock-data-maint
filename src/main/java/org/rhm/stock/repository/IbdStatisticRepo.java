package org.rhm.stock.repository;

import org.rhm.stock.domain.IbdStatistic;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IbdStatisticRepo extends MongoRepository<IbdStatistic, String> {

}

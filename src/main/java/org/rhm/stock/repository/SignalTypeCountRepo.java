package org.rhm.stock.repository;

import org.rhm.stock.domain.SignalTypeCount;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SignalTypeCountRepo extends MongoRepository<SignalTypeCount, String> {

}

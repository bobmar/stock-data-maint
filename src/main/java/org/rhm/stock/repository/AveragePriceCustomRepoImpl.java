package org.rhm.stock.repository;

import java.util.Date;

import org.rhm.stock.domain.AveragePrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.client.result.DeleteResult;

public class AveragePriceCustomRepoImpl implements AveragePriceCustomRepo {

	@Autowired
	private MongoTemplate mongoTemplate = null;
	
	@Override
	public long deleteOlderThan(Date deleteBefore) {
		CriteriaDefinition crit = Criteria.where("priceDate").lt(deleteBefore);
		DeleteResult result = mongoTemplate.remove(Query.query(crit), AveragePrice.class);
		return result.getDeletedCount();
	}

}

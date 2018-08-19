package org.rhm.stock.repository;

import java.util.Date;

public interface StatisticCustomRepo {
	public long deleteOlderThan(Date deleteBefore);

}

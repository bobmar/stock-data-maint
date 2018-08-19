package org.rhm.stock.repository;

import java.util.Date;

public interface PriceCustomRepo {
	public long deleteOlderThan(Date deleteBefore);
}

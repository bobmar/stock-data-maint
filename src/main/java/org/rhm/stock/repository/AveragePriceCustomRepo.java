package org.rhm.stock.repository;

import java.util.Date;

public interface AveragePriceCustomRepo {
	public long deleteOlderThan(Date deleteBefore);
}

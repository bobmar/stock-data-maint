package org.rhm.stock.repository;

import java.util.List;

import org.rhm.stock.domain.StockSignal;

public interface SignalCustomRepo {
	public List<StockSignal> findSignalsByType(List<String> signalTypeList, int lookBackDays);
}

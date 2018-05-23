package org.rhm.stock.service;

import java.util.List;
import java.util.stream.Collectors;

import org.rhm.stock.domain.SignalType;
import org.rhm.stock.domain.StockSignal;
import org.rhm.stock.repository.SignalRepo;
import org.rhm.stock.repository.SignalTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignalService {
	@Autowired
	private SignalTypeRepo signalTypeRepo = null;
	@Autowired
	private SignalRepo signalRepo = null;
	
	public SignalType createSignalType(SignalType signalType) {
		SignalType sigType = signalTypeRepo.save(signalType);
		return sigType;
	}
	
	public List<SignalType> signalTypes() {
		return signalTypeRepo.findAll().stream()
				.sorted((o1,o2)->o1.getSignalDesc()
				.compareTo(o2.getSignalDesc()))
				.collect(Collectors.toList());
	}
	
	public StockSignal createSignal(StockSignal signal) {
		StockSignal newSignal = null;
		newSignal = signalRepo.save(signal);
		return newSignal;
	}
	
	public List<StockSignal> findSignals(String signalType) {
		return signalRepo.findBySignalTypeOrderByPriceId(signalType);
	}
}

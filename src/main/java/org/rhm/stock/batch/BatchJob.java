package org.rhm.stock.batch;

import java.util.concurrent.CompletableFuture;

public interface BatchJob {

	public CompletableFuture<BatchStatus> run();
}

package org.rhm.stock.handler.maint;

import java.util.Date;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("prunePrice")
public class PrunePrice implements MaintHandler {

	@Override
	public void prune(Date deleteBefore) {
		// TODO Auto-generated method stub

	}

}

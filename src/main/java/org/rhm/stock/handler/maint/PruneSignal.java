package org.rhm.stock.handler.maint;

import java.util.Date;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("pruneSignal")
public class PruneSignal implements MaintHandler {

	@Override
	public void prune(Date deleteBefore) {
		// TODO Auto-generated method stub

	}

}

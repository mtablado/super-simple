package com.mt.jpmorgan.service.trade;

import com.mt.jpmorgan.model.Trade;

public interface TradeService {

	/**
	 * Method that records trades.
	 * @param trade The trade to save.
	 */
	void recordTrade(Trade trade);
}

package com.mt.jpmorgan.service.trade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mt.jpmorgan.dao.stock.TradeDAO;
import com.mt.jpmorgan.model.Trade;

@Component
public class TradeServiceImpl implements TradeService {

	@Autowired
	private TradeDAO tradeDao;

	@Override
	public void recordTrade(Trade trade) {
		this.tradeDao.recordTrade(trade);

	}

}

package com.mt.jpmorgan.controller.stock;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mt.jpmorgan.facade.stock.StockFacade;
import com.mt.jpmorgan.types.StockSymbol;
import com.mt.jpmorgan.types.TradeType;

@RestController
@RequestMapping("/trade/**")
public class TradeController {

	private static final Logger LOG = LoggerFactory.getLogger(StockController.class);

	@Autowired
	StockFacade stockFacade;

	@RequestMapping(value = "/buy-stocks", method = RequestMethod.POST)
	@ResponseBody
	public String buyStocks(
			@RequestParam(name = "id", required = true) @NotEmpty StockSymbol id
			, @RequestParam(name = "price", required = true) @NotEmpty BigDecimal price
			, @RequestParam(name = "quantity", required = true) @NotEmpty Integer quantity) {

		LOG.info("Buy trade request received for stock id: " + id);

		this.stockFacade.buyStocks(id, price, quantity);
		String message = this.messageBuffer(TradeType.BUY, id, price, quantity);
		LOG.info(message);
		return message;
	}

	@RequestMapping(value = "/sell-stocks", method = RequestMethod.POST)
	@ResponseBody
	public String sellStocks(
			@RequestParam(name = "id", required = true) @NotEmpty StockSymbol id
			, @RequestParam(name = "price", required = true) @NotEmpty BigDecimal price
			, @RequestParam(name = "quantity", required = true) @NotEmpty Integer quantity) {

		LOG.info("Buy trade request received for stock id: " + id);
		this.stockFacade.sellStocks(id, price, quantity);
		String message = this.messageBuffer(TradeType.SELL, id, price, quantity);
		LOG.info(message);
		return message;
	}

	private String messageBuffer(TradeType type, StockSymbol id, BigDecimal price, Integer quantity) {
		BigDecimal totalPrice = price.multiply(new BigDecimal(quantity));

		String action = (type.equals(TradeType.BUY)) ? " bought " : " sold ";

		StringBuffer sb = new StringBuffer();
		sb.append("You have");
		sb.append(action);
		sb.append(quantity);
		sb.append(" stocks of ");
		sb.append(id.toString());
		sb.append(" for a total price of ");
		sb.append(totalPrice);
		sb.append("cur");
		return sb.toString();
	}
}


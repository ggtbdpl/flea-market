package com.shumei.DAO;

import com.shumei.pojo.Trade;
import java.util.List;

public interface TradeDAO {
    int createTrade(Trade trade);
    Trade getTradeById(Integer id);
    Trade getTradeByProductAndBuyer(Integer productId, Integer buyerId);
    List<Trade> getTradesBySellerId(Integer sellerId);
    List<Trade> getTradesByBuyerId(Integer buyerId);
    List<Trade> getAllTrades();

    int updateTradeStatus(Integer id, String status);
    int updateTradeCompleteTime(Integer id);
}

package org.rhm.stock.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import org.springframework.data.annotation.Id;

public class YahooKeyStatistic {
	@Id
	private String keyStatId;
	private String tickerSymbol;
	private LocalDate priceDate;
    private BigDecimal enterpriseValue;
    private BigDecimal forwardPE;
    private BigDecimal profitMargins;
    private BigDecimal floatShares;
    private BigDecimal sharesOutstanding;
    private BigDecimal sharesShort;
    private BigDecimal sharesShortPriorMonth;
    private LocalDate sharesShortPreviousMonthDate;
    private LocalDate dateShortInterest;
    private BigDecimal sharesPercentSharesOut;
    private BigDecimal heldPercentInsiders;
    private BigDecimal heldPercentInstitutions;
    private BigDecimal shortRatio;
    private BigDecimal shortPercentOfFloat;
    private BigDecimal beta;
    private BigDecimal morningStarOverallRating;
    private BigDecimal morningStarRiskRating;
    private String category;
    private BigDecimal bookValue;
    private BigDecimal priceToBook;
    private BigDecimal annualReportExpenseRatio;
    private BigDecimal ytdReturn;
    private BigDecimal beta3Year;
    private BigDecimal totalAssets;
    private BigDecimal yield;
    private String fundFamily;
    private LocalDate fundInceptionDate;
    private String legalType;
    private BigDecimal threeYearAverageReturn;
    private BigDecimal fiveYearAverageReturn;
    private BigDecimal priceToSalesTrailing12Months;
    private LocalDate lastFiscalYearEnd;
    private LocalDate nextFiscalYearEnd;
    private LocalDate mostRecentQuarter;
    private BigDecimal earningsQuarterlyGrowth;
    private BigDecimal revenueQuarterlyGrowth;
    private BigDecimal netIncomeToCommon;
    private BigDecimal trailingEps;
    private BigDecimal forwardEps;
    private BigDecimal pegRatio;
    private String lastSplitFactor;
    private LocalDate lastSplitDate;
    private BigDecimal enterpriseToRevenue;
    private BigDecimal enterpriseToEbitda;
    private BigDecimal s52WeekChange;
    private BigDecimal SandP52WeekChange;
    private BigDecimal lastDividendValue;
    private BigDecimal lastCapGain;
    private BigDecimal annualHoldingsTurnover;
    private String maxAge;
    private BigDecimal currentPrice;
    private BigDecimal targetHighPrice;
    private BigDecimal targetLowPrice;
    private BigDecimal targetMeanPrice;
    private BigDecimal targetMedianPrice;
    private BigDecimal recommendationMean;
    private String recommendationKey;
    private BigDecimal numberOfAnalystOpinions;
    private BigDecimal totalCash;
    private BigDecimal totalCashPerShare;
    private BigDecimal ebitda;
    private BigDecimal totalDebt;
    private BigDecimal quickRatio;
    private BigDecimal currentRatio;
    private BigDecimal totalRevenue;
    private BigDecimal debtToEquity;
    private BigDecimal revenuePerShare;
    private BigDecimal returnOnAssets;
    private BigDecimal returnOnEquity;
    private BigDecimal grossProfits;
    private BigDecimal freeCashflow;
    private BigDecimal operatingCashflow;
    private BigDecimal earningsGrowth;
    private BigDecimal revenueGrowth;
    private BigDecimal grossMargins;
    private BigDecimal ebitdaMargins;
    private BigDecimal operatingMargins;
    private BigDecimal profitMargins2;
    private String financialCurrency;
    private LocalDate createDate;
	public BigDecimal getEnterpriseValue() {
		return enterpriseValue;
	}
	public void setEnterpriseValue(BigDecimal enterpriseValue) {
		this.enterpriseValue = enterpriseValue;
	}
	public BigDecimal getForwardPE() {
		return forwardPE;
	}
	public void setForwardPE(BigDecimal forwardPE) {
		this.forwardPE = forwardPE;
	}
	public BigDecimal getProfitMargins() {
		return profitMargins;
	}
	public void setProfitMargins(BigDecimal profitMargins) {
		this.profitMargins = profitMargins;
	}
	public BigDecimal getFloatShares() {
		return floatShares;
	}
	public void setFloatShares(BigDecimal floatShares) {
		this.floatShares = floatShares;
	}
	public BigDecimal getSharesOutstanding() {
		return sharesOutstanding;
	}
	public void setSharesOutstanding(BigDecimal sharesOutstanding) {
		this.sharesOutstanding = sharesOutstanding;
	}
	public BigDecimal getSharesShort() {
		return sharesShort;
	}
	public void setSharesShort(BigDecimal sharesShort) {
		this.sharesShort = sharesShort;
	}
	public BigDecimal getSharesShortPriorMonth() {
		return sharesShortPriorMonth;
	}
	public void setSharesShortPriorMonth(BigDecimal sharesShortPriorMonth) {
		this.sharesShortPriorMonth = sharesShortPriorMonth;
	}
	public LocalDate getSharesShortPreviousMonthDate() {
		return sharesShortPreviousMonthDate;
	}
	public void setSharesShortPreviousMonthDate(LocalDate sharesShortPreviousMonthDate) {
		this.sharesShortPreviousMonthDate = sharesShortPreviousMonthDate;
	}
	public LocalDate getDateShortInterest() {
		return dateShortInterest;
	}
	public void setDateShortInterest(LocalDate dateShortInterest) {
		this.dateShortInterest = dateShortInterest;
	}
	public BigDecimal getSharesPercentSharesOut() {
		return sharesPercentSharesOut;
	}
	public void setSharesPercentSharesOut(BigDecimal sharesPercentSharesOut) {
		this.sharesPercentSharesOut = sharesPercentSharesOut;
	}
	public BigDecimal getHeldPercentInsiders() {
		return heldPercentInsiders;
	}
	public void setHeldPercentInsiders(BigDecimal heldPercentInsiders) {
		this.heldPercentInsiders = heldPercentInsiders;
	}
	public BigDecimal getHeldPercentInstitutions() {
		return heldPercentInstitutions;
	}
	public void setHeldPercentInstitutions(BigDecimal heldPercentInstitutions) {
		this.heldPercentInstitutions = heldPercentInstitutions;
	}
	public BigDecimal getShortRatio() {
		return shortRatio;
	}
	public void setShortRatio(BigDecimal shortRatio) {
		this.shortRatio = shortRatio;
	}
	public BigDecimal getShortPercentOfFloat() {
		return shortPercentOfFloat;
	}
	public void setShortPercentOfFloat(BigDecimal shortPercentOfFloat) {
		this.shortPercentOfFloat = shortPercentOfFloat;
	}
	public BigDecimal getBeta() {
		return beta;
	}
	public void setBeta(BigDecimal beta) {
		this.beta = beta;
	}
	public BigDecimal getMorningStarOverallRating() {
		return morningStarOverallRating;
	}
	public void setMorningStarOverallRating(BigDecimal morningStarOverallRating) {
		this.morningStarOverallRating = morningStarOverallRating;
	}
	public BigDecimal getMorningStarRiskRating() {
		return morningStarRiskRating;
	}
	public void setMorningStarRiskRating(BigDecimal morningStarRiskRating) {
		this.morningStarRiskRating = morningStarRiskRating;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public BigDecimal getBookValue() {
		return bookValue;
	}
	public void setBookValue(BigDecimal bookValue) {
		this.bookValue = bookValue;
	}
	public BigDecimal getPriceToBook() {
		return priceToBook;
	}
	public void setPriceToBook(BigDecimal priceToBook) {
		this.priceToBook = priceToBook;
	}
	public BigDecimal getAnnualReportExpenseRatio() {
		return annualReportExpenseRatio;
	}
	public void setAnnualReportExpenseRatio(BigDecimal annualReportExpenseRatio) {
		this.annualReportExpenseRatio = annualReportExpenseRatio;
	}
	public BigDecimal getYtdReturn() {
		return ytdReturn;
	}
	public void setYtdReturn(BigDecimal ytdReturn) {
		this.ytdReturn = ytdReturn;
	}
	public BigDecimal getBeta3Year() {
		return beta3Year;
	}
	public void setBeta3Year(BigDecimal beta3Year) {
		this.beta3Year = beta3Year;
	}
	public BigDecimal getTotalAssets() {
		return totalAssets;
	}
	public void setTotalAssets(BigDecimal totalAssets) {
		this.totalAssets = totalAssets;
	}
	public BigDecimal getYield() {
		return yield;
	}
	public void setYield(BigDecimal yield) {
		this.yield = yield;
	}
	public String getFundFamily() {
		return fundFamily;
	}
	public void setFundFamily(String fundFamily) {
		this.fundFamily = fundFamily;
	}
	public LocalDate getFundInceptionDate() {
		return fundInceptionDate;
	}
	public void setFundInceptionDate(LocalDate fundInceptionDate) {
		this.fundInceptionDate = fundInceptionDate;
	}
	public String getLegalType() {
		return legalType;
	}
	public void setLegalType(String legalType) {
		this.legalType = legalType;
	}
	public BigDecimal getThreeYearAverageReturn() {
		return threeYearAverageReturn;
	}
	public void setThreeYearAverageReturn(BigDecimal threeYearAverageReturn) {
		this.threeYearAverageReturn = threeYearAverageReturn;
	}
	public BigDecimal getFiveYearAverageReturn() {
		return fiveYearAverageReturn;
	}
	public void setFiveYearAverageReturn(BigDecimal fiveYearAverageReturn) {
		this.fiveYearAverageReturn = fiveYearAverageReturn;
	}
	public BigDecimal getPriceToSalesTrailing12Months() {
		return priceToSalesTrailing12Months;
	}
	public void setPriceToSalesTrailing12Months(BigDecimal priceToSalesTrailing12Months) {
		this.priceToSalesTrailing12Months = priceToSalesTrailing12Months;
	}
	public LocalDate getLastFiscalYearEnd() {
		return lastFiscalYearEnd;
	}
	public void setLastFiscalYearEnd(LocalDate lastFiscalYearEnd) {
		this.lastFiscalYearEnd = lastFiscalYearEnd;
	}
	public LocalDate getNextFiscalYearEnd() {
		return nextFiscalYearEnd;
	}
	public void setNextFiscalYearEnd(LocalDate nextFiscalYearEnd) {
		this.nextFiscalYearEnd = nextFiscalYearEnd;
	}
	public LocalDate getMostRecentQuarter() {
		return mostRecentQuarter;
	}
	public void setMostRecentQuarter(LocalDate mostRecentQuarter) {
		this.mostRecentQuarter = mostRecentQuarter;
	}
	public BigDecimal getEarningsQuarterlyGrowth() {
		return earningsQuarterlyGrowth;
	}
	public void setEarningsQuarterlyGrowth(BigDecimal earningsQuarterlyGrowth) {
		this.earningsQuarterlyGrowth = earningsQuarterlyGrowth;
	}
	public BigDecimal getRevenueQuarterlyGrowth() {
		return revenueQuarterlyGrowth;
	}
	public void setRevenueQuarterlyGrowth(BigDecimal revenueQuarterlyGrowth) {
		this.revenueQuarterlyGrowth = revenueQuarterlyGrowth;
	}
	public BigDecimal getTrailingEps() {
		return trailingEps;
	}
	public void setTrailingEps(BigDecimal trailingEps) {
		this.trailingEps = trailingEps;
	}
	public BigDecimal getForwardEps() {
		return forwardEps;
	}
	public void setForwardEps(BigDecimal forwardEps) {
		this.forwardEps = forwardEps;
	}
	public BigDecimal getPegRatio() {
		return pegRatio;
	}
	public void setPegRatio(BigDecimal pegRatio) {
		this.pegRatio = pegRatio;
	}
	public String getLastSplitFactor() {
		return lastSplitFactor;
	}
	public void setLastSplitFactor(String lastSplitFactor) {
		this.lastSplitFactor = lastSplitFactor;
	}
	public LocalDate getLastSplitDate() {
		return lastSplitDate;
	}
	public void setLastSplitDate(LocalDate lastSplitDate) {
		this.lastSplitDate = lastSplitDate;
	}
	public BigDecimal getEnterpriseToRevenue() {
		return enterpriseToRevenue;
	}
	public void setEnterpriseToRevenue(BigDecimal enterpriseToRevenue) {
		this.enterpriseToRevenue = enterpriseToRevenue;
	}
	public BigDecimal getEnterpriseToEbitda() {
		return enterpriseToEbitda;
	}
	public void setEnterpriseToEbitda(BigDecimal enterpriseToEbitda) {
		this.enterpriseToEbitda = enterpriseToEbitda;
	}
	public BigDecimal getSandP52WeekChange() {
		return SandP52WeekChange;
	}
	public void setSandP52WeekChange(BigDecimal sandP52WeekChange) {
		SandP52WeekChange = sandP52WeekChange;
	}
	public BigDecimal getLastDividendValue() {
		return lastDividendValue;
	}
	public void setLastDividendValue(BigDecimal lastDividendValue) {
		this.lastDividendValue = lastDividendValue;
	}
	public BigDecimal getLastCapGain() {
		return lastCapGain;
	}
	public void setLastCapGain(BigDecimal lastCapGain) {
		this.lastCapGain = lastCapGain;
	}
	public BigDecimal getAnnualHoldingsTurnover() {
		return annualHoldingsTurnover;
	}
	public void setAnnualHoldingsTurnover(BigDecimal annualHoldingsTurnover) {
		this.annualHoldingsTurnover = annualHoldingsTurnover;
	}
	public String getMaxAge() {
		return maxAge;
	}
	public void setMaxAge(String maxAge) {
		this.maxAge = maxAge;
	}
	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}
	public BigDecimal getTargetHighPrice() {
		return targetHighPrice;
	}
	public void setTargetHighPrice(BigDecimal targetHighPrice) {
		this.targetHighPrice = targetHighPrice;
	}
	public BigDecimal getTargetLowPrice() {
		return targetLowPrice;
	}
	public void setTargetLowPrice(BigDecimal targetLowPrice) {
		this.targetLowPrice = targetLowPrice;
	}
	public BigDecimal getTargetMeanPrice() {
		return targetMeanPrice;
	}
	public void setTargetMeanPrice(BigDecimal targetMeanPrice) {
		this.targetMeanPrice = targetMeanPrice;
	}
	public BigDecimal getTargetMedianPrice() {
		return targetMedianPrice;
	}
	public void setTargetMedianPrice(BigDecimal targetMedianPrice) {
		this.targetMedianPrice = targetMedianPrice;
	}
	public BigDecimal getRecommendationMean() {
		return recommendationMean;
	}
	public void setRecommendationMean(BigDecimal recommendationMean) {
		this.recommendationMean = recommendationMean;
	}
	public String getRecommendationKey() {
		return recommendationKey;
	}
	public void setRecommendationKey(String recommendationKey) {
		this.recommendationKey = recommendationKey;
	}
	public BigDecimal getNumberOfAnalystOpinions() {
		return numberOfAnalystOpinions;
	}
	public void setNumberOfAnalystOpinions(BigDecimal numberOfAnalystOpinions) {
		this.numberOfAnalystOpinions = numberOfAnalystOpinions;
	}
	public BigDecimal getTotalCash() {
		return totalCash;
	}
	public void setTotalCash(BigDecimal totalCash) {
		this.totalCash = totalCash;
	}
	public BigDecimal getTotalCashPerShare() {
		return totalCashPerShare;
	}
	public void setTotalCashPerShare(BigDecimal totalCashPerShare) {
		this.totalCashPerShare = totalCashPerShare;
	}
	public BigDecimal getEbitda() {
		return ebitda;
	}
	public void setEbitda(BigDecimal ebitda) {
		this.ebitda = ebitda;
	}
	public BigDecimal getTotalDebt() {
		return totalDebt;
	}
	public void setTotalDebt(BigDecimal totalDebt) {
		this.totalDebt = totalDebt;
	}
	public BigDecimal getQuickRatio() {
		return quickRatio;
	}
	public void setQuickRatio(BigDecimal quickRatio) {
		this.quickRatio = quickRatio;
	}
	public BigDecimal getCurrentRatio() {
		return currentRatio;
	}
	public void setCurrentRatio(BigDecimal currentRatio) {
		this.currentRatio = currentRatio;
	}
	public BigDecimal getTotalRevenue() {
		return totalRevenue;
	}
	public void setTotalRevenue(BigDecimal totalRevenue) {
		this.totalRevenue = totalRevenue;
	}
	public BigDecimal getDebtToEquity() {
		return debtToEquity;
	}
	public void setDebtToEquity(BigDecimal debtToEquity) {
		this.debtToEquity = debtToEquity;
	}
	public BigDecimal getRevenuePerShare() {
		return revenuePerShare;
	}
	public void setRevenuePerShare(BigDecimal revenuePerShare) {
		this.revenuePerShare = revenuePerShare;
	}
	public BigDecimal getReturnOnAssets() {
		return returnOnAssets;
	}
	public void setReturnOnAssets(BigDecimal returnOnAssets) {
		this.returnOnAssets = returnOnAssets;
	}
	public BigDecimal getReturnOnEquity() {
		return returnOnEquity;
	}
	public void setReturnOnEquity(BigDecimal returnOnEquity) {
		this.returnOnEquity = returnOnEquity;
	}
	public BigDecimal getGrossProfits() {
		return grossProfits;
	}
	public void setGrossProfits(BigDecimal grossProfits) {
		this.grossProfits = grossProfits;
	}
	public BigDecimal getFreeCashflow() {
		return freeCashflow;
	}
	public void setFreeCashflow(BigDecimal freeCashflow) {
		this.freeCashflow = freeCashflow;
	}
	public BigDecimal getEarningsGrowth() {
		return earningsGrowth;
	}
	public void setEarningsGrowth(BigDecimal earningsGrowth) {
		this.earningsGrowth = earningsGrowth;
	}
	public BigDecimal getRevenueGrowth() {
		return revenueGrowth;
	}
	public void setRevenueGrowth(BigDecimal revenueGrowth) {
		this.revenueGrowth = revenueGrowth;
	}
	public BigDecimal getGrossMargins() {
		return grossMargins;
	}
	public void setGrossMargins(BigDecimal grossMargins) {
		this.grossMargins = grossMargins;
	}
	public BigDecimal getEbitdaMargins() {
		return ebitdaMargins;
	}
	public void setEbitdaMargins(BigDecimal ebitdaMargins) {
		this.ebitdaMargins = ebitdaMargins;
	}
	public BigDecimal getOperatingMargins() {
		return operatingMargins;
	}
	public void setOperatingMargins(BigDecimal operatingMargins) {
		this.operatingMargins = operatingMargins;
	}
	public BigDecimal getProfitMargins2() {
		return profitMargins2;
	}
	public void setProfitMargins2(BigDecimal profitMargins2) {
		this.profitMargins2 = profitMargins2;
	}
	public String getFinancialCurrency() {
		return financialCurrency;
	}
	public void setFinancialCurrency(String financialCurrency) {
		this.financialCurrency = financialCurrency;
	}
	public String getKeyStatId() {
		return keyStatId;
	}
	public void setKeyStatId(String keyStatId) {
		this.keyStatId = keyStatId;
	}
	public LocalDate getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}
	public String getTickerSymbol() {
		return tickerSymbol;
	}
	public void setTickerSymbol(String tickerSymbol) {
		this.tickerSymbol = tickerSymbol;
	}
	public BigDecimal getNetIncomeToCommon() {
		return netIncomeToCommon;
	}
	public void setNetIncomeToCommon(BigDecimal netIncomeToCommon) {
		this.netIncomeToCommon = netIncomeToCommon;
	}
	public BigDecimal getS52WeekChange() {
		return s52WeekChange;
	}
	public void setS52WeekChange(BigDecimal s52WeekChange) {
		this.s52WeekChange = s52WeekChange;
	}
	public BigDecimal getOperatingCashflow() {
		return operatingCashflow;
	}
	public void setOperatingCashflow(BigDecimal operatingCashflow) {
		this.operatingCashflow = operatingCashflow;
	}
	public LocalDate getPriceDate() {
		return priceDate;
	}
	public void setPriceDate(LocalDate priceDate) {
		this.priceDate = priceDate;
	}

}

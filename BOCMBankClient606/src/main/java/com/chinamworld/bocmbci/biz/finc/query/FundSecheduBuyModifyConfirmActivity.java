package com.chinamworld.bocmbci.biz.finc.query;

import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chinamworld.bocmbci.R;
import com.chinamworld.bocmbci.bii.BiiResponse;
import com.chinamworld.bocmbci.bii.BiiResponseBody;
import com.chinamworld.bocmbci.bii.constant.Finc;
import com.chinamworld.bocmbci.biz.finc.FincBaseActivity;
import com.chinamworld.bocmbci.biz.finc.control.FincControl;
import com.chinamworld.bocmbci.constant.LocalData;
import com.chinamworld.bocmbci.http.engine.BaseHttpEngine;
import com.chinamworld.bocmbci.utils.PopupWindowUtils;
import com.chinamworld.bocmbci.utils.StepTitleUtils;
import com.chinamworld.bocmbci.utils.StringUtil;

/**
 * 基金定期定额申购修改确认页面
 * 
 * 
 */
public class FundSecheduBuyModifyConfirmActivity extends FincBaseActivity {
	
	/** 基金代码Tv */
	private TextView fundCodeTextView;
	private TextView fundNameTextView;
	/** 单位净值Tv */
	private TextView netPriceTextView;
	/** 产品风险等级Tv */
	private TextView productRiskLevelTextView;
	/** 收费方式Tv */
	private TextView feeTypeTextView;
	/** 定投下限Tv */
	private TextView lowLimitTextView;
	/** 交易币种Tv */
	private TextView tradeCurrencyTextView;
	/** 每月申购日期 Tv */
	private TextView saleDayOfMonthTextView;
	/**
	 * 申购金额 Tv
	 */
	private TextView saleAmountTextView;
	/**
	 * 下一步按钮
	 */
	private Button confirmBtn;
	private Button lastBtn;
	// 页面显示的值
	private String fundCodeStr;
	private String fundNameStr;
	private String netPriceStr;
	private String productRiskLevelStr;
	private String feeTypeStr;
	private String tradeCurrencyStr, cashFlagCode;
	private String dayInMonthStr;
	/**
	 * 申购金额
	 */
	private String saleAmoundStr;
	private String lowLimitStr;
	private String userRiskLevel;
	//原定定投定赎序号
	private String fundSeq, oldApplyDate;

	/**
	 * 定投周期， 每周定投日， 结束条件，指定结束日期，累计成交份额，累计成交金额
	 */
	private String transcycle, paymentdateofweek, endflag, fundpointenddate,
			endsum, fundpointendamount;
	private String paymentDate, endContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		initData();
		setFundCompanyInfo();
	}

	@Override
	public void requestCommConversationIdCallBack(Object resultObj) {
		super.requestCommConversationIdCallBack(resultObj);
		requestPSNGetTokenId();
	}

	@Override
	public void requestPSNGetTokenIdCallback(Object resultObj) {
		super.requestPSNGetTokenIdCallback(resultObj);
		BiiResponse biiResponse = (BiiResponse) resultObj;
		List<BiiResponseBody> biiResponseBodys = biiResponse.getResponse();
		BiiResponseBody biiResponseBody = biiResponseBodys.get(0);
		String token = (String) biiResponseBody.getResult();
//		fundScheduledBuy(saleAmoundStr, fundCodeStr, feeTypeStr, token,
//				transcycle, paymentDate, endflag, endContext, "");// 缺少指令交易后台交易ID
		fundScheduBuyModify(fundCodeStr, fundSeq, oldApplyDate,
				saleAmoundStr, paymentDate, endflag, endContext, token, transcycle);
	}

	@Override
	public void fundScheduBuyModifyCallback(Object resultObj) {
		BaseHttpEngine.dissMissProgressDialog();
		super.fundScheduBuyModifyCallback(resultObj);
		BiiResponse biiResponse = (BiiResponse) resultObj;
		List<BiiResponseBody> biiResponseBodys = biiResponse.getResponse();
		BiiResponseBody biiResponseBody = biiResponseBodys.get(0);
		Map<String, Object> resultMap = (Map<String, Object>) biiResponseBody
				.getResult();
		String fundSql = String.valueOf(resultMap.get(Finc.FINC_FUNDSEQ_REQ));
		String transationId = String.valueOf(resultMap
				.get(Finc.FINC_TRANSACTIONID_REQ));
		Intent intent = getIntent();
		intent.putExtra(Finc.I_FINCBUYSQL, fundSql);
		intent.putExtra(Finc.I_TRANSACTIONID, transationId);
		intent.putExtra(Finc.I_FUNDCODE, fundCodeStr);
		intent.putExtra(Finc.I_FUNDNAME, fundNameStr);
		intent.putExtra(Finc.I_FEETYPE, feeTypeStr);
		intent.putExtra(Finc.I_CURRENCYCODE, tradeCurrencyStr);
		intent.putExtra(Finc.TRANSCYCLE, transcycle);// 定投定赎周期
		intent.putExtra(Finc.I_DAYINMOUNTH, dayInMonthStr);
		intent.putExtra(Finc.I_SALEAMOUNT, saleAmoundStr);
		intent.putExtra(Finc.PAYMENTDATEOFWEEK, paymentDate);// 扣款日期
		intent.putExtra(Finc.ENDFLAG, endflag);// 结束条件
		intent.putExtra(Finc.FUNDPOINTENDDATE, fundpointenddate);// 结束日期
		intent.putExtra(Finc.ENDSUM, endsum);// 成交份额
		intent.putExtra(Finc.FUNDPOINTENDAMOUNT, fundpointendamount);// 成交金额
		intent.setClass(this, FundSecheduBuyModifySuccessActivity.class);
		startActivityForResult(intent, 1);
	}	


	/**
	 * 初始化布局
	 * 
	 * @Author xyl
	 */
	private void init() {
		View childview = mainInflater.inflate(
				R.layout.finc_trade_scheduledbuy_confirm, null);
		tabcontent.addView(childview);
		StepTitleUtils.getInstance().initTitldStep(this,
				fincControl.getStepsForDingTou());
		StepTitleUtils.getInstance().setTitleStep(2);
		setTitle(R.string.finc_title_scheduedit_buy);
		TextView fincLowLimtTitleView = (TextView) childview
				.findViewById(R.id.finc_lowLimt_titleView);
		fundCodeTextView = (TextView) childview
				.findViewById(R.id.finc_fundCode_textview);
		fundNameTextView = (TextView) childview
				.findViewById(R.id.finc_fundName_textview);
		netPriceTextView = (TextView) childview
				.findViewById(R.id.finc_netvalue_textView);
		productRiskLevelTextView = (TextView) childview
				.findViewById(R.id.finc_productrisklevel_textView);
		feeTypeTextView = (TextView) childview
				.findViewById(R.id.finc_feetype_textView);
		lowLimitTextView = (TextView) childview
				.findViewById(R.id.finc_lowLimt_textView);
		tradeCurrencyTextView = (TextView) childview
				.findViewById(R.id.finc_tradecurrency_textView);
		saleDayOfMonthTextView = (TextView) childview
				.findViewById(R.id.finc_dayInMonth_TextView);
		saleAmountTextView = (TextView) childview
				.findViewById(R.id.finc_scheduledbuyAmount_TextView);
		confirmBtn = (Button) childview.findViewById(R.id.finc_confirm);
		lastBtn = (Button) childview.findViewById(R.id.finc_pre);

		confirmBtn.setOnClickListener(this);
		lastBtn.setOnClickListener(this);
		initRightBtnForMain();
		PopupWindowUtils.getInstance().setOnShowAllTextListener(this,
				fundNameTextView);
		PopupWindowUtils.getInstance().setOnShowAllTextListener(this,
				fincLowLimtTitleView);
	}

	/**
	 * 初始化数据
	 * 
	 * @Author xyl
	 */
	private void initData() {
		if (!StringUtil.isNullOrEmpty(fincControl.fundScheduQueryMap)) {
			Intent intent = getIntent();
			Bundle extra = intent.getExtras();
			fundCodeStr = extra.getString(Finc.I_FUNDCODE);
			fundNameStr = extra.getString(Finc.I_FUNDNAME);
			netPriceStr = extra.getString(Finc.I_NETPRICE);
			productRiskLevelStr = extra.getString(Finc.I_RISKLEVEL);
			feeTypeStr = extra.getString(Finc.I_FEETYPE);
			lowLimitStr = extra.getString(Finc.I_SCHEDULEDBUYLOWLIMTE);
			tradeCurrencyStr = extra.getString(Finc.I_CURRENCYCODE);
			cashFlagCode = extra.getString(Finc.I_CASHFLAG);
			dayInMonthStr = extra.getString(Finc.I_DAYINMOUNTH);
			saleAmoundStr = extra.getString(Finc.I_SALEAMOUNT);
			
			fundSeq = (String) fincControl.fundScheduQueryMap
					.get(Finc.FINC_FUNDBUY_FUNDSEQ);
			oldApplyDate = (String) fincControl.fundScheduQueryMap
					.get(Finc.I_APPLYDATE);
	
			fundCodeTextView.setText(fundCodeStr); // 只有详情中详情中进入才显示
			fundNameTextView.setText(fundNameStr); // 只有详情中详情中进入才显示
			netPriceTextView.setText(StringUtil.parseStringPattern(netPriceStr, 4));
			productRiskLevelTextView.setText(LocalData.fincRiskLevelCodeToStrFUND
					.get(productRiskLevelStr));
			feeTypeTextView.setText(LocalData.fundfeeTypeCodeToStr.get(feeTypeStr));
			lowLimitTextView.setText(StringUtil.parseStringCodePattern(
					tradeCurrencyStr, lowLimitStr, 2));
			tradeCurrencyTextView.setText(FincControl.fincCurrencyAndCashFlag(
					tradeCurrencyStr, cashFlagCode));
			saleDayOfMonthTextView.setText(dayInMonthStr);
			saleAmountTextView.setText(StringUtil.parseStringCodePattern(
					tradeCurrencyStr, saleAmoundStr, 2));
			initP405ModiData(extra);
		}
	}

	/**
	 * 405定投优化 增加初始化数据 添加选择定投期 ，增加周定投 ，增加结束条件
	 */
	private void initP405ModiData(Bundle extra) {
		if (extra != null) {
			transcycle = extra.getString(Finc.TRANSCYCLE);
			switch (Integer.parseInt(transcycle)) {
			case 0:
				findViewById(R.id.finc_dayInMonth_ll).setVisibility(
						View.VISIBLE);
				saleDayOfMonthTextView.setText(StringUtil
						.valueOf1(dayInMonthStr));
				paymentDate = dayInMonthStr;
				break;
			case 1:
				TextView fincSaleDayOfWeekTv = (TextView) findViewById(R.id.fincSaleDayOfWeekTv);
				findViewById(R.id.fincSaleDayOfWeekLl).setVisibility(
						View.VISIBLE);
				paymentdateofweek = extra.getString(Finc.PAYMENTDATEOFWEEK);
				fincSaleDayOfWeekTv.setText(paymentdateofweek);
				paymentDate = getValueByWeek(paymentdateofweek);
				break;
			}
			((TextView) findViewById(R.id.fincScheduledbuyPeriod))
					.setText((String) LocalData.transCycleMap.get(transcycle));
			endflag = extra.getString(Finc.ENDFLAG);
			TextView finc_scheduledbuy_setEndTime = (TextView) findViewById(R.id.finc_scheduledbuy_setEndTime);
			finc_scheduledbuy_setEndTime
					.setText((String) LocalData.dtEndFlagMap.get(endflag));
			TextView endName = (TextView) findViewById(R.id.endName);
			TextView endContext = (TextView) findViewById(R.id.endContext);
			switch (Integer.parseInt(endflag)) {
			case 1:
				fundpointenddate = extra.getString(Finc.FUNDPOINTENDDATE);
				this.endContext = fundpointenddate;
				endName.setText(getString(R.string.finc_scheduledbuy_end_time));
				endContext.setText(StringUtil.valueOf1(fundpointenddate));
				break;
			case 2:
				endsum = extra.getString(Finc.ENDSUM);
				this.endContext = endsum;
				endName.setText(getString(R.string.finc_scheduledbuy_total_deal_count));
				endContext.setText(StringUtil.valueOf1(endsum));
				break;
			case 3:
				fundpointendamount = extra.getString(Finc.FUNDPOINTENDAMOUNT);
				this.endContext = fundpointendamount;
				endName.setText(getString(R.string.finc_scheduledbuy_total_deal_amount));
				endContext.setText(StringUtil.parseStringCodePattern(
						tradeCurrencyStr, fundpointendamount, 2));
				endContext.setTextColor(getResources().getColor(R.color.red));
				break;
			default:
				findViewById(R.id.end_ll).setVisibility(View.GONE);
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.finc_pre:// 上一步
			finish();
			break;

		case R.id.finc_confirm:// 确定按钮
			BaseHttpEngine.showProgressDialog();
			//requestPsnFundRiskEvaluationQueryResult();
			requestCommConversationId();
			break;
		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case RESULT_OK:
			setResult(RESULT_OK);
			finish();
			break;

		default:
			break;
		}
	}

}

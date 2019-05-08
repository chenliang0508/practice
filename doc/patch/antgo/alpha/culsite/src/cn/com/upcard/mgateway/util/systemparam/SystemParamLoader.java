package cn.com.upcard.mgateway.util.systemparam;

public class SystemParamLoader {

	public static void init() {
		//基础系统参数
		SysPara.init();
		//加载交易类型信息
		CommercialInfo.init();
		//条码类型配置文件加载
		BarcodeType.init();
	}
}

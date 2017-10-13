package com.eshel.currencyspirit.util;

import android.widget.Toast;

import com.eshel.currencyspirit.CurrencySpiritApp;

/**
 * createBy Eshel
 * createTime: 2017/10/5 01:46
 * desc: TODO
 */

public class UIUtil {

	private static Toast mToast;
	private static boolean debug;
	public static void setDebug(boolean isDebug){
		debug = isDebug;
	}

	public static String getString(int resId){
		return CurrencySpiritApp.getContext().getResources().getString(resId);
	}
	public static int getColor(int resId){
		return CurrencySpiritApp.getContext().getResources().getColor(resId);
	}
	public static void debugToast(CharSequence text){
		if(debug)
			toast(text);
	}
	public static void toast(final CharSequence text){
		if(Thread.currentThread().getName().equals(CurrencySpiritApp.getMainThreadName()))
			showToast(text);
		else
			CurrencySpiritApp.getApp().getHandler().post(new Runnable() {
				@Override
				public void run() {
					showToast(text);
				}
			});
	}
	private static void showToast(CharSequence text){
		if(mToast == null) {
			synchronized (Toast.class) {
				if(mToast == null) {
					mToast = Toast.makeText(CurrencySpiritApp.getContext(), text, Toast.LENGTH_LONG);
				}
			}
		}else {
			mToast.setText(text);
		}
		mToast.show();
	}
	public static int getScreenHeight(){
		return CurrencySpiritApp.getContext().getResources().getDisplayMetrics().heightPixels;
	}
	public static int getScreenWidth(){
		return CurrencySpiritApp.getContext().getResources().getDisplayMetrics().widthPixels;
	}
}

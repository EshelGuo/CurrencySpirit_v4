package com.eshel.currencyspirit.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eshel.currencyspirit.R;
import com.eshel.currencyspirit.util.UIUtil;
import com.eshel.model.EssenceModel;

import baseproject.base.BaseActivity;
import baseproject.util.Log;
import baseproject.util.NetUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guoshiwen on 2017/10/12.
 */

public class EssenceDetailsActivity extends BaseActivity {

	@BindView(R.id.title)
	TextView mTitle;
	@BindView(R.id.wv_essence)
	WebView mWvEssence;
	@BindView(R.id.progress_bar)
	ProgressBar mProgressBar;
	private EssenceModel mEssenceModel;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_essence_details);
		hideActionBar();
		ButterKnife.bind(this);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			mTitle.setElevation(HomeActivity.titleElevation);
		}
		mProgressBar.setProgress(0);
		Intent intent = getIntent();
		if (intent != null) {
			mEssenceModel = (EssenceModel) intent.getSerializableExtra("essenceModel");
		}
		mWvEssence.getSettings().setCacheMode(NetUtils.hasNetwork(this) ? WebSettings.LOAD_DEFAULT : WebSettings.LOAD_CACHE_ELSE_NETWORK);
		mWvEssence.getSettings().setJavaScriptEnabled(true);
		mWvEssence.getSettings().setUseWideViewPort(true);
		mWvEssence.getSettings().setLoadWithOverviewMode(true);
		mWvEssence.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//		mWvEssence.setWebViewClient(new WebViewClient());
		mWvEssence.getSettings().setSupportZoom(true);
		mWvEssence.getSettings().setBuiltInZoomControls(true);
		mWvEssence.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				Log.i(newProgress);
				if(newProgress == 100){
					mProgressBar.setVisibility(View.GONE);
				}else {
					if(mProgressBar.getVisibility() == View.GONE)
						mProgressBar.setVisibility(View.VISIBLE);
					mProgressBar.setProgress(newProgress);
					mProgressBar.invalidate();
				}
				super.onProgressChanged(view, newProgress);
			}
		});
		if (mEssenceModel.url != null) {
			mWvEssence.loadUrl(mEssenceModel.url);
		} else {
			UIUtil.toast("加载失败");
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mWvEssence.canGoBack()) {
				mWvEssence.goBack();//返回上一页面
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}

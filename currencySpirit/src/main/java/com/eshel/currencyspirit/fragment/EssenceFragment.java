package com.eshel.currencyspirit.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eshel.currencyspirit.CurrencySpiritApp;
import com.eshel.currencyspirit.R;
import com.eshel.currencyspirit.activity.EssenceDetailsActivity;
import com.eshel.currencyspirit.util.UIUtil;
import com.eshel.currencyspirit.widget.RecycleViewDivider;
import com.eshel.currencyspirit.widget.util.GlideCircleTransform;
import com.eshel.currencyspirit.widget.util.GlideRoundedRectangleTransform;
import com.eshel.currencyspirit.widget.util.LoadMoreView;
import com.eshel.database.dao.EssenceHistoryDao;
import com.eshel.model.EssenceModel;
import com.eshel.viewmodel.EssenceViewModel;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.lhh.ptrrv.library.footer.loadmore.BaseLoadMoreView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import baseproject.base.BaseFragment;
import baseproject.util.DensityUtil;
import baseproject.util.StringUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * createBy Eshel
 * createTime: 2017/10/4 20:41
 * desc: 精华 fragment
 */

public class EssenceFragment extends BaseFragment {

	private PullToRefreshRecyclerView  mRv_essence;
	private EssenceAdapter mEssenceAdapter;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EssenceViewModel.getEssenceData(EssenceViewModel.Mode.NORMAL);
	}

	@Override
	protected void reloadData() {
		EssenceViewModel.getEssenceData(EssenceViewModel.Mode.NORMAL);
	}

	@Override
	public View getLoadSuccessView() {
		ViewGroup parent = (ViewGroup) View.inflate(getActivity(), R.layout.view_essence, null);
		mRv_essence = (PullToRefreshRecyclerView) parent.findViewById(R.id.rv_essence);
		mRv_essence.setSwipeEnable(true);//open swipe
		mRv_essence.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
		mRv_essence.getRecyclerView().addItemDecoration(new RecycleViewDivider(getActivity(),LinearLayoutManager.HORIZONTAL,
				DensityUtil.dp2px(1),UIUtil.getColor(R.color.dividerColor),DensityUtil.dp2px(10),DensityUtil.dp2px(10)));
		mEssenceAdapter = new EssenceAdapter();
		mRv_essence.setAdapter(mEssenceAdapter);

		LoadMoreView loadMoreView = new LoadMoreView(getActivity(), mRv_essence.getRecyclerView());
		loadMoreView.setLoadmoreString(getString(R.string.string_loadmore));
		loadMoreView.setLoadMorePadding(100);
		mRv_essence.setLoadMoreFooter(loadMoreView);
		//remove header
		mRv_essence.removeHeader();
		// set true to open swipe(pull to refresh, default is true)
		// set PagingableListener
		mRv_essence.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
			@Override
			public void onLoadMoreItems() {
				// todo do loadmore here
				EssenceViewModel.getEssenceData(EssenceViewModel.Mode.LOADMORE);
			}
		});

		// set OnRefreshListener
		mRv_essence.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				mRv_essence.setRefreshing(true);
				// todo do refresh here
				CurrencySpiritApp.getApp().getHandler().postDelayed(new Runnable() {
					@Override
					public void run() {
						EssenceViewModel.refreshData();
						mRv_essence.setOnRefreshComplete();
						mRv_essence.onFinishLoading(true, false);
					}
				},1000);
			}
		});
//		mRv_essence.addHeaderView(View.inflate(getActivity(), android.R.layout.simple_list_item_1, null));
		// add headerView
		//mRv_essence.addHeaderView(View.inflate(this, R.layout.header, null));

		//set EmptyVlist
		//mRv_essence.setEmptyView(View.inflat(this,R.layout.empty_view, null));

		// set loadmore String
		//mRv_essence.setLoadmoreString("loading");

		// set loadmore enable, onFinishLoading(can load more? , select before item)
		mRv_essence.onFinishLoading(true, false);
		return parent;
	}

	@Override
	public void notifyView() {
		if(EssenceModel.loadDataCount < 20)
			mRv_essence.onFinishLoading(false, false);
		else
			mRv_essence.onFinishLoading(true,false);
		mEssenceAdapter.notifyDataSetChanged();
	}

	public class EssenceAdapter extends RecyclerView.Adapter<EssenceViewHolder> {

		@Override
		public EssenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			return new EssenceViewHolder();
		}

		@Override
		public void onBindViewHolder(EssenceViewHolder holder, int position) {
			holder.bindDataToView(EssenceModel.getEssenceDataByPosition(position));
		}

		@Override
		public int getItemCount() {
			ArrayList<EssenceModel> essenceData = EssenceModel.essenceData;
			if(essenceData != null)
				return essenceData.size();
			return 0;
		}
	}

	@Override
	public void refreshFailed() {
		super.refreshFailed();
	}

	@Override
	public void loadModeFailed() {
		super.loadModeFailed();
		mRv_essence.onFinishLoading(false, false);
		mRv_essence.onFinishLoading(true, false);
	}

	public class EssenceViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.time)
		TextView time;
		@BindView(R.id.title)
		TextView title;
		@BindView(R.id.icon)
		ImageView icon;
		private SimpleDateFormat mFormat;

		public EssenceViewHolder() {
			super(LayoutInflater.from(getActivity()).inflate(R.layout.item_essence, null));
			ButterKnife.bind(this,itemView);
			Glide.with(getActivity())
					.load(R.drawable.default_image)
					.transform(new GlideRoundedRectangleTransform(getActivity()))
					.into(icon);
			icon.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					icon.setMinimumHeight((int) (icon.getWidth() * 0.75f));
					icon.setMaxHeight((int) (icon.getWidth() * 0.75f));
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
						itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
					}
				}
			});
		}
		public void bindDataToView(final EssenceModel essenceModel){
			if(mFormat == null)
				mFormat = new SimpleDateFormat(UIUtil.getString(R.string.item_time_format), Locale.getDefault());
			time.setText(mFormat.format(new Date(essenceModel.update_time)));
			title.setText(essenceModel.title);
			if(!StringUtils.isEmpty(essenceModel.imageurl)) {
				Glide.with(getActivity()).
						load(essenceModel.imageurl)
						.transform(new GlideRoundedRectangleTransform(getActivity()))
						.into(icon);
			} else {
				Glide.with(getActivity()).
						load(R.drawable.default_image)
						.transform(new GlideRoundedRectangleTransform(getActivity()))
						.into(icon);
			}
			itemView.setBackgroundResource(R.drawable.item_selector);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					essenceModel.isClicked = true;
					if(essenceModel.isClicked){
						title.setTextColor(UIUtil.getColor(android.R.color.darker_gray));
					}else {
						title.setTextColor(UIUtil.getColor(android.R.color.black));
					}
					EssenceViewModel.addDataToHistory(essenceModel);
					Intent intent = new Intent(getActivity(), EssenceDetailsActivity.class);
					intent.putExtra(EssenceDetailsActivity.key,essenceModel);
					startActivity(intent);
				}
			});
			if(essenceModel.isClicked){
				title.setTextColor(UIUtil.getColor(android.R.color.darker_gray));
			}else {
				title.setTextColor(UIUtil.getColor(android.R.color.black));
			}
		}
	}
}

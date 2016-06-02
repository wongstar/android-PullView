package pull.ianc.com.pullview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class PullUpView extends LinearLayout implements View.OnClickListener {

	private ImageView ivPullUp;
	private TextView tvLeft;
	private TextView tvRight;
	private FrameLayout rlTop;
	private boolean isDown = true;
	private RelativeLayout rlPullUp;
	private boolean isMoreView;
	private TextView tvTip;
	boolean haveChildView;

	public PullUpView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		TypedArray a = context
				.obtainStyledAttributes(attrs, R.styleable.pullup);

		LayoutInflater inflater = LayoutInflater.from(context);
		rlTop = (FrameLayout) inflater.inflate(R.layout.menu_item_top_view,
				null);
		ivPullUp = (ImageView) rlTop.findViewById(R.id.ivPullUp);
		tvTip=(TextView)rlTop.findViewById(R.id.tvTip);
		tvLeft = (TextView) rlTop.findViewById(R.id.tvLeft);
		tvRight = (TextView) rlTop.findViewById(R.id.tvRight);
		rlPullUp=(RelativeLayout)rlTop.findViewById(R.id.rlPullUp);

		CharSequence leftText = a.getText(R.styleable.pullup_leftText);
		CharSequence rightText = a.getText(R.styleable.pullup_rightText);
		int leftVisible = a.getInteger(R.styleable.pullup_leftTextVisible, 1);
		int rightVisible = a.getInteger(R.styleable.pullup_rightTextVisible, 1);
		int moreVisible = a.getInteger(R.styleable.pullup_moreVisible, 1);
		int childView=a.getInteger(R.styleable.pullup_haveChildView,0);
		int moreView=a.getInteger(R.styleable.pullup_isMoreView,0);
		int tipVisible=a.getInteger(R.styleable.pullup_tipVisible,0);
		CharSequence tipText=a.getText(R.styleable.pullup_tipText);




		if (tipVisible==1){
			tvTip.setVisibility(View.VISIBLE);
			tvTip.setText(tipText);
		}else{
			tvTip.setVisibility(GONE);
		}

		if (childView==0){
			haveChildView=false;
		}else{
			haveChildView=true;
			hideViews();
		}
		// int topBackground=a.getInteger(R.styleable.pullup_topBackground,
		// R.drawable.edit_input_default);

		// rlTop.setBackgroundResource(topBackground);
		ivPullUp.setBackgroundResource(R.drawable.btn_arrow_down_default);
		if (moreVisible == 0) {
			ivPullUp.setVisibility(View.INVISIBLE);
		} else {
			ivPullUp.setVisibility(View.VISIBLE);
			//rlPullUp.setOnClickListener(this);
		}
		tvLeft.setText(leftText);
		if (leftVisible == 0) {
			tvLeft.setVisibility(View.INVISIBLE);
		} else {
			tvLeft.setVisibility(View.VISIBLE);
		}
		ivPullUp.setOnClickListener(this);
		rlTop.setOnClickListener(this);

		tvRight.setText(rightText);
		if (rightVisible == 0) {
			tvRight.setVisibility(View.INVISIBLE);
		} else {
			tvRight.setVisibility(View.VISIBLE);
		}


		if (moreView==0){
			isMoreView=false;
			ivPullUp.setBackgroundResource(R.drawable.btn_arrow_down_default);
		}else{
			isMoreView=true;
			ivPullUp.setBackgroundResource(R.drawable.btn_arrow_right_default);
		}

		setOrientation(VERTICAL);

		layoutCenter();

/*		LayoutParams p=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		p.gravity= Gravity.VERTICAL_GRAVITY_MASK;*/
		//setBackgroundColor(getResources().getColor(R.color.blue));
		isDown =true;
		addView(rlTop, 0);

		a.recycle();

	}





	private void layoutCenter() {
		LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		params.gravity=Gravity.CENTER_VERTICAL;

		rlTop.setLayoutParams(params);
	}


	public void setTipText(String text){
		tvTip.setVisibility(VISIBLE);
		tvTip.setText(text);
	}

	public void setRightText(String text) {
		tvRight.setVisibility(View.VISIBLE);
		tvRight.setText(text);
	}
	
	public void setTextSize(){
		tvLeft.setTextSize(15);
		tvRight.setTextSize(15);
	}



	public void setLeftText(String text) {
		tvLeft.setText(text);
	}

	private void showViews() {
		for (int i = 1; i < getChildCount(); i++) {
			getChildAt(i).setVisibility(View.VISIBLE);
		}
	}

	private void hideViews() {
		for (int i = 1; i < getChildCount(); i++) {
			getChildAt(i).setVisibility(View.GONE);
		}
	}

	public void setEnable(boolean enable){
		if(enable){
			this.setEnabled(true);
			rlTop.setEnabled(true);
			ivPullUp.setEnabled(true);
			tvLeft.setTextColor(getResources().getColor(R.color.black));
			tvRight.setTextColor(getResources().getColor(R.color.black));
		}else{
			this.setEnabled(false);
			rlTop.setEnabled(false);
			ivPullUp.setEnabled(false);

			tvLeft.setTextColor(getResources().getColor(R.color.person_setting_disable_color));
			tvRight.setTextColor(getResources().getColor(R.color.person_setting_disable_color));
		}
	}

	private IListener listener;
	public interface IListener{
		public void onClick();
	}

	public void setListener(IListener listener){
		this.listener=listener;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (!isMoreView) {


			if (isDown) {
				ivPullUp.setBackgroundResource(R.drawable.btn_arrow_up_default);
				Log.e("star","haveChildView..."+haveChildView);
				if (haveChildView) {
					showViews();
				}
			} else {
				ivPullUp.setBackgroundResource(R.drawable.btn_arrow_down_default);

				Log.e("star","haveChildView*****"+haveChildView);
				if (haveChildView) {
					hideViews();
				}

			}
		}
		isDown = !isDown;
		if (listener != null) {
			listener.onClick();
		}

	}


	public void hideChild(){
		isDown =true;
		ivPullUp.setBackgroundResource(R.drawable.btn_arrow_down_default);
		hideViews();
	}

}

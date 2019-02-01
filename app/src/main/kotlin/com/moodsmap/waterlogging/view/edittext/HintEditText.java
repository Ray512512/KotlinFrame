package com.moodsmap.waterlogging.view.edittext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moodsmap.waterlogging.R;

/**
 * 
 * @author
 * 自动提示剩余输入字数
 *
 */
public class HintEditText extends RelativeLayout implements TextWatcher{
	
 
	private EditText mEditText;
	private TextView mTextView;
	private int maxLength=0;
	private RelativeLayout mRelativeLayout;
	@SuppressLint("NewApi") public HintEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		
	}
	public HintEditText(Context context) {
		super(context);
		
		
	}
	public HintEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray mTypedArray=context.obtainStyledAttributes(attrs, R.styleable.HintEditText);
		maxLength=mTypedArray.getInt(R.styleable.HintEditText_maxLength, 0);
		mRelativeLayout=(RelativeLayout)LayoutInflater.from(context).inflate(R.layout.hint_edittext, this,true);
		mEditText=(EditText)mRelativeLayout.findViewById(R.id.edit);
		mTextView=(TextView)mRelativeLayout.findViewById(R.id.text);
		mTextView.setHint("0/"+maxLength);
		//限定最多可输入多少字符
		mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)}); 
		mEditText.addTextChangedListener(this);
		
	}


	public String getContent(){
		return mEditText.getText().toString();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		mTextView.setHint(s.toString().length()+"/"+maxLength);
	}
	@Override
	public void afterTextChanged(Editable s) {

	}
	
 
}

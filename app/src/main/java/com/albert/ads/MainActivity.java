package com.albert.ads;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.mopub.nativeads.AdapterHelper;
import com.mopub.nativeads.MoPubNative;
import com.mopub.nativeads.MoPubStaticNativeAdRenderer;
import com.mopub.nativeads.NativeAd;
import com.mopub.nativeads.NativeErrorCode;
import com.mopub.nativeads.ViewBinder;

public class MainActivity extends AppCompatActivity {
    private static final String MY_AD_UNIT_ID = "4e62e1e56c924885bd228716ca626cd9";
    private MoPubNative mMoPubNative;
    private ViewBinder mViewBinder;
    private RelativeLayout mRelativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);
        loadMopubAds();
    }

    private void loadMopubAds(){
        mMoPubNative = new MoPubNative(getApplicationContext(), MY_AD_UNIT_ID, new MoPubNative.MoPubNativeNetworkListener() {
            @Override
            public void onNativeLoad(NativeAd nativeAd) {
                Log.v("albert","onNativeLoad");
                AdapterHelper adapterHelper = new AdapterHelper(getApplicationContext(),0,5);
                mRelativeLayout.addView(adapterHelper.getAdView(null,mRelativeLayout,nativeAd,mViewBinder));
            }

            @Override
            public void onNativeFail(NativeErrorCode errorCode) {
                Log.v("albert","onNativeFail");
            }
        });

        mViewBinder = new ViewBinder.Builder(R.layout.mopub_native_ad_layout)
                .mainImageId(R.id.native_ad_main_image)
                .iconImageId(R.id.native_ad_icon_image)
                .titleId(R.id.native_ad_title)
                .textId(R.id.native_ad_text)
                .privacyInformationIconImageId(R.id.native_ad_privacy_information_icon_image)
                .build();

        MoPubStaticNativeAdRenderer moPubStaticNativeAdRenderer = new MoPubStaticNativeAdRenderer(mViewBinder);
        mMoPubNative.registerAdRenderer(moPubStaticNativeAdRenderer);
        mMoPubNative.makeRequest();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mMoPubNative.destroy();
        super.onDestroy();
    }
}

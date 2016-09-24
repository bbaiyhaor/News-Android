package com.ihandy.a2014011419.news;

import android.app.Activity;
import android.view.Gravity;

import com.ihandy.a2014011419.R;
import com.ihandy.a2014011419.view.SubFv;

/**
 * Created by byr on 2016/9/3.
 */
public class NewsDetail {
    public static void showDetail(Activity activity, NewsItem thisNews){
        int choose = (int) (Math.random() * 3.9999999999);
        if (choose == 0) {
            SubFv.SubBuilder sb = (SubFv.SubBuilder) new SubFv.SubBuilder(activity)
                    .theme(R.style.RedTheme)
                    .titleDefault(thisNews.getTitle())
                    .webViewBuiltInZoomControls(true)
                    .webViewDisplayZoomControls(true)
                    .dividerHeight(0)
                    .gradientDivider(false)
                    .setCustomAnimations(R.anim.activity_open_enter, R.anim.activity_open_exit,
                            R.anim.activity_close_enter, R.anim.activity_close_exit)
                    .injectJavaScript("javascript: document.getElementById('msg').innerHTML='Hello "
                            + "TheFinestArtist"
                            + "!';");
            sb.show(thisNews);
        }

        if (choose == 1) {
            SubFv.SubBuilder sb = (SubFv.SubBuilder) new SubFv.SubBuilder(activity)
                    .theme(R.style.FinestWebViewTheme)
                    .titleDefault(thisNews.getTitle())
                    .showUrl(false)
                    .statusBarColorRes(R.color.bluePrimaryDark)
                    .toolbarColorRes(R.color.bluePrimary)
                    .titleColorRes(R.color.finestWhite)
                    .urlColorRes(R.color.bluePrimaryLight)
                    .iconDefaultColorRes(R.color.finestWhite)
                    .progressBarColorRes(R.color.finestWhite)
                    .stringResCopiedToClipboard(R.string.copied_to_clipboard)
                    .stringResCopiedToClipboard(R.string.copied_to_clipboard)
                    .stringResCopiedToClipboard(R.string.copied_to_clipboard)
                    .showSwipeRefreshLayout(true)
                    .swipeRefreshColorRes(R.color.bluePrimaryDark)
                    .menuSelector(R.drawable.selector_light_theme)
                    .menuTextGravity(Gravity.CENTER)
                    .menuTextPaddingRightRes(R.dimen.defaultMenuTextPaddingLeft)
                    .dividerHeight(0)
                    .gradientDivider(false)
                    .setCustomAnimations(R.anim.slide_up, R.anim.hold, R.anim.hold, R.anim.slide_down);
            sb.show(thisNews);

        }


        if (choose == 2) {
            SubFv.SubBuilder sb = (SubFv.SubBuilder) new SubFv.SubBuilder(activity)
                    .theme(R.style.FinestWebViewTheme)
                    .titleDefault(thisNews.getTitle())
                    .toolbarScrollFlags(0)
                    .statusBarColorRes(R.color.blackPrimaryDark)
                    .toolbarColorRes(R.color.blackPrimary)
                    .titleColorRes(R.color.finestWhite)
                    .urlColorRes(R.color.blackPrimaryLight)
                    .iconDefaultColorRes(R.color.finestWhite)
                    .progressBarColorRes(R.color.finestWhite)
                    .swipeRefreshColorRes(R.color.blackPrimaryDark)
                    .menuSelector(R.drawable.selector_light_theme)
                    .menuTextGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT)
                    .menuTextPaddingRightRes(R.dimen.defaultMenuTextPaddingLeft)
                    .dividerHeight(0)
                    .gradientDivider(false)
                    //                    .setCustomAnimations(R.anim.slide_up, R.anim.hold, R.anim.hold, R.anim.slide_down)
                    .setCustomAnimations(R.anim.slide_left_in, R.anim.hold, R.anim.hold,
                            R.anim.slide_right_out);
                    //                    .setCustomAnimations(R.anim.fade_in_fast, R.anim.fade_out_medium, R.anim.fade_in_medium, R.anim.fade_out_fast)
                    // .disableIconBack(true)
                    // .disableIconClose(true)
                    // .disableIconForward(true)
                    // .disableIconMenu(true)
            sb.show(thisNews);

        }

        if (choose == 3) {
            SubFv.SubBuilder sb = (SubFv.SubBuilder) new SubFv.SubBuilder(activity)
                    .titleDefault(thisNews.getTitle());
            sb.show(thisNews);
        }
    }
}

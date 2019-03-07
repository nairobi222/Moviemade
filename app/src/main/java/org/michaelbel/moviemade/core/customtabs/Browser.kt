package org.michaelbel.moviemade.core.customtabs

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.preference.PreferenceManager
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_BROWSER

object Browser {

    private const val REQUEST_CODE = 100

    fun openUrl(context: Context, url: String) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val inApp = preferences.getBoolean(KEY_BROWSER, true)

        if (inApp) {
            openInAppUrl(context, url)
        } else {
            openBrowserUrl(context, url)
        }
    }

    private fun openInAppUrl(context: Context, url: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, url)

        val pendingIntent = PendingIntent.getActivity(context, REQUEST_CODE, shareIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val shareIcon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_share)

        val builder = CustomTabsIntent.Builder()
        builder.addDefaultShareMenuItem()
        builder.setShowTitle(true)
        builder.setToolbarColor(ContextCompat.getColor(context, R.color.primary))
        builder.setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.primary))
        builder.setActionButton(shareIcon, context.getString(R.string.share_link), pendingIntent, true)

        val intent = builder.build()
        intent.launchUrl(context, url.toUri())

        /*val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.putExtra("android.support.customtabs.extra.SESSION", null as Parcelable?)
        intent.putExtra("android.support.customtabs.extra.TOOLBAR_COLOR", ViewUtil.getAttrColor(context, R.attr.colorPrimary))
        intent.putExtra("android.support.customtabs.extra.TITLE_VISIBILITY", 1)
        val actionIntent = Intent(Intent.ACTION_SEND)
        actionIntent.type = "text/plain"
        actionIntent.putExtra(Intent.EXTRA_TEXT, Uri.parse(url).toString())
        actionIntent.putExtra(Intent.EXTRA_SUBJECT, "")
        val pendingIntent = PendingIntent.getActivity(context, 0, actionIntent, PendingIntent.FLAG_ONE_SHOT)
        val bundle = Bundle()
        bundle.putInt("android.support.customtabs.customaction.ID", 0)
        bundle.putParcelable("android.support.customtabs.customaction.ICON", BitmapFactory.decodeResource(context.resources, R.drawable.ic_share))
        bundle.putString("android.support.customtabs.customaction.DESCRIPTION", context.getString(R.string.share_link))
        bundle.putParcelable("android.support.customtabs.customaction.PENDING_INTENT", pendingIntent)
        intent.putExtra("android.support.customtabs.extra.ACTION_BUTTON_BUNDLE", bundle)
        intent.putExtra("android.support.customtabs.extra.TINT_ACTION_BUTTON", false)
        intent.putExtra(android.provider.Browser.EXTRA_APPLICATION_ID, context.packageName)
        context.startActivity(intent)*/
    }

    private fun openBrowserUrl(context: Context, url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
    }
}
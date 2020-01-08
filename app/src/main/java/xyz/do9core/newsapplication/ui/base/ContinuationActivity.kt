package xyz.do9core.newsapplication.ui.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.resume

private typealias RequestCode = Int
private typealias ResultCode = Int
private typealias ActivityCoroutine = CancellableContinuation<ActivityResult>
private typealias ActivityResult = Pair<ResultCode, Intent?>

abstract class ContinuationActivity : AppCompatActivity() {

    private val requestMap by lazy { ConcurrentHashMap<RequestCode, ActivityCoroutine>() }

    suspend fun launchActivity(
        requestCode: RequestCode,
        intent: Intent,
        onCancel: ((Throwable?) -> Unit)? = null
    ): ActivityResult {
        if (requestMap.containsKey(requestCode)) {
            requestMap[requestCode]?.cancel()
            requestMap.remove(requestCode)
        }
        return suspendCancellableCoroutine { continuation ->
            continuation.invokeOnCancellation { onCancel?.invoke(it) }
            requestMap[requestCode] = continuation
            startActivityForResult(intent, requestCode)
        }
    }

    final override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        requestMap[requestCode]?.let {
            it.resume(resultCode to data)
            requestMap.remove(requestCode)
        }
    }
}

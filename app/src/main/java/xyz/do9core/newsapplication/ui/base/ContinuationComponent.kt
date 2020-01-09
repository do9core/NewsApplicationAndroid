package xyz.do9core.newsapplication.ui.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.resume

private typealias RequestCode = Int
private typealias ResultCode = Int
private typealias ActivityCoroutine = CancellableContinuation<ActivityResult>
private typealias ActivityResult = Pair<ResultCode, Intent?>

private val requestMap by lazy { ConcurrentHashMap<Identifier, ActivityCoroutine>() }

private interface ContinuationComponent

private fun ContinuationComponent.getIdentifier(requestCode: RequestCode): Identifier =
    Identifier(this.hashCode(), requestCode)

private data class Identifier(
    val hashCode: Int,
    val requestCode: RequestCode
)

abstract class ContinuationActivity : AppCompatActivity(), ContinuationComponent {

    suspend fun launchActivity(
        requestCode: RequestCode,
        intent: Intent,
        onCancel: ((Throwable?) -> Unit)? = null
    ): ActivityResult {
        val identifier = getIdentifier(requestCode)
        if (requestMap.containsKey(identifier)) {
            requestMap[identifier]?.cancel()
            requestMap.remove(identifier)
        }
        return suspendCancellableCoroutine { continuation ->
            continuation.invokeOnCancellation { onCancel?.invoke(it) }
            requestMap[identifier] = continuation
            startActivityForResult(intent, requestCode)
        }
    }

    final override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val identifier = getIdentifier(requestCode)
        requestMap[identifier]?.let {
            it.resume(resultCode to data)
            requestMap.remove(identifier)
        }
    }
}

abstract class ContinuationFragment : Fragment(), ContinuationComponent {

    suspend fun launchActivity(
        requestCode: RequestCode,
        intent: Intent,
        onCancel: ((Throwable?) -> Unit)? = null
    ): ActivityResult {
        val identifier = getIdentifier(requestCode)
        if (requestMap.containsKey(identifier)) {
            requestMap[identifier]?.cancel()
            requestMap.remove(identifier)
        }
        return suspendCancellableCoroutine { continuation ->
            continuation.invokeOnCancellation { onCancel?.invoke(it) }
            requestMap[identifier] = continuation
            startActivityForResult(intent, requestCode)
        }
    }

    final override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val identifier = getIdentifier(requestCode)
        requestMap[identifier]?.let {
            it.resume(resultCode to data)
            requestMap.remove(identifier)
        }
    }
}
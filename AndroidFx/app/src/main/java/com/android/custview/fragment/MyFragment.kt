package com.android.custview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.android.custview.R
import com.android.custview.utils.KLog

class MyLifecycleObserver(private val registry: ActivityResultRegistry) : DefaultLifecycleObserver {
    lateinit var getContent: ActivityResultLauncher<String>

    override fun onCreate(owner: LifecycleOwner) {
        getContent = registry.register("key", owner, ActivityResultContracts.GetContent(), ActivityResultCallback { uri ->
            KLog.logI(uri.toString())
        })
    }

    fun selectImage() {
        getContent.launch("image/*")
    }

}

class MyFragment : Fragment() {
    lateinit var observer: MyLifecycleObserver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observer = MyLifecycleObserver(requireActivity().activityResultRegistry)
        lifecycle.addObserver(observer)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val selectButton = view.findViewById<Button>(R.id.select_button)

        selectButton.setOnClickListener {
            observer.selectImage()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.my_fragment, container, false)
    }
}
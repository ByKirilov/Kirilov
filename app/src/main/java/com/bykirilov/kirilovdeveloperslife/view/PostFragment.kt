package com.bykirilov.kirilovdeveloperslife.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bykirilov.kirilovdeveloperslife.R
import com.bykirilov.kirilovdeveloperslife.databinding.FragmentPostBinding
import com.bykirilov.kirilovdeveloperslife.viewModel.PostViewModel

private const val ARG_PARAM1 = "fragmentName"

/**
 * A simple [Fragment] subclass.
 * Use the [PostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PostFragment : Fragment() {
    private var fragmentName: String? = null

    lateinit var binding: FragmentPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fragmentName = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = activity?.let { DataBindingUtil.setContentView(it, R.layout.fragment_post) }!!
        binding.viewModel = ViewModelProviders.of(this).get(PostViewModel::class.java)
        binding.executePendingBindings()

        binding.viewModel?.getNextPost()

        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onStart() {
        super.onStart()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param name Name of fragment.
         * @return A new instance of fragment PostFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(name: String) =
                PostFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, fragmentName)
                    }
                }
    }
}
package com.farshad.mytodo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.farshad.mytodo.databinding.FragmentHomeBinding
import com.farshad.mytodo.ui.BaseFragment

class HomeFragment:BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.itemEntityLiveData.observe(viewLifecycleOwner){itemEntityList ->

        }
    }







    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
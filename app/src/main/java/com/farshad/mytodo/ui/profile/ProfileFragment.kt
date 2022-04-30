package com.farshad.mytodo.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.farshad.mytodo.database.entity.ItemEntity
import com.farshad.mytodo.databinding.FragmentAddItemEntityBinding
import com.farshad.mytodo.databinding.FragmentProfileBinding
import com.farshad.mytodo.ui.BaseFragment
import com.farshad.mytodo.ui.add.AddItemEntityFragmentArgs

class ProfileFragment:BaseFragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }//FUN










    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

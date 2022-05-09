package com.farshad.mytodo.ui.colorPicker

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.farshad.mytodo.SharedPrefUtil
import com.farshad.mytodo.databinding.FragmentColorPickerBinding
import com.farshad.mytodo.ui.BaseFragment
import java.util.*

class ColorPickerFragment:BaseFragment() {

    private var _binding:FragmentColorPickerBinding?=null
    private val binding get() = _binding!!

    private val safeArgs:ColorPickerFragmentArgs by navArgs()
    private val viewModel: CustomColorPickerViewModel by viewModels()

    private class SeekBarListener(
        private val onProgressChange: (Int) -> Unit
    ) : SeekBar.OnSeekBarChangeListener {

        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            onProgressChange(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
            // Nothing to do
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            // Nothing to do
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentColorPickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setPriorityName(safeArgs.priorityName) { red, green, blue ->
            binding.redColorLayout.seekBar.progress = red
            binding.greenColorLayout.seekBar.progress = green
            binding.blueColorLayout.seekBar.progress = blue
        }

        binding.redColorLayout.apply {
            textView.text = "Red"
            seekBar.setOnSeekBarChangeListener(SeekBarListener(viewModel::onRedChange))
        }

        binding.greenColorLayout.apply {
            textView.text = "Green"
            seekBar.setOnSeekBarChangeListener(SeekBarListener(viewModel::onGreenChange))
        }

        binding.blueColorLayout.apply {
            textView.text = "Blue"
            seekBar.setOnSeekBarChangeListener(SeekBarListener(viewModel::onBlueChange))
        }

        viewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            binding.titleTextView.text = viewState.getFormattedTitle()

            val color = Color.rgb(viewState.red, viewState.green, viewState.blue)
            binding.colorView.setBackgroundColor(color)
        }

        binding.saveButton.setOnClickListener {
            val viewState = viewModel.viewStateLiveData.value ?: return@setOnClickListener
            val color = Color.rgb(viewState.red, viewState.green, viewState.blue)

            when (safeArgs.priorityName.toLowerCase(Locale.US)) {
                "low" -> SharedPrefUtil.setLowPriorityColor(color)
                "medium" -> SharedPrefUtil.setMediumPriorityColor(color)
                "high" -> SharedPrefUtil.setHighPriorityColor(color)
            }

            Toast.makeText(requireActivity(), "Saved!", Toast.LENGTH_SHORT).show()
            navigateUp()
        }


    }//FUN





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
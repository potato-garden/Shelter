package com.bangkit.shelter.ui.setting

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.bangkit.shelter.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onMode()
    }

    private fun onMode() {

        val sharedPref = activity?.getSharedPreferences("night", Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        editor?.putBoolean("Finished", true)
        editor?.apply()
        val booleanValue: Boolean = sharedPref!!.getBoolean("night_mode", false)
        if (booleanValue) {
            binding.switchCompat.text = "Dark Mode"
            binding.switchCompat.isChecked = true
        } else {
            binding.switchCompat.text = "Light Mode"
            binding.switchCompat.isChecked = false
        }


        binding.switchCompat.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked) {
                    binding.switchCompat.text = "Dark Mode"
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    binding.switchCompat.isChecked = true
                    editor?.putBoolean("night_mode", true)
                    editor?.apply()
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    binding.switchCompat.isChecked = false
                    editor?.putBoolean("night_mode", false)
                    editor?.apply()
                }
            }

        })
    }

}
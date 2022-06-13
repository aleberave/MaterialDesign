package ru.geekbrains.materialdesign.view.picture

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.geekbrains.materialdesign.R
import ru.geekbrains.materialdesign.databinding.FragmentBottomNavigationLayoutBinding

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomNavigationLayoutBinding? = null
    private val binding: FragmentBottomNavigationLayoutBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_one -> {
                    Log.i("@2", getString(R.string.onScreen1))
                    dismiss()
                }
                R.id.navigation_two -> {
                    Log.i("@2", getString(R.string.onScreen2))
                    dismiss()
                }
            }
            true
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = BottomNavigationDrawerFragment()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
package ru.geekbrains.materialdesign.view.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import ru.geekbrains.materialdesign.R
import ru.geekbrains.materialdesign.databinding.FragmentSettingsBinding
import ru.geekbrains.materialdesign.view.*

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding!!

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val context: Context =
            ContextThemeWrapper(requireActivity(), getRealStyleLocal(getCurrentThemeLocal()))
        val localInflater = inflater.cloneInContext(context)
        _binding = FragmentSettingsBinding.inflate(localInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        Toast.makeText(
                            requireContext(), tab.position.toString(), Toast.LENGTH_SHORT
                        ).show()
                        setCurrentTheme(ThemeRed)
                        requireActivity().recreate()
                        requireActivity().onBackPressed()
                    }
                    1 -> {
                        Toast.makeText(
                            requireContext(), tab.position.toString(), Toast.LENGTH_SHORT
                        ).show()
                        setCurrentTheme(ThemeBlue)
                        requireActivity().recreate()
                        requireActivity().onBackPressed()
                    }
                    2 -> {
                        Toast.makeText(
                            requireContext(), tab.position.toString(), Toast.LENGTH_SHORT
                        ).show()
                        setCurrentTheme(ThemeGreen)
                        requireActivity().recreate()
                        requireActivity().onBackPressed()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                //TODO("Not yet implemented")
            }

        })
    }

    fun setCurrentTheme(currentTheme: Int) {
        val sharedPreferences =
            requireActivity().getSharedPreferences(
                KEY_SP,
                AppCompatActivity.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_SP_CURRENT_THEME, currentTheme)
        editor.apply()
    }

    private fun getCurrentThemeLocal(): Int {
        val sharedPreferences =
            requireActivity().getSharedPreferences(
                KEY_SP,
                AppCompatActivity.MODE_PRIVATE
            )
        return sharedPreferences.getInt(KEY_SP_CURRENT_THEME, -1)
    }

    private fun getRealStyleLocal(currentTheme: Int): Int {
        return when (currentTheme) {
            ThemeRed -> R.style.MyRedStyle
            ThemeBlue -> R.style.MyBlueStyle
            ThemeGreen -> R.style.MyGreenStyle
            else -> 0
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
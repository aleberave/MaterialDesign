package ru.geekbrains.materialdesign.view.picture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import ru.geekbrains.materialdesign.R
import ru.geekbrains.materialdesign.databinding.FragmentPictureOfTheDayBinding
import ru.geekbrains.materialdesign.utils.pathWikipedia
import ru.geekbrains.materialdesign.view.MainActivity
import ru.geekbrains.materialdesign.view.settings.SettingsFragment
import ru.geekbrains.materialdesign.viewmodel.PictureOfTheDayAppState
import ru.geekbrains.materialdesign.viewmodel.PictureOfTheDayViewModel
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayFragment : Fragment() {

    private var isMain = true
    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding: FragmentPictureOfTheDayBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> {
                Log.i("@2", "app_bar_fav")
            }
            R.id.app_bar_settings -> {
                Log.i("@2", "app_bar_settings")
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance())
                    .addToBackStack(getString(R.string.empty)).commit()
            }
            android.R.id.home -> {
                BottomNavigationDrawerFragment.newInstance()
                    .show(requireActivity().supportFragmentManager, "@2")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            renderData(it)
        }
        viewModel.sendRequest()

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("$pathWikipedia${binding.inputEditText.text.toString()}")
            })
        }

        getBottomSheetBehavior()
        getActionBar()
        getFab()
        getChip()
    }

    private fun getChip() {
        binding.myChipGroup.setOnCheckedChangeListener { group, position ->
            val currentDate: String =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

            val arr = arrayOf(
                currentDate.substring(0, 2),
                currentDate.substring(3, 5),
                currentDate.substring(6, 10)
            )

            val newMyDate: String

            group.findViewById<Chip>(position)?.let {
                Log.i("@2", "${it.text}$position")
                when (position) { // TODO
                    R.id.today -> {
                        newMyDate = "${arr[2]}-${arr[1]}-${arr[0]}"
                        this.viewModel.sendRequest(newMyDate)
                    }
                    R.id.yesterday -> {
                        val number: Int = arr[0].toInt() - 1
                        newMyDate = if (number > 0) {
                            "${arr[2]}-${arr[1]}-$number"
                        } else {
                            "${arr[2]}-${arr[1]}-${arr[0]}"
                        }
                        this.viewModel.sendRequest(newMyDate)
                    }
                    R.id.tdby -> {
                        val number: Int = arr[0].toInt() - 2
                        newMyDate = if (number > 0) {
                            "${arr[2]}-${arr[1]}-$number"
                        } else {
                            "${arr[2]}-${arr[1]}-${arr[0]}"
                        }
                        this.viewModel.sendRequest(newMyDate)
                    }
                }
            }
        }
    }

    private fun getFab() {
        binding.fab.setOnClickListener {
            if (isMain) {
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_back_fab
                    )
                )
//                binding.bottomAppBar.replaceMenu(//TODO какое-то другое меню)
            } else {
                binding.bottomAppBar.navigationIcon = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_hamburger_menu_bottom_bar
                )
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_plus_fab
                    )
                )
//                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)//TODO показать меню
            }
            isMain = !isMain
        }
    }

    private fun getActionBar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)
    }

    private fun getBottomSheetBehavior() {
        val bottomSheetBehavior =
            BottomSheetBehavior.from(binding.containerBottomSheetLayout.bottomSheetContainer)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {}
                    BottomSheetBehavior.STATE_DRAGGING -> {}
                    BottomSheetBehavior.STATE_EXPANDED -> {}
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {}
                    BottomSheetBehavior.STATE_HIDDEN -> {}
                    BottomSheetBehavior.STATE_SETTLING -> {}
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.i("ProgressInPercentage", slideOffset.toString())
            }
        })
    }

    private fun renderData(pictureOfTheDayAppState: PictureOfTheDayAppState) {
        when (pictureOfTheDayAppState) {
            is PictureOfTheDayAppState.Error -> TODO()
            is PictureOfTheDayAppState.Loading -> {}
            is PictureOfTheDayAppState.Success -> {
                binding.imageView.load(pictureOfTheDayAppState.pictureOfTheDayResponseData.url)
                binding.containerBottomSheetLayout.title.text =
                    pictureOfTheDayAppState.pictureOfTheDayResponseData.title
                binding.containerBottomSheetLayout.explanation.text =
                    pictureOfTheDayAppState.pictureOfTheDayResponseData.explanation
                binding.containerBottomSheetLayout
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PictureOfTheDayFragment()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
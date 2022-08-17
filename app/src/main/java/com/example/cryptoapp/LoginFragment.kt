package com.example.cryptoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.cryptoapp.databinding.FragmentLoginBinding
import com.example.cryptoapp.viewModels.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {
    private val viewModel by viewModels<LoginViewModel>()

    private var _binding: FragmentLoginBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel.username.value = ""
        binding.loginButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                //TheMovieDBRepository().apply {
                val nameObserver = Observer<String> { newName ->
                    // Update the UI, in this case, a TextView.

                }

                // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.

//                    val username = binding.textInputEditText.text.toString()
//                    val password = binding.passwordText.text.toString()
//                    val credentials =
//                        CredentialsModel("bogdipetrisor123", "parola123", requestToken().requestToken)
//                    val login = login(credentials)
//                    if (login.success) {
//                        val homeScreenFragment = SecondFragment()
//                        activity?.supportFragmentManager?.beginTransaction()?.apply {
//                        }
//                            ?.replace(
//                                R.id.fragment_container_view_tag,
//                                homeScreenFragment,
//                                "findThisFragment"
//                            )
//                            ?.addToBackStack(null)
//                            ?.commit()
//                    }
                //}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
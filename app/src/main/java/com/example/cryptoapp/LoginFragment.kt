package com.example.cryptoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.cryptoapp.databinding.FragmentLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                TheMovieDBRepository().apply {
                    println("SUNT AICI")
                    val username = binding.textInputEditText.text.toString()
                    val password = binding.passwordText.text.toString()
                    val token = requestToken()
                    println(token)
                    val credentials =
                        CredentialsModel(username, password, token.requestToken)
                    val login = login(credentials)
                    println(login)
                    if (login.success) {
                        val homeScreenFragment = SecondFragment()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(
                                R.id.fragment_container_view_tag,
                                homeScreenFragment,
                                "findThisFragment"
                            )
                            ?.addToBackStack(null)
                            ?.commit()
                    }
                    val sessionId = sessionId(login)
                    println(sessionId)
                    val delete = deleteSession(sessionId)
                    println(delete)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
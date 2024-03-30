package com.nurlanamirzayeva.gamejet.network.repositories

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.nurlanamirzayeva.gamejet.utils.NetworkState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class SignInRepository @Inject constructor(private val auth: FirebaseAuth) {

    suspend fun signIn(email: String, password: String) = callbackFlow<NetworkState<Boolean>> {

        val successListener = OnSuccessListener<AuthResult> {
            trySend(NetworkState.Success(true))
        }

        val failureListener = object : OnFailureListener {

            override fun onFailure(p0: Exception) {
                trySend(NetworkState.Error(p0.localizedMessage))
            }

        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener(successListener)
            .addOnFailureListener(failureListener)



        awaitClose {
            channel.close()
        }


    }


}
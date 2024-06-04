package com.nurlanamirzayeva.gamejet.network.repositories

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.nurlanamirzayeva.gamejet.utils.EMAIL
import com.nurlanamirzayeva.gamejet.utils.NetworkState
import com.nurlanamirzayeva.gamejet.utils.PASSWORD
import com.nurlanamirzayeva.gamejet.utils.UID
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class Repository @Inject constructor(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) {

    private val database = fireStore.collection("users")

    suspend fun signUp(userMap: HashMap<String, String>) = callbackFlow<NetworkState<Boolean>> {

        val password = userMap[PASSWORD]
        val email = userMap[EMAIL]

        val successListener = OnCompleteListener<Void> {
            if (it.isSuccessful) {

                trySend(NetworkState.Success(true))
            } else {
                trySend(NetworkState.Error(errorMessage = null))
            }

        }

        val failureListener = object : OnFailureListener {
            override fun onFailure(p0: Exception) {
                trySend(NetworkState.Error(p0.localizedMessage))
            }
        }
        auth.createUserWithEmailAndPassword(email!!, password!!).addOnSuccessListener {
            Log.d("TAG", "signUp: Success")
            val userId = it.user?.uid
            userId?.let { uid ->
                userMap[UID] = uid
                database.document(uid).set(userMap)
                    .addOnFailureListener(failureListener).addOnCompleteListener { emailVerify ->
                        if (emailVerify.isSuccessful) {
                            it.user?.sendEmailVerification()?.addOnCompleteListener { emailTask ->
                                if (emailTask.isSuccessful) {
                                    trySend(NetworkState.Success(true))
                                } else {
                                    trySend(NetworkState.Error(emailTask.exception?.localizedMessage))
                                }

                            }
                        } else {
                            trySend(NetworkState.Error(emailVerify.exception?.localizedMessage))
                        }

                    }
            }
        }.addOnFailureListener {
            trySend(NetworkState.Error(it.localizedMessage))
        }

        awaitClose {
            channel.close()
        }


    }


    suspend fun signIn(email: String, password: String) = callbackFlow<NetworkState<Boolean>> {

        val successListener = OnSuccessListener<AuthResult> {authResult->
            val user=authResult.user
            if(user!=null && user.isEmailVerified){
                trySend(NetworkState.Success(true))
            }else{
                trySend(NetworkState.Error("Email not verified. Please verify your email."))
                auth.signOut()
            }

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

    suspend fun resetPassword(email: String) = callbackFlow<NetworkState<Boolean>> {
        val completeListener = OnCompleteListener<Void> {
            if (it.isSuccessful)
                trySend(NetworkState.Success(true))
            else
                trySend(NetworkState.Error(errorMessage = null))
        }

        val failureListener = object : OnFailureListener {
            override fun onFailure(p0: java.lang.Exception) {
                trySend(NetworkState.Error(p0.localizedMessage))
            }

        }

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(completeListener)
            .addOnFailureListener(failureListener)


        awaitClose {
            channel.close()
        }

    }

}
package com.nurlanamirzayeva.gamejet.network.repositories

import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nurlanamirzayeva.gamejet.utils.EMAIL
import com.nurlanamirzayeva.gamejet.utils.NetworkState
import com.nurlanamirzayeva.gamejet.utils.PASSWORD
import com.nurlanamirzayeva.gamejet.utils.UID
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class SignUpRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) {

    private val database = fireStore.collection("users")

    suspend fun signUp(userMap: HashMap<String, String>) = callbackFlow {

        val password = userMap[PASSWORD]
        val email = userMap[EMAIL]

        val successListener = OnSuccessListener<Void> {
            trySend(NetworkState.Success(true))
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
                    .addOnFailureListener(failureListener).addOnSuccessListener(successListener)
            }
        }.addOnFailureListener {
            Log.d("TAG", "signUp: Failure -> ${it.localizedMessage}")
        }


        awaitClose {
            channel.close()
        }


    }


}
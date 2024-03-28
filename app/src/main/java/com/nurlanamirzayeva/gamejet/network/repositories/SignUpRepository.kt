package com.nurlanamirzayeva.gamejet.network.repositories

import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore
import com.nurlanamirzayeva.gamejet.utils.COUNTRY_CODE
import com.nurlanamirzayeva.gamejet.utils.EMAIL
import com.nurlanamirzayeva.gamejet.utils.NetworkState
import com.nurlanamirzayeva.gamejet.utils.PASSWORD
import com.nurlanamirzayeva.gamejet.utils.UID
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.util.Locale.IsoCountryCode
import java.util.UUID

class SignUpRepository {
    private val auth = FirebaseAuth.getInstance()
    private val database = Firebase.firestore.collection("users")

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
                userMap[UID]=uid
                database.document(uid).set(userMap).addOnFailureListener(failureListener).addOnSuccessListener(successListener)
            }
        }.addOnFailureListener {
            Log.d("TAG", "signUp: Failure -> ${it.localizedMessage}")
        }


        awaitClose {
            channel.close()
        }


    }


}
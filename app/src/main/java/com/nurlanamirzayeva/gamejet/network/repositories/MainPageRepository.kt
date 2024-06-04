package com.nurlanamirzayeva.gamejet.network.repositories

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.nurlanamirzayeva.gamejet.model.DiscoverResponse
import com.nurlanamirzayeva.gamejet.model.ProfileItemDTO
import com.nurlanamirzayeva.gamejet.model.TrendingResponse
import com.nurlanamirzayeva.gamejet.model.UpcomingResponse
import com.nurlanamirzayeva.gamejet.network.ApiService
import com.nurlanamirzayeva.gamejet.room.FavoriteFilm
import com.nurlanamirzayeva.gamejet.room.FavoriteFilmDao
import com.nurlanamirzayeva.gamejet.utils.NetworkState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import retrofit2.Response
import javax.inject.Inject


class MainPageRepository @Inject constructor(
    private val apiService: ApiService,
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val favoriteFilmDao: FavoriteFilmDao
) {
    suspend fun getMovies(page: Int): Response<DiscoverResponse> = apiService.getMoviesByPage(page)
    suspend fun getTrendingNow(page: Int): Response<TrendingResponse> =
        apiService.getTrendingNow(page)

    suspend fun getUpcomingMovies(page: Int): Response<UpcomingResponse> =
        apiService.getUpcomingMovies(page)

    suspend fun getSearchMovies(page: Int, query: String): Response<DiscoverResponse> =
        apiService.getSearchMovies(page, query)

    suspend fun getUserData(userId: String) = callbackFlow<NetworkState<ProfileItemDTO>> {
        val userDataRef = fireStore.collection("users").document(userId)


        userDataRef.get().addOnCompleteListener { userDataSnapshot ->

            if (userDataSnapshot.isSuccessful) {
                val name = userDataSnapshot.result.getString("user_name")
                val email = userDataSnapshot.result.getString("email")
                val profileImage = userDataSnapshot.result.getString("profileImage")

                trySend(NetworkState.Success(ProfileItemDTO(name, email, profileImage)))
            } else {
                trySend(NetworkState.Error(errorMessage = null))

            }

        }.addOnFailureListener {
            trySend(NetworkState.Error(it.localizedMessage))
        }

        awaitClose {
            channel.close()
        }

    }


    suspend fun updateUserEmailAndName(
        name: String,
        email: String,

    ) = callbackFlow<NetworkState<Boolean>> {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            trySend(NetworkState.Error("User not authenticated"))
            awaitClose { channel.close() }
            return@callbackFlow
        }

        val userId = currentUser.uid
        val userMap = hashMapOf("user_name" to name, "email" to email)

        try {
            if (email != currentUser.email) {
                currentUser.verifyBeforeUpdateEmail(email).addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        trySend(NetworkState.Error("Email update verification failed"))
                    }
                }
            }

            fireStore.collection("users").document(userId).set(userMap).addOnSuccessListener {
                trySend(NetworkState.Success(true))
            }.addOnFailureListener {
                trySend(NetworkState.Error(it.localizedMessage))
            }
        } catch (e: Exception) {
            trySend(NetworkState.Error(e.localizedMessage))
        }
        awaitClose { channel.close() }
    }



    suspend fun updateUserPassword(newPassword: String, newConfirmPassword: String) = callbackFlow<NetworkState<Boolean>> {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            trySend(NetworkState.Error("User not authenticated"))
            awaitClose { channel.close() }
            return@callbackFlow
        }

        val userId = currentUser.uid

        try {
            if (newPassword.isNotEmpty() && newPassword == newConfirmPassword) {
                currentUser.updatePassword(newPassword)

                fireStore.collection("users").document(userId)
                    .update("password", newPassword, "confirm_password", newConfirmPassword)
                    .addOnSuccessListener {
                        trySend(NetworkState.Success(true))
                    }
                    .addOnFailureListener {
                        trySend(NetworkState.Error(it.localizedMessage))
                    }
            } else {
                trySend(NetworkState.Error("Passwords do not match or are empty"))
            }
        } catch (e: Exception) {
            trySend(NetworkState.Error(e.localizedMessage))
        }

        awaitClose { channel.close() }
    }

    suspend fun addFavoriteFilms(film: FavoriteFilm) = callbackFlow<NetworkState<Boolean>> {
        val userId = auth.currentUser?.uid
        if (userId != null) {

            fireStore.collection("users").document(userId).collection("favorites")
                .document(film.id.toString())
                .set(film).addOnCompleteListener {
                    trySend(NetworkState.Success(true))

                }.addOnFailureListener {
                    trySend(NetworkState.Error(it.localizedMessage))
                }
        } else {
            trySend(NetworkState.Error(null))
        }

        awaitClose {
            channel.close()
        }

    }


    suspend fun addHistory(film: FavoriteFilm) = callbackFlow<NetworkState<Boolean>> {
        val userId = auth.currentUser?.uid
        if (userId != null) {

            fireStore.collection("users").document(userId).collection("history")
                .document(film.id.toString())
                .set(film).addOnCompleteListener {
                    trySend(NetworkState.Success(true))

                }.addOnFailureListener {
                    trySend(NetworkState.Error(it.localizedMessage))
                }
        } else {
            trySend(NetworkState.Error(null))
        }

        awaitClose {
            channel.close()
        }

    }


    suspend fun getFavoriteFilms(id: Int) = callbackFlow<NetworkState<List<FavoriteFilm>>> {
        val userId = auth.currentUser?.uid

        if (userId != null) {
            fireStore.collection("users").document(userId).collection("favorites").get()
                .addOnCompleteListener {
                    val listOfFavorites = mutableListOf<FavoriteFilm>()
                    if (it.isSuccessful) {
                        for (document in it.result) {
                            val movie = document.toObject(FavoriteFilm::class.java)
                            listOfFavorites.add(movie)
                        }

                        trySend(NetworkState.Success(listOfFavorites))
                    } else {
                        trySend(NetworkState.Success(emptyList()))
                    }
                }.addOnFailureListener {
                    trySend(NetworkState.Error(it.localizedMessage))
                }
        } else {
            trySend(NetworkState.Error(null))
        }

        awaitClose {
            channel.close()
        }
    }


    suspend fun getHistory(id: Int) = callbackFlow<NetworkState<List<FavoriteFilm>>> {
        val userId = auth.currentUser?.uid

        if (userId != null) {
            fireStore.collection("users").document(userId).collection("history").get()
                .addOnCompleteListener {
                    val listOfHistory = mutableListOf<FavoriteFilm>()
                    if (it.isSuccessful) {
                        for (document in it.result) {
                            val movie = document.toObject(FavoriteFilm::class.java)
                            listOfHistory.add(movie)
                        }

                        trySend(NetworkState.Success(listOfHistory))
                    } else {
                        trySend(NetworkState.Success(emptyList()))
                    }
                }.addOnFailureListener {
                    trySend(NetworkState.Error(it.localizedMessage))
                }
        } else {
            trySend(NetworkState.Error(null))
        }

        awaitClose {
            channel.close()
        }
    }


    suspend fun removeFavoriteFilm(id: Int) = callbackFlow<NetworkState<Boolean>> {
        val userId = auth.currentUser?.uid

        if (userId != null) {
            fireStore.collection("users").document(userId).collection("favorites")
                .document(id.toString()).delete()
                .addOnSuccessListener {
                    trySend(NetworkState.Success(true))
                }.addOnFailureListener {
                    trySend(NetworkState.Error(it.localizedMessage))
                }
        } else {
            trySend(NetworkState.Error(null))
        }

        awaitClose {
            channel.close()
        }
    }

    suspend fun removeHistory(id: Int) = callbackFlow<NetworkState<Boolean>> {
        val userId = auth.currentUser?.uid

        if (userId != null) {
            fireStore.collection("users").document(userId).collection("history")
                .document(id.toString()).delete()
                .addOnSuccessListener {
                    trySend(NetworkState.Success(true))
                }.addOnFailureListener {
                    trySend(NetworkState.Error(it.localizedMessage))
                }
        } else {
            trySend(NetworkState.Error(null))
        }

        awaitClose {
            channel.close()
        }
    }


    suspend fun addFavoriteLocal(film: FavoriteFilm) {
        favoriteFilmDao.insertFavoriteFilm(film)
    }

   suspend fun getFavoriteLocal(userId: String):List<FavoriteFilm> {
         return favoriteFilmDao.getFavoritesFilms(userId)
    }



    suspend fun removeFavoriteLocal(film: FavoriteFilm) {
        favoriteFilmDao.deleteFavoriteFilm(film)

    }

    suspend fun checkFavoriteFilm(id: Int) = callbackFlow<NetworkState<Boolean>> {
        val userId = auth.currentUser?.uid

        if (userId != null) {
            fireStore.collection("users").document(userId).collection("favorites")
                .document(id.toString()).get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        trySend(NetworkState.Success(true))
                    } else {
                        trySend(NetworkState.Success(false))
                    }
                }.addOnFailureListener {
                    trySend(NetworkState.Error(it.localizedMessage))
                }
        } else {
            trySend(NetworkState.Error(null))
        }

        awaitClose {
            channel.close()
        }
    }

    suspend fun checkHistoryFilm(id: Int) = callbackFlow<NetworkState<Boolean>> {
        val userId = auth.currentUser?.uid

        if (userId != null) {
            fireStore.collection("users").document(userId).collection("history")
                .document(id.toString()).get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        trySend(NetworkState.Success(true))
                    } else {
                        trySend(NetworkState.Success(false))
                    }
                }.addOnFailureListener {
                    trySend(NetworkState.Error(it.localizedMessage))
                }
        } else {
            trySend(NetworkState.Error(null))
        }

        awaitClose {
            channel.close()
        }
    }


    suspend fun uploadProfileImage(profileImageUri: Uri) = callbackFlow<NetworkState<String>> {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            trySend(NetworkState.Error("User not authenticated"))
            awaitClose { channel.close() }
            return@callbackFlow
        }

        val userId = currentUser.uid
        try {
            val storageReference =
                FirebaseStorage.getInstance().reference.child("profile_images/$userId")
            storageReference.putFile(profileImageUri)
                .addOnSuccessListener {
                    storageReference.downloadUrl.addOnSuccessListener { uri ->
                        val imageUrl = uri.toString()
                        val userRef = fireStore.collection("users").document(userId)
                        userRef.get().addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                if (task.result.exists()) {
                                    userRef.update("profileImage", imageUrl).addOnSuccessListener {
                                        trySend(NetworkState.Success(imageUrl))
                                    }.addOnFailureListener { exception ->
                                        trySend(
                                            NetworkState.Error(
                                                exception.localizedMessage
                                                    ?: "Failed to update profile image URL in Firestore"
                                            )
                                        )
                                    }
                                } else {
                                    val userMap = hashMapOf("profileImage" to imageUrl)
                                    userRef.set(userMap).addOnSuccessListener {
                                        trySend(NetworkState.Success(imageUrl))
                                    }.addOnFailureListener { exception ->
                                        trySend(
                                            NetworkState.Error(
                                                exception.localizedMessage
                                                    ?: "Failed to create user document with profile image URL in Firestore"
                                            )
                                        )
                                    }
                                }
                            } else {
                                trySend(
                                    NetworkState.Error(
                                        task.exception?.localizedMessage
                                            ?: "Failed to check if user document exists"
                                    )
                                )
                            }
                        }
                    }
                }
                .addOnFailureListener { e ->
                    trySend(NetworkState.Error(e.localizedMessage))
                }
        } catch (e: Exception) {
            trySend(NetworkState.Error(e.localizedMessage))
        }

        awaitClose { channel.close() }
    }


}
package com.nurlanamirzayeva.gamejet.model

import com.google.gson.annotations.SerializedName

data class ActorImageResponse(

	@field:SerializedName("profiles")
	val profiles: List<ProfilesItem?>? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class ProfilesItem(

	@field:SerializedName("aspect_ratio")
	val aspectRatio: Any? = null,

	@field:SerializedName("file_path")
	val filePath: String? = null,

	@field:SerializedName("vote_average")
	val voteAverage: Any? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("iso_639_1")
	val iso6391: Any? = null,

	@field:SerializedName("vote_count")
	val voteCount: Int? = null,

	@field:SerializedName("height")
	val height: Int? = null
)

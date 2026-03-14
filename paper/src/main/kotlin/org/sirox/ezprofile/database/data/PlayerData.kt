package org.sirox.ezprofile.database.data

import java.util.UUID

data class PlayerData(
    val uuid: UUID,
    var name: String,
    var likes: Int,
    var dislikes: Int
)

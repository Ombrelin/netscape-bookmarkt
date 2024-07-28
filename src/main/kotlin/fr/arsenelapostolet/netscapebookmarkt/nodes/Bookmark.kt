package fr.arsenelapostolet.netscapebookmarkt.nodes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("bookmark")
data class Bookmark(
    override val title: String,
    override val addDate: String? = null,
    override val lastModified: String? = null,
    val url: String? = null,
    val icon: String? = null,
    val iconUri: String? = null,
    val tags: List<String>? = emptyList()
) : NetScapeBookmarkNode
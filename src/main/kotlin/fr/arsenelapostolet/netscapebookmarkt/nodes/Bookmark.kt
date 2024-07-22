package fr.arsenelapostolet.netscapebookmarkt.nodes

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("bookmark")
data class Bookmark(
    override val title: String,
    override val addDate: String? = null,
    override val lastModified: String? = null,
    val url: String? = null,
    @SerialName("add_date")
    val iconUri: String? = null,
    val icon: String? = null
) : NetScapeBookmarkNode {
}
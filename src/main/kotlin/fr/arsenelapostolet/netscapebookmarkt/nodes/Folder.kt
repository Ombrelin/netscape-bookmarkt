package fr.arsenelapostolet.netscapebookmarkt.nodes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("folder")
data class Folder(
    override val title: String,
    val nsRoot: String?,
    override val addDate: String? = null,
    override val lastModified: String? = null,
    val children: List<NetScapeBookmarkNode> = emptyList()
) : NetScapeBookmarkNode {

}
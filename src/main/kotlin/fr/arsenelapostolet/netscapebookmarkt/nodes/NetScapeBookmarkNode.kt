package fr.arsenelapostolet.netscapebookmarkt.nodes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = Serializer.NetScapeBookmarkNodeSerializer::class)
interface NetScapeBookmarkNode {
    
    val title: String
    
    @SerialName("add_date")
    val addDate: String?
    
    @SerialName("last_modified")
    val lastModified: String?
}